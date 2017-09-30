package com.vision.smarthomeapi.util;

import java.io.InputStream;
import java.security.MessageDigest;

public class MD5ToText {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * MD5加密
     *
     * @param origin String数据
     * @return 加密后的String数据
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception ex) {
        }
        return resultString;
    }

    /**
     * MD5加密
     *
     * @param origin String数据
     * @return 加密后的字节数组数据
     */
    public static byte[] MD5Encodebyte(String origin) {
        String resultString = null;
        byte[] resultbyte = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultbyte = md.digest(resultString.getBytes());
        } catch (Exception ex) {
        }
        return resultbyte;
    }

    /**
     * 函数名称：md5sum</br>
     * 功能描述：算出升级文件的MD5
     *
     * @param input 去掉头40个字节后的板子升级包的文件流
     * @return 修改日志:</br>在Android4.3以下版本(numRead = fis.read(buffer))会出现bug，解决方法是在升级包上最后的字节加上三个空格。
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-3-5</td><td>gxz</td><td>初始创建</td>
     * </table>
     */
    public static byte[] md5sum(InputStream input) {
        InputStream fis = input;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        int i = 0;
        try {
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                i++;
                md5.update(buffer, 0, numRead);
                if (i == 256)
                    break;
            }
            // byte[] tempBuffer = new byte[1];
            // int _numRead = 0;
            // i = 0;
            // OutPutMessage.outputSystem("----->1.23",
            // "---------------------------------------------------------------------------------------------------------------");
            // while((_numRead = fis.read(tempBuffer))>0){
            // i++;
            // OutPutMessage.outputSystem("----->1.23", " i is "+
            // i+"  "+Integer.toHexString(tempBuffer[0]));
            //
            // }
            fis.close();
            return md5.digest();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 给byte数组内容MD5加密
     *
     * @param b byte数组
     * @return 加密后的byte数组
     */
    public static byte[] byteArrayMd5(byte[] b) {
        byte[] resultbyte = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            resultbyte = md.digest();
        } catch (Exception ex) {
        }
        return resultbyte;
    }

    /**
     * 将字节数组转化为十六进制的String
     *
     * @param b 字节数组
     * @return 十六进制的String
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0;
             i < b.length;
             i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 将字节转化为十六进制的String
     *
     * @param b 字节
     * @return 十六进制的String
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

}


