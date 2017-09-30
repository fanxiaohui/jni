package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 根据设备mac查询设备
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityDeviceByMac extends Bean{
    private String token;
    private String mac;

    public CSecurityDeviceByMac(String token, String mac){

        this.token = token;
        this.mac = mac;
        this.urlOrigin = Constant.UrlOrigin.security_deviceByMac;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
