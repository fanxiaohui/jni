package com.vision.smarthomeapi.bean;

/**
 * 获取门锁的授权解锁码
 * Created by yangle on 2017/1/22.
 */
public class RSecurityGetLockKey extends Bean {

    /**
     * 解锁码
     */
    private String key;

    public RSecurityGetLockKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
