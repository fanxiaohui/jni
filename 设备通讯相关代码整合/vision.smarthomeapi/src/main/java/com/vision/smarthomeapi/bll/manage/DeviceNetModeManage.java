package com.vision.smarthomeapi.bll.manage;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.net.QuickConnect;
import com.vision.smarthomeapi.net.UDPChannel;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.NetworkControl;
import com.vision.smarthomeapi.util.Notification;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 设备网络切换管理
 */
public class DeviceNetModeManage {
    //网络配置方法
    private NetworkControl networkControl;
    //开启线程
    private WifiConnectThread wifiConnectThread;
    private KeyRetrieve keyRetrieve;
    private int index = 0;
    private DeviceNetModeResult deviceNetModeResult;
    private Timer timeOutTimer;
    private Context context;
    public static final int STA = 0;

    public static final int AP = 0x01;

    //切换到的AP网
    //guru- "加上mac后3位"

    //AP网密码
    private String AppPs = "12345678";


    public static final String WIFI_TAG = "AP功能设置";





    /**
     * 构造方法
     */
    public DeviceNetModeManage(Context context) {
        this.context = context;
        networkControl = new NetworkControl(context);
    }


    //********************事件注册***************************************

    /**
     * 监听网络改变接口
     */
    public void setOnDeviceNetModeResult(DeviceNetModeResult deviceNetModeResult) {
        this.deviceNetModeResult = deviceNetModeResult;
    }

