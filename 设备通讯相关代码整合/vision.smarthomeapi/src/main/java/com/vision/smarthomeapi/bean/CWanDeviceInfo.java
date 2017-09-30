package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.OutPutMessage;

/**
 * 设备信息查询
 */
public class CWanDeviceInfo extends Bean {

    private String cookie;
    private String dId;

    public CWanDeviceInfo(String cookie, String dId) {
        this.cookie = cookie;
        this.dId = dId;
        OutPutMessage.LogCatInfo("消息推送", "设备ID：" + dId);
        this.urlOrigin = Constant.UrlOrigin.wan_device_info;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public String getDId() {
        return dId;
    }

    public void setDId(String dId) {
        this.dId = dId;
    }
}

