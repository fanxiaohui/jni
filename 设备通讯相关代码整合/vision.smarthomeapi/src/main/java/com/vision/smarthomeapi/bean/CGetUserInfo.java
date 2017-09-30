package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户信息
 */
public class CGetUserInfo extends Bean {

    private String cookie;
    private String uId;

    public CGetUserInfo(String cookie, String uId) {
        this.cookie = cookie;
        this.uId = uId;
        this.urlOrigin = Constant.UrlOrigin.get_userinfo;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    @Override
    public String toString() {
        return "cookie=" + cookie + "&" + "uId=" + uId;
    }
}

