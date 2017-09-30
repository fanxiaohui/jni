package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/5/27.
 */
public class CSecurityAlarmMonth extends Bean {
    private String token;

    public CSecurityAlarmMonth(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_alarmMonth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
