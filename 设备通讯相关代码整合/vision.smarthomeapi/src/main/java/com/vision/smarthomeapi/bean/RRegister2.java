package com.vision.smarthomeapi.bean;

/**
 * 用户手机号注册第一步回复
 */
public class RRegister2 extends Bean {

    private String step2key;

    public RRegister2(String status, String statusMsg, String step2key) {
        this.status = status;
        this.statusMsg = statusMsg;
        this.step2key = step2key;
    }

    public String getStep2key() {
        return step2key;
    }

    public void setStep2key(String step2key) {
        this.step2key = step2key;
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

            case "0039":
                statusMsg = "短信验证码不可以为空";
                return Bean.TOAST;

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
