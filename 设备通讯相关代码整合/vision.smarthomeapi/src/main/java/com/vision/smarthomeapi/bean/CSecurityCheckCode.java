package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 验证安全码
 * Created by yangle on 2016/1/6.
 */
public class CSecurityCheckCode extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 安全码
     */
    private String code;

    public CSecurityCheckCode(String token, String code) {
        this.token = token;
        this.code = code;
        this.urlOrigin = Constant.UrlOrigin.security_check_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
