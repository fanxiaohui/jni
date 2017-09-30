package com.vision.smarthomeapi.bean;

/**
 * 用户通过旧密码修改密码第一步
 */
public class RChangePass1 extends Bean {

    private String step1key;

    public RChangePass1(String status, String statusMsg, String step1key) {
        this.status = status;
        this.statusMsg = statusMsg;
        this.step1key = step1key;
    }

    public String getStep1key() {
        return step1key;
    }

    public void setStep1key(String step1key) {
        this.step1key = step1key;
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
            case "000F":
                statusMsg = "密码不正确";
                return Bean.TOAST;
            case "0046":
                statusMsg = "修改密码次数已达上限";
                return Bean.TOAST;
            default:
                break;
        }
        return Bean.ERROR;
    }
}
