package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取猫眼的告警详情
 * Created by yangle on 2017/1/22.
 */
public class CSecurityGetCatEyeInfo extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 告警ID
     */
    private int id;
    /**
     * 时间戳
     */
    private long time;

    public CSecurityGetCatEyeInfo(String token, int id,long time) {
        this.token = token;
        this.id = id;
        this.time = time;
        this.urlOrigin = Constant.UrlOrigin.security_get_cateye_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
