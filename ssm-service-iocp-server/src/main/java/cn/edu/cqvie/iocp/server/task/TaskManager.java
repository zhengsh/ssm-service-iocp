package cn.edu.cqvie.iocp.server.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 消息发布线程
 *
 * @author ZHENG SHAOHONG
 */
public class TaskManager {

    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private static TaskManager instance = new TaskManager();

    private ExecutorService executors = Executors.newSingleThreadExecutor();

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        return instance;
    }

    public void submit(Runnable runnable) {
        try {
            executors.submit(runnable);
        } catch (Throwable t) {
            logger.error("task submit err {}", t);
        }

    }
}
