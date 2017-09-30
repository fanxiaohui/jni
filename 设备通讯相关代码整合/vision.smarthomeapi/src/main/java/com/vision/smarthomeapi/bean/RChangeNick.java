package com.vision.smarthomeapi.bean;

/**
 * 修改昵称
 */
public class RChangeNick extends Bean {

    public RChangeNick(String status, String statusMsg) {
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
            case "0025":
                statusMsg = "昵称为空";
                return Bean.TOAST;

            case "0026":
                statusMsg = "昵称没有变化";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}

