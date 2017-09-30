package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/9/13.
 */
public class CSecurityGetStateReport extends Bean{

    private String token;
    private int deviceId;
    private int type;
    private int timeType;
    public CSecurityGetStateReport(String token, int deviceId,int type,int timeType){
        this.urlOrigin = Constant.UrlOrigin.security_get_statereport;
        this.token = token;
        this.deviceId = deviceId;
        this.type = type;
        this.timeType = timeType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }
}
