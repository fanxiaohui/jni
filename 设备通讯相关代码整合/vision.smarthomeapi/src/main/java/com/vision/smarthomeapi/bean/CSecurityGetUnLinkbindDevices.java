package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/8/11.
 */

public class CSecurityGetUnLinkbindDevices extends Bean {
    private String token;
    private int realType;

    public CSecurityGetUnLinkbindDevices(String token,int realType){

        this.urlOrigin = Constant.UrlOrigin.security_getUnLinkBindDevices;
        this.token = token;
        this.realType = realType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRealType() {
        return realType;
    }

    public void setRealType(int realType) {
        this.realType = realType;
    }
}
