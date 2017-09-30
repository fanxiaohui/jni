package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户访客记录
 * Created by yangle on 2017/1/22.
 */
public class CSecurityGetVisitors extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 用户ID
     */
    private String customerId;
    /**
     * 开始位置
     */
    private int start;
    /**
     * 记录条数
     */
    private int limit;

    public CSecurityGetVisitors(String token, String customerId, int start, int limit) {
        this.token = token;
        this.customerId = customerId;
        this.start = start;
        this.limit = limit;
        this.urlOrigin = Constant.UrlOrigin.security_get_visitors;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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
}
