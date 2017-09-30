package com.vision.smarthomeapi.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by zhanglong on 2015/9/17.
 */
public class PhoneUtil {

    /**
     * 函数名称：getMacAddress</br> 功能描述：获取手机MAC地址
     *
     * @return 手机的mac地址 修改日志:</br>
     * <table>
     * <tr>
     * <th>版本</th>
     * <th>日期</th>
     * <th>作者</th>
     * <th>描述</th>
     * <tr>
     * <td>0.1</td>
     * <td>2013年8月7日</td>
     * <td>孟凡硕</td>
     * <td>初始创建</td>
     * </table>
     */
    public static String getMacAddress(Context mApplication) {
        String result = "";
        WifiManager wifiManager = (WifiManager) mApplication
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//		result = "54:A0:50:55:4D:89";
        result = wifiInfo.getMacAddress();
        if (result == null) {
            result = "0";
        }
        result = result.replace(":", "");
        return result;
    }



}
