package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取门铃的红外推送策略
 * Created by yang on 2017/1/22.
 */
public class CSecurityGetPushStrategy extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 设备ID
     */
    private int devId;

    public CSecurityGetPushStrategy(String token, int devId) {
        this.token = token;
        this.devId = devId;
        this.urlOrigin = Constant.UrlOrigin.security_get_push_strategy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
    }
}
