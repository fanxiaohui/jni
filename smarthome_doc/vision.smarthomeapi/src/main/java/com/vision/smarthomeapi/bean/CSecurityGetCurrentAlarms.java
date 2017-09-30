package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 查首页告警信息
 * Created by zhaoqing on 2016/8/2.
 */
public class CSecurityGetCurrentAlarms extends Bean {

    private String token;


    public CSecurityGetCurrentAlarms(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_getCurrentAlarms;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
