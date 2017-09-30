package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取手机号绑定信息
 */
public class CGetPhoneBindInfo extends Bean {

    private String cookie;

    public CGetPhoneBindInfo(String cookie) {
        super();
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.get_bindphone_info;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

