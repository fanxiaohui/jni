package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改备用联系人
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdateStandby extends Bean {

    private String token;
    private String name;
    private String phone;

    public CSecurityUpdateStandby(String token, String name, String phone){
        this.token = token;
        this.name = name;
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.security_updateStandby;
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
