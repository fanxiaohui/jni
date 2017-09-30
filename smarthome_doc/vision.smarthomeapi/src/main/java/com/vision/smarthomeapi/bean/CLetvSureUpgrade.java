package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/6/22.
 */

public class CLetvSureUpgrade extends Bean {

    private String token;
    private String mac;

    public CLetvSureUpgrade(String token,String mac){
        this.token = token;
        this.mac = mac;
        this.urlOrigin = Constant.UrlOrigin.letv_sure_upgrade;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
