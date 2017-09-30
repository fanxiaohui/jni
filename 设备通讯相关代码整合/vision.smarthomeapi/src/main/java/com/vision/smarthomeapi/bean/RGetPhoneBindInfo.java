package com.vision.smarthomeapi.bean;

/**
 * 获取手机号码绑定信息
 */
public class RGetPhoneBindInfo extends Bean {

    private String phone;

    public RGetPhoneBindInfo(String status, String statusMsg, String phone) {
        this.phone = phone;
        this.status = status;
        this.statusMsg = statusMsg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * http返回值处理
     *
     * @return 通知类型
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue != Bean.ERROR) {
            return rValue;
        }
        switch (status) {
        }
        return Bean.ERROR;
    }
}

