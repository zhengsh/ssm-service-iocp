package cn.edu.cqvie.iocp.server.handler;

import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * 消息延迟发，分两种：
 * 1. 本身延迟发
 * 2. 客户端暂不在线，2h 内重发
 *
 * @author ZHENG SHAOHONG
 */
public class MessageDelayHandler extends ChannelOutboundHandlerAdapter {
}
