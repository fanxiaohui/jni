package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class CShareSureShare extends Bean {

    private String token;
    private int shareId;

    public CShareSureShare(String token,int shareId){

        this.urlOrigin = Constant.UrlOrigin.share_sure_share;
        this.token = token;
        this.shareId = shareId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }
}