    private void addSuccess(Notification notification) {
        String name = notification.name;
        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "addSuccess回复名称：：：：" + name);
        if (name.equals(Constant.NotificationType.LAN_FIND_DEVICE)) {
            stopKeyRetrieve();
        }
        if (deviceNetModeResult != null) {
            switch (name) {
                case Constant.NotificationType.LAN_OK:
                    String device = (String) notification.arg;
                    OutPutMessage.LogCatInfo("广播回复", "LANOK回调::" + device);
                    if (device != null) {
                        if (device.equals(addDeviceMac)) {
                            if (mc != null) {
                                mc.cancel();
                                mc = null;
                            }
                            if (deviceNetModeResult != null) {
                                deviceNetModeResult.addSuccess(DeviceNetModeResult.NetMode.AP_OK, notification);
                            }
                            Controller.canSearch = false;
                            NotificationManager.defaultManager().removeObserver(this);
                        }
                    }
                    break;
                case Constant.NotificationType.ACTIVITY_AP_ING:
                    NotificationManager.defaultManager().removeObserver(this);
                    NotificationManager.defaultManager().addObserver(this, Constant.NotificationType.ACTIVITY_AP_OK, "addSuccess");
                    OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "当前::" + notification.arg.toString());
                    deviceNetModeResult.addSuccess(DeviceNetModeResult.NetMode.AP_ING, notification);
                    break;
                case Constant.NotificationType.ACTIVITY_AP_OK://设备重启
                    if (timeOutTimer != null) {
                        timeOutTimer.cancel();
                        timeOutTimer = null;
                    }
                    OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "当前ACTIVITY_AP_OK::" + notification.arg.toString());
                    if (deviceNetModeResult != null) {
                        deviceNetModeResult.addSuccess(DeviceNetModeResult.NetMode.AP_OK, notification);
                    }
                    break;
                case Constant.NotificationType.ADD_DEVICE_GATEWAY:
                    if (deviceNetModeResult != null) {
                        SmartDevice smartDevice = (SmartDevice) notification.arg;
                        int code = smartDevice.getSmartDeviceLogic().getStateReturnCode();
                        switch (code) {
                            case 0:
                                deviceNetModeResult.addSuccess(DeviceNetModeResult.NetMode.AddDevice, notification);
                                break;
                            case 2002://子设备连接超时
                                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
                                break;
                            case 2005://子设备已满
                                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.FULL);
                                break;
                        }


                    }
                    break;
            }
        }
        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "addSuccess----------------->");
    }


    //********************事件注册***************************************

    //**************************Ap功能**********************************************
    public void connectWifiThread(String ssId, String pw, String apSsId, boolean connect) {
        wifiConnectThread = null;
        wifiConnectThread = new WifiConnectThread(context, ssId, pw, apSsId, connect);
        wifiConnectThread.setName("WIFI");
        wifiConnectThread.start();
    }



    /**
     * 连接当前设备AP
     */
    public void connectDeviceApMode(String ssId, String pw, String apSsId) {
        connectWifiThread(ssId, pw, apSsId, true);
        NotificationManager.defaultManager().addObserver(this, Constant.NotificationType.ACTIVITY_AP_ING, "addSuccess");
        timeOutTimer = new Timer();
        timeOutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "当前运行超时::");
                NotificationManager.defaultManager().removeObserver(DeviceNetModeManage.this);
                if (deviceNetModeResult != null) {
                    deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
                }
                if (timeOutTimer != null) {
                    timeOutTimer.cancel();
                    this.cancel();
                    timeOutTimer = null;
                }
            }
        }, 120 * 1000);//60秒未连接则超时
    }

    /**
     * 断开WIFI连接
     */
    public void disconnectDeviceApMode() {
        if (wifiConnectThread != null) {
            wifiConnectThread.wifiDisconnect(true);
        }
        clearTimer();
    }

    public void connectQuickConnectThread(String mac,String ssid,String passWord) {
        mc = new Countdown(80 * 1000, 1000);
        mc.start();
        this.addDeviceMac = mac;
        quickConnectThread = new QuickConnectThread(mac,ssid,passWord);
        quickConnectThread.start();
        NotificationManager.defaultManager().addObserver(this, Constant.NotificationType.LAN_OK, "addSuccess");

    }

    /**
     * 倒计时
     */
    private Countdown mc;

    private String addDeviceMac;

    private QuickConnectThread quickConnectThread;

    /*定义一个倒计时的内部类*/
    private class Countdown extends CountDownTimer {
        public Countdown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            //超时
            if(deviceNetModeResult != null){
                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
            }
            Controller.canSearch = false;
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    }


    private class QuickConnectThread extends Thread{
        private String mac;
        private String ssid;
        private String passWord;
        public QuickConnectThread(String mac,String ssid,String passWord){
             this.mac = mac;
             this.ssid = ssid;
             this.passWord = passWord;
        }

        @Override
        public void run() {
            super.run();
            Controller.canSearch = true;
            OutPutMessage.LogCatInfo("快速连接", "发送MAC : " + mac + "!!!发送" + ssid + "!!!!" + passWord);
            QuickConnect connect = new QuickConnect(ByteUtil.hexStr2Byte(mac), ssid, passWord);
            connect.doInBackground();
        }
    }

    /**
     * 尝试连接AP网络线程
     */
    private class WifiConnectThread extends Thread {
        private boolean available;

        private Context context;
        private boolean isConnected;
        private String apssid;
        private String ssid;
        private String pass;
        private boolean isConnectedDevice;

        public WifiConnectThread(Context context, String ssid, String pass, String apssid, boolean isConnectedDevice) {
            available = false;
            this.context = context;
            this.isConnected = false;
            this.apssid = apssid;
            this.ssid = ssid;
            this.pass = pass;
            this.isConnectedDevice = isConnectedDevice;
        }

        @Override
        public void run() {
            super.run();
            //检测当前WIFI状态
            for (int number = 0; number < 3; number++) {//3次检测设备状态
                if (!isConnected) {//是否取消连接
                    //获取Wifi网卡状态
                    int state = networkControl.checkState();
                    switch (state) {
                        case WifiManager.WIFI_STATE_ENABLING://WIFI网卡正在打开
                        case WifiManager.WIFI_STATE_DISABLING://WIFI正在关闭
                        case WifiManager.WIFI_STATE_UNKNOWN://未知网卡状态
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case WifiManager.WIFI_STATE_DISABLED://WIFI网卡不可用
                            networkControl.openWifi();//打开WIFI
                            try {
                                sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            break;
                        case WifiManager.WIFI_STATE_ENABLED://WIFI网卡可用
                            available = true;
                            break;
                    }
                }
            }
            OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册00：" + available + "----------------------" + isConnectedDevice + "!!!isConnected" + isConnected);
            OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册11：" + networkControl.isWifiEnabled());

            //如果WIFI连接可用，并且属于可连接标记
            if (available && !isConnected) {
                String ap_ssid = "";
                String passWd = "";
                NetworkControl.SafeMode safeMode = null;
                if (isConnectedDevice) {//连接设备Ap
                    ap_ssid = apssid;
                    apSsId = apssid;
                    passWd = AppPs;
                    safeMode = NetworkControl.SafeMode.WPA;
                    OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "注册-------------->：" + ap_ssid + "密码：" + passWd);
                    boolean state = networkControl.connectWifiToSSID(ap_ssid, passWd, safeMode);
                    if (!state) {
                        OutPutMessage.showToast("AP连接失败");
                    }
                } else {
                    WifiConfiguration wifiConfiguration = networkControl.IsExsits(ssid);
                    OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册1：" + wifiConfiguration);

                    if (wifiConfiguration != null) {
                        ap_ssid = ssid;
                        BitSet bitSet = wifiConfiguration.allowedKeyManagement;
                        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册2：" + bitSet.toString());
                        if (bitSet.get(WifiConfiguration.KeyMgmt.NONE)) {
                            safeMode = NetworkControl.SafeMode.NO_PASSWORD;
                        } else if (bitSet.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
                            safeMode = NetworkControl.SafeMode.WPA;
                        } else if (bitSet.get(WifiConfiguration.KeyMgmt.WPA_EAP)) {
                            safeMode = NetworkControl.SafeMode.WPA;
                        } else {
                            safeMode = NetworkControl.SafeMode.NO_PASSWORD;
                        }
                        passWd = pass;
                        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册2：" + ap_ssid + "密码：" + passWd + "模式：" + safeMode);
                        boolean state = networkControl.connectWifiToSSID(ap_ssid, passWd, safeMode);
                        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "回复网络注册2：" + state);
                    }

                }
                available = false;//并初始化状态
            }
        }

        public void wifiDisconnect(boolean isConnected) {
            this.isConnected = isConnected;
        }
    }

    private static String apSsId = "";


    public static String getApSsid() {
        return apSsId;
    }

    //**************************Ap功能**********************************************

    /**
     * 返回Wifi列表
     *
     * @return
     */
    public List<String> getWifiList() {
        List<ScanResult> strings = networkControl.getWifiNetWorkList();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i).SSID;
            if (!TextUtils.isEmpty(s)) {
                stringList.add(s);
            }
        }
        return stringList;
    }


    /**
     * 通过扫描二维码添加设备
     *
     * @param newDevice 需要添加的设备
     * @param netWork   需要添加设备的网络类型
     */
    public void directAddDevice(SmartDevice newDevice, int netWork) {
        int i = SmartDeviceManage.defaultManager().addDevice(newDevice, netWork);
        if (deviceNetModeResult != null) {
            if (i == SmartDeviceConstant.addError) {
                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.Other);
            } else if (i == SmartDeviceConstant.addExist) {
                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.DeviceExist);
            } else {
                deviceNetModeResult.addSuccess(DeviceNetModeResult.NetMode.AddDevice, null);
            }
        }
    }

    /**
     * 将设备添加到网关
     *
     * @param mac 需要添加到网关的子设备
     */
    public void addDeviceToGetaway(String mac) {
        List<SmartDevice> smartDeviceList = SmartDeviceManage.defaultManager().findGatewayDeviceList();

        if (smartDeviceList.size() > 0) {
            SmartDevice smartDevice = smartDeviceList.get(0);
            smartDevice.getSmartDeviceLogic().addSubDevice(mac, (byte) 0);
            NotificationManager.defaultManager().addObserver(this, Constant.NotificationType.ADD_DEVICE_GATEWAY, "addSuccess");
            timeOutTimer = new Timer();
            timeOutTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    NotificationManager.defaultManager().removeObserver(DeviceNetModeManage.this);
                    if (deviceNetModeResult != null) {
                        deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
                    }
                    timeOutTimer = null;
                }
            }, 5 * 1000);
        } else {
            if (deviceNetModeResult != null) {
                deviceNetModeResult.addFail(DeviceNetModeResult.Fail.NotFindGetaway);
            }
        }
    }

    /**
     * 通过Zigbee设置设备网络（目前主要提供给空调使用）
     *
     * @param ssid   网络名称
     * @param passwd 网络密码
     * @param device 需要配置网络的设备对象
     */
    public void sendNetworkToZigbee(String ssid, String passwd, SmartDevice device) {
        //  BCControl.defaultManager().sendGatewayNetwork(ssid, passwd, device);
        NotificationManager.defaultManager().addObserver(this, Constant.NotificationType.ACTIVITY_AP_OK, "addSuccess");
        timeOutTimer = new Timer();
        timeOutTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                NotificationManager.defaultManager().removeObserver(DeviceNetModeManage.this);
                if (deviceNetModeResult != null) {
                    deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
                }
                timeOutTimer = null;
            }
        }, 5 * 1000);
    }


