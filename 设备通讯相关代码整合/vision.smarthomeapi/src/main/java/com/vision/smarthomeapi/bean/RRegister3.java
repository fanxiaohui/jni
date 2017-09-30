package com.vision.smarthomeapi.bean;

/**
 * 用户手机号注册第一步回复
 */
public class RRegister3 extends Bean {

    private String uId;

    public RRegister3(String status, String statusMsg, String uId) {
        this.status = status;
        this.statusMsg = statusMsg;
        this.uId = uId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
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
                statusMsg = "手机已注册，请直接登录";
                return Bean.TOAST;

            case "0040":
                statusMsg = "注册操作超时";
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
