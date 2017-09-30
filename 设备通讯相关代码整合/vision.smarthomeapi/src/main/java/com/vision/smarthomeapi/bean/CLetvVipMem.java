package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class CLetvVipMem extends Bean {
    private String token;
    public CLetvVipMem(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.letv_vip_mem;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
