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

    public static void main(String[] args) {
        new MessageClient().start();
    }

    public void start() {
        ConnectManger messageClient = ConnectManger.getInstance();
        messageClient.start();
        for (;;) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
        }, "MessageClient-shutdown-hook"));
    }
}