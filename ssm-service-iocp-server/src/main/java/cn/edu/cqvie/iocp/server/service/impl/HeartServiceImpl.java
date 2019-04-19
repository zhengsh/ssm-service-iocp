package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.em.DirectionEnum;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 心跳处理(提示且断开连接)
 *
 * @author ZHENG SHAOHONG
 */
public class HeartServiceImpl extends AbstractMessageService implements MessageService {

    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        MessageProtocol protocol = new MessageProtocol(
                msg.getPacketNo(),
                DirectionEnum.ANSWER.getCode(),
                CommandEnum.A006.getCode(),
                "ok"
        );
        ctx.channel().writeAndFlush(protocol);
    }
}
