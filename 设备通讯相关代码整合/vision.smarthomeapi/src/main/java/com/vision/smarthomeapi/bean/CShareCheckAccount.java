package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/1.
 */

public class CShareCheckAccount extends Bean {

    private String token;
    private String account;
    private int deviceId;
    public CShareCheckAccount(String token,String account){
        this.token = token;
        this.account = account;
        this.urlOrigin = Constant.UrlOrigin.share_check_account;
    }


    public CShareCheckAccount(String token,String account,int deviceId){
        this.token = token;
        this.deviceId = deviceId;
        this.account = account;
        this.urlOrigin = Constant.UrlOrigin.share_check_account;
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

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
