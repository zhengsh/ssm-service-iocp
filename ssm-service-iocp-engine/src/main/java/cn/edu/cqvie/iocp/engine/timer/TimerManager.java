package cn.edu.cqvie.iocp.engine.timer;

import cn.edu.cqvie.iocp.engine.pool.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时任务/操作补偿
 *
 * @author ZHENG SHAOHONG
 */
public class TimerManager {

    private static final Logger logger = LoggerFactory.getLogger(TimerManager.class);

    private static TimerManager instance = new TimerManager();

    private ScheduledExecutorService scheduledExecutorService =
            ThreadPool.newScheduledExecutor("timer");

    private TimerManager() {

    }

    public static TimerManager getInstance() {
        return instance;
    }

    public void submit(HxTimerTask timerTask) {
        submit(timerTask, 1000, 1000);
    }

    /**
     * 提交定时任务
     * @param timerTask    定时任务
     * @param initialDelay 初始化延迟，单位:ms
     * @param period       执行间隔，单位:ms
     */
    public void submit(HxTimerTask timerTask, int initialDelay, int period) {
        scheduledExecutorService.scheduleAtFixedRate(timerTask,initialDelay,period, TimeUnit.MILLISECONDS);

    }

    public static class HxTimerTask extends TimerTask {

        @Override
        public void run() {
            //todo 处理不在线的情况
            logger.info("//todo message task");
        }
    }


    public static void main(String[] args) {
        TimerManager.getInstance().submit(new HxTimerTask());
    }
}
