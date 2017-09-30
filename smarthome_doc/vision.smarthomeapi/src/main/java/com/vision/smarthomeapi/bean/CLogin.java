package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户登录
 */
public class CLogin extends Bean {

    private String phone;
    private String passwd;

    public CLogin(String phone, String passwd) {
        super();
        this.phone = phone;
        this.passwd = passwd;
        this.urlOrigin = Constant.UrlOrigin.user_login;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}

