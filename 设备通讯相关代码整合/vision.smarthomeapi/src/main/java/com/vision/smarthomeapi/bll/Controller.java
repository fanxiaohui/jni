package com.vision.smarthomeapi.bll;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;

import com.smarthome.head.SmartHomeConstant;
import com.smarthome.head.SmartHomeData;
import com.smarthome.head.SmartHomeHead;
import com.vision.smarthomeapi.bean.RWebSocketInfo;
import com.vision.smarthomeapi.bll.manage.DeviceUpgradeManage;
import com.vision.smarthomeapi.bll.manage.ResendQueueManage;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.DeviceSendHeadData;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.data.SmartDeviceLogic;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.net.WebSocketChannel;
import com.vision.smarthomeapi.receiver.NetWorkListener;
import com.vision.smarthomeapi.receiver.ScreenListener;
import com.vision.smarthomeapi.sqlutil.LitePalApplication;
import com.vision.smarthomeapi.sqlutil.tablemanager.Connector;
import com.vision.smarthomeapi.util.AESUtils;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.FileIO;
import com.vision.smarthomeapi.util.NetworkUtils;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PhoneUtil;
import com.vision.smarthomeapi.util.ScreenManager;
import com.vision.smarthomeapi.util.StringUtil;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanglong on 2015/9/15.
 */
public class Controller implements ScreenListener.ScreenStateListener, NetWorkListener.NetWorkStateListener {

    public static boolean getShortQ = true;
    public static String UPDATE_APP_INFO = "";
    /**
     * 内网升级设备
     */
    //public static String UPDATE_URL = "";
    /**
     * 测试使用
     */
    //public static final String SERVER_URL = "http://app.api.5127life.cn:10004";
    /**
     * 开发使用
     */
    public static String SERVER_URL = "";


    public static final String MFS_TAG = "MFS_TAG";
    public static final String ZIGBEE = "Zigbee";
    /**
     * ZigBee路径
     */
    public static final String ZIGBEE_PATH = "/dev/zigbee";

    public static void setContext(Context mContext) {
        context = mContext;
    }

    public static Context getContext() {
        return context;
    }

    private static boolean isGreePhone;

    public static boolean isGreePhone() {
        return isGreePhone;
    }

    public static boolean canSearch = false;

    public final static int OK = 0;//连接成功
    public final static int REPEAT_LOGIN = 12;//重复登陆
    public final static int CLOSE = -1;//断开

    /**
     * 获取APP升级
     */
    public static boolean APPUpade = false;

    public enum ApplicationState {
        /**
         * 程序在前台
         */
        ApplicationActivate,
        /**
         * 程序在后台
         */
        ApplicationBackground;

    }

    public enum ScreenState {
        /**
         * 开屏状态
         */
        ScreenOn,
        /**
         * 锁屏状态
         */
        ScreenOff,
        /**
         * 解锁状态
         */
        serPresent;
    }

    public enum NetWorkState {
        /**
         * WIFI状态
         */
        Net_WIFI,
        /**
         * 3G状态
         */
        Net_3G,
        /**
         * 无网状态
         */
        Net_Non;
    }

    /**
     * 上下文
     */
    private static Context context;
    /**
     * Controller实例
     */
    private static Controller defaultController;
    /**
     * 程序状态
     */
    public ApplicationState applicationState;
    /**
     * 屏幕状态
     */
    public ScreenState screenState;
    /**
     * 网络状态
     */
    public NetWorkState netWorkState;
    /**
     * 回调管理
     */
    private NotificationManager notificationManager;
    /**
     * 网络管理
     */
    private NetworkManager networkManager;
    /**
     * 设备管理
     */
    private SmartDeviceManage smartDeviceManage;

    private Activity currentActivity;

    private List<Activity> activityList;

    public static boolean isRetrieve = false;

    private SecurityUserManage securityUserManage;

    private Controller() {
        activityList = new ArrayList<>();
    }

    /**
     * 获取Controller实例
     *
     * @return Controller
     */
    public static Controller defaultController() {
        if (defaultController == null) {
            defaultController = new Controller();
        }
        return defaultController;
    }

