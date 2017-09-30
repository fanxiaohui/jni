package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 通过旧密码修改登录密码第一步
 */
public class CChangePass1 extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 旧密码
     */
    private String o;

    public CChangePass1(String cookie, String o) {
        this.cookie = cookie;
        this.o = o;
        this.urlOrigin = Constant.UrlOrigin.user_change_pass1;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }
}

