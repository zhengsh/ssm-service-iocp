package cn.edu.cqvie.iocp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZEHNG SHAOHONG
 */
@SuppressWarnings("ALL")
public class MessageClient {

    private static final Logger logger = LoggerFactory.getLogger(MessageClient.class);
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition stop = lock.newCondition();

    public static void main(String[] args) {
        new MessageClient().start();
    }

    public void start() {
        ConnectManger messageClient = ConnectManger.getInstance();
        messageClient.start();
        logger.info("service start success !~");
        addHook(messageClient);
        //主线程阻塞等待，守护线程释放锁后退出
        lock.lock();
        try {
            //noinspection AlibabaLockShouldWithTryFinally
            stop.await();
        } catch (InterruptedException e) {
            logger.warn(" service   stopped, interrupted by other thread!", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * @param manger
     */
    private void addHook(ConnectManger manger) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                manger.stop();
            } catch (Exception e) {
                logger.error("MessageClient stop exception ", e);
            }
            logger.info("jvm exit, all service stopped.");
            lock.lock();
            try {
                stop.signal();
            } finally {
                lock.unlock();
            }
        }, "MessageClient-shutdown-hook"));
    }
}
