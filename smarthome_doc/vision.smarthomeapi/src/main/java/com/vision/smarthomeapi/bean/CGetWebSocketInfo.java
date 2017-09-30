package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取地理服务器地址
 */
public class CGetWebSocketInfo extends Bean {

    private String token;

    public CGetWebSocketInfo(String token) {
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.user_getwebsocketinfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
