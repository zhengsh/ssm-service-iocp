package cn.edu.cqvie.iocp.engine.pool;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂类
 *
 * @author ZHENG SHAOHONG
 */
public class HxThreadFactory implements ThreadFactory {

    private String prefix;
    private boolean daemon;
    private int number = 1;

    public HxThreadFactory() {
    }

    public HxThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    public HxThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix;
        this.daemon = daemon;
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r, prefix + "-pool-" + number++);
        thread.setDaemon(daemon);
        return thread;
    }
}
