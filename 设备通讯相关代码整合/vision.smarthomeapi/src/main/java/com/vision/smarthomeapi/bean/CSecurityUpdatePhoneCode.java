package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取修改手机的验证码
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdatePhoneCode extends Bean {

    public String token;
    public String phone;

    public CSecurityUpdatePhoneCode(String token,String phone){
        this.token = token;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_updatePhone_code;
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
