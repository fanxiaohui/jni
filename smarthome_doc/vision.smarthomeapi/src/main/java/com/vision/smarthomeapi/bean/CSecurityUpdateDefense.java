package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 布防撤防
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityUpdateDefense extends Bean{
    private String token;
    private int state;

    public CSecurityUpdateDefense(String token, int state){

        this.token = token;
        this.state = state;

        this.urlOrigin = Constant.UrlOrigin.security_updateDefense;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
