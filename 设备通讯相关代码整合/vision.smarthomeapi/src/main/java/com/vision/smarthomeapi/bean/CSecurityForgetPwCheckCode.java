package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 找回密码验证安全码
 * Created by yang on 2017/1/20.
 */
public class CSecurityForgetPwCheckCode extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 安全码
     */
    private String code;
    /**
     * 手机号
     */
    private String phone;

    public CSecurityForgetPwCheckCode(String token, String code, String phone) {
        this.token = token;
        this.code = code;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_check_code_by_phone;
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
