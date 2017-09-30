package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改绑定手机号第一步
 */
public class CChangePhone1 extends Bean {

    private String cookie;

    public CChangePhone1(String cookie) {
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.user_change_phone1;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

