package com.vision.smarthomeapi.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by zhanglong on 2015/11/11.
 */
public class SocketHead {

    /**
     * GreeHead数据
     */
    public static class SocketHeadData {
        public byte[] data;
        public byte opcode;
    }

    /**
     * 添加头
     *
     * @param data   数据
     * @param opcode
     * @return
     */
    public static byte[] buildSocketHead(byte[] data, byte opcode) {
        if (data.length > (0x00ff - 2)) {
            return null;
        }
        ByteBuffer bb = ByteBuffer.allocate(7 + data.length);
        bb.put((byte) 0x6D);
        bb.put((byte) 0x64);
        bb.put((byte) (data.length + 4));
        bb.put((byte) 0);
        bb.put((byte) 0);
        bb.put(opcode);
        bb.put(data);
        bb.put(check(bb.array(), 2, bb.array().length));
        return bb.array();
    }

    /**
     * 解析头
     * <p/>
     * 帧头	帧长度			    	有效数据	校验字
     * 2字节	1字节	1字节	1字节	1字节	N	1字节
     *
     * @param data 内容
     * @return
     */
    public static SocketHeadData parseSocketHead(byte[] data) {
        if (data.length < 1) {
            return null;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        byte head_1 = byteBuffer.get(0);
        byte head_2 = byteBuffer.get(1);
        if (head_1 != 0x6D && head_2 != 0x64) {
            return null;
        }
        byte head_3 = byteBuffer.get(2);
        if ((data.length - 2 - 1) != (head_3 & 0xff)) {
            return null;
        }
        byte head_4 = byteBuffer.get(3);//版本号
        byte head_5 = byteBuffer.get(4);//预留
        byte head_6 = byteBuffer.get(5);//命令字
        byte temp = byteBuffer.get(data.length - 1);//校验字
        data[data.length - 1] = 0;
//		if (temp!=crc(data))
//			return null;

        if (temp != check(data, 2, data.length)) {
            return null;
        }

        SocketHeadData ghd = new SocketHeadData();
        ghd.data = new byte[head_3 - 4];
        ByteBuffer buffer = byteBuffer.get(ghd.data);
        System.arraycopy(data, 6, ghd.data, 0, ghd.data.length);
        ghd.opcode = head_6;
        return ghd;
    }

    /**
     * 得出校验位
     *
     * @param data   数据
     * @param s      开始位置
     * @param length 校验的数据长度
     * @return 校验位
     */
    private static byte check(byte[] data, int s, int length) {
        int check = 0;
        for (int i = s; i < length; i++) {
            check += data[i] & 0xff;
            check &= 0xff;
        }
        return (byte) (check & 0xff);
    }

    private static byte crc(byte[] data) {
        byte POLYNOMIAL = 0x31;
        byte crc = 0;
        byte byteCtr;

        for (byteCtr = 0; byteCtr < data.length; byteCtr++) {
            crc ^= (data[byteCtr]);
            for (byte bit = 8; bit > 0; bit--) {
                if ((crc & 0x80) != 0) {
                    crc = (byte) ((crc << 1) ^ POLYNOMIAL);
                } else {
                    crc = (byte) (crc << 1);
                }
            }
        }
        return crc;
    }

}
