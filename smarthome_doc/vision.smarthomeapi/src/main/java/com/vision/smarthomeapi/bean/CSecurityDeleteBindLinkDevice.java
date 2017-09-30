package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 解除猫眼门锁关系
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityDeleteBindLinkDevice extends Bean {

    private String token;
    private int id;


    public CSecurityDeleteBindLinkDevice(String token, int id){
        this.token = token;
        this.id = id;
        this.urlOrigin = Constant.UrlOrigin.security_deletebindlinkdevice;
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

  
}
