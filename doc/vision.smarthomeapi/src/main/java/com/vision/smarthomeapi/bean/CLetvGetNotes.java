package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class CLetvGetNotes extends Bean{

    private String token;
    private int start;
    private int limit;
    private int deviceId;


    public CLetvGetNotes(String token, int start, int limit,int deviceId){
        this.urlOrigin = Constant.UrlOrigin.letv_get_notes;
        this.token = token;
        this.start = start;
        this.limit = limit;
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}
