package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改绑定手机号第二步
 */
public class CChangePhone2 extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 第一步返回凭证
     */
    private String step1key;
    /**
     * 旧手机短信验证码
     */
    private String code;

    public CChangePhone2(String cookie, String step1key, String code) {
        this.cookie = cookie;
        this.step1key = step1key;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.user_change_phone2;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

