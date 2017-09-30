package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/9/13.
 */
public class CSecuritySaveTwoLinkage extends Bean{

    private String token;
    private int deviceId;
    private String ids;
    public CSecuritySaveTwoLinkage(String token, int deviceId,String ids){
        this.urlOrigin = Constant.UrlOrigin.security_savetwolinkage;
        this.token = token;
        this.deviceId = deviceId;
        this.ids = ids;
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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
