package cn.edu.cqvie.iocp.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ZEHNG SHAOHONG
 */
public class MessageClient {

    private static final Logger logger = LoggerFactory.getLogger(MessageClient.class);

    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();

    public static void main(String[] args) {
        ConnectManger messageClient = ConnectManger.getInstance();
        messageClient.start();
        logger.info("service start success !~");
        addHook(messageClient);
        //主线程阻塞等待，守护线程释放锁后退出
        try {
            LOCK.lock();
            STOP.await();
        } catch (InterruptedException e) {
            logger.warn(" service   stopped, interrupted by other thread!", e);
        } finally {
            LOCK.unlock();
        }
    }

    /**
     *
     * @param manger
     */
    private static void addHook(ConnectManger manger) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                manger.stop();
            } catch (Exception e) {
                logger.error("MessageClient stop exception ", e);
            }
            logger.info("jvm exit, all service stopped.");
            try {
                LOCK.lock();
                STOP.signal();
            } finally {
                LOCK.unlock();
            }
        }, "MessageClient-shutdown-hook"));
    }
}