package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 设备绑定
 * Created by yangle on 2017/2/8.
 */
public class CSecurityDeviceBind extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 二维码
     */
    private String qrcode;

    public CSecurityDeviceBind(String token, String qrcode) {
        this.token = token;
        this.qrcode = qrcode;
        this.urlOrigin = Constant.UrlOrigin.security_device_bind;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }
}
