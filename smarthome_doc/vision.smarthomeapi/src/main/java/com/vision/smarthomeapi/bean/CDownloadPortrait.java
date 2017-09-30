package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 下载用户头像
 */
public class CDownloadPortrait extends Bean {

    private String cookie;
    private String uId;
    private String facestamp;

    public CDownloadPortrait(String cookie, String uId, String facestamp) {
        this.cookie = cookie;
        this.uId = uId;
        this.facestamp = facestamp;
        this.urlOrigin = Constant.UrlOrigin.user_download_portrait;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFacestamp() {
        return facestamp;
    }

    public void setFacestamp(String facestamp) {
        this.facestamp = facestamp;
    }
}

