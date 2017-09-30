package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class CLetvUpdateFingerprint extends Bean{

    private String token;
    private String headId;
    private String name;
    private String fingerprint;
    private int deviceId;



    public CLetvUpdateFingerprint(String token, String headId, String name,String fingerprint,int deviceId){
        this.urlOrigin = Constant.UrlOrigin.letv_update_fingerprint;
        this.token = token;
        this.headId = headId;
        this.name = name;
        this.fingerprint = fingerprint;
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeadId() {
        return headId;
    }

    public void setHeadId(String headId) {
        this.headId = headId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
