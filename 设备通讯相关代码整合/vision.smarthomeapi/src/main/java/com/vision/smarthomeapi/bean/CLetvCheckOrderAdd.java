package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class CLetvCheckOrderAdd extends Bean {
    private String address;
    private String token;
    public CLetvCheckOrderAdd(String token, String address){
        this.token = token;
        this.address = address;
        this.urlOrigin = Constant.UrlOrigin.letv_check_orderAdd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
