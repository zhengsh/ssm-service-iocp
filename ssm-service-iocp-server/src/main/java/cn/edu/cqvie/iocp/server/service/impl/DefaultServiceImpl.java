package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import cn.edu.cqvie.iocp.server.MessageReadHandler;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServiceImpl extends AbstractMessageService implements MessageService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceImpl.class);


    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        logger.info("default dispose");
        //ctx.writeAndFlush(new MessageProtocol());
        //SessionContent.getInstance().remove(ctx.channel());
        //ctx.disconnect();
    }
}
