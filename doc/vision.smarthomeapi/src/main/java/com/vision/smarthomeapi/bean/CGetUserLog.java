package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户日志
 */
public class CGetUserLog extends Bean {
    /**
     * 用户登录成功后返回的cookie
     */
    private String cookie;
    /**
     * 获取的日志信息的截止时间戳
     */
    private String laststamp;

    public CGetUserLog(String cookie, String laststamp) {
        this.cookie = cookie;
        this.laststamp = laststamp;
        this.urlOrigin = Constant.UrlOrigin.get_userlog;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getLaststamp() {
        return laststamp;
    }

    public void setLaststamp(String laststamp) {
        this.laststamp = laststamp;
    }
}

