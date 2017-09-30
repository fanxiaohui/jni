package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改绑定手机号第四步
 */
public class CChangePhone4 extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 第三步返回凭证
     */
    private String step3key;
    /**
     * 新手机收到的验证码
     */
    private String code;

    public CChangePhone4(String cookie, String step3key, String code) {
        this.cookie = cookie;
        this.step3key = step3key;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.user_change_phone4;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getStep3key() {
        return step3key;
    }

    public void setStep3key(String step3key) {
        this.step3key = step3key;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

