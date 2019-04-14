package cn.edu.cqvie.iocp.client.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * 登录处理
 *
 * @author ZHENG SHAOHONG
 */
public class LoginServiceImpl extends AbstractService {

    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        super.dispose(ctx, msg);
    }

    @Override
    public void callBack(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        super.callBack(ctx, msg);
    }
}
