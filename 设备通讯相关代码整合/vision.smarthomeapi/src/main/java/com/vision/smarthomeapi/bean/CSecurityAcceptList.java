package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/1.
 */

public class CSecurityAcceptList extends Bean {

    private String token;
    public CSecurityAcceptList(String token){
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.share_accept_list;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
