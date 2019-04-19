package cn.edu.cqvie.iocp.client;

import cn.edu.cqvie.iocp.client.codec.MessageDecoder;
import cn.edu.cqvie.iocp.client.codec.MessageEncoder;
import cn.edu.cqvie.iocp.client.handler.MessageClientHandler;
import cn.edu.cqvie.iocp.client.listener.ConnectionListener;
import cn.edu.cqvie.iocp.engine.constant.SystemConstant;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 连接管理器
 *
 * @author ZHENG SHAOHONG
 */
public class ConnectManger {

    private static final Logger logger = LoggerFactory.getLogger(ConnectManger.class);

    private static ConnectManger instance = new ConnectManger();
    EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private List<Channel> channels = new ArrayList<>(256);

    private ConnectManger() {

    }

    public static ConnectManger getInstance() {
        return instance;
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

                            pipeline.addLast(new IdleStateHandler(10, 10, 30, TimeUnit.SECONDS));
                            pipeline.addLast(new MessageClientHandler());
                        }
                    });

            // 一个终端200个连接
            for (int i = 0; i < 1024; i++) {
                ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
                channels.add(channelFuture.channel());
                channelFuture.addListener(new ConnectionListener());

                Thread.sleep(10);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (channels != null && channels.size() > 0) {
                for (Channel channel : channels) {
                    if (channel != null && channel.isOpen()) {
                        channel.closeFuture().sync();
                    }
                }
            }
            eventLoopGroup.shutdownGracefully();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
