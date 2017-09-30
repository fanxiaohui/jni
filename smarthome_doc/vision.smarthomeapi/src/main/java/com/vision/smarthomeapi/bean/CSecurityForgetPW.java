package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 忘记密码
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityForgetPW extends Bean {

    private String token;
    private String phone;
    private String code;
    private String newPassword;

    public CSecurityForgetPW(String token, String phone, String code, String newPassword){
        this.token = token;
        this.phone = phone;
        this.code = code;
        this.newPassword = newPassword;
        this.urlOrigin = Constant.UrlOrigin.security_forgetPW;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
