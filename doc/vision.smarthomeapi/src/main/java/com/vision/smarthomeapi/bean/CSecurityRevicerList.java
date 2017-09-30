package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/1.
 */

public class CSecurityRevicerList extends Bean {

    private String token;
    public CSecurityRevicerList(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.share_revicer_list;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
