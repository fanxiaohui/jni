package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 通过手机号修改登录密码第二步
 */
public class CChangePass3_Phone extends Bean {
    /**
     * 用户手机号
     */
    private String phone;
    /**
     * 上一步返回凭证
     */
    private String step2key;
    /**
     * 用户密码
     */
    private String w;

    public CChangePass3_Phone(String phone, String step2key, String w) {
        this.phone = phone;
        this.step2key = step2key;
        this.w = w;
        this.urlOrigin = Constant.UrlOrigin.user_change_pass_byphone3;
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

