package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class CLetvVipBm extends Bean {
    private String pid;
    private String token;
    public CLetvVipBm(String token,String pid){
        this.token = token;
        this.pid = pid;
        this.urlOrigin = Constant.UrlOrigin.letv_vip_bm;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
