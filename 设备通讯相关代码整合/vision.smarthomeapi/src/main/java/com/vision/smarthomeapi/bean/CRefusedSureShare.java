package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 *
 * Created by zhaoqing on 2017/9/5.
 */

public class CRefusedSureShare extends Bean {

    private String token;
    private int shareId;
    private String deviceIds;

    public CRefusedSureShare(String token, int shareId,String deviceIds){

        this.urlOrigin = Constant.UrlOrigin.cancel_share_revicer;
        this.token = token;
        this.shareId = shareId;
        this.deviceIds = deviceIds;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getDeviceIds() {
        return deviceIds;
    }

    public void setDeviceIds(String deviceIds) {
        this.deviceIds = deviceIds;
    }
}
