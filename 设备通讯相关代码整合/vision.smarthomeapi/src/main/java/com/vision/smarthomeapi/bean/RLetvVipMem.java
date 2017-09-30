package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class RLetvVipMem extends RBean {

    private RLetvProducts[] products;

    public RLetvVipMem(RLetvProducts[] products){

        this.products = products;
    }

    public RLetvProducts[] getProducts() {
        return products;
    }

    public void setProducts(RLetvProducts[] products) {
        this.products = products;
    }
}
