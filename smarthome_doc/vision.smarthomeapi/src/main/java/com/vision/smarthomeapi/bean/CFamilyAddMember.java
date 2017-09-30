package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 添加家庭成员
 */
public class CFamilyAddMember extends Bean {

    private String cookie;
    private String uId;

    public CFamilyAddMember(String cookie, String uId) {
        this.cookie = cookie;
        this.uId = uId;
        this.urlOrigin = Constant.UrlOrigin.family_add;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    @Override
    public String toString() {
        return "cookie=" + cookie + "&" + "uId=" + uId;
    }
}

