package com.vision.smarthomeapi.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.vision.smarthomeapi.bll.manage.DeviceNetModeManage;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Lal on 13-10-28.
 */
public class NetworkControl {
    private static NetworkControl mNetworkControl;
    private Context context = null;
    private WifiManager mWifiManager;

    //    public static NetworkControl getNetworkControl(Context context){
//        if (mNetworkControl == null){
//            mNetworkControl = new NetworkControl(context);
//        }
//        return mNetworkControl;
//    }
    public NetworkControl(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
    }

    public String getSSID() {
        return mWifiManager.getConnectionInfo().getSSID();
    }

    public void openWifi() {
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
    }


    public boolean isWifiEnabled() {
        return mWifiManager.isWifiEnabled();
    }


    // 关闭WIFI
    public void closeWifi() {
        if (mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(false);
        }
    }

    // 检查当前WIFI状态
    public int checkState() {
        return mWifiManager.getWifiState();
    }


    public WifiConfiguration IsExsits(String SSID) {
        List<WifiConfiguration> existingConfigs = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals("\"" + SSID + "\"")) {
                return existingConfig;
            }
        }
        return null;
    }

    /**
     * 连接当前WIFI
     *
     * @param SSID     WIFI账号
     * @param password WIFI密码
     * @param safeMode 模式
     * @return 连接是否成功
     */
    public boolean connectWifiToSSID(String SSID, String password, SafeMode safeMode) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        WifiConfiguration tempConfig = this.IsExsits(SSID);
        if (tempConfig != null) {
            mWifiManager.removeNetwork(tempConfig.networkId);
        }

        switch (safeMode) {
            case NO_PASSWORD:
                config.hiddenSSID = true;
//                config.wepKeys[0] = "";
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//                config.wepTxKeyIndex = 0;
                break;
            case WEP_64:
            case WEP_128:
                config.hiddenSSID = true;
                config.wepKeys[0] = "\"" + password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
                break;
            case WPA:
            case WPA2:
                config.preSharedKey = "\"" + password + "\"";
                config.hiddenSSID = true;
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                //config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                config.status = WifiConfiguration.Status.ENABLED;
                break;
        }
        int res = mWifiManager.addNetwork(config);
        boolean isEnable = mWifiManager.enableNetwork(res, true);
        OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "WIFI回复状态：" + isEnable);
        return isEnable;

    }

    /**
     * 返回原来的WIFI
     */
    public void returnWIFIInfo(String SSid) {
        WifiConfiguration tempConfig = this.IsExsits(SSid);
        if (tempConfig != null) {
            mWifiManager.enableNetwork(tempConfig.networkId, true);
        }
    }

    /**
     * 返回Wifi列表
     *
     * @return
     */
    public List<String> getWifiList() {
        List<ScanResult> a = mWifiManager.getScanResults();
        String[] list = new String[a.size()];
        for (int i = 0; i < a.size(); i++) {
            list[i] = a.get(i).SSID;
        }
        return Arrays.asList(list);
    }

    /**
     * 返回Wifi列表
     *
     * @return
     */
    public List<ScanResult> getWifiNetWorkList() {
        return mWifiManager.getScanResults();
    }

    public enum SafeMode {
        NO_PASSWORD(0x00),
        WEP_64(0x02),
        WEP_128(0x04),
        WPA(0x06),
        WPA2(0x08);

        private int value;

        SafeMode(int value) {
            this.value = value;
        }
    }
}

