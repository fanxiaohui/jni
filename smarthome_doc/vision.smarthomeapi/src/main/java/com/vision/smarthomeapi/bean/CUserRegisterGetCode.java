package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户注册获取验证码
 * Created by yangle on 2017/1/14.
 */
public class CUserRegisterGetCode extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 用户手机号码
     */
    private String phone;

    public CUserRegisterGetCode(String token, String phone) {
        this.token = token;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_register_get_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
