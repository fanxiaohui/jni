package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改昵称
 */
public class CChangeNick extends Bean {
    /**
     * 用户cookie
     */
    private String cookie;
    /**
     * 用户昵称
     */
    private String nick;

    public CChangeNick(String cookie, String nick) {
        this.cookie = cookie;
        this.nick = nick;
        this.urlOrigin = Constant.UrlOrigin.user_alter_nick;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }
}

