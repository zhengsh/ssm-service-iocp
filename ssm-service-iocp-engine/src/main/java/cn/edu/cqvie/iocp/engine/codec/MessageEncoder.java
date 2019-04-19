package cn.edu.cqvie.iocp.engine.codec;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.util.ByteUtil;
import cn.edu.cqvie.iocp.engine.util.CRC16;
import cn.edu.cqvie.iocp.engine.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码器
 *
 * @author ZHENG SHAOHONG
 */
public class MessageEncoder extends MessageToByteEncoder<MessageProtocol> {

    private static final Logger logger = LoggerFactory.getLogger(MessageEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {

        final byte[] heard = StringUtil.strToByteArray(msg.getHeadFlag());
        byte[] bytes = StringUtil.strToByteArray(JSONObject.toJSONString(msg.getData()));
        int dataLen = bytes.length;

        // TODO 待优化
        byte[] protocol = new byte[17 + dataLen];
        // heard 4 0-3
        System.arraycopy(heard, 0, protocol, 0, 4);
        // version 2 4-5
        System.arraycopy(ByteUtil.shortToByteArray(msg.getVersion()), 0, protocol, 4, 2);
        // serial number 4 6-9
        System.arraycopy(ByteUtil.intToByteArray(msg.getPacketNo()), 0, protocol, 6, 4);
        // length 4 10-13
        System.arraycopy(ByteUtil.intToByteArray(dataLen), 0, protocol, 10, 4);
        // direction 1 14
        protocol[14] = msg.getDirection();
        // command 15-16
        System.arraycopy(ByteUtil.shortToByteArray(msg.getCommand()), 0, protocol, 15, 2);
        // data 17 - (17 + length)
        System.arraycopy(bytes, 0, protocol, 17, dataLen);
        // check code
        int cc = CRC16.crc16CcittXmodemShort(protocol);

        int len = 17 + dataLen + 4;
        byte[] packet = new byte[len];
        System.arraycopy(protocol, 0, packet, 0, dataLen + 17);
        System.arraycopy(ByteUtil.intToByteArray(cc), 0, packet, dataLen + 17, 4);

        logger.info("encode haxString:{}", ByteUtil.byteArrayToHexString(packet));
        out.writeBytes(packet);
    }
}
