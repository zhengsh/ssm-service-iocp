package cn.edu.cqvie.iocp.client.listener;

import cn.edu.cqvie.iocp.client.ConnectManger;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 负责监听启动时连接失败，重新连接功能
 *
 * @author ZHENG SHAOHONG
 */
public class ConnectionListener implements ChannelFutureListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionListener.class);

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (!channelFuture.isSuccess()) {
            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(() -> {
                logger.info("服务端链接不上，开始重连操作...");
                ConnectManger.getInstance().start();
            }, 1L, TimeUnit.SECONDS);
        } else {
            logger.info("服务端链接成功...");
        }
    }
}