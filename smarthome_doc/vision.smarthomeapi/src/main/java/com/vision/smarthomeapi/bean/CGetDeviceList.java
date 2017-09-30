package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户设备列表
 */
public class CGetDeviceList extends Bean {

    private String cookie;

    public CGetDeviceList(String cookie) {
        super();
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.user_devices;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

