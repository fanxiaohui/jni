package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 更新分享昵称
 * Created by yang on 2017/1/21.
 */
public class CSecurityUpdateShareNick extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 昵称
     */
    private String nickName;

    /**
     * 接受者ID
     */
    private int revicerId;

    public CSecurityUpdateShareNick(String token, String nickName,int revicerId) {
        this.token = token;
        this.nickName = nickName;
        this.revicerId = revicerId;
        this.urlOrigin = Constant.UrlOrigin.security_update_share_nick;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
    }
}
