package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityGetThreeLinkage extends Bean {

    private int scheme;

    public RSecurityGetThreeLinkage(int scheme){
        this.scheme = scheme;

    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }
}
