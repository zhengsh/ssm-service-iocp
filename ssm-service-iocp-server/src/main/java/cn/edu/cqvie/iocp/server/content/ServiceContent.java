package cn.edu.cqvie.iocp.server.content;

import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import cn.edu.cqvie.iocp.server.service.impl.DefaultServiceImpl;
import cn.edu.cqvie.iocp.server.service.impl.FailServiceImpl;
import cn.edu.cqvie.iocp.server.service.impl.LoginServiceImpl;
import cn.edu.cqvie.iocp.server.service.impl.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息业务处理上下文
 * @author ZHENG SHAOHONG
 */
public class ServiceContent {

    private static final Logger logger = LoggerFactory.getLogger(ServiceContent.class);

    private static ServiceContent instance = new ServiceContent();

    private Map<Short, MessageService> serviceMap = new HashMap<>();

    private ServiceContent() {
        serviceMap.put(CommandEnum.A004.getCode(), new LoginServiceImpl());
        serviceMap.put(CommandEnum.A007.getCode(), new FailServiceImpl());
        serviceMap.put(CommandEnum.A008.getCode(), new MessageServiceImpl());
        // TODO 其他指令处理
        // serviceMap.put(CommandEnum.A008.getCode(), new MessageServiceImpl());
    }

    public static ServiceContent getInstance() {
        return instance;
    }

    public MessageService get(Short code) {
        if (serviceMap.containsKey(code)) {
            return serviceMap.get(code);
        } else {
            logger.info("not find detail service instance");
            return new DefaultServiceImpl();
        }
    }
}
