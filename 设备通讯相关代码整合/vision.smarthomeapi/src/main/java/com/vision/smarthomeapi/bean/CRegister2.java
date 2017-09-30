package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户手机号注册第二步
 */
public class CRegister2 extends Bean {

    private String phone;
    private String step1key;
    private String code;

    public CRegister2(String phone, String step1key, String code) {
        this.phone = phone;
        this.step1key = step1key;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.user_registerbyphone2;
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
