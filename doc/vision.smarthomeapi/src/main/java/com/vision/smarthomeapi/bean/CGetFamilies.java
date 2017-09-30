package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取家庭成员列表
 */
public class CGetFamilies extends Bean {

    private String cookie;

    public CGetFamilies(String cookie) {
        this.cookie = cookie;
        this.urlOrigin = Constant.UrlOrigin.get_families;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }
}

