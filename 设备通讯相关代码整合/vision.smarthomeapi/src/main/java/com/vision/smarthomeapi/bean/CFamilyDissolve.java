package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 解散家庭
 */
public class CFamilyDissolve extends Bean {

    private String cookie;

    public CFamilyDissolve(String cookie) {
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.family_demission;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}

