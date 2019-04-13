package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.bean.dto.MessageDTO;
import cn.edu.cqvie.iocp.engine.bean.dto.UserDTO;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.em.DirectionEnum;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * 登录协议
 *
 * @author ZHENG SHAOHONG
 */
public class MessageServiceImpl extends AbstractMessageService implements MessageService {


    private int serialNum;

    /**
     * 执行消息发送
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        MessageDTO dto = JSONObject.parseObject(msg.getData().toString(), MessageDTO.class);
        if (dto != null) {
            Channel channel = SessionContent.getInstance().get(dto.getCode());
            if (channel != null) {
                int serialNum = ++this.serialNum;
                MessageProtocol protocol = new MessageProtocol(
                        serialNum,
                        DirectionEnum.REQUEST.getCode(),
                        CommandEnum.A008.getCode(),
                        msg.getData()
                );
                channel.writeAndFlush(protocol);
            } else {
                // todo 连接没有在这台机器上，在别的机器上

                // todo 都不在线，直接进入schedule直到过期
            }
        } else {
            // 非法用户,断开连接
            //ctx.disconnect();
        }
    }
}
