package com.vision.smarthomeapi.net;

import android.util.Log;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Zigbee连接类
 */
public class ZigbeeChannel implements ISerialPort {
    private final static int SERIAL_PORT_LENGTH = 7;
    private final static int AD_RSSI_LENGTH = 1;

    private static boolean isIPv6 = false;
    private SerialPortManager serialPortManager;
    private FileWriter fileWriter;
    private static ZigbeeChannel zigbeeChannel;

    /**
     * 获取ZigbeeChannel对象
     *
     * @return ZigbeeChannel对象
     */
    public static ZigbeeChannel getZigbeeChannel() {
        if (zigbeeChannel == null) {
            zigbeeChannel = new ZigbeeChannel();
        }
        return zigbeeChannel;
    }

    /**
     * zigbeeData类
     */
    private class ZigbeeData {
        public int rssi;
        public byte msgID;
        public byte[] mac;
        public byte[] data;
    }

    public ZigbeeChannel() {
        try {
            //5秒后开启zigbee
            fileWriter = new FileWriter(Constant.ZIGBEE_PATH);
            fileWriter.write("1");
            fileWriter.flush();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(5000);
                        checkZigbeeVer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serialPortManager = new SerialPortManager(this);
        serialPortManager.open();
        checkZigbeeVer();
    }

    //    boolean bbbbb=false;
    public void sendMessage(byte[] msg, String mac) {
//        if(bbbbb)
//        {
        sendMessage(msg, mac, (byte) 0xAD);
//        }else
//        {
//             sendMessage(null,null,(byte)0x02);
//        }
//        bbbbb = !bbbbb;

    }

    public void sendMessage(byte[] msg, String mac, byte msgID) {
        byte[] ipv6ip = null;
        if (msgID == (byte) 0xAD) {
            if (mac == null) {
                throw new NullPointerException("透传指令MAC不能为空");
            }
            if (isIPv6) {
                byte[] temp = ByteUtil.hexStr2Byte(mac);
                temp[0] = 0x02;//TODO zigbee默认改成 强制O2发送
                ipv6ip = new byte[]{(byte) 0xfe, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
                System.arraycopy(temp, 0, ipv6ip, 8, 8);
            } else {
                ipv6ip = ByteUtil.hexStr2Byte(mac);
                ipv6ip[0] = 0x02;//TODO zigbee默认改成 强制O2发送
            }

        }
        msg = addZigbeehead(msg, ipv6ip, msgID);
        serialPortManager.send(msg);
    }


    /**
     * zigbee数据接收
     *
     * @param buffer 数据
     * @param size   数据长度
     */
    @Override
    public void onDataReceived(byte[] buffer, int size) {
        ZigbeeData zd = parseZigbeeData(buffer);
        Log.i(Controller.ZIGBEE, "接收到的" + ByteUtil.byteArrayToHexString(buffer, true));

        if (zd != null) {
            Log.i(Controller.ZIGBEE, "接收到的" + Integer.toHexString(zd.msgID));
            if (zd.msgID == (byte) 0xAD) {
                String mac = "";
                byte[] temp = new byte[8];
                if (zd.mac.length == 16) {
                    System.arraycopy(zd.mac, 8, temp, 0, 8);
                } else if (zd.mac.length == 8) {
                    temp = zd.mac;
                }
                if (temp != null) {
                    mac = ByteUtil.byteArrayToHexString(temp);
                }
                Log.i(Controller.ZIGBEE, zd.data.length + "接收到的" + ByteUtil.byteArrayToHexString(zd.data, true));
                NetworkMessage message = new NetworkMessage(false, zd.data, mac,
                        Constant.NetWork.ZIG_BEE_NET, null);
                NetworkManager.defaultNetworkManager().reciveMessage(message);
            }
        }

    }

    @Override
    public void onDataDidSend(byte[] buffer, int size) {

        Log.i(Controller.ZIGBEE, "发送成功:" + ByteUtil.byteArrayToHexString(buffer, true));
    }

    @Override
    public void onDataReceivedError(Exception e) {
        Log.i(Controller.ZIGBEE, "发送失败:");
        e.printStackTrace();
    }

    @Override
    public void onDataSendError(Exception e) {

    }

    private void checkZigbeeVer() {
        sendMessage(null, null, (byte) 0x02);
    }

    /**
     * 加头并组包组成最终发送数据
     *
     * @param data   数据
     * @param ipv6ip
     * @param msgID
     * @return
     */
    private static byte[] addZigbeehead(byte[] data, byte[] ipv6ip, byte msgID) {
        int packetLength = 0;
        int length = 0;
        int adIPLength = 8;
        int dataLength;
        if (data != null) {
            dataLength = data.length;
        } else {
            dataLength = 0;
        }
        if (isIPv6) {
            adIPLength = 16;
        }
        if (msgID == (byte) 0xAD) {
            packetLength = SERIAL_PORT_LENGTH + adIPLength + AD_RSSI_LENGTH;
            length = adIPLength + AD_RSSI_LENGTH + 4;
            packetLength += dataLength;
            length += dataLength;

        } else {
            packetLength = SERIAL_PORT_LENGTH;
            length = 4;
            length += dataLength;

        }

        ByteBuffer bb = ByteBuffer.allocate(packetLength).order(ByteOrder.LITTLE_ENDIAN);
        bb.put(new byte[]{0x6D, 0X64});
        bb.put((byte) (length));
        bb.put((byte) 0);
        bb.put((byte) 0);
        bb.put(msgID);

        if (msgID == (byte) 0xAD) {
            bb.put((byte) 0);
            bb.put(ipv6ip);
        }

        if (data != null) {
            bb.put(data);
        }

        byte check = 0;
        byte[] temp = bb.array();
        for (int i = 2; i < temp.length; i++) {
            check ^= temp[i];
        }
        check = (byte) ~check;
        bb.put(check);
        return bb.array();
    }

    /**
     * 将zigbee接收到的数据转化为ZigbeeData对象
     *
     * @param data zigbee数据
     * @return ZigbeeData对象
     */
    public ZigbeeData parseZigbeeData(byte[] data) {
        if (data.length < SERIAL_PORT_LENGTH) {
            return null;
        }
        ZigbeeData zd = new ZigbeeData();
        if (data[0] == (byte) 0x6d && data[1] == (byte) 0x64) {
            zd.msgID = data[5];
            if (data[5] == (byte) 0xAD) {
                int length = (data[2] & 0x00ff);
                if ((length + 3) > data.length) {
                    return null;
                } else {
                    zd.rssi = data[6];


                    int adIPLength = 8;
                    if (isIPv6) {
                        adIPLength = 16;
                    }
                    int dataLenght = length & 0x0fffffff;
                    dataLenght -= 4;
                    dataLenght -= 1;
                    dataLenght -= adIPLength;
                    int macLenght = adIPLength;
                    zd.mac = new byte[adIPLength];
                    zd.data = new byte[dataLenght];

                    System.arraycopy(data, 7, zd.mac, 0, macLenght);
                    System.arraycopy(data, 7 + adIPLength, zd.data, 0, dataLenght);
                }

            } else if (data[5] == (byte) 0xA2) {
                //TODO 此处获取mac地址
                bytes = new byte[8];
                System.arraycopy(data, 10, bytes, 0, bytes.length);
                byte b = (byte) (bytes[0] ^ 0x02);
                bytes[0] = b;
                isIPv6 = true;
                return null;
            }
        } else {
            return null;
        }
        return zd;
    }

    private byte[] bytes;

    public String getZigBeeSrc() {
        if (bytes == null) {
            return "0";
        }
        if (bytes.length < 8) {
            return "0";
        }
        long src = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).getLong(0);
        String zibMac = ByteUtil.longToMacStr(src);
        OutPutMessage.LogCatInfo("测试大于", "数据内容222::" + zibMac);
        return zibMac;
    }
}
