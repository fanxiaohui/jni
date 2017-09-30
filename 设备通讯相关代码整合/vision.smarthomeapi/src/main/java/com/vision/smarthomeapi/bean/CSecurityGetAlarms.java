package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/1/21.
 */

public class CSecurityGetAlarms extends Bean{

    private String token;
    private String customerId;
    private int start;
    private int limit;


    public CSecurityGetAlarms(String token,String customerId,int start,int limit){
        this.urlOrigin = Constant.UrlOrigin.security_getalarms;
        this.token = token;
        this.customerId = customerId;
        this.start = start;
        this.limit = limit;
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
