package cn.edu.cqvie.iocp.engine.em;

/**
 * decoder 状态枚举
 *
 * @author ZHENG SHAOHONG
 */
public enum DecodeStateEnum {

    /**
     * 协议头
     */
    HEAD(1),
    /**
     * 协议数据
     */
    DATA(2),
    /**
     * 协议校验位
     */
    CRC16(3);


    private int code;

    DecodeStateEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }}
