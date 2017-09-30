package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/12.
 */

public class RWXPay {

    private String sign;
    private String timestamp;
    private String partnerid;
    private String prepayid;
    private String appid;
    private String noncestr;

    public RWXPay(    String sign,
                      String timestamp,
                      String partnerid,
                      String prepayid,
                      String appid,
                      String noncestr){

        this.sign = sign;
        this.timestamp = timestamp;
        this.partnerid = partnerid;
        this.prepayid = prepayid;
        this.appid = appid;
        this.noncestr = noncestr;

    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }
}
