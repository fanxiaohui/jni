package com.vision.smarthomeapi.bean;

/**
 * 用户修改手机号第三步
 */
public class RChangePhone3 extends Bean {

    private String step3key;
    private int count;

    public RChangePhone3(String status, String statusMsg, String step3key, int count) {
        this.status = status;
        this.statusMsg = statusMsg;
        this.step3key = step3key;
        this.count = count;
    }

    public String getStep3key() {
        return step3key;
    }

    public void setStep3key(String step3key) {
        this.step3key = step3key;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

            case "0036":
                statusMsg = "手机号码已注册，请绑定其他手机号,";
                return Bean.TOAST;

            case "0037":
                statusMsg = "操作太频繁了，请稍后再试";
                return Bean.TOAST;

            case "0038":
                statusMsg = "发送短信验证码失败";
                return Bean.TOAST;

            case "0040":
                statusMsg = "修改手机操作超时";
                return Bean.TOAST;

            case "0044":
                statusMsg = "新手机号与旧手机号一致，请绑定其他手机号";
                return Bean.TOAST;

            case "002C":
                statusMsg = "发送短信验证码次数已达上限";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}
