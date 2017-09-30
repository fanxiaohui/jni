package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户是否做过问卷
 * Created by zhaoqing on 2016/5/18.
 */
public class CSecurityCheckQuestion extends Bean{
    private String token;
    public CSecurityCheckQuestion(String token){

        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_check_question;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
