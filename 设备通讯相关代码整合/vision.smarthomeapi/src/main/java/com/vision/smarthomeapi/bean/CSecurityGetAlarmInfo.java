package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户告警信息
 * Created by yangle on 2017/2/7.
 */
public class CSecurityGetAlarmInfo extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 告警ID
     */
    private int id;

    public CSecurityGetAlarmInfo(String token, int id) {
        this.token = token;
        this.id = id;
        this.urlOrigin = Constant.UrlOrigin.security_get_alarm_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
