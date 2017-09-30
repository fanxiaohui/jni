package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityDeviceCanModify extends Bean {
    private String token;

    public CSecurityDeviceCanModify(String token){

        this.token = token;

        this.urlOrigin = Constant.UrlOrigin.security_device_can_modify;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
