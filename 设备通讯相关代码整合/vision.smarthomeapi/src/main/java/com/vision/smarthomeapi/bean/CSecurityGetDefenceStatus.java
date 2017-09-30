package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取用户的布撤防状态
 * Created by yangle on 2017/1/22.
 */
public class CSecurityGetDefenceStatus extends Bean {

    /**
     * 访问令牌
     */
    private String token;

    public CSecurityGetDefenceStatus(String token) {
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_get_defence_status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
