package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.util.JsonUtil;

/**
 * Created by zhaoqing on 2017/6/9.
 */

public class CLetvVipFinal extends Bean {



    private String alipay_trade_app_pay_response;
    private String sign;
    private String sign_type;

    public CLetvVipFinal(LetvAlipayResult letvAlipayResult,String alipay_trade_app_pay_response){
        this.alipay_trade_app_pay_response =alipay_trade_app_pay_response;
        this.sign = letvAlipayResult.getSign();
        this.sign_type = letvAlipayResult.getSign_type();

        this.urlOrigin = Constant.UrlOrigin.letv_vip_final;
    }

    public String getAlipay_trade_app_pay_response() {
        return alipay_trade_app_pay_response;
    }

    public void setAlipay_trade_app_pay_response(String alipay_trade_app_pay_response) {
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
