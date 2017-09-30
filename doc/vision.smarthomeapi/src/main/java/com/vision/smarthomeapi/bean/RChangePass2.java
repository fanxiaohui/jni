package com.vision.smarthomeapi.bean;

/**
 * 用户通过旧密码修改密码第二步
 */
public class RChangePass2 extends Bean {

    public RChangePass2(String status, String statusMsg) {
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
                statusMsg = "修改密码操作超时";
                return Bean.TOAST;

            case "0045":
                statusMsg = "新密码不可与旧密码一致";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}
