package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import io.netty.channel.ChannelHandlerContext;

/**
 * 异常处理(提示且断开连接)
 *
 * @author ZHENG SHAOHONG
 */
public class FailServiceImpl extends AbstractMessageService implements MessageService {


    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        ctx.writeAndFlush(new MessageProtocol());
        SessionContent.getInstance().remove(ctx.channel());
        ctx.disconnect();
    }
}
