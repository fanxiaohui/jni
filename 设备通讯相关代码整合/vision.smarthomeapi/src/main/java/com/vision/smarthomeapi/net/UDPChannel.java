/********************************************************************
 * 文件名称：UDPC.java
 * 所属项目名称：PowerSocket
 * 创建人：
 * 创建时间：2014-5-20 下午2:43:33
 * Copyright (c) 2013 GD. All rights reserved.
 ********************************************************************/
package com.vision.smarthomeapi.net;


import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Random;


public class UDPChannel {

    private static UDPChannel channel;
    private static final int PORT = 1066;//测试7500 正式1066

    public int udpprot = 30001;
    private DatagramSocket udpsocket;
    private Thread mReceiveThread;


    public static UDPChannel getUDPChannel() {

        if (channel == null) {
            channel = new UDPChannel();
        }
        return channel;
    }

    public UDPChannel() {
        open();
    }

    /**
     * 打开UDP连接
     */
    public void open() {
        if (udpsocket == null) {
            udpprot = 30001;
            while (udpsocket == null && udpprot < 30127) {
                try {
                    //udpsocket = new DatagramSocket(udpprot);
                    if (udpsocket == null) {
                        udpsocket = new DatagramSocket(null);
                        udpsocket.setReuseAddress(true);
                        udpsocket.bind(new InetSocketAddress(udpprot));
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                udpprot++;
            }
        }
        if (mReceiveThread == null || mReceiveThread.isInterrupted()) {

            if (udpsocket == null){
                return;
            }
            mReceiveThread = new Thread() {
                public void run() {
                    while (!isInterrupted()) {
                        UDPReceive();
                    }
                }
            };
            mReceiveThread.start();
        }
    }

    /**
     * 关闭UDP连接
     */
    public void close() {
        if (udpsocket != null) {
            udpsocket.close();
        }
        udpsocket = null;
        if (mReceiveThread != null && !mReceiveThread.isInterrupted()) {
            mReceiveThread.interrupt();
        }
        mReceiveThread = null;

    }

    /**
     * 发送UDP数据
     *
     * @param data 数据
     * @param ip   地址
     */
    public void sendBinaryData(byte[] data, String ip) {
        sendBinaryData(data, ip, PORT);
    }

    /**
     * 发送数据，带端口
     *
     * @param data 数据
     * @param ip   地址
     * @param port 端口
     */
    public void sendBinaryData(byte[] data, String ip, int port) {
        if(udpsocket == null){
            return;
        }
        InetAddress serverAdd = null;
        //上来发送广播255.255.255.255，data为null

        try {
            serverAdd = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        DatagramPacket udppackage = new DatagramPacket(data, data.length);
        udppackage.setPort(port);
        udppackage.setAddress(serverAdd);
        OutPutMessage.LogCatInfo(NetworkManager.NEI_TAG, "-------------------------------->UDP发送：" + ByteUtil.byteArrayToHexString(data, true));

        try {
            udpsocket.send(udppackage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * UDP接收方法
     *
     * @return
     */
    public byte[] UDPReceive() {

        byte data[] = new byte[1024];
        DatagramPacket pa = new DatagramPacket(data, data.length);
        try {
            udpsocket.setSoTimeout(500);
            udpsocket.receive(pa);

            String ip = pa.getAddress().toString().replace("/", "");
            int len = pa.getLength();
            byte rdata[] = new byte[len];
            System.arraycopy(pa.getData(), 0, rdata, 0, len);
            NetworkMessage message = new NetworkMessage(false, rdata, null, Constant.NetWork.LAN_NET, ip);
            NetworkManager.defaultNetworkManager().reciveMessage(message);
            OutPutMessage.LogCatInfo(NetworkManager.NEI_TAG, "-------------------------------->UDP接收：" + ByteUtil.byteArrayToHexString(rdata, true));
        } catch (SocketTimeoutException e) {

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }
    public void send (byte[] address)
    {
        try {
            InetAddress group=InetAddress.getByAddress(address);//设定多播IP

            Random random=new Random();
            int dataL = random.nextInt(30);
            byte data[] = new byte[dataL];
            random.nextBytes(data);

            byte[] buff = data;//设定多播报文的数据
//            udpsocket.setTimeToLive(1);//设定TTL
            // 设定UDP报文（内容，内容长度，多播组，端口）
            DatagramPacket packet = new// DatagramPacket(buff,buff.length,group,30001);
                    DatagramPacket(buff,buff.length,group,30001);
            udpsocket.send(packet);//发送报文
            OutPutMessage.LogCatInfo("快速连接", "1111111");
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }


}

