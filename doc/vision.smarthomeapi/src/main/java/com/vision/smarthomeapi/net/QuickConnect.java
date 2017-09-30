package com.vision.smarthomeapi.net;

import android.os.AsyncTask;
import android.util.Log;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.MD5ToText;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoqing on 2016/1/15.
 */
public class QuickConnect extends AsyncTask<Void, Integer, Boolean> {
    private byte[] mac = new byte[]{(byte) 0x02, (byte) 0x12, (byte) 0x9e, (byte) 0x4e, (byte) 0x26, (byte) 0x0A, (byte) 0x48, (byte) 0x35};
    private String ssid = "Black";
    private String password = "wabjtam123";
    private static final short POLY16 = 0x1021;

    public QuickConnect() {
    }

    public QuickConnect(byte[] mac) {
        this.mac = mac;
    }

    public QuickConnect(byte[] mac, String ssid, String password) {
        this.mac = mac;
        this.ssid = ssid;
        this.password = password;

    }

    @Override
    public Boolean doInBackground(Void... params) {
        int port = 30001;
        Log.d("QCTest", ByteUtil.byteArrayToHexString(mac));
        //拼接SSID和Password
        String ssidPW = ssid + "\n" + password;
        int strLen = ssidPW.length();
        if (strLen % 2 != 0) {
            strLen++;
        } else {
            strLen += 2;
        }
        byte[] data = new byte[strLen];
        System.arraycopy(ssidPW.getBytes(), 0, data, 0, ssidPW.length());
        byte portbuf[] = new byte[2];
        portbuf[0] = (byte) (port);
        portbuf[1] = (byte) (port >> 8);

        byte xorCheck = 0;
        byte andCheck = 0;
        short crcCheck = crc16Caculate(ssidPW.getBytes());

        byte[] macMd5 = MD5ToText.byteArrayMd5(this.mac);
        if (macMd5 == null || macMd5.length != 16) {
            return false;
        }

        java.util.Random random = new java.util.Random();


        List<byte[]> list = new ArrayList<>();

        list.add(new byte[]{(byte) 0xE5, (byte) (0x00 | 0x00), macMd5[0], macMd5[1]});
        list.add(new byte[]{(byte) 0xE5, (byte) ((0x01 << 2) | 0x00), macMd5[0], macMd5[1]});
        list.add(new byte[]{(byte) 0xE5, (byte) ((0x02 << 2) | 0x00), macMd5[0], macMd5[1]});
        list.add(new byte[]{(byte) 0xE5, (byte) ((0x03 << 2) | 0x00), macMd5[0], macMd5[1]});

        xorCheck = (byte) (xorCheck ^ portbuf[1] ^ portbuf[0]);
        andCheck = (byte) (andCheck + portbuf[1] + portbuf[0]);
        byte num = (byte) ((0x0D << 2) | 0x00);
        portbuf[0] = maskContent(portbuf[0], macMd5, num);
        portbuf[1] = maskContent(portbuf[1], macMd5, num);

        list.add(new byte[]{(byte) 0xE5, num, (portbuf[0]), (portbuf[1])});

        int length = data.length / 2;
        for (int i = 0; i < length; i++) {
            num = (byte) ((0x0E + i) << 2);
            portbuf[0] = data[2 * i];
            portbuf[1] = data[2 * i + 1];

            xorCheck = (byte) (xorCheck ^ portbuf[1] ^ portbuf[0]);
            andCheck = (byte) (andCheck + portbuf[1] + portbuf[0]);

            num = (byte) ((num) | 0x00);

            portbuf[0] = maskContent(portbuf[0], macMd5, num);
            portbuf[1] = maskContent(portbuf[1], macMd5, num);

            list.add(new byte[]{(byte) 0xE5, num, portbuf[0], portbuf[1]});
        }

        byte len = (byte) (data.length + 2);
        list.add(4, new byte[]{(byte) 0xE5, (byte) ((0x04 << 2) | 0x00), len, len});
        list.add(5, new byte[]{(byte) 0xE5, (byte) ((0x05 << 2) | 0x00), len, len});
        list.add(6, new byte[]{(byte) 0xE5, (byte) ((0x06 << 2) | 0x00), len, len});

        list.add(7, new byte[]{(byte) 0xE5, (byte) ((0x07 << 2) | 0x00), xorCheck, andCheck});
        list.add(8, new byte[]{(byte) 0xE5, (byte) ((0x08 << 2) | 0x00), xorCheck, andCheck});
        list.add(9, new byte[]{(byte) 0xE5, (byte) ((0x09 << 2) | 0x00), xorCheck, andCheck});

        list.add(0x0a, new byte[]{(byte) 0xE5, (byte) ((0x0a << 2) | 0x00), (byte) crcCheck, (byte) (crcCheck >> 8)});
        list.add(0x0b, new byte[]{(byte) 0xE5, (byte) ((0x0b << 2) | 0x00), (byte) crcCheck, (byte) (crcCheck >> 8)});
        list.add(0x0c, new byte[]{(byte) 0xE5, (byte) ((0x0c << 2) | 0x00), (byte) crcCheck, (byte) (crcCheck >> 8)});

        Log.d("QCTest", ByteUtil.shortToHexString(crcCheck, true));

        for (byte[] bb : list) {
            Log.d("QCTest", ByteUtil.byteArrayToHexString(bb));
        }
//        UDPChannel udpChannel = UDPChannel.getUDPChannel();
//       for(int i=0;i<100;i++){
//            try {
//            for(byte[]bb : list)
//            {
//                udpChannel.send(bb);
//                Thread.sleep(10);
//            }
//                Thread.sleep(100);
//
//            } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//        }
        while (Controller.canSearch) {


            for (int i = 0; i < 10; i++) {
                for (byte[] bb : list) {
                    OutPutMessage.LogCatInfo("快速连接", "发送" + i);
                    UDPChannel.getUDPChannel().send(bb);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SmartDeviceManage.defaultManager().sendLanRetrieve(mac,1);

        }

        return false;
    }

    private byte maskContent(byte content, byte[] mask, byte offset) {
        return (byte) (content ^ mask[offset % 16] ^ offset);
    }

    private short crc16Caculate(byte[] data) {
        short crc = 0;
        for (int j = 0; j < data.length; j++) {
            crc = (short) (crc ^ (short) (data[j] << 8));
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x8000) != 0) {
                    crc = (short) (crc << 1 ^ POLY16);
                } else {
                    crc = (short) (crc << 1);
                }
            }
        }
        return (short) (crc & 0xffff);
    }


//    private void searchLoop(List<byte[]> list){
//        while (true){
//
//            for (byte[] bb : list){
//                OutPutMessage.LogCatInfo("快速连接","发送");
//                UDPChannel.getUDPChannel().send(bb);
//            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }


//    private void send (byte[] address)
//    {
//        try {
//            InetAddress group=InetAddress.getByAddress(address);//设定多播IP
//            byte[] buff = "QQ".getBytes("utf-8");//设定多播报文的数据
//            mSocket.setTimeToLive(1);//设定TTL
//            // 设定UDP报文（内容，内容长度，多播组，端口）
//            DatagramPacket packet = new// DatagramPacket(buff,buff.length,group,30001);
//                    DatagramPacket(buff,buff.length,group,30001);
//            mSocket.send(packet);//发送报文
//            System.out.println("11111111");
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }
}