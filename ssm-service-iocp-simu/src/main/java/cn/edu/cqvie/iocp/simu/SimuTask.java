package cn.edu.cqvie.iocp.simu;

import cn.edu.cqvie.iocp.client.MessageClient;
import cn.edu.cqvie.iocp.engine.pool.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟终端
 *
 * @author ZHENG SHAOHONG
 */
public class SimuTask {

    private static SimuTask instance = new SimuTask();

    private List<ExecutorService> executorList = new ArrayList<>(32);

    private final AtomicInteger connect = new AtomicInteger();


    private SimuTask() {
        for (int i = 0; i < 32; i++) {

            ExecutorService executorService =
                    ThreadPool.newThreadExecutor("simu-task-" + i, 1000, true);
            executorList.add(executorService);
        }
    }

    public static SimuTask getInstance() {
        return instance;
    }

    public synchronized int count() {
        //return executorList.stream().mapToInt(item -> ((ThreadPoolExecutor) item).getActiveCount()).sum();
        return connect.get();
    }

    public synchronized void submit(MessageClient client) {
        connect.incrementAndGet();

        Runnable runnable = client::start;
        int index = new Random().nextInt(32);

        ExecutorService executorService = executorList.get(index);
        if (executorService != null) {
            // 提交客户端连接
            executorService.submit(runnable);
        } else {
            executorService = executorList.get(0);
            executorService.submit(runnable);
        }


    }
}
