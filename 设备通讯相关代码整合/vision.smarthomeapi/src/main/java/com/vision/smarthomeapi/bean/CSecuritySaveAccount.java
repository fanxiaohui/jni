package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 个性账号保存
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecuritySaveAccount extends Bean{
    private String token;
    private String userId;
    private String password;
    private String account;

    public CSecuritySaveAccount(String token, String userId, String account,String password){

        this.token = token;
        this.userId = userId;
        this.password = password;
        this.account = account;
        this.urlOrigin = Constant.UrlOrigin.security_saveAccount;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
