package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 更改门铃的红外推送策略
 * Created by yang on 2017/1/22.
 */
public class CSecuritySetPushStrategy extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 设备ID
     */
    private int devId;
    /**
     * 0.不推送 1.推送 2.延迟推送
     */
    private int strategy;

    public CSecuritySetPushStrategy(String token, int devId, int strategy) {
        this.token = token;
        this.devId = devId;
        this.strategy = strategy;
        this.urlOrigin = Constant.UrlOrigin.security_set_push_strategy;
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

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }
}
