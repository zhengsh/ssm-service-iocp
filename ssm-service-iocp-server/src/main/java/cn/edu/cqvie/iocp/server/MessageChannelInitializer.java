package cn.edu.cqvie.iocp.server;

import cn.edu.cqvie.iocp.engine.codec.MessageDecoder;
import cn.edu.cqvie.iocp.engine.codec.MessageEncoder;
import cn.edu.cqvie.iocp.engine.em.DecodeStateEnum;
import cn.edu.cqvie.iocp.server.handler.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;


/**
 * 初始化管道
 *
 * @author ZHENG SHAOHONG
 */
public class MessageChannelInitializer extends ChannelInitializer {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MessageEncoder());
        pipeline.addLast(new MessageDecoder(DecodeStateEnum.HEAD));
        pipeline.addLast(new IdleStateHandler(60, 60, 180, TimeUnit.SECONDS));
        pipeline.addLast(new MessageReadHandler());
        pipeline.addLast(new MessageWriteHandler());
        // 消息客户端在其他服务节点，路由过去
        pipeline.addLast(new MessageRouteHandler());
        // 消息延迟发
        pipeline.addLast(new MessageDelayHandler());
        pipeline.addLast(new ConnectCountHandler());

    }
}
