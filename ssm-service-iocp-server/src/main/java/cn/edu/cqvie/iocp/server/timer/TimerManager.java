package cn.edu.cqvie.iocp.server.timer;

import cn.edu.cqvie.iocp.server.task.TaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 定时任务/操作补偿
 *
 * @author ZHENG SHAOHONG
 */
public class TimerManager {
    private static final Logger logger = LoggerFactory.getLogger(TaskManager.class);

    private static TimerManager instance = new TimerManager();

    private ExecutorService executors = Executors.newSingleThreadExecutor();

    private TimerManager() {

    }

    public void submit(HxTimerTask timerTask) {
        executors.submit(timerTask);
    }

    public static class HxTimerTask implements Runnable {

        @Override
        public void run() {

        }
    }
}
