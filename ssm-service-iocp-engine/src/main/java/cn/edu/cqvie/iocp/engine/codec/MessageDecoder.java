package cn.edu.cqvie.iocp.engine.codec;

import cn.edu.cqvie.iocp.engine.bean.MessageProtocol;
import cn.edu.cqvie.iocp.engine.em.DecodeStateEnum;
import cn.edu.cqvie.iocp.engine.util.ByteUtil;
import cn.edu.cqvie.iocp.engine.util.CRC16;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

/**
 * 解码器
 *
 * @author ZHENG SHAOHONG
 */
public class MessageDecoder extends ReplayingDecoder<DecodeStateEnum> {

    private static final Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

    public MessageDecoder() {
    }

    public MessageDecoder(DecodeStateEnum initialState) {
        super(initialState);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        try {
            // TODO 待优化
            byte[] head = new byte[17];
            in.readBytes(head);

            byte[] h = new byte[4];
            System.arraycopy(head, 0, h, 0, 4);

            // flag  4
            String f = new String(h, "UTF-8");
            // version 2
            short v = ByteUtil.byteArrayToShort(new byte[]{head[4], head[5]});
            // serial number 4
            int no = ByteUtil.byteArrayToInt(new byte[]{head[6], head[7], head[8], head[9]});
            // length 4
            int len = ByteUtil.byteArrayToInt(new byte[]{head[10], head[11], head[12], head[13]});
            // direction
            byte d = head[14];
            // command
            short com = ByteUtil.byteArrayToShort(new byte[]{head[15], head[16]});

            byte[] content = new byte[len];
            in.readBytes(content);

            int cc = in.readInt();
            byte[] packet = new byte[17 + len];
            System.arraycopy(head, 0, packet, 0, head.length);
            System.arraycopy(content, 0, packet, 17, len);
            int i = CRC16.crc16CcittXmodem(packet);
            if (cc != i) {
                logger.error("decode crc16 fail {}", cc);
            }

            // 校验包
            MessageProtocol protocol = new MessageProtocol();
            protocol.setHeadFlag(f);
            protocol.setVersion(v);
            protocol.setPacketNo(no);
            protocol.setLength(len);
            protocol.setDirection(d);
            protocol.setCommand(com);
            protocol.setData(new String(content, "UTF-8"));
            protocol.setCrc16(cc);

            logger.info("encode MessageProtocol:{}", protocol);

            out.add(protocol);
        } catch (Throwable t) {
            logger.error("decode fail", t);
            throw t;
        }
    }
}
