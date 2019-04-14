package cn.edu.cqvie.iocp.engine.constant;

/**
 * 系统常量
 * @author ZHENG SHAOHONG
 */
public class SystemConstant {


    /**
     * 客户端是否发送心跳
     */
    public static final boolean HEARTBEAT = true;

    /**
     * 服务器地址
     */
    public static final String SERVER_HOST = "127.0.0.1";

    /**
     * 服务器端口
     */
    public static final int SERVER_PORT = 12345;

    /**
     * 包头FLAG
     */
    public static final String HEAD_FLAG = "AABB";

    /**
     * 字符集
     */
    public static final String CHARSET_NAME = "UTF-8";

    /**
     * 版本
     */
    public static final short VERSION = 0x0101;

    /**
     * 链接超时时间
     */
    public static final int CONNECT_TIME_OUT = 9 * 1000;

    /**
     * 等待超时时间
     */
    public static final int WAIT_TIME_OUT = 3 * 1000;

    /**
     * 重试等待时间
     */
    public static final int RETRY_DELAY_TIME = 2 * 1000;

}
