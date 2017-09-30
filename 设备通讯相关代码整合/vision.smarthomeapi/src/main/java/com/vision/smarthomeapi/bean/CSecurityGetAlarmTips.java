package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 获取告警类型的提示
 * Created by yangle on 2017/2/7.
 */
public class CSecurityGetAlarmTips extends Bean {

    /**
     * 访问令牌
     */
    private String token;

    public CSecurityGetAlarmTips(String token) {
        this.token = token;
        this.urlOrigin = Constant.UrlOrigin.security_get_alarm_tips;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
