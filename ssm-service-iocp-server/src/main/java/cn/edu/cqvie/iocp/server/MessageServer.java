package cn.edu.cqvie.iocp.server;

import cn.edu.cqvie.iocp.engine.constant.SystemConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 项目启动类
 *
 * @author ZHENG SHAOHONG
 */
public class MessageServer {

    private static final Logger logger = LoggerFactory.getLogger(MessageServer.class);


    public static void main(String[] args) throws InterruptedException {
        start();
    }

    private static void start() throws InterruptedException {
        int port = SystemConstant.SERVER_PORT;
        int nThread = Runtime.getRuntime().availableProcessors() * 2;
        EventLoopGroup bossGroup = new NioEventLoopGroup(nThread);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new MessageChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 4096)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
