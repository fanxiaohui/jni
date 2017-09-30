package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 修改密码
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityUpdatePwd extends Bean {

    private String token;
    private String oldPwd;
    private String newPwd;

    public CSecurityUpdatePwd(String token, String oldPwd, String newPwd){
        this.token = token;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        this.urlOrigin = Constant.UrlOrigin.security_updatePwd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
