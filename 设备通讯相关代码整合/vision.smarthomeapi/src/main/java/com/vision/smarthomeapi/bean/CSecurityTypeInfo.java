package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityTypeInfo extends Bean{
    private String token;

    public CSecurityTypeInfo(String token){

        this.token = token;

        this.urlOrigin = Constant.UrlOrigin.security_getType;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
