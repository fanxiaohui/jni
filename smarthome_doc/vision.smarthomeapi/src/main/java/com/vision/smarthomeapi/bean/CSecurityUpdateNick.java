package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 更新昵称
 * Created by yang on 2017/1/21.
 */
public class CSecurityUpdateNick extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 昵称
     */
    private String name;

    public CSecurityUpdateNick(String token, String name) {
        this.token = token;
        this.name = name;
        this.urlOrigin = Constant.UrlOrigin.security_update_nick;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
