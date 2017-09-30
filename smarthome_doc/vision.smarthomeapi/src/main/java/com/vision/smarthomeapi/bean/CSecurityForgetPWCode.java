package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取忘记密码的验证码
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityForgetPWCode extends Bean {

    public String token;
    public String phone;

    public CSecurityForgetPWCode(String token, String phone){
        this.token = token;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_forgetPW_code;
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
}
