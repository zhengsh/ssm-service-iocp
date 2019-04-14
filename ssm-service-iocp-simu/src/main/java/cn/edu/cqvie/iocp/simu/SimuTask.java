package cn.edu.cqvie.iocp.simu;

import cn.edu.cqvie.iocp.client.MessageClient;
import cn.edu.cqvie.iocp.engine.pool.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟终端
 *
 * @author ZHENG SHAOHONG
 */
public class SimuTask {

    private static SimuTask instance = new SimuTask();

    private ExecutorService executorService =
            ThreadPool.newThreadExecutor("simu-task", 1000);

    private SimuTask () {

    }

    public static SimuTask getInstance() {
        return instance;
    }

    public void submit(MessageClient client) {
        Thread thread = new Thread(() -> client.start());
        thread.setDaemon(true);

        // 提交客户端连接
        executorService.submit(thread);

    }
}
