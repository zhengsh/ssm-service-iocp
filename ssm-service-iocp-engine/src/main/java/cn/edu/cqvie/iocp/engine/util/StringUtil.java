package cn.edu.cqvie.iocp.engine.util;

import cn.edu.cqvie.iocp.engine.constant.SystemConstant;

import java.io.UnsupportedEncodingException;

public class StringUtil {

    public static byte[] strToByteArray(String str) {
        if (str == null) {
            return null;
        }
        try {
            return str.getBytes(SystemConstant.CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
