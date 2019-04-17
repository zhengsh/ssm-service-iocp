package cn.edu.cqvie.iocp.simu;

import cn.edu.cqvie.iocp.client.MessageClient;
import cn.edu.cqvie.iocp.engine.pool.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟终端
 *
 * @author ZHENG SHAOHONG
 */
public class SimuTask {

    private static SimuTask instance = new SimuTask();

    private ExecutorService executorService = ThreadPool.newThreadExecutor("simu-task", 40);
    private final AtomicInteger connect = new AtomicInteger();


    private SimuTask() {
    }

    public static SimuTask getInstance() {
        return instance;
    }

    public synchronized int count() {
        return connect.get();
    }

    public synchronized void submit(MessageClient client) {
        connect.incrementAndGet();

        Runnable runnable = client::start;
        executorService.submit(runnable);
    }
}
