package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class CLetvGetOrderList extends Bean{

    private String token;
    private int start;
    private int limit;


    public CLetvGetOrderList(String token, int start, int limit){
        this.urlOrigin = Constant.UrlOrigin.letv_get_orderlist;
        this.token = token;
        this.start = start;
        this.limit = limit;
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
}