//    public enum WiFiMode {
//        AUTO("自动", 0),
//        OPEN("OPEN", 1),
//        WEP_64_OPEN("WEP64（开放）", 2),
//        WEP_64("WEP64（共享）", 3),
//        WEP_128_OPEN("WEP128（开放）", 4),
//        WEP_128("WEP128（共享）", 5),
//        WPA_TKIP("WPA-TKIP", 6),
//        WPA_AES("WPA-AES", 7),
//        WPA2_TKIP("WPA2-TKIP", 8),
//        WPA2_AES("WPA2-AES", 9);
//        private String str;
//        private int mode;
//
//        WiFiMode(String s, int m) {
//            this.str = s;
//            this.mode = m;
//        }
//
//
//        @Override
//        public String toString() {
//            return str;
//        }
//
//        public static int toMode(String str) {
//            int m = 0;
//            switch (str) {
//                case "自动":
//                    m = AUTO.mode;
//                    break;
//                case "OPEN":
//                    m = OPEN.mode;
//                    break;
//                case "WEP64（开放）":
//                    m = WEP_64_OPEN.mode;
//                    break;
//                case "WEP64（共享）":
//                    m = WEP_64.mode;
//                    break;
//                case "WEP128（开放）":
//                    m = WEP_128_OPEN.mode;
//                    break;
//                case "WEP128（共享）":
//                    m = WEP_128.mode;
//                    break;
//                case "WPA-TKIP":
//                    m = WPA_TKIP.mode;
//                    break;
//                case "WPA-AES":
//                    m = WPA_AES.mode;
//                    break;
//                case "WPA2-TKIP":
//                    m = WPA2_TKIP.mode;
//                    break;
//                case "WPA2-AES":
//                    m = WPA2_AES.mode;
//                    break;
//            }
//            return m;
//        }
//    }

    /**
     * 清理定时
     */
    private void clearTimer() {
        if (timeOutTimer != null) {
            timeOutTimer.cancel();
            timeOutTimer = null;
        }
    }

    /**
     * 开启一键连接
     */
    public void startKeyRetrieve(String ssid, String key) {
        keyRetrieve = null;
        keyRetrieve = new KeyRetrieve(UDPChannel.getUDPChannel(), ssid, key);
        keyRetrieve.setName("KEY_RETRIEVE");
        keyRetrieve.start();
    }

    /**
     * 关闭一键连接
     */
    public void stopKeyRetrieve() {
        if (keyRetrieve != null) {
            keyRetrieve.findDevice();//重置状态位
            keyRetrieve.interrupt();//关闭线程
            keyRetrieve = null;
        }
    }


    public class KeyRetrieve extends Thread {

        private UDPChannel udpChannel;
        private String ssid;
        private String key;

        public KeyRetrieve(UDPChannel udpChannel, String ssid, String key) {
            this.udpChannel = udpChannel;
            this.ssid = ssid;
            this.key = key;
        }

        //一键连接相关常量
        private final int flag1 = 3;
        private final int flag2 = 15;
        private final int bassLength = 300;
        private final int startFlag = 600;
        private final int bitLength = 30;
        byte[][] m_maskKeyList = {
                {0x54, 0x68, 0x69, 0x73},
                {0x27, 0x73, 0x20, 0x47},
                {0x55, 0x52, 0x55, 0x20},
                {0x50, 0x61, 0x74, 0x2e},
        };
        public boolean isContinueOneKey;
        //一键连接相关常量 end

        @Override
        public void run() {
            super.run();
            onKeyConnect(ssid, key);
        }

        //一件连接相关方法  ，有搜索应答就返回成功
        public void onKeyConnect(String ssid, String key) {
            isContinueOneKey = true;
            String sendStr = ssid + "\n" + key;
            byte[] sendData = sendStr.getBytes();
            NetworkManager s = NetworkManager.defaultNetworkManager();
            ContextData contextData = new ContextData();
            contextData.length = (byte) sendData.length;  //数据长度
            contextData.crypt = (byte) (0x80 + System.currentTimeMillis() % 4);//密码
            contextData.aux = (byte) (udpChannel.udpprot - 30000);//端口,
            byte[] b = {contextData.aux, contextData.crypt};
            contextData.data = mask_content(sendData, m_maskKeyList[contextData.crypt & 0x03], 0);  //数据内容
            contextData.check = check_xor8(b, contextData.data);

            long begin = System.currentTimeMillis();
            if (udpChannel == null) {
                //  handler.sendEmptyMessage();
                return;
            }

            long value = 0;
            NotificationManager.defaultManager().addObserver(DeviceNetModeManage.this, Constant.NotificationType.LAN_FIND_DEVICE, "addSuccess");
            while (isContinueOneKey && value < 60000) {
                byte[] _bytes;
                _bytes = contextData.data;//mask_content(cd.data, m_maskKeyList[k], 0);
                //发送开始标记
                sendData(new int[]{startFlag + bassLength});
                //发送长度（SSID或KEY长度）
                sendData(new int[]{bitLength, (contextData.length & 0xff) + bassLength});
                //发送check
                sendData(new int[]{bitLength + 1, (contextData.check & 0xff) + bassLength});
                //发送crypt
                sendData(new int[]{bitLength + 2, (contextData.crypt & 0xff) + bassLength});
                //发送aux
                sendData(new int[]{bitLength + 3, (contextData.aux & 0xff) + bassLength});
                //发送有效数据（SSID 或 KEY）
                for (int i = 0; i < _bytes.length; i++) {
                    sendData(new int[]{bitLength + 4 + i, _bytes[i] + bassLength});
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value = System.currentTimeMillis() - begin;
                OutPutMessage.LogCatInfo("AddDeviceWiFiActivity", ssid + "!!!!!!!!!" + key + "发送数据---------------->" + value);
                if (deviceNetModeResult != null) {
                    deviceNetModeResult.addProgress(DeviceNetModeResult.Progress.SendData, (int) value / 600);
                }
            }
            if (isContinueOneKey && value > 60000) {
                if (deviceNetModeResult != null)
                    deviceNetModeResult.addFail(DeviceNetModeResult.Fail.TIME_OUT);
            }
            NotificationManager.defaultManager().removeObserver(DeviceNetModeManage.this);
        }

        private void sendData(int[] lengthArray) {
            byte[] bytes = new byte[2048];

            byte[] sendData;
            sendData = new byte[flag1];
            System.arraycopy(bytes, 0, sendData, 0, flag1);
            udpChannel.sendBinaryData(sendData, "255.255.255.255", 6546);
            sendData = new byte[flag2];
            System.arraycopy(bytes, 0, sendData, 0, flag2);
            udpChannel.sendBinaryData(sendData, "255.255.255.255", 6546);
            for (int length : lengthArray) {
                sendData = new byte[length];
                System.arraycopy(bytes, 0, sendData, 0, length);
                udpChannel.sendBinaryData(sendData, "255.255.255.255", 6546);
                // Log.i("AddDeviceWiFiActivity","发送数据---------------->");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private byte[] mask_content(byte[] payload, byte[] mask, int desp) {
            int iter = 0;
            int mask_index = 0;

            while (iter < payload.length) {
        /* rotate mask and apply it */
                mask_index = (iter + desp) % 4;
                payload[iter] ^= mask[mask_index];
                iter++;
            } /* end while */

            return payload;
        }

        private byte check_xor8(byte[] p1, byte[] p2) {
            byte check = 0, i;

            for (i = 0; i < p1.length; i++) {
                check ^= p1[i];
            }
            for (byte b : p2) {
                check ^= b;
            }
            return (byte) (~check);
        }

        //用来重置状态
        public void findDevice() {
            isContinueOneKey = false;
        }

        public class ContextData {
            byte length;
            byte crypt;
            byte check;
            byte aux;
            byte[] data;
        }

    }


    public interface DeviceNetModeResult {
        enum Fail {
            DeviceExist,
            NotFindGetaway,
            QRError,
            OpenWIFIFail,
            ConectAPFail,
            TIME_OUT,
            Other,
            FULL
        }

        enum Progress {
            OpenWIFI,
            ConectAP,
            SendData
        }

        enum NetMode {
            AP_ING,
            AP_OK,
            ZigBee,
            AddDevice
        }

        /**
         * 添加设备成功回调
         */
        void addSuccess(NetMode mode, Notification notification);

        /**
         * 添加设备失败回调
         *
         * @param fail 失败原因
         */
        void addFail(Fail fail);

        /**
         * 添加设备进度改变回调
         *
         * @param progress    具体步骤
         * @param progressNum 此步骤的百分百除SendData步外一般为0
         */
        void addProgress(Progress progress, int progressNum);

        void needSelectGateway(DeviceNetModeManage manage);

//        /**
//         * 需要输入网络名称和密码进行进一步添加
//         * @param manage 当前设备管理对象
//         * @param device 待添加设备
//         */
//        void needEnterNetworkInfo(AddDeviceManage manage,SmartDevice device);
    }
}
