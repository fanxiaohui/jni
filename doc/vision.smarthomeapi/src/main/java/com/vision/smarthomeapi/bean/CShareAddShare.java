package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class CShareAddShare extends Bean {
    private String token;
    private String account;
    private String deviceIds;


    public CShareAddShare(String token,String account,String deviceIds){

        this.urlOrigin = Constant.UrlOrigin.share_add_share;
        this.token = token;
        this.account = account;
        this.deviceIds = deviceIds;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
