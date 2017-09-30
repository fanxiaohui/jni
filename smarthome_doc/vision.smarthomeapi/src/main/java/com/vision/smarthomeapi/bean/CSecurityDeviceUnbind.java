package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 设备解绑
 * Created by yangle on 2017/2/8.
 */
public class CSecurityDeviceUnbind extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 设备ID
     */
    private String deviceId;

    public CSecurityDeviceUnbind(String token, String deviceId) {
        this.token = token;
        this.deviceId = deviceId;
        this.urlOrigin = Constant.UrlOrigin.security_device_unbind;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
