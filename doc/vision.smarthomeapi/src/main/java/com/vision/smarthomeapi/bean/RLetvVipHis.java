package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/9.
 */

public class RLetvVipHis extends RBean{

    private RLetvVipHisOrder[] orders;

    public RLetvVipHis(RLetvVipHisOrder[] orders){
        this.orders = orders;
    }

    public RLetvVipHisOrder[] getOrders() {
        return orders;
    }

    public void setOrders(RLetvVipHisOrder[] orders) {
        this.orders = orders;
    }
}
