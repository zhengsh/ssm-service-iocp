package cn.edu.cqvie.iocp.server.handler;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.server.content.ServiceContent;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import cn.edu.cqvie.iocp.server.task.TaskManager;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听客户端
 *
 * @author ZHENG SHAOHONG
 */
public class MessageReadHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(MessageReadHandler.class);

    private int count;

    /**
     * 客户端和服务器建立连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 加入组
        logger.info("[客户端]{}加入", channel.remoteAddress());
        //channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //移除|连接中断后会自动移除ChannelGroup中的成员
        Channel channel = ctx.channel();
        logger.info("[客户端]{}退出", channel.remoteAddress());
    }

    /**
     * 捕获异常
     *
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 读取客户端数据
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        logger.info("服务端接收到的消息数据：{}", JSONObject.toJSONString(msg));
        logger.info("长度：{}", msg.getLength());
        logger.info("内容：{}", msg.getData());

        logger.info("服务端接收到的消息数量：{}", (++this.count));

        TaskManager.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("executors.execute invoke!");
                    ServiceContent instance = ServiceContent.getInstance();
                    instance.get(msg.getCommand()).dispose(ctx, msg);
                } catch (Throwable t) {
                    logger.error("channelRead task err {}", t);
                }
            }
        });

    }


    /**
     * 事件处理
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                // 移除通道
                SessionContent.getInstance().remove(ctx.channel());
                ctx.disconnect();
                logger.info("超时关闭 : {}", ctx);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
