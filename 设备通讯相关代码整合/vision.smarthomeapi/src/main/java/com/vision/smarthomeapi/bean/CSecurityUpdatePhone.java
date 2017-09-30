package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改手机号码
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdatePhone extends Bean {

    private String token;
    private String phone;
    private String code;

    public CSecurityUpdatePhone(String token,String phone,String code){
        this.token = token;
        this.phone = phone;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.security_updatePhone;
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
}
