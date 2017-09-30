package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 安防设备列表 带分享
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityDeviceListAndShareList extends Bean{
    private String token;
    private String userId;

    public CSecurityDeviceListAndShareList(String token, String userId){

        this.token = token;
        this.userId = userId;
        this.urlOrigin = Constant.UrlOrigin.security_devicelist_ContainShare;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
