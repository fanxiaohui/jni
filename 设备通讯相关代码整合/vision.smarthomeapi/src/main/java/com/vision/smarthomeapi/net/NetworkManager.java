package com.vision.smarthomeapi.net;

import android.os.Handler;
import android.os.Message;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.lang.ref.WeakReference;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import de.tavendo.autobahn.WebSocketException;


/**
 * Created by Lal on 2015/1/28.
 */
public class NetworkManager {

    public final static String NEI_TAG = "网络收发";

    private static NetworkManager mNetworkManager;

    private ReceiveThread mReceiveThread;
    private SendThread mSendThread;
    private BlockingQueue<NetworkMessage> mSendMessageList;
    private BlockingQueue<NetworkMessage> mReceiveMessageList;

    private UDPChannel udpChannel;
    private WebSocketChannel websocketChannel;
    private ZigbeeChannel zigbeeChannel;


    private Handler httpHandler;


    /**
     * 获取NetworkManager对象
     *
     * @return NetworkManager
     */
    public static NetworkManager defaultNetworkManager() {
        if (mNetworkManager == null) {
            mNetworkManager = new NetworkManager();
        }
        return mNetworkManager;
    }

    public static final String ZIGBEE_PATH = "/dev/zigbee";

    /**
     * 构造方法
     */
    private NetworkManager() {
        mSendMessageList = new ArrayBlockingQueue<NetworkMessage>(50, true);
        mReceiveMessageList = new ArrayBlockingQueue<NetworkMessage>(50, true);

        //http静态掉哟个不需要实例化
        //TCP目前需求不需要实现
        udpChannel = UDPChannel.getUDPChannel();
        websocketChannel = WebSocketChannel.defaultWebSocketChannel();
        if (Controller.isGreePhone()) {
            zigbeeChannel = ZigbeeChannel.getZigbeeChannel();
        }

        mReceiveThread = new ReceiveThread();
        mReceiveThread.start();

        mSendThread = new SendThread();
        mSendThread.start();

        httpHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                //TODO 新协议去掉/api
                NetworkMessage message = (NetworkMessage) msg.obj;

                if (message.isHttpGet()) {
                    SecurityHTTPChannel.sendMessageGet(message.getUri(), message.getParams(), message.getToken());
                } else {
                    SecurityHTTPChannel.sendMessagePost(message.getUri(), message.getParams(), message.getToken());
                }


            }
        };

    }


    /**
     * 将数据放入发送队列
     *
     * @param networkMessage
     */
    public void sendMessage(NetworkMessage networkMessage) {
        boolean needNotify = false;
        try {
            if (mSendMessageList.size() <= 0) {
//                needNotify = true;
            }

            mSendMessageList.put(networkMessage);
            OutPutMessage.LogCatInfo("MFS", "发送的数据1: " + ByteUtil.byteArrayToHexString(networkMessage.getBinaryMsg(), true));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        if (needNotify)
//            mSendThread.doNotify();

    }

    /**
     * 获取当前zigBee源ID
     */
    public String zigBeeSrc() {
        if (zigbeeChannel == null) {
            return "0";
        }
        return zigbeeChannel.getZigBeeSrc();
    }

    /**
     * zigbee睡眠
     */
    public void sleepZigBee() {
        zigbeeChannel.sendMessage(null, null, (byte) 0x2F);
    }

    /**
     * zigbee唤醒
     */
    public void wakeZigBee() {
        zigbeeChannel.sendMessage(null, null, (byte) 0x02);
    }

    /**
     * 尝试连接websocket
     *
     * @param authKey  websocket认证key
     * @param hearTime 心跳时间
     * @param url      服务器地址 例：ws://xx.xx
     * @return 连接方法调用状态，返回true并不代表成功连接和登录，仅表示方法调用成功。判断登录状态需要根据服务器返回数据判断（走报文接收通道）。
     */
    public boolean connectWebSocket(String authKey, int hearTime, String url,byte[] key) {
        try {
            websocketChannel.connectWS(authKey, hearTime, url,key);
            return true;
        } catch (WebSocketException e) {

            OutPutMessage.LogCatInfo("websocket", "websocket异常：" + e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 断开websocket连接
     */
    public void closeWebSocket() {

        websocketChannel.closeWebSocket();
    }

    /**
     * 广域网连接状态
     *
     * @return
     */
    public WebSocketChannel.WSState getState() {
        return websocketChannel.status;
    }

    protected void reciveMessage(NetworkMessage networkMessage) {
        try {
            mReceiveMessageList.put(networkMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class ReceiveThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
                if (mReceiveMessageList == null) {
                    continue;
                }
                try {
                    NetworkMessage networkMessage = mReceiveMessageList.take();
                    if (networkMessage == null) {
                        continue;
                    }
                    OutPutMessage.LogCatInfo("接收数据", "headData  parseSmartHeadInfo--->" + ByteUtil.byteArrayToHexString(networkMessage.getBinaryMsg()) + "   ");
                    Controller.defaultController().receiveCode(networkMessage);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

//        public void doWait() {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public synchronized void doNotify() {
//            notify();
//        }
    }


    private class SendThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    return;
                }
                if (mSendMessageList == null) {
                    continue;
                }
                try {
                    NetworkMessage msg = mSendMessageList.take();
                    if (msg == null) {
                        continue;
                    }
                    if (msg.isSend()) {
                        switch (msg.getNetWorkType()) {
                            case Constant.NetWork.HTTP:
                                //使用Handler_Http第三方框架本身已经属于异步方式
                                Message message = new Message();
                                message.obj = msg;
                                httpHandler.sendMessage(message);
                                break;
                            case Constant.NetWork.LAN_NET:
                                udpChannel.sendBinaryData(msg.getBinaryMsg(), msg.getIp());
                                break;
                            case Constant.NetWork.WAN_NET:
                                websocketChannel.sendMessage(msg.getBinaryMsg());
                                break;
                            case Constant.NetWork.ZIG_BEE_NET:
                                if (Controller.isGreePhone()) {
                                    zigbeeChannel.sendMessage(msg.getBinaryMsg(), msg.getMac());
                                }
                                break;
                        }
                        clearObject(msg);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 清理掉NetWork
         *
         * @param networkMessage
         */
        private void clearObject(NetworkMessage networkMessage) {
            WeakReference weakReference = new WeakReference(networkMessage);
            weakReference.clear();
        }


//        public void doWait() {
//            try {
//                wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        public synchronized void doNotify() {
//            notify();
//        }
    }
}
