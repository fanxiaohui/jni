package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/5/19.
 */
public class CSecurityGetLongQuestion extends Bean{

    private String token;
    public CSecurityGetLongQuestion(String token){
        this.urlOrigin = Constant.UrlOrigin.security_get_long_question;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
