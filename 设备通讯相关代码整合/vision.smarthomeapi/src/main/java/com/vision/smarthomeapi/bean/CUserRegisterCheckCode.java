package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户注册验证验证码
 * Created by yangle on 2017/1/14.
 */
public class CUserRegisterCheckCode extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 验证码
     */
    private String code;
    /**
     * 用户手机号码
     */
    private String phone;

    public CUserRegisterCheckCode(String token, String code, String phone) {
        this.token = token;
        this.code = code;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_register_check_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
