package com.vision.smarthomeapi.bean;

/**
 * 用户通过手机号修改密码第三步
 */
public class RChangePass3_Phone extends Bean {

    public RChangePass3_Phone(String status, String statusMsg) {
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
            case "0035":
                statusMsg = "手机号码不可以为空";
                return Bean.TOAST;

            case "0040":
                statusMsg = "忘记密码操作超时";
                return Bean.TOAST;

            case "0043":
                statusMsg = "登录密码不可为空";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}
