package com.vision.smarthomeapi.bean;

/**
 * 绑定设备
 */
public class RDeviceBind extends Bean {

    private String mac;

    public RDeviceBind(String urlOrigin, String status, String statusMsg, String mac) {
        this.urlOrigin = urlOrigin;
        this.status = status;
        this.statusMsg = statusMsg;
        this.mac = mac;
    }

    public String getMac() {
        return mac.toUpperCase();
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * http请求返回码处理
     *
     * @return 处理方式
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue != Bean.ERROR) {
            return rValue;
        }
        switch (status) {
            case "0003":
                statusMsg = "设备不存在";
                return Bean.TOAST;

            case "0004":
                statusMsg = "该设备已被绑定";
                return Bean.TOAST;

            case "0005":
                statusMsg = "没有权限解绑此设备";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}

