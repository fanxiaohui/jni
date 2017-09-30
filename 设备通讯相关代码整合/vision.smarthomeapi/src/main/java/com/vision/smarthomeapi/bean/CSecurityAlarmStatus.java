package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户告警状态
 * Created by yang on 2017/2/17.
 */
public class CSecurityAlarmStatus extends Bean {

    /**
     * 访问令牌
     */
    private String token;

    public CSecurityAlarmStatus(String token) {
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_alarm_status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
