package cn.edu.cqvie.iocp.server.control;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 控制面板参数
 *
 * @author ZHENG SHAOHONG
 */
public class ServerControl {

    private static ServerControl instance = new ServerControl();

    private final AtomicInteger connect = new AtomicInteger();

    private ServerControl() {
    }

    public static ServerControl getInstance() {
        return instance;
    }

    public synchronized AtomicInteger getConnect() {
        return connect;
    }

    public synchronized void incrementAndGet() {
        connect.incrementAndGet();
    }

    public synchronized void decrementAndGet() {
        connect.incrementAndGet();
    }

    public synchronized int get() {
       return connect.get();
    }
}
