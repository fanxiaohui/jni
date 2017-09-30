package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户注册验证验证码
 * Created by yangle on 2017/1/14.
 */
public class CUserRegister extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 验证码
     */
    private String code;
    /**
     * 用户名称
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户手机号码
     */
    private String phone;
    /**
     * 安全码
     */
    private String securityCode;

    public CUserRegister(String token, String code, String name, String password,
                         String phone, String securityCode) {
        this.token = token;
        this.code = code;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.securityCode = securityCode;
        this.urlOrigin = Constant.UrlOrigin.security_register;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
