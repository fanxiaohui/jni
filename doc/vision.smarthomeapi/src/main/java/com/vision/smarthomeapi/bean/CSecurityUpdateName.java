package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改设备位置
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityUpdateName extends Bean {

    private String token;
    private String devId;
    private String address;

    public CSecurityUpdateName(String token, String devId, String address) {
        this.token = token;
        this.devId = devId;
        this.address = address;
        this.urlOrigin = Constant.UrlOrigin.security_updateName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
