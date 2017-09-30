package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取设备报警详情
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityAlarmInfo extends Bean {

    private String token;


    public CSecurityAlarmInfo(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_alarmInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
