package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 绑定猫眼门锁
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityBindLinkDevice extends Bean {

    private String token;
    private int id;
    private int bindId;


    public CSecurityBindLinkDevice(String token,int id,int bindId){
        this.token = token;
        this.id = id;
        this.bindId = bindId;
        this.urlOrigin = Constant.UrlOrigin.security_bindlinkdevice;
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

    public int getBindId() {
        return bindId;
    }

    public void setBindId(int bindId) {
        this.bindId = bindId;
    }
}
