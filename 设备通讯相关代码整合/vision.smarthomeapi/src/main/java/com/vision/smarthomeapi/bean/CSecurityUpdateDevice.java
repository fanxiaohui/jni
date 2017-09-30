package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityUpdateDevice extends Bean{
    private String token;
    /**
     * 设备类型_版本号
     */
    private String devType;

    private String mac;

    private String mode;

    public CSecurityUpdateDevice(String token, String devType,String mac,String mode){

        this.token = token;
        this.devType = devType;
        this.mac = mac;
        this.mode = mode;
        this.urlOrigin = Constant.UrlOrigin.security_check_device_version;

    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
