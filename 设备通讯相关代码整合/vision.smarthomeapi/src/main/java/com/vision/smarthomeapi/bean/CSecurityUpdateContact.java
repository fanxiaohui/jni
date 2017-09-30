package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改紧急联系人
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdateContact extends Bean {

    private String token;
    private String name;
    private String phone;

    public CSecurityUpdateContact(String token, String name, String phone){
        this.token = token;
        this.name = name;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_updateContact;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
