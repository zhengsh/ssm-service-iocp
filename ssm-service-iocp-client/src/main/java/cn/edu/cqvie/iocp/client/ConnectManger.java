package cn.edu.cqvie.iocp.client;

import cn.edu.cqvie.iocp.client.handler.MessageClientHandler;
import cn.edu.cqvie.iocp.client.listener.ConnectionListener;
import cn.edu.cqvie.iocp.engine.codec.MessageDecoder;
import cn.edu.cqvie.iocp.engine.codec.MessageEncoder;
import cn.edu.cqvie.iocp.engine.constant.SystemConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ConnectManger {

    private static final Logger logger = LoggerFactory.getLogger(ConnectManger.class);

    private static ConnectManger instance = new ConnectManger();
    private Channel channel;
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private ConnectManger() {

    }

    public static ConnectManger getInstance() {
        return instance;
    }


    public static void init() {

    }

    public void start() {
        String host = SystemConstant.SERVER_HOST;
        int port = SystemConstant.SERVER_PORT;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();

                            pipeline.addLast(new MessageEncoder());
                            pipeline.addLast(new MessageDecoder());

                            pipeline.addLast(new IdleStateHandler(60, 60, 180, TimeUnit.SECONDS));
                            pipeline.addLast(new MessageClientHandler());
                        }
                    });

            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            this.channel = channelFuture.channel();
            channelFuture.addListener(new ConnectionListener());
            channelFuture.channel().closeFuture().sync();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (channel != null && channel.isActive()) {
                channel.closeFuture().sync();
            }
            eventLoopGroup.shutdownGracefully();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
