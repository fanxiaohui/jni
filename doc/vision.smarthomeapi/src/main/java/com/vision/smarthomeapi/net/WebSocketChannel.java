/********************************************************************
 * 文件名称：NetworkManager.java
 * 所属项目名称：PowerSocket
 * 创建人：
 * 创建时间：2014-3-17 下午3:31:11
 * Copyright (c) 2013 GD. All rights reserved.
 ********************************************************************/
package com.vision.smarthomeapi.net;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PhoneUtil;

import java.util.concurrent.TimeUnit;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;

public class WebSocketChannel {
    private int websocketC = 0;

    /**
     * WebSocket连接状态
     */
    public enum WSState {
        /**
         * 关闭状态
         **/
        WS_STATE_CLOSE,
        /**
         * 连接状态
         **/
        WS_STATE_CONNECT,
        /**
         * 验证中状态
         **/
        WS_STATE_AUTH_ING,
        /**
         * 打开状态
         **/
        WS_STATE_OPEN,
        /**
         * 打开中状态
         **/
        WS_STATE_OPENING;
    }

    public static final String TAG = "广域数据";
    /**
     * WebSocket当前状态
     */
    public WSState status;
    private String wsUrl;
    private int heartTime;
    private boolean heartBreakTime = true;
    private WebSocketConnection ws;
    private static WebSocketChannel websocketChannel;
    private HeartThread heartThread;
    private String key = "";

    public WebSocketChannel() {
        ws = new WebSocketConnection();
        status = WSState.WS_STATE_CLOSE;
    }

    public static WebSocketChannel defaultWebSocketChannel() {
        if (websocketChannel == null) {
            websocketChannel = new WebSocketChannel();
        }
        return websocketChannel;
    }

