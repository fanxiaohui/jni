package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/4.
 */

public class RShareCheckAccount extends Bean {
    private RShareAccount[] list;
    public RShareCheckAccount(RShareAccount[] list){
        this.list = list;
    }

    public RShareAccount[] getList() {
        return list;
    }

    public void setList(RShareAccount[] list) {
        this.list = list;
    }
}
