package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class RLetvGetOrderDetail extends Bean {

    private RLetvOrderDetail order;

    public RLetvGetOrderDetail(RLetvOrderDetail order){
        this.order = order;
    }

    public RLetvOrderDetail getOrder() {
        return order;
    }

    public void setOrder(RLetvOrderDetail order) {
        this.order = order;
    }
}
