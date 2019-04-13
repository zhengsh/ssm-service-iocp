package cn.edu.cqvie.iocp.engine.em;

/**
 * 发送消息指令
 * @author ZHENG SHAOHONG
 */
public enum CommandEnum {

    /**
     * 登录
     */
    A004((short)1004),

    /**
     * 登出
     */
    A005((short)1005),
    /**
     * 心跳
     */
    A006((short)1006),
    /**
     * 编码错误
     */
    A007((short)1007),
    /**
     * 消息发送
     */
    A008((short)1008);

    private short code;

    CommandEnum(short code) {
        this.code = code;
    }


    public Short getCode() {
        return code;
    }
}
