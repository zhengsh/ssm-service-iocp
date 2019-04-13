package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import io.netty.channel.ChannelHandlerContext;

/**
 * 实现类
 *
 * @author ZHENG SHAOHONG
 */
public abstract class AbstractMessageService implements MessageService {



    @Override
    public void callBack(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

    }
}
