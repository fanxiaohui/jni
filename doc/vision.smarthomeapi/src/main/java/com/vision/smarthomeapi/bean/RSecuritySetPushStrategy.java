package com.vision.smarthomeapi.bean;

/**
 * 更改门铃的红外推送策略
 * Created by yangle on 2017/1/22.
 */
public class RSecuritySetPushStrategy extends Bean {

    /**
     * 是否成功
     */
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

