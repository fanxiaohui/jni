package com.vision.smarthomeapi.bean;

/**
 * 省
 * Created by zhaoqing on 2017/5/13.
 */

public class RLetvAreaProvince {

    public RLetvArea area;
    public RLetvAreaCity[] childArea;

    RLetvAreaProvince(RLetvArea area,RLetvAreaCity[] childArea){
        this.area = area;
        this.childArea = childArea;

    }

    public RLetvArea getArea() {
        return area;
    }

    public void setArea(RLetvArea area) {
        this.area = area;
    }

    public RLetvAreaCity[] getChildArea() {
        return childArea;
    }

    public void setChildArea(RLetvAreaCity[] childArea) {
        this.childArea = childArea;
    }
}
