package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/7.
 */

public class RLetvVipGo extends RBean {

    private String orderStr;


    public RLetvVipGo(String orderStr){
        this.orderStr = orderStr;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
