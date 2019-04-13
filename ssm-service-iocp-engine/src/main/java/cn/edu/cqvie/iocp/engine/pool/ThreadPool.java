package cn.edu.cqvie.iocp.engine.pool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.*;

/**
 * 线程池创建
 *
 * @author ZHENG SHAOHONG
 */
public class ThreadPool {

    /**
     * 创建线程池
     *
     * @param threadNamePrefix 线程名称前缀
     * @return
     */
    public static ExecutorService newThreadExecutor(String threadNamePrefix) {
        int nThreads = Runtime.getRuntime().availableProcessors() * 2;
        int minThreads = 2;
        long keepAliveTime = 30L;
        int maximumPoolSize;
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(nThreads * 1000);
        nThreads = Math.max(minThreads, nThreads);
        maximumPoolSize = nThreads * 2;
        ThreadPoolExecutor.AbortPolicy handler = new ThreadPoolExecutor.AbortPolicy();
        return new ThreadPoolExecutor(
                nThreads,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                linkedBlockingQueue,
                new HxThreadFactory(threadNamePrefix),
                handler);
    }

    /**
     * 创建线程池
     *
     * @param threadNamePrefix 线程名称前缀
     * @return
     */
    public static ScheduledExecutorService newScheduledExecutor(String threadNamePrefix) {
        int nThreads = Runtime.getRuntime().availableProcessors() * 2;

        return new ScheduledThreadPoolExecutor(nThreads, new HxThreadFactory(threadNamePrefix));
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = newThreadExecutor("zhangsan");
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("zhangsan out print");
            }
        });

        ExecutorService executors = Executors.newFixedThreadPool(10);

        executors.submit(() -> {
            System.out.println("lisi out print");

        });
        Thread.sleep(10000);


    }
}
