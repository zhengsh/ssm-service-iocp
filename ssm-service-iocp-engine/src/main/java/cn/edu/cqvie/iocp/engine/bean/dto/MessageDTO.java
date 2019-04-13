package cn.edu.cqvie.iocp.engine.bean.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息内容
 *
 * @author ZHENG SHAOHONG
 */
@Getter
@Setter
public class MessageDTO {
    /**
     * 时间戳
     */
    private Long time;
    /**
     * 编码
     */
    private String code;
    /**
     * 内容
     */
    private String content;
}
