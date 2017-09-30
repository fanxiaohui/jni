package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/9/8.
 */
public class CSecurityGetShortQuestionnaire extends Bean{

    private String token;
    public CSecurityGetShortQuestionnaire(String token){
        this.urlOrigin = Constant.UrlOrigin.security_get_short_questionnaire;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
