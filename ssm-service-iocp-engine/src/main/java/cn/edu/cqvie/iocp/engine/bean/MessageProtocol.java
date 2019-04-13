package cn.edu.cqvie.iocp.engine.bean;


import cn.edu.cqvie.iocp.engine.constant.SystemConstant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 最小包
 *
 * @author ZHENG SHAOHONG
 */
@Setter
@Getter
@NoArgsConstructor
public class MessageProtocol {


    /**
     * 长度: 4 0-3
     * 标志位：固定四个字符'SZTC'怡丰平台：固定 SZYF
     */
    private String headFlag = SystemConstant.HEAD_FLAG;

    /**
     * 长度：2 4-5
     * 主版本.次版本，各一个字节 怡丰平台：主版本.次版本FF FF
     */
    private short version = SystemConstant.VERSION;

    /**
     * 长度：6 6-9
     * 消息流水号无符号整数，1-int.Max之间循环，应答必须和请求包号相同
     */
    private int packetNo;

    /**
     * 长度：4 10-13
     * 数据区data长度
     */
    private int length;

    /**
     * 长度：1 14
     * 0：请求，1：应答
     */
    private byte direction;

    /**
     * 长度：2 15-16
     * 采用接口编号:1-66536
     */
    private short command;

    /**
     * 长度：N 17-length+16
     * length 指定长度，JSON格式，编码采用UTF-8
     */
    private Object data;


    /**
     * 长度：2 length+17 - 18
     * 从 headFlag到data(包括)所有数据crc16(ccitt-xmodem)校验
     */
    private int crc16;


    public MessageProtocol(int packetNo, byte direction, short command, Object data) {
        this.packetNo = packetNo;
        this.direction = direction;
        this.command = command;
        this.data = data;
    }
}
