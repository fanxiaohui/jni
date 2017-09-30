package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 添加家庭成员，被动方确认是否加入
 */
public class CFamilyAddConfirm extends Bean {

    private String cookie;
    private String uId;
    private String asw;

    public CFamilyAddConfirm(String cookie, String uId, String asw) {
        this.cookie = cookie;
        this.uId = uId;
        this.asw = asw;
        this.urlOrigin = Constant.UrlOrigin.family_add_confirm;
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

    public void setAsw(String asw) {
        this.asw = asw;
    }

    public String getAsw() {
        return asw;
    }

    @Override
    public String toString() {
        return "cookie=" + cookie + "&" + "uId=" + uId + "&" + "asw=" + asw;
    }
}

