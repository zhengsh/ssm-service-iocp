package cn.edu.cqvie.iocp.client.handler;

import cn.edu.cqvie.iocp.client.ConnectManger;
import cn.edu.cqvie.iocp.client.content.StatisticalContent;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 消息客户端
 *
 * @author ZHENG SHAOHONG
 */
public class MessageClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(MessageClientHandler.class);

    private int count;
    private int serialNum = 1;
    private long loginTime;
    private long messageTime;

    private final String username = "zhangsan";
    private final String password = "password";
    private Map<Integer, CommandEnum> message = new ConcurrentHashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("通道已连接！！");

        int serialNum = ++this.serialNum;
        MessageProtocol protocol = new MessageProtocol(
                serialNum,
                DirectionEnum.REQUEST.getCode(),
                CommandEnum.A004.getCode(),
                new UserDTO(username, password)
        );

        if (ctx.channel().isActive()) {
            ctx.writeAndFlush(protocol);
            loginTime = System.currentTimeMillis();
            message.put(serialNum, CommandEnum.A004);
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
        final EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            ConnectManger.getInstance().start();
        }, 1L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        logger.info("客户端接收到的消息内容：{}", JSONObject.toJSONString(msg));
        logger.info("长度：{}", msg.getLength());
        logger.info("内容：{}", msg.getData());
        logger.info("客户端接收到的消息数量：{}", ++this.count);

        try {

            // 登录成功
            if (message.containsKey(msg.getPacketNo())) {

                StatisticalContent instance = StatisticalContent.getInstance();

                CommandEnum commandEnum = message.get(msg.getPacketNo());
                if (commandEnum.equals(CommandEnum.A004)) {
                    // 登录成功
                    logger.info("login success");

                    //todo 发送业务指令
                    MessageDTO dto = new MessageDTO();
                    dto.setCode(username);
                    dto.setContent("this is a test message send to " + username);
                    instance.set(username, (int) (System.currentTimeMillis() - loginTime));

                    int serialNum = ++this.serialNum;
                    MessageProtocol protocol = new MessageProtocol(
                            serialNum,
                            DirectionEnum.REQUEST.getCode(),
                            CommandEnum.A008.getCode(),
                            dto
                    );

                    if (ctx.channel().isActive()) {
                        ctx.writeAndFlush(protocol);
                        message.put(serialNum, CommandEnum.A008);
                        messageTime = System.currentTimeMillis();
                    }

                } else if (commandEnum.equals(CommandEnum.A008)) {

                    // 消息发送成功
                    logger.info("message send success");
                    instance.set(username, (int) (System.currentTimeMillis() - messageTime));

                }
            }
        } catch (Throwable t) {
            logger.error("fail message: {}", t);
            throw t;
        } finally {
            // 如果是应答就delete key
            if (msg.getDirection() == DirectionEnum.ANSWER.getCode()) {
                message.remove(msg.getPacketNo());
            }
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                logger.info("长期没收到服务器推送数据");
                //可以选择重新连接
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                logger.info("长期未向服务器发送数据");
                if (SystemConstant.HEARTBEAT) {
                    //发送心跳包
                    Map<String, String> map = new HashMap<>();
                    map.put("name", "heartbeat");

                    MessageProtocol protocol = new MessageProtocol(
                            1,
                            DirectionEnum.ANSWER.getCode(),
                            CommandEnum.A006.getCode(),
                            map
                    );
                    ctx.writeAndFlush(protocol);
                }

            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("ALL");
            }
        }
    }
}
