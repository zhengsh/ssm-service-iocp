package cn.edu.cqvie.iocp.client.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理
 *
 * @author ZHENG SHAOHONG
 */
public abstract class AbstractService implements MessageService {

    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        //todo 业务处理，如果没有登录可以先去登录后执行
    }

    @Override
    public void callBack(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

    }
}
