package cn.edu.cqvie.iocp.engine.bean.dto;

import io.netty.channel.ChannelId;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录用户
 *
 * @author ZEHNG SHAOHONG
 */
@Getter
@Setter
public class UserDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 通道ID
     */
    private ChannelId channelId;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
