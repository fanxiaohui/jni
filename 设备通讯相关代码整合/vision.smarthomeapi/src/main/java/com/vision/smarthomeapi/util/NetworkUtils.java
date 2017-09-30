package com.vision.smarthomeapi.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.vision.smarthomeapi.bll.Controller;

/**
 * 网络工具
 * Created by yangle on 2017/3/17.
 */
public class NetworkUtils {

    /**
     * 判断当前网络是否可用
     *
     * @return 当前网络是否可用
     */
    public static boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        Context context = Controller.getContext();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