    /**
     * 连接WebSocket
     *
     * @param authKey   服务器返回key
     * @param heartTime 心跳时间
     * @param url       连接url
     * @throws WebSocketException
     */
    public void connectWS(final String authKey, final int heartTime, String url,final byte[] keyData) throws WebSocketException {
        status = WSState.WS_STATE_OPENING;
        if (url == null || "".equals(url)) {
            return;
        }
//        if (ws != null) {
//            ws.disconnect();
//        }
//        if (ws.isConnected()){
//            return;
//        }
        String[] keyArray = authKey.split("\\|");

        if (keyArray.length < 4) {
            return;
        }
        String userID = keyArray[0];//UserID;
        String user = SecurityUserManage.getShare().getUser().getUserID();

        if (userID == null || userID.equals("")) {
            return;
        }
        OutPutMessage.LogCatInfo(TAG, "连接状态--->" + ws.isConnected());
        if (userID.equals(user) && status == WSState.WS_STATE_CONNECT) {
            return;
        }else if (ws != null){
            ws.disconnect();
        }


      //  byte[] keyData = null;
//        OutPutMessage.LogCatInfo("链接WebSocket", "userID" + userID);
//        String key1 = keyArray[1];
//        String ket2 = keyArray[3];
        ws = new WebSocketConnection();
        String mac = PhoneUtil.getMacAddress(Controller.getContext());
        StringBuffer sb = new StringBuffer();
        key = sb.append(authKey).append("|").append(mac).append("A").append("|").append(0).toString();
        OutPutMessage.LogCatInfo(TAG, "获取Key值" + key);
        OutPutMessage.LogCatInfo("链接WebSocket", "获取Key值" + key);
        this.wsUrl = url;
        this.heartTime = heartTime;
        ws.connect(this.wsUrl, new WebSocket.ConnectionHandler() {
            @Override
            public void onOpen() {
                status = WSState.WS_STATE_OPEN;
                OutPutMessage.LogCatInfo("发送WebSocket", "发送Key值" +  ByteUtil.byteArrayToHexString(keyData));
                ws.sendBinaryMessage(keyData);

                status = WSState.WS_STATE_AUTH_ING;
                if (heartThread != null) {
                    heartThread.interrupt();//关闭线程
                    heartThread = null;
                }
                heartThread = new HeartThread(Constant.ThreadName.WS_HEART);
                heartThread.start();

//                MobclickAgent.onEvent(Controller.getContext(), "webSocket_onOpen", "webSocket打开的key-->" + authKey +
//                        "\n!!!webSocket打开的时间-->" + TimeUtils.dateTimeToString(new Date()));
                OutPutMessage.LogCatInfo("连接广域网", wsUrl + ":地址" + heartTime + "!!!!key的值：" + authKey);
            }

            @Override
            public void onClose(int i, String s) {
                OutPutMessage.LogCatInfo(TAG, "WebSocket已断开" + "i:" + i + "!!!String:" + status);
//                MobclickAgent.onEvent(Controller.getContext(), "webSocket_onClose", "webSocket错误号：" + i +
//                        "\n!!!webSocket错误信息：" + s.toString() +
//                        "\n!!!webSocket错误key值：" + authKey +
//                        "\n!!!webSocket关闭的时间-->" + TimeUtils.dateTimeToString(new Date()));
//                if (status != WSState.WS_STATE_CLOSE) {
//                    Controller.defaultController().handler.obtainMessage(2,"").sendToTarget();
//                }
                status = WSState.WS_STATE_CLOSE;
                if (heartThread != null) {
                    if (!heartThread.isInterrupted()) {
                        heartThread.interrupt();
                    }
                    heartThread = null;
                }
                heartThread = null;
            }


            @Override
            public void onTextMessage(String s) {
                OutPutMessage.LogCatInfo(TAG, "数据接收解析的长度---1------------》" + s.getBytes().length + "的长度" + ByteUtil.byteArrayToHexString(s.getBytes(), true));

            }

            @Override
            public void onRawTextMessage(byte[] bytes) {
                OutPutMessage.LogCatInfo(TAG, "数据接收解析的长度---2------------》" + bytes.length + "的长度" + ByteUtil.byteArrayToHexString(bytes, true));
            }

            @Override
            public void onBinaryMessage(byte[] bytes) {
                OutPutMessage.LogCatInfo(TAG, "数据接收解析的长度----3-----------》" + bytes.length + "的长度" + ByteUtil.byteArrayToHexString(bytes, true));
                if (bytes.length == 1) {
                    byte b = bytes[0];
                    //赋值给WebSocket连接成功标志
                    status = WSState.WS_STATE_CONNECT;
                    SecurityUserManage.getShare().webSocketConnectState(b);//交给用户管理类去处理
                } else if (bytes.length == 1 && bytes[0] == Controller.REPEAT_LOGIN) {
                    status = WSState.WS_STATE_CLOSE;
                    byte b = bytes[0];
                    SecurityUserManage.getShare().webSocketConnectState(b);//交给用户管理类去处理
                } else if (bytes.length > 1) {
                    status = WSState.WS_STATE_CONNECT;
                    NetworkMessage message = new NetworkMessage(false, bytes, null, Constant.NetWork.WAN_NET, null);
                    NetworkManager.defaultNetworkManager().reciveMessage(message);
                }
            }

            @Override
            public void onPingMessage(byte[] bytes) {
                OutPutMessage.LogCatInfo(TAG, "心跳发送 :" + "onPingMessage:::--->" + status.toString());
            }

            @Override
            public void onPongMessage(byte[] bytes) {
                heartBreakTime = true;
                OutPutMessage.LogCatInfo(TAG, "心跳接收 :" + "onPongMessage" + status.toString());
            }
        });
    }

    /**
     * 发送数据
     *
     * @param msg
     */
    public void sendMessage(byte[] msg) {
        OutPutMessage.LogCatInfo(TAG, "发送发送" + ByteUtil.byteArrayToHexString(msg));
        if (status != WSState.WS_STATE_CONNECT) {
            return;
        }
        if (ws == null) {
            return;
        }
        OutPutMessage.LogCatInfo(TAG, "发送长度：" + msg.length + "!!!!发送 : " + ByteUtil.byteArrayToHexString(msg, true));

        ws.sendBinaryMessage(msg);

    }

    /**
     * 关闭webSocket
     */
    public void closeWebSocket() {
        status = WSState.WS_STATE_CLOSE;
        OutPutMessage.LogCatInfo("链接WebSocket", "closeWebsocket :" + status.toString());
        if (ws != null) {
            ws.disconnect();
        }
    }

    private class HeartThread extends Thread {

        private HeartThread(String threadName) {
            super(threadName);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (!heartBreakTime) {
                    if (ws != null) {
                        ws.disconnect();
                    }
                } else {
                    OutPutMessage.LogCatInfo(TAG, "发送心跳 :" + status.toString());
                    if (ws != null) {
                        ws.sendPingMessage(null);
                    }
                }
                try {
                    TimeUnit.SECONDS.sleep(heartTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

