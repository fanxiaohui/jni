package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 通过旧密码修改登录密码第二步
 */
public class CChangePass2 extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 第一步返回凭证
     */
    private String step1key;
    /**
     * 新密码
     */
    private String n;

    public CChangePass2(String cookie, String step1key, String n) {
        this.cookie = cookie;
        this.step1key = step1key;
        this.n = n;
        this.urlOrigin = Constant.UrlOrigin.user_change_pass2;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getStep1key() {
        return step1key;
    }

    public void setStep1key(String step1key) {
        this.step1key = step1key;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}

