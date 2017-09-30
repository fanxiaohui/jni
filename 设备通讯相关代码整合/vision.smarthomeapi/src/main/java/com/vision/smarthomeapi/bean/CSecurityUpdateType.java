package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改通用盒子的设备类型
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityUpdateType extends Bean {
    private String token;
    private int deviceId;
    private int type;


    public CSecurityUpdateType(String token, int deviceId,int type){

        this.token = token;
        this.deviceId = deviceId;
        this.type = type;
        this.urlOrigin = Constant.UrlOrigin.security_deviceType;

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
}
