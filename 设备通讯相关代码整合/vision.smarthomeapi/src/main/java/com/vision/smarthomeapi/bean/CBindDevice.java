package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户绑定设备
 */
public class CBindDevice extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 要添加设备的mac
     */
    private String mac;
    /**
     * 设备的sn
     */
    private String sn;

    public CBindDevice(String cookie, String mac, String sn) {
        this.cookie = cookie;
        this.mac = mac;
        this.sn = sn;
        this.urlOrigin = Constant.UrlOrigin.device_bind;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }
}

