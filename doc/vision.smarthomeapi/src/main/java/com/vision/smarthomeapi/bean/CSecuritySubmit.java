package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/5/25.
 */
public class CSecuritySubmit extends Bean{
    private String token;
    private String data;

    public CSecuritySubmit(String token,String data){
        this.token = token;
        this.data = data;
        this.urlOrigin = Constant.UrlOrigin.security_questionnaire_submit;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
