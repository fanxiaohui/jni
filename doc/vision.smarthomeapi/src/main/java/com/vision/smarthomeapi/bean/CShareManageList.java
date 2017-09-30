package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/6.
 */

public class CShareManageList extends Bean {
    private String token;
    private int revicerId;

    public CShareManageList(String token,int revicerId){
        this.token = token;
        this.revicerId = revicerId;
        this.urlOrigin = Constant.UrlOrigin.revicer_share_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
    }
}
