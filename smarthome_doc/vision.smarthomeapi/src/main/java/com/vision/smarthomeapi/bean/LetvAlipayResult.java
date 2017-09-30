package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/9.
 */

public class LetvAlipayResult extends Bean{

    private LetvAppPayResponse alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    LetvAlipayResult(LetvAppPayResponse alipay_trade_app_pay_response,
                     String sign,String sign_type){

        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
        this.sign = sign;
        this.sign_type = sign_type;
    }

    public LetvAppPayResponse getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(LetvAppPayResponse alipay_trade_app_pay_response) {
        this.alipay_trade_app_pay_response = alipay_trade_app_pay_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}
