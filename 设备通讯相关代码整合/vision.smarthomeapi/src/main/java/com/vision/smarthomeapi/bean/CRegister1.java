package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户手机号注册第一步
 */
public class CRegister1 extends Bean {

    private String phone;

    public CRegister1(String phone) {
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.user_registerbyphone1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
