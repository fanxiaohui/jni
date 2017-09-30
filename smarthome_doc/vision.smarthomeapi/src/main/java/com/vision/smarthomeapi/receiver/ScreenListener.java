package com.vision.smarthomeapi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.vision.smarthomeapi.bll.Controller;

/**
 * 解锁和锁屏改变监听类
 */
public class ScreenListener {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 屏幕状态改变广播接收者
     */
    private ScreenBroadcastReceiver mScreenReceiver;
    /**
     * 屏幕状态改变监听
     */
    private ScreenStateListener mScreenStateListener;
    /**
     * ScreenListener实例
     */
    private static ScreenListener screenListener;

    private ScreenListener(Context context) {
        mContext = context;
        mScreenReceiver = new ScreenBroadcastReceiver();
    }

    /**
     * 获取ScreenListener实例
     * @param context
     * @return
     */
    public static ScreenListener defaultScreenListener(Context context){

        if (screenListener == null){
            screenListener = new ScreenListener(context);
        }
        return screenListener;
    }
    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mScreenStateListener.changeScreenState(Controller.ScreenState.ScreenOn);
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mScreenStateListener.changeScreenState(Controller.ScreenState.ScreenOff);
            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                mScreenStateListener.changeScreenState(Controller.ScreenState.serPresent);
            }
        }
    }
    /**
     * 开始监听screen状态
     * @param listener 当前状态改变事件
     */
    public void begin(ScreenStateListener listener) {
        mScreenStateListener = listener;
        registerListener();
    }
    /**
     * 启动screen状态广播接收器
     */
    private void registerListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        mContext.registerReceiver(mScreenReceiver, filter);
    }
    /**
     * 停止screen状态监听
     */
    public void unregisterListener() {
        mContext.unregisterReceiver(mScreenReceiver);
    }


    /**
     * 状态改变监听
     */
    public interface ScreenStateListener {
        /**
         * 屏幕状态改变回调方法
         * @param state  屏幕状态
         */
        public void changeScreenState(Controller.ScreenState state);
    }

}
