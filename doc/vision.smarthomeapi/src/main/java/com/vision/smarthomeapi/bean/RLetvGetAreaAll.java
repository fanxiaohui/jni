package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/13.
 */

public class RLetvGetAreaAll extends Bean {


    public RLetvAreaProvince[] list;
    public RLetvGetAreaAll(RLetvAreaProvince[] list){
        this.list = list;

    }

    public RLetvAreaProvince[] getList() {
        return list;
    }

    public void setList(RLetvAreaProvince[] list) {
        this.list = list;
    }
}
