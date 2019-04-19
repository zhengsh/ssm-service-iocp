package cn.edu.cqvie.iocp.client.content;

import cn.edu.cqvie.iocp.client.service.impl.LoginServiceImpl;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.util.ExpiryMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SessionContent {


    private static final Logger logger = LoggerFactory.getLogger(ControlContent.class);

    private static SessionContent instance = new SessionContent();

    private Map<String, Long> session = new ExpiryMap<>(1024,10 * 6000);


    private SessionContent() {

    }

    public static SessionContent getInstance() {
        return instance;
    }

    public Map<String, Long> getSession() {
        return session;
    }

    public void setSession(Map<String, Long> session) {
        this.session = session;
    }
}
