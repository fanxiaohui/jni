package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取门锁的授权解锁码
 * Created by yangle on 2017/1/22.
 */
public class CSecurityGetLockKey extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 设备ID
     */
    private int id;

    public CSecurityGetLockKey(String token, int id) {
        this.token = token;
        this.id = id;
        this.urlOrigin = Constant.UrlOrigin.security_get_lock_key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
