package com.vision.smarthomeapi.bean;

/**
 * 用户修改绑定手机号第四步
 */
public class RChangePhone4 extends Bean {

    public RChangePhone4(String status, String statusMsg) {
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
            case "0040":
                statusMsg = "短信验证码超时";
                return Bean.TOAST;

            case "0042":
                statusMsg = "短信验证码有误";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}
