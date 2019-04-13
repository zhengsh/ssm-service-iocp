package cn.edu.cqvie.iocp.engine.em;

/**
 * 应答状态枚举
 *
 * @author ZHENG SHAOHONG
 */
public enum DirectionEnum {

    /**
     * 请求
     */
    REQUEST((byte)0),

    /**
     * 应答
     */
    ANSWER((byte)1);


    private byte code;

    DirectionEnum(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return code;
    }
}
