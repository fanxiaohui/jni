package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改绑定手机号第三步
 */
public class CChangePhone3 extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 第二步返回凭证
     */
    private String step2key;
    /**
     * 新手机号
     */
    private String phone;

    public CChangePhone3(String cookie, String step2key, String phone) {
        this.cookie = cookie;
        this.step2key = step2key;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.user_change_phone3;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getStep2key() {
        return step2key;
    }

    public void setStep2key(String step2key) {
        this.step2key = step2key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

