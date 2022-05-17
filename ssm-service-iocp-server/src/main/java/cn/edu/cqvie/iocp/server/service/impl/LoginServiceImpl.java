package cn.edu.cqvie.iocp.server.service.impl;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.bean.dto.UserDTO;
import cn.edu.cqvie.iocp.engine.config.SystemConfig;
import cn.edu.cqvie.iocp.engine.em.CommandEnum;
import cn.edu.cqvie.iocp.engine.em.DirectionEnum;
import cn.edu.cqvie.iocp.engine.redis.RedisOperation;
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

            String username = dto.getUsername();
            RedisOperation redisOperation = RedisOperation.getInstance();
            redisOperation.set(String.format("iocp:server_id:user_id_%s", username), SystemConfig.SERVER_ID, 12L);

            // 登录应答
            ctx.writeAndFlush(protocol);

            // 拉存量消息
            pushBacklogMessage(username);
        } else {
            // 非法用户,断开连接
            ctx.disconnect();
        }
    }

    // 推送积压的消息
    private void pushBacklogMessage(String username) {
        // todo 顺序推
    }
}
