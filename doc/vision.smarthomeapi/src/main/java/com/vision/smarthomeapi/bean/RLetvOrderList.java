package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvOrderList extends Bean{

    public RLetvOrder[] list;

    public RLetvOrderList(RLetvOrder[] list){
        this.list = list;
    }

    public RLetvOrder[] getList() {
        return list;
    }

    public void setList(RLetvOrder[] list) {
        this.list = list;
    }
}
