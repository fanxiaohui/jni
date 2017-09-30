package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取设备历史报警记录
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityAlarmList extends Bean {

    private String token;
    private int recordId;
    private String time;

    public CSecurityAlarmList(String token, String time){
        this.token = token;
        this.time = time;
        this.urlOrigin = Constant.UrlOrigin.security_alarmList;
    }

    public CSecurityAlarmList(String token, int recordId,String time){
        this.token = token;
        this.recordId = recordId;
        this.time = time;
        this.urlOrigin = Constant.UrlOrigin.security_alarmList;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
