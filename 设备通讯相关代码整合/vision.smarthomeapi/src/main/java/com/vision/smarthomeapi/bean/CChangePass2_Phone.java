package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 通过手机号修改登录密码第二步
 */
public class CChangePass2_Phone extends Bean {
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 上一步返回凭证
     */
    private String step1key;
    /**
     * 短信验证码
     */
    private String code;

    public CChangePass2_Phone(String phone, String step1key, String code) {
        this.phone = phone;
        this.step1key = step1key;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.user_change_pass_byphone2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

