package com.vision.smarthomeapi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Locale;


public class ByteUtil {

    /**
     * 用来把mac字符串转换为long
     *
     * @param strMac
     * @return
     */
    public static long macToLong(String strMac) {
        byte[] mb = new BigInteger(strMac, 16).toByteArray();
        ByteBuffer mD = ByteBuffer.allocate(9);
        mD.put(mb);
        long mac = 0;
        if (mb.length >= 9) {
            mac = mD.getLong(1);
        } else if (mb.length == 8) {
            mac = mD.getLong(0);
        } else if (mb.length < 8) {
            mac = Long.parseLong(strMac, 16);
        }
        return mac;
    }

    public static String reverseSSidStr(String mac) {
        int length = mac.length();
        if (length < 16) {
            return mac;
        }
        String s = mac.substring(10, 16);


//        return new StringBuilder().append(s5).append(s6).append(s3).append(s4).append(s1).append(s2).toString();

        return s;
    }

    /**
     * long转成Mac地址
     *
     * @param longMac Mac地址
     * @return Mac地址字符串
     */
    public static String longToMacStr(long longMac) {
        byte[] byteMac = ByteUtil.LongToByte(longMac);
        //将mac地址转换成为 字符串形式
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < byteMac.length; i++) {
            buffer.append(ByteUtil.byteToHex(byteMac[i]));
        }
        String macStr = buffer.toString();
        return macStr;
    }

    public static String getDeviceAppNum(short appNum) {
        int ver_1 = appNum / 1000;
        int ver_2 = (appNum / 100) % 10;
        int ver_3 = (appNum / 10) % 100 % 10;
        int ver_4 = appNum % 10;
        StringBuffer stringBuffer = new StringBuffer().append("V")
                .append(ver_1).append(".")
                .append(ver_2).append(".")
                .append(ver_3).append(".")
                .append(ver_4);
        return stringBuffer.toString();
    }

    private static byte[] getBytes(Object obj) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bout);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = bout.toByteArray();
        bout.close();
        out.close();
        return bytes;
    }

    /**
     * 函数名称：hexStr2Byte</br>
     * 功能描述：String 转数组
     *
     * @param hex
     * @return 修改日志:</br>
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-7-16</td><td>ZhaoQing</td><td>初始创建</td>
     * </table>
     * @author ZhaoQing
     */
    public static byte[] hexStr2Byte(String hex) {

        ByteBuffer bf = ByteBuffer.allocate(hex.length() / 2);
        for (int i = 0; i < hex.length(); i++) {
            String hexStr = hex.charAt(i) + "";
            i++;
            hexStr += hex.charAt(i);
            byte b = (byte) Integer.parseInt(hexStr, 16);
            bf.put(b);
        }
        return bf.array();
    }

    /**
     * 函数名称：byteToHex</br>
     * 功能描述：byte转16进制
     *
     * @param b
     * @return 修改日志:</br>
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-6-26</td><td>ZhaoQing</td><td>初始创建</td>
     * </table>
     * @author ZhaoQing
     */
    public static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;

        }
        return hex.toUpperCase(Locale.getDefault());

    }


    public static Object getObject(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object obj = oi.readObject();
        bi.close();
        oi.close();
        return obj;
    }

    public static ByteBuffer getByteBuffer(Object obj) throws IOException {
        byte[] bytes = ByteUtil.getBytes(obj);
        ByteBuffer buff = ByteBuffer.wrap(bytes);

        return buff;
    }

    /**
     * byte[] 转short  2字节
     *
     * @param bytes
     * @return
     */
    public static short bytesToshort(byte[] bytes) {
        return (short) ((bytes[0] & 0xff) | ((bytes[1] << 8) & 0xff00));

    }

    /**
     * byte 转Int
     *
     * @param b
     * @return
     */
    public static int byteToInt(byte b) {
        return (b) & 0xff;
    }

    /**
     * byte数组转int
     *
     * @param bytes
     * @return
     */
    public static int bytesToInt(byte[] bytes) {
        int addr = bytes[0] & 0xFF;
        addr |= ((bytes[1] << 8) & 0xFF00);
        addr |= ((bytes[2] << 16) & 0xFF0000);
        addr |= ((bytes[3] << 24) & 0xFF000000);
        return addr;
    }


    /**
     * 验证设备协议前4位是否正确
     *
     * @param bytes
     * @return
     */
    public static int deviceByteFourValidation(byte[] bytes) {
        byte[] newByte = new byte[4];
        System.arraycopy(bytes, 0, newByte, 0, 4);
        int i = ByteUtil.bytesToInt(newByte);
        return i;
    }

    /**
     * int转byte数组
     *
     * @param i
     * @return
     */
    public static byte[] intToByte(int i) {
        byte[] abyte0 = new byte[4];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);
        return abyte0;

    }

    /**
     * long转byte数组
     *
     * @param i
     * @return
     */
    public static byte[] LongToByte(Long i) {

        byte[] abyte0 = new byte[8];
        abyte0[0] = (byte) (0xff & i);
        abyte0[1] = (byte) ((0xff00 & i) >> 8);
        abyte0[2] = (byte) ((0xff0000 & i) >> 16);
        abyte0[3] = (byte) ((0xff000000 & i) >> 24);
        abyte0[4] = (byte) ((0xff00000000l & i) >> 32);
        abyte0[5] = (byte) ((0xff0000000000l & i) >> 40);
        abyte0[6] = (byte) ((0xff000000000000l & i) >> 48);
        abyte0[7] = (byte) ((0xff00000000000000l & i) >> 56);
        return abyte0;

    }

    /**
     * 函数名称：shortChange</br>
     * 功能描述：short 大端转小端
     *
     * @param mshort
     * @return 修改日志:</br>
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-6-26</td><td>ZhaoQing</td><td>初始创建</td>
     * </table>
     * @author ZhaoQing
     */
    public static short shortChange(Short mshort) {

        mshort = (short) ((mshort >> 8 & 0xFF) | (mshort << 8 & 0xFF00));

        return mshort;
    }


    /**
     * 函数名称：intChange</br>
     * 功能描述：int 大端转小端
     *
     * @param mint
     * @return 修改日志:</br>
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-6-26</td><td>ZhaoQing</td><td>初始创建</td>
     * </table>
     * @author ZhaoQing
     */
    public static int intChange(int mint) {

        mint = (int) (((mint) >> 24 & 0xFF) | ((mint) >> 8 & 0xFF00) | ((mint) << 8 & 0xFF0000) | ((mint) << 24 & 0xFF000000));

        return mint;
    }

    /**
     * 函数名称：intChange</br>
     * 功能描述：LONG 大端转小端
     *
     * @param mint
     * @return 修改日志:</br>
     * <table>
     * <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     * <tr><td>0.1</td><td>2014-6-26</td><td>ZhaoQing</td><td>初始创建</td>
     * </table>
     * @author ZhaoQing
     */
    public static long longChange(long mint) {
        mint = (mint & 0xff00000000000000l) >> 56 |
                (mint & 0x00ff000000000000l) >> 40 |
                (mint & 0x0000ff0000000000l) >> 24 |
                (mint & 0x000000ff00000000l) >> 8 |
                (mint & 0x00000000ff000000l) << 8 |
                (mint & 0x0000000000ff0000l) << 24 |
                (mint & 0x000000000000ff00l) << 40 |
                (mint & 0x00000000000000ffl) << 56;
        return mint;
    }

    /**
     * 将byte转换为无符号的short类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的short
     */
    public static short byteToUshort(byte b) {
        return (short) (b & 0x00ff);
    }

    /**
     * 将byte转换为无符号的int类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的int
     */
    public static int byteToUint(byte b) {
        return b & 0x00ff;
    }

    /**
     * 将byte转换为无符号的long类型
     *
     * @param b 需要转换的字节数
     * @return 转换完成的long
     */
    public static long byteToUlong(byte b) {
        return b & 0x00ff;
    }

    /**
     * 将short转换为无符号的int类型
     *
     * @param s 需要转换的short
     * @return 转换完成的int
     */
    public static int shortToUint(short s) {
        return s & 0x00ffff;
    }

    /**
     * 将short转换为无符号的long类型
     *
     * @param s 需要转换的字节数
     * @return 转换完成的long
     */
    public static long shortToUlong(short s) {
        return s & 0x00ffff;
    }

    /**
     * 将int转换为无符号的long类型
     *
     * @param i 需要转换的字节数
     * @return 转换完成的long
     */
    public static long intToUlong(int i) {
        return i & 0x00ffffffff;
    }

    /**
     * 将short转换成小端序的byte数组
     *
     * @param s 需要转换的short
     * @return 转换完成的byte数组
     */
    public static byte[] shortToLittleEndianByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(s).array();
    }

    /**
     * 将int转换成小端序的byte数组
     *
     * @param i 需要转换的int
     * @return 转换完成的byte数组
     */
    public static byte[] intToLittleEndianByteArray(int i) {
        return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(i).array();
    }

    /**
     * 将long转换成小端序的byte数组
     *
     * @param l 需要转换的long
     * @return 转换完成的byte数组
     */
    public static byte[] longToLittleEndianByteArray(long l) {
        return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(l).array();
    }

    /**
     * 将short转换成大端序的byte数组
     *
     * @param s 需要转换的short
     * @return 转换完成的byte数组
     */
    public static byte[] shortToBigEndianByteArray(short s) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(s).array();
    }

    /**
     * 将int转换成大端序的byte数组
     *
     * @param i 需要转换的int
     * @return 转换完成的byte数组
     */
    public static byte[] intToBigEndianByteArray(int i) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putInt(i).array();
    }

    /**
     * 将long转换成大端序的byte数组
     *
     * @param l 需要转换的long
     * @return 转换完成的byte数组
     */
    public static byte[] longToBigEndianByteArray(long l) {
        return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putLong(l).array();
    }

    /**
     * 将short转换为16进制字符串
     *
     * @param s              需要转换的short
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String shortToHexString(short s, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = shortToLittleEndianByteArray(s);
        } else {
            byteArray = shortToBigEndianByteArray(s);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将int转换为16进制字符串
     *
     * @param i              需要转换的int
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String intToHexString(int i, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = intToLittleEndianByteArray(i);
        } else {
            byteArray = intToBigEndianByteArray(i);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将long转换为16进制字符串
     *
     * @param l              需要转换的long
     * @param isLittleEndian 是否是小端序（true为小端序false为大端序）
     * @return 转换后的字符串
     */
    public static String longToHexString(long l, boolean isLittleEndian) {
        byte byteArray[] = null;
        if (isLittleEndian) {
            byteArray = longToLittleEndianByteArray(l);
        } else {
            byteArray = longToBigEndianByteArray(l);
        }
        return byteArrayToHexString(byteArray);
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param array   需要转换的字符串
     * @param toPrint 是否为了打印输出，如果为true则会每4自己添加一个空格
     * @return 转换完成的字符串
     */
    public static String byteArrayToHexString(byte[] array, boolean toPrint) {
        if (array == null) {
            return "null";
        }
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < array.length; i++) {
            sb.append(byteToHex(array[i]));
            if (toPrint && (i + 1) % 4 == 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * 将字节数组转换成16进制字符串
     *
     * @param array 需要转换的字符串(字节间没有分隔符)
     * @return 转换完成的字符串
     */
    public static String byteArrayToHexString(byte[] array) {
        return byteArrayToHexString(array, false);
    }

    /**
     * 将字节数组转换成long类型
     *
     * @param bytes 字节数据
     * @return long类型
     */
    public static long byteArrayToLong(byte[] bytes) {
        return ((((long) bytes[0] & 0xff) << 56)
                | (((long) bytes[1] & 0xff) << 48)
                | (((long) bytes[2] & 0xff) << 40)
                | (((long) bytes[3] & 0xff) << 32)
                | (((long) bytes[4] & 0xff) << 24)
                | (((long) bytes[5] & 0xff) << 16)
                | (((long) bytes[6] & 0xff) << 8)
                | (((long) bytes[7] & 0xff) << 0));
    }
}
