package cn.edu.cqvie.iocp.client.content;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端统计
 *
 * @author ZHENG SHAOHONG
 */
public class ControlContent {
    private static final Logger logger = LoggerFactory.getLogger(ControlContent.class);

    private static ControlContent instance = new ControlContent();

    private Map<String, AtomicInteger> contextMap = new ConcurrentHashMap<>(1024);

    private ControlContent() {

    }

    public static ControlContent getInstance() {
        return instance;
    }

    public synchronized void set(String key, int maxnum) {
        if (contextMap.containsKey(key)) {
            AtomicInteger atomicInteger = contextMap.get(key);
            int n = atomicInteger.get();
            boolean b = atomicInteger.compareAndSet(n, maxnum);
            if (!b) {
                logger.error("compareAndSet err {}, {}", n, maxnum);
            }

        } else {
            contextMap.put(key, new AtomicInteger(maxnum));
        }
    }

    public synchronized int get(String key) {
        if (contextMap.containsKey(key)) {
            return contextMap.get(key).get();
        } else {
            return 0;
        }

    }

    public synchronized void remove(String key) {
        contextMap.remove(key);
    }

    public synchronized int max() {
        return contextMap.values().stream().map(AtomicInteger::get).max(Comparator.naturalOrder()).orElse(0);
    }
}
