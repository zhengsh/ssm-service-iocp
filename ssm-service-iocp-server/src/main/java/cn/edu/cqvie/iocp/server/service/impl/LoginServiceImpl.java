package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.bean.dto.UserDTO;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.em.DirectionEnum;
import cn.edu.cqvie.iocp.server.content.SessionContent;
import cn.edu.cqvie.iocp.engine.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;

/**
 * 登录协议
 *
 * @author ZHENG SHAOHONG
 */
public class LoginServiceImpl extends AbstractMessageService implements MessageService {


    @Override
    public void dispose(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

        UserDTO dto = JSONObject.parseObject(msg.getData().toString(), UserDTO.class);
        if (dto != null) {
            // todo 校验规则

            // 登录成功,存储channel
            SessionContent.getInstance().set(dto.getUsername(), ctx.channel());

            MessageProtocol protocol = new MessageProtocol(msg.getPacketNo(),
                    DirectionEnum.ANSWER.getCode(), CommandEnum.A004.getCode(), "ok");

            // 登录应答
            ctx.writeAndFlush(protocol);
        } else {
            // 非法用户,断开连接
            //ctx.disconnect();
        }
    }
}
