package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/9/13.
 */
public class CSecurityGetThreeLinkage extends Bean{

    private String token;
    public CSecurityGetThreeLinkage(String token){
        this.urlOrigin = Constant.UrlOrigin.security_getthreelinkage;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
