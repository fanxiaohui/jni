package com.vision.smarthomeapi.bean;

/**
 * 设备名称
 */
public class RDeviceName extends Bean {

    public RDeviceName(String urlOrigin, String status, String statusMsg) {
        this.urlOrigin = urlOrigin;
        this.status = status;
        this.statusMsg = statusMsg;
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

            default:
                break;
        }
        return Bean.ERROR;
    }
}