    /**
     * 程序启动流程
     */
    private void APPStart() {
        NetWorkListener netWorkListener = NetWorkListener.defaultNetListener(context);
        netWorkListener.begin(this);
        ScreenListener screenListener = ScreenListener.defaultScreenListener(context);
        screenListener.begin(this);

        this.applicationState = ApplicationState.ApplicationActivate;
        createAsyncObject();

        String userID = StringUtil.getUserID();
        if (!userID.equals("")) {
            String PASS_WORD = "";
            try {
                PASS_WORD = FileIO.getShareFielIo(Controller.getContext()).readFile("pw");
            } catch (Exception e) {
                e.printStackTrace();
            }

            OutPutMessage.LogCatInfo("密钥", "存储密钥：" + PASS_WORD);
            SmartDeviceLogic.PASS_WORD = ByteUtil.hexStr2Byte(PASS_WORD);
            OutPutMessage.LogCatInfo("密钥", "16进制密钥" + ByteUtil.byteArrayToHexString(SmartDeviceLogic.PASS_WORD));
        }
        sendRefreshToken();
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    RWebSocketInfo rWebSocketInfo = (RWebSocketInfo) msg.obj;
                    DeviceSendHeadData smartDeviceHead = new DeviceSendHeadData("");
                    String mac = PhoneUtil.getMacAddress(Controller.getContext());
                    StringBuffer sb = new StringBuffer();
                    String key = sb.append(SecurityUserManage.token).append("|").append(mac).append("ANDROID").toString();
                    OutPutMessage.LogCatInfo("发送WebSocket", "发送Key值" + key);
                    byte[] data = smartDeviceHead.sendWanHeadData(key.getBytes());

                    networkManager.connectWebSocket(rWebSocketInfo.getProxyKey(),
                            Integer.valueOf(rWebSocketInfo.getHeartBeat()), rWebSocketInfo.getProxyAddr(), data);
                    break;
                case 2:
                    break;
                case 3://获取局域网列表
                    if (!isRetrieve) {//是否在检索中
                        isRetrieve = true;
                        smartDeviceManage.sendLanRetrieve();
                    }
                    break;
            }
        }
    };

    /**
     * 记录当前Activity
     *
     * @param activity
     */
    public void currentActivity(Activity activity) {
        OutPutMessage.LogCatInfo("跳转录入", activity.toString());
        this.currentActivity = activity;
        ScreenManager.getScreenManager().addActivityToStack(activity);
    }

    /**
     * 程序重新启动流程，如从后台切入前台，锁屏后开屏等操作
     */
    private void APPResume(String string) {
        sendWebSocketInfo();
        isApMode = false;
    }

    /**
     * 初始化对象
     *
     * @param context    上下文
     * @param serverPath 服务器地址
     * @param appPath    App升级地址
     */
    public void initialize(Context context, String serverPath, String appPath) {
        this.context = context;
        LitePalApplication.initialize(context);

        APPStart();
        File file = new File(Constant.ZIGBEE_PATH);
        isGreePhone = file.exists();

        OutPutMessage.createToastHandler();//toast管理
        notificationManager = NotificationManager.defaultManager();//消息通知管理
        networkManager = NetworkManager.defaultNetworkManager();//网络管理
        smartDeviceManage = SmartDeviceManage.defaultManager();//设备管理
        SERVER_URL = serverPath;
        UPDATE_APP_INFO = appPath;
//        UPDATE_URL = updatePath;
        DeviceUpgradeManage.defaultDeviceUpgradeManager();//升级管理
    }

    /**
     * 程序暂停流程，如切入后台，锁屏等操作
     */
    public void APPStop() {
        OutPutMessage.LogCatInfo("事件测试", "APPStop");
        if (!isApMode) {
            notificationManager.postNotification(Constant.NotificationType.APP_STATE_CHANGE, "");
        }
        smartDeviceManage.cleaningDeviceHead();
        ResendQueueManage.defaultQueue().removeAll();
        //断开WebSocket
        networkManager.closeWebSocket();
    }

    private boolean isApMode;

    public void addApMode(boolean isApMode) {
        this.isApMode = isApMode;
    }

    /**
     * 程序结束流程
     */
    private void APPFinish() {
        NetWorkListener netWorkListener = NetWorkListener.defaultNetListener(context);
        netWorkListener.unregisterListener();
        ScreenListener screenListener = ScreenListener.defaultScreenListener(context);
        screenListener.unregisterListener();
    }

    /**
     * 所有发码都要通过此方法鉴定是否可以发码
     *
     * @return 是否
     */
    public boolean APPSendCode() {
        if (!NetworkUtils.isNetworkAvailable()) {
            OutPutMessage.showToast("网络异常");
            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.SECURITY_HTTP_ERROR);
        }
        return true;
    }

    /**
     * 从设备用户层等收到数据发给网络层
     *
     * @param message
     */
    public void sendCode(NetworkMessage message) {
        if (message == null) {
            throw new NullPointerException("this NetworkMessage is NullPointer");
        }
        networkManager.sendMessage(message);
    }

    /**
     * 从网络层接收到的数据并且分发
     *
     * @param message
     */
    public void receiveCode(NetworkMessage message) {
        //解析头信息用来获取设备mac 和协议id
        OutPutMessage.LogCatInfo("接收数据", ByteUtil.byteArrayToHexString(message.getBinaryMsg()));

        SmartHomeData _headData = SmartHomeHead.parseData(message.getBinaryMsg(), (short) 0, (short) 0, new byte[]{}, smartDeviceManage.noSecretKey(), false);//false data为null

        OutPutMessage.LogCatInfo("结完数据头后的接收数据", ByteUtil.byteArrayToHexString(message.getBinaryMsg()));
        // OutPutMessage.LogCatInfo("接收数据", "消息ID：" + Integer.toHexString( _headData.msgID) + "!!!!!数据receiveCode：" + ByteUtil.byteArrayToHexString(message.getBinaryMsg(), true));
        OutPutMessage.LogCatInfo("接收数据", "消息ID：" + Integer.toHexString(_headData.msgID) + "opcode：" + Integer.toHexString(_headData.opcode) + "!!!!!数据receiveCode：" + ByteUtil.byteArrayToHexString(message.getBinaryMsg(), true));
        if (isHeadData(_headData)) {
//            OutPutMessage.LogCatInfo("接收数据", "SmartHomeData  1  数据内容--》" + _headData.toString());

            if (_headData.opcode == SmartHomeConstant.OPCode.WAN_PROXY) {//平台代理
                smartDeviceManage.parseNetWorkData(message);
            } else if (_headData.opcode == SmartHomeConstant.OPCode.WAN_PLATFORM) {//与平台交互

                if (_headData.code == 0 && _headData.msgID == SmartDeviceConstant.MsgID.WAN_ONLINE) {//连接广域网成功

                    SmartHomeData msgData = SmartHomeHead.parseData(message.getBinaryMsg(), (short) 0, (short) 0, new byte[]{}, smartDeviceManage.noSecretKey(), true);
                    OutPutMessage.LogCatInfo("key", ByteUtil.byteArrayToHexString(SecurityUserManage.pwEncrypt));
                    OutPutMessage.LogCatInfo("key  秘钥传输  原文", ByteUtil.byteArrayToHexString(msgData.data));

                    byte[] array = AESUtils.decrypt(msgData.data, SecurityUserManage.pwEncrypt);
                    OutPutMessage.LogCatInfo("key  秘钥传输   解密", ByteUtil.byteArrayToHexString(array));
                    SecurityUserManage.tmpEncrypt = array;
                    OutPutMessage.LogCatInfo("key 秘钥传输", ByteUtil.byteArrayToHexString(array));
                    SecurityUserManage.getShare().webSocketConnectState(0);
                }

                if (_headData.code == 0 && _headData.msgID == SmartDeviceConstant.MsgID.MESSAGE_PLATFORM_INFO) {
                    SmartHomeData msgData = SmartHomeHead.parseData(message.getBinaryMsg(), (short) 0, (short) 0, new byte[]{}, smartDeviceManage.noSecretKey(), true);
                    if (isHeadData(msgData)) {
                        parseMessageInfo(msgData.data);
                    }
                } else if (_headData.msgID == SmartDeviceConstant.MsgID.DEVICE_NOT_ONLINE) {
                    OutPutMessage.LogCatInfo("心跳下线协议", "接收  不做处理");
                } else {//设备其它消息指令，交给设备管理类处理
                    //smartDeviceManage.parseNetWorkData(message);
                }
            } else {
                if (_headData.code == 0 && _headData.msgID == SmartDeviceConstant.MsgID.MESSAGE_PLATFORM_INFO) {
                    SmartHomeData msgData = SmartHomeHead.parseData(message.getBinaryMsg(), (short) 0, (short) 0, new byte[]{}, smartDeviceManage.noSecretKey(), true);
                    if (isHeadData(msgData)) {
                        parseMessageInfo(msgData.data);
                    }
                } else if (_headData.msgID == SmartDeviceConstant.MsgID.DEVICE_NOT_ONLINE) {
                    OutPutMessage.LogCatInfo("心跳下线协议", "接收  不做处理");
                } else {//设备其它消息指令，交给设备管理类处理
                    smartDeviceManage.parseNetWorkData(message);
                }
            }
        }
    }

    /**
     * 解析消息推送，并交给相关管理类去处理
     *
     * @param data
     */
    private void parseMessageInfo(byte[] data) {
        ByteBuffer md = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        md.put(data, 0, data.length);
        //消息类型码 1
        byte msgType = md.get(0);
        OutPutMessage.LogCatInfo("消息推送接收", "消息-->：" + msgType);
        //判断消息类型，跳转不同的Activity
        switch (msgType) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 11:
            case 12:
                return;//用户类消息，交给用户管理类
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
                smartDeviceManage.parseDeviceMessageInfo(data);
                return;//设备类消息，交给设备管理类
        }
    }

    /**
     * 验证头数据
     *
     * @param _headData
     * @return
     */
    private boolean isHeadData(SmartHomeData _headData) {
        OutPutMessage.LogCatInfo("接收数据", "数据_isHeadData：" + _headData.toString());
        if (_headData == null) {
            throw new NullPointerException("this SmartHomeData is NullPointer");
        }
        if (_headData.code != 0) {//如果返回码错误直接返回
            return false;
        }
        return true;
    }

    @Override
    public void changeScreenState(ScreenState state) {
        this.screenState = state;
        if (screenState == ScreenState.ScreenOn) {
            APPResume("changeScreenState");
        } else if (screenState == ScreenState.ScreenOff) {
            APPStop();
        }
    }

    @Override
    public void changeNetState(NetWorkState state, NetworkInfo networkInfo) {
        OutPutMessage.LogCatInfo("网络事件测试", "changeNetState::" + state);
        this.netWorkState = state;
        notificationManager.postNotification(Constant.NotificationType.NET_STATE_CHANGE, "");
        //如果前台网络改变，则刷新
        if (applicationState == ApplicationState.ApplicationActivate) {
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                this.netWorkState = state;
                OutPutMessage.LogCatInfo("网络事件测试", "state :" + state);
                APPResume("changeNetState");
            } else {
                OutPutMessage.LogCatInfo("网络事件测试", "changeNetState::");
                APPStop();
            }
        }
        //当网络变化时自动重连一次（除非完全没有网络）
    }

    /**
     * APP状态改变
     *
     * @param state 前台 后台
     */
    public void changeAppState(ApplicationState state) {
        OutPutMessage.LogCatInfo("事件测试", "程序运行------------------>" + state.toString());
        this.applicationState = state;
        if (applicationState == ApplicationState.ApplicationActivate) {
            APPResume("changeAppState");
        } else if (applicationState == ApplicationState.ApplicationBackground) {
            APPStop();
        }
    }

    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = context.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化对象
     */
    private void createAsyncObject() {
        Connector.getDatabase();//数据库
        OutPutMessage.createToastHandler();//toast管理
        notificationManager = NotificationManager.defaultManager();//消息通知管理
        networkManager = NetworkManager.defaultNetworkManager();//网络管理
        smartDeviceManage = SmartDeviceManage.defaultManager();//设备管理
        DeviceUpgradeManage.defaultDeviceUpgradeManager();//升级管理
        securityUserManage = SecurityUserManage.getShare();//
    }

    private void clearNetWork() {
        smartDeviceManage.cleaningDeviceHead();
        smartDeviceManage.queryDeviceTable();
        smartDeviceManage.clearNetWork();
    }

    /**
     * 发送刷新Token
     */
    public void sendRefreshToken() {
        String token = "";
        String refresh_token = "";
        try {
            token = FileIO.getShareFielIo(Controller.getContext()).readFile("token");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (token.equals("")) {
            SecurityUserManage.getShare().getToken();
        } else {
            try {
                refresh_token = FileIO.getShareFielIo(Controller.getContext()).readFile("refresh_token");
            } catch (Exception e) {
                e.printStackTrace();
            }
            SecurityUserManage.getShare().getRefreshToken(token, refresh_token);
        }
    }

    /**
     *
     */
    public void sendWebSocketInfo() {
        clearNetWork();
        if (applicationState == ApplicationState.ApplicationActivate) {
            String userID = StringUtil.getUserID();
            if (!userID.equals("")) {

                OutPutMessage.LogCatInfo("下拉刷新", networkManager.getState() + "@@@@@!!!!");
                if (networkManager.getState() != WebSocketChannel.WSState.WS_STATE_CONNECT) {
                    if (SecurityUserManage.token == null){
                        Controller.defaultController().sendRefreshToken();
                    }else {
                        SecurityUserManage.getShare().getUser().getCometadr();
                    }
                } else {
                    SecurityUserManage.getShare().getUser().getDeviceListAndShareList();//获取设备列表
                }
            }
        }
    }

    /**
     * 获取当前activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * 添加activity到集合中
     *
     * @param activity 当前activity
     */
    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    /**
     * 退出当前应用，关闭所有activity
     */
    public void exitApp() {
        try {
            for (Activity activity : activityList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    /**
     * 获取本地版本号
     */
    public static String getAPPVersion() {
        // 包管理器
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), 0);
            // 得到当前应用的版本号
            String currVersion = packInfo.versionName;
            return currVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }



}
