package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改邮箱
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdateEmail extends Bean {

    private String token;
    private String oldEmail;
    private String newEmail;

    public CSecurityUpdateEmail(String token, String oldEmail, String newEmail){
        this.token = token;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.urlOrigin = Constant.UrlOrigin.security_updateEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
