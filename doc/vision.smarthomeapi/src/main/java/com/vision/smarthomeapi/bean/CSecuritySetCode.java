package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 设置安全码
 * Created by yangle on 2017/2/10.
 */
public class CSecuritySetCode extends Bean {

    /**
     * 访问令牌
     */
    private String token;
    /**
     * 身份证号
     */
    private String identifier;
    /**
     * 安全码
     */
    private String code;

    public CSecuritySetCode(String token,String identifier, String code) {
        this.token = token;
        this.code = code;
        this.identifier = identifier;
        this.urlOrigin = Constant.UrlOrigin.security_set_code;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
