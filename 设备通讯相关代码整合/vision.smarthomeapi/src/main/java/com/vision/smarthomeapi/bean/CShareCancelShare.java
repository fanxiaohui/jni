package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class CShareCancelShare extends Bean {
    private String token;
    private int revicerId;
    private String deviceIds;

    public CShareCancelShare(String token ,int revicerId,String deviceIds){
        this.token = token;
        this.revicerId = revicerId;
        this.deviceIds = deviceIds;
        this.urlOrigin = Constant.UrlOrigin.share_cancel_share;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
