package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityRefreshToken extends Bean {

    private String refresh_token;
    private String key;
    private String token;
    private String sign;
    private String uid;

    public CSecurityRefreshToken(String refresh_token, String key, String token, String sign,
                                 String uid) {
        this.refresh_token = refresh_token;
        this.key = key;
        this.token = token;
        this.sign = sign;
        this.uid = uid;
        this.urlOrigin = Constant.UrlOrigin.security_refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
