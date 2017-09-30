package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户信息
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityUserInfo extends Bean {
    private String token;
    private String userId;

    public CSecurityUserInfo(String token, String userId){

        this.token = token;
        this.userId = userId;
        this.urlOrigin = Constant.UrlOrigin.security_userinfo;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
