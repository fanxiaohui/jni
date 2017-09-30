package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 用户主动退出某家庭
 */
public class CFamilyQuit extends Bean {

    private String cookie;
    private String fid;

    public CFamilyQuit(String cookie, String fid) {
        this.cookie = cookie;
        this.fid = fid;
        this.urlOrigin = Constant.UrlOrigin.family_quit;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFid() {
        return fid;
    }
}

