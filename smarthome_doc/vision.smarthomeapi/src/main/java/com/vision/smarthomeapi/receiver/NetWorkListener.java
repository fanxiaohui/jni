package com.vision.smarthomeapi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.DeviceNetModeManage;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;

/**
 * 网络改变监听类
 */
public class NetWorkListener {

//    private boolean isRead;
    /**
     * net状态改变监听
     */
    private static NetWorkListener netListener;
    /**
     * net状态改变广播接收者
     */
    private NetBroadcastReceiver netBroadcastReceiver;
    /**
     * NetWorkListener实例
     */
    private NetWorkStateListener listener;
    /**
     * 上下文
     */
    private Context context;

    private NetWorkListener(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        netBroadcastReceiver = new NetBroadcastReceiver();
//        isRead = false;
    }

    /**
     * 获取NetWorkListener实例
     *
     * @param context
     * @return
     */
    public static NetWorkListener defaultNetListener(Context context) {

        if (netListener == null) {
            netListener = new NetWorkListener(context);
        }
        return netListener;
    }

    /**
     * 开始监听net状态
     *
     * @param listener 当前状态改变事件
     */
    public void begin(NetWorkStateListener listener) {
        this.listener = listener;
        registerListener();
    }

    /**
     * 启动net状态广播接收器
     */
    private void registerListener() {

        IntentFilter netfilter = new IntentFilter();
        netfilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        context.registerReceiver(netBroadcastReceiver, netfilter);
    }

    /**
     * 停止net状态监听
     */
    public void unregisterListener() {
        context.unregisterReceiver(netBroadcastReceiver);
    }

    /**
     * net状态广播接收者
     */
    private class NetBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
//            if (!isRead) {
//                isRead = true;
//                return;
//            }
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {
                    //网络连接
//                    String name = netInfo.getTypeName();
                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        if (netInfo.getState() == NetworkInfo.State.CONNECTED) {//如果已经连接
                            OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "网络WIFI设置的SSID::" + netInfo.getState());
                            //WiFi网络
                            listener.changeNetState(Controller.NetWorkState.Net_WIFI, netInfo);
                        } else if (!StringUtil.getUserID().equals("")) {

                            listener.changeNetState(Controller.NetWorkState.Net_Non, netInfo);
                            //UserLogManage.defaultManager().setHomePageLog("网络连接异常，已断开连接", UserLogManage.LogCategory.LOG_NORMAL);
                        }
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        //有线网络
                        OutPutMessage.LogCatInfo("net", "有线网络");
                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        //3g网络
                        OutPutMessage.LogCatInfo("net", "3G网络");
                        if (netInfo.getState() == NetworkInfo.State.CONNECTED) {//如果已经连接
                            //WiFi网络
                            listener.changeNetState(Controller.NetWorkState.Net_3G, netInfo);
                        }
                    }
                } else {
                    listener.changeNetState(Controller.NetWorkState.Net_Non, netInfo);
                    if (!StringUtil.getUserID().equals("")) {
                        // UserLogManage.defaultManager().setHomePageLog("网络连接异常，已断开连接", UserLogManage.LogCategory.LOG_NORMAL);
                    }
                }
            }
        }

    }

    /**
     * 状态改变监听
     */
    public interface NetWorkStateListener {
        /**
         * 网络状态改变回调方法
         *
         * @param state 网络状态
         */
        public void changeNetState(Controller.NetWorkState state, NetworkInfo netInfo);
    }
}

