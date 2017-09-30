package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/9/13.
 */
public class CSecuritySaveThreeLinkage extends Bean{

    private String token;
    private int scheme;
    public CSecuritySaveThreeLinkage(String token, int scheme){
        this.urlOrigin = Constant.UrlOrigin.security_savethreelinkage;
        this.token = token;
        this.scheme = scheme;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }
}
