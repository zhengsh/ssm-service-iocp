package cn.edu.cqvie.iocp.engine.service;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import io.netty.channel.ChannelHandlerContext;

/**
 * 消息处理
 *
 * @author ZHENG SHAOHONG
 */
public interface MessageService {

    /**
     * 消息处理
     * @param ctx
     * @param msg
     */
    void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception;

    /**
     * 消息ack回调、对于有确认回复的接口
     * @param ctx
     * @param msg
     */
    void callBack(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception;
}
