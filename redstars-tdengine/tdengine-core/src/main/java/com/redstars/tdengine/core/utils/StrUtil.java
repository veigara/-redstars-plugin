package com.redstars.tdengine.core.utils;

import cn.hutool.core.convert.Convert;

import java.io.UnsupportedEncodingException;

/**
 * @author : zhouhx
 * @date : 2023/3/3 10:31
 */
public class StrUtil extends cn.hutool.core.util.StrUtil {
    /**
     *
     * @author zhuohx
     * @description  byte 转string
     * @parms  [ascs]
     * @return java.lang.String
     * @throws
     * @date 2023/3/3 10:31
     */
    public static String ascii2Str(byte[] ascs) {
        byte[] data = ascs;
        String asciiStr = null;
        try {
            asciiStr = new String(data, "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return asciiStr;
    }

    public static String ascii2Str(Byte[] ascs) {
        byte[] data =Convert.toPrimitiveByteArray(ascs);
        return ascii2Str(data);
    }

    /**
     * 将秒转为时分秒格式【01:01:01】
     * @param second 需要转化的秒数
     * @return
     */
    public static String secondConvertHourMinSecond(Long second) {
        String str = "0时0分";
        if (second == null || second <= 0) {
            return str;
        }

        // 得到小时
        long h = second / 3600;
        str = h > 0 ? ((h < 10 ? ("0" + h) : h) + "时") : "0时";

        // 得到分钟
        long m = (second % 3600) / 60;
        //得到剩余秒
        long s = second % 60;
        if(s >0){
            //有秒表名分钟数+1
            m = m+1;
        }
        str += (m < 10 ? ("0" + m) : m) + "分";

//        //得到剩余秒
//        long s = second % 60;
//        str += (s < 10 ? ("0" + s) : s);
        return str;
    }
}
