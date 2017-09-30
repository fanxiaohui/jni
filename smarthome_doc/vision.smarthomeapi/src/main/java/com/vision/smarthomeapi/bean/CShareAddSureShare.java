package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class CShareAddSureShare extends Bean {
    private String token;
    private int revicerId;
    private String deviceIds;


    public CShareAddSureShare(String token, int revicerId, String deviceIds){

        this.urlOrigin = Constant.UrlOrigin.share_add_share_sure;
        this.token = token;
        this.revicerId = revicerId;
        this.deviceIds = deviceIds;
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
