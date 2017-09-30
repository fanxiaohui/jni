package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户访客记录
 * Created by yangle on 2017/1/22.
 */
public class CSecurityGetVisitorsForID extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 设备ID
     */
    private int deviceId;
    /**
     * 开始位置
     */
    private int start;
    /**
     * 记录条数
     */
    private int limit;
    /**
     * 时间戳
     */
    private long time;

    public CSecurityGetVisitorsForID(String token, int deviceId, int start, int limit,long time) {
        this.token = token;
        this.deviceId = deviceId;
        this.start = start;
        this.limit = limit;
        this.time = time;
        this.urlOrigin = Constant.UrlOrigin.security_get_visitors_byid;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
