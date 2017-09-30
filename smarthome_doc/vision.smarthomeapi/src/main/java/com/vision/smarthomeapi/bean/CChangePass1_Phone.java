package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 通过手机号修改登录密码第一步
 */
public class CChangePass1_Phone extends Bean {
    /**
     * 用户手机号
     */
    private String phone;

    public CChangePass1_Phone(String phone) {
        this.phone = phone;
        this.urlOrigin = Constant.UrlOrigin.user_change_pass_byphone1;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

