package com.vision.smarthomeapi.bean;

/**
 * 市
 * Created by zhaoqing on 2017/5/13.
 */

public class RLetvAreaCity {

    public RLetvArea area;
    public RLetvAreaDistrict[] childArea;

    RLetvAreaCity(RLetvArea area,RLetvAreaDistrict[] childArea){
        this.childArea = childArea;
        this.area = area;

    }

    public RLetvArea getArea() {
        return area;
    }

    public void setArea(RLetvArea area) {
        this.area = area;
    }

    public RLetvAreaDistrict[] getChildArea() {
        return childArea;
    }

    public void setChildArea(RLetvAreaDistrict[] childArea) {
        this.childArea = childArea;
    }
}
