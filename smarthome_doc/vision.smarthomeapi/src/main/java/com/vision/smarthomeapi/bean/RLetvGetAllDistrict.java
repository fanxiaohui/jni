package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/23.
 */

public class RLetvGetAllDistrict extends RBean {


    private RLetvGetDistrict[] list;

    public RLetvGetAllDistrict(RLetvGetDistrict[] list){
        this.list = list;
    }

    public RLetvGetDistrict[] getList() {
        return list;
    }

    public void setList(RLetvGetDistrict[] list) {
        this.list = list;
    }
}
