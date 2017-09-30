package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户登出
 */
public class CLogout extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;

    public CLogout(String cookie) {
        super();
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.user_logout;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

