package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取单个设备的报警记录
 * Created by zhaoqing on 2017/7/21.
 */

public class CSecurityGetDeviceAlarms extends Bean{

    private String token;
    private int deviceId;
    private int start;
    private int limit;


    public CSecurityGetDeviceAlarms(String token, int deviceId, int start, int limit){
        this.urlOrigin = Constant.UrlOrigin.security_device_getalarms;
        this.token = token;
        this.deviceId = deviceId;
        this.start = start;
        this.limit = limit;
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

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
