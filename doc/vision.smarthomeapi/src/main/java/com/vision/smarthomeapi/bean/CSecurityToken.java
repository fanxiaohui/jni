package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.MD5ToText;
import com.vision.smarthomeapi.util.PrefUtils;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class CSecurityToken extends Bean {
    private String key;
    private String uid;
    private String sign;

    public CSecurityToken(String key, String secret) {

        this.key = key;
        this.uid = PrefUtils.getString("regId");
        this.sign = MD5ToText.MD5Encode(key + uid + secret);
        this.urlOrigin = Constant.UrlOrigin.security_token;

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
