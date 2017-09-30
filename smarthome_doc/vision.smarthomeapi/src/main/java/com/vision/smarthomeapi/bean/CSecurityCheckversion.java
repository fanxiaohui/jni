package com.vision.smarthomeapi.bean;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/3/15.
 */
public class CSecurityCheckversion extends Bean{
    private String token;
    /**
     * 1,ios 2,android
     */
    private int type;
    private String version;

    public CSecurityCheckversion(String token, String version){

        this.token = token;
        this.type = 2;
        this.version = version;
        this.urlOrigin = Constant.UrlOrigin.security_checkversion;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


}
