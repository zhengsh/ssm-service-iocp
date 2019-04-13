package cn.edu.cqvie.iocp.engine.pool;

import java.util.concurrent.ThreadFactory;

/**
 * 线程工厂类
 *
 * @author ZHENG SHAOHONG
 */
public class HxThreadFactory implements ThreadFactory {

    private String prefix;
    private int number = 1;

    public HxThreadFactory() {
    }

    public HxThreadFactory(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-pool-" + number++);
    }
}
