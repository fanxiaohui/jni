package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecuritySaveLinkage extends Bean {

    private boolean success;

    public RSecuritySaveLinkage(boolean success){
        this.success = success;

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
