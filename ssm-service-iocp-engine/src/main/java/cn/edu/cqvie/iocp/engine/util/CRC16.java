package cn.edu.cqvie.iocp.engine.util;

/**
 * crc16多项式算法
 *
 * @author ZHENG SHAOHONG
 */
public class CRC16 {

    /**
     * CRC16-XMODEM算法（四字节）
     *
     * @param bytes
     * @return
     */
    public static int crc16CcittXmodem(byte[] bytes) {
        return crc16CcittXmodem(bytes, 0, bytes.length);
    }

    /**
     * CRC16-XMODEM算法（四字节）
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public static int crc16CcittXmodem(byte[] bytes, int offset, int count) {
        // initial value
        int crc = 0x0000;
        // poly value
        int polynomial = 0x1021;
        for (int index = offset; index < count; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        return crc;
    }

    /**
     * CRC16-XMODEM算法（两字节）
     *
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public static short crc16CcittXmodemShort(byte[] bytes, int offset, int count) {
        return (short) crc16CcittXmodem(bytes, offset, count);
    }

    /**
     * CRC16-XMODEM算法（两字节）
     *
     * @param bytes
     * @return
     */
    public static int crc16CcittXmodemShort(byte[] bytes) {
        int crc = 0x00;          // initial value
        int polynomial = 0x1021;
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }
        crc &= 0xffff;
        return crc;
    }

}