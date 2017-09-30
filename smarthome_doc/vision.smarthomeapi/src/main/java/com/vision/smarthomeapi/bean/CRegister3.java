package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户手机号注册第三步
 */
public class CRegister3 extends Bean {

    private String phone;
    private String step2key;
    private String w;

    public CRegister3(String phone, String step2key, String w) {
        this.phone = phone;
        this.step2key = step2key;
        this.w = w;
        this.urlOrigin = Constant.UrlOrigin.user_registerbyphone3;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStep2key() {
        return step2key;
    }

    public void setStep2key(String step2key) {
        this.step2key = step2key;
    }

    public String getW() {
        return w;
    }

    public void setW(String w) {
        this.w = w;
    }
}
