package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/1/21.
 */

public class CSecurityDeleteCatAlarms extends Bean{

    private String token;
    private int deviceId;


    public CSecurityDeleteCatAlarms(String token, int deviceId){
        this.urlOrigin = Constant.UrlOrigin.security_delete_cat_alarms_byid;
        this.token = token;
        this.deviceId = deviceId;
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
}
