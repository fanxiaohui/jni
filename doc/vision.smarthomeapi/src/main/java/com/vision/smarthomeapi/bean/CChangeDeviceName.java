package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户绑定设备
 */
public class CChangeDeviceName extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 要添加设备的mac
     */
    private String mac;
    /**
     * 设备名称
     */
    private String name;

    public CChangeDeviceName(String cookie, String mac, String name) {
        this.cookie = cookie;
        this.mac = mac;
        this.name = name;
        this.urlOrigin = Constant.UrlOrigin.changing_device_name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

