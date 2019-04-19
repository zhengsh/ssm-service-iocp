package cn.edu.cqvie.iocp.client.handler;

import cn.edu.cqvie.iocp.client.ConnectManger;
import cn.edu.cqvie.iocp.client.content.ControlContent;
import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.bean.dto.MessageDTO;
import cn.edu.cqvie.iocp.engine.bean.dto.UserDTO;
import cn.edu.cqvie.iocp.engine.constant.SystemConstant;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.em.DirectionEnum;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消息客户端
 *
 * @author ZHENG SHAOHONG
 */
public class MessageClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(MessageClientHandler.class);

    private int count;
    private int serialNum = 1;
    private String username;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.error("通道已连接, object: {}", this);

        int serialNum = this.serialNum++;
        this.username = UUID.randomUUID().toString().replace("-", "");
        String password = "password";
        MessageProtocol protocol = new MessageProtocol(
                serialNum,
                DirectionEnum.REQUEST.getCode(),
                CommandEnum.A004.getCode(),
                new UserDTO(username, password)
        );

        if (ctx.channel().isActive()) {
            ctx.writeAndFlush(protocol);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 断线
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("断线了。。。。。。");
        //使用过程中断线重连
        ControlContent instance = ControlContent.getInstance();

        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            ConnectManger.getInstance().start();
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        logger.info("客户端接收到的消息内容：{}", JSONObject.toJSONString(msg));
        logger.info("客户端接收到的消息数量：{}", ++this.count);

        try {

            // 如果是应答
            if (msg.getDirection() == DirectionEnum.ANSWER.getCode()) {
                short command = msg.getCommand();

                if (command == CommandEnum.A004.getCode()) {

                    // 登录成功推送消息给自己
                    if (ctx.channel().isActive()) {
                        MessageDTO dto = new MessageDTO();
                        dto.setCode(username);
                        dto.setContent("this is a test message send to " + username);

                        // 推送
                        int serialNum = ++this.serialNum;
                        MessageProtocol protocol = new MessageProtocol(
                                serialNum,
                                DirectionEnum.REQUEST.getCode(),
                                CommandEnum.A008.getCode(),
                                dto
                        );

                        ctx.writeAndFlush(protocol);
                    }

                } else if (command == CommandEnum.A008.getCode()) {

                    // 收消息后ask
                    MessageProtocol protocol = new MessageProtocol(
                            msg.getPacketNo(),
                            DirectionEnum.ANSWER.getCode(),
                            CommandEnum.A008.getCode(),
                            "ok"
                    );
                    ctx.writeAndFlush(protocol);

                }

            }
        } catch (Throwable t) {
            logger.error("channelRead0 fail:", t);
            throw t;
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                logger.info("长期没收到服务器推送数据");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                if (SystemConstant.HEARTBEAT) {
                    MessageProtocol protocol = new MessageProtocol(
                            1,
                            DirectionEnum.ANSWER.getCode(),
                            CommandEnum.A006.getCode(),
                            "heartbeat"
                    );
                    ctx.writeAndFlush(protocol);
                    logger.info("长期未向服务器发送数据, 客户端发送心跳信息");
                }

            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL");
            }
        }
    }
}
