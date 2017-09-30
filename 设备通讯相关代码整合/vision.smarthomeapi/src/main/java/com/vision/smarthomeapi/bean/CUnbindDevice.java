package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 解绑设备
 */
public class CUnbindDevice extends Bean {

    private String cookie;
    private String mac;

    public CUnbindDevice(String cookie, String mac) {
        this.cookie = cookie;
        this.mac = mac;
        this.urlOrigin = Constant.UrlOrigin.device_unbind;
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

}

