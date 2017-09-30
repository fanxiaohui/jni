package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/21.
 */

public class RLetvCheckAdd extends RBean {

    private ReMap remap;
    public RLetvCheckAdd(ReMap remap){

        this.remap = remap;
    }

    public ReMap getRemap() {
        return remap;
    }

    public void setRemap(ReMap remap) {
        this.remap = remap;
    }
}
