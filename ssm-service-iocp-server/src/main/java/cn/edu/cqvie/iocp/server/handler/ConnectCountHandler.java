package cn.edu.cqvie.iocp.server.handler;

import cn.edu.cqvie.iocp.server.control.ServerControl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计服务端连接
 *
 * @author ZHENG SHAOHONG
 */
public class ConnectCountHandler extends ChannelInboundHandlerAdapter {

    private final ServerControl serverControl = ServerControl.getInstance();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        serverControl.incrementAndGet();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        serverControl.decrementAndGet();
    }
}
