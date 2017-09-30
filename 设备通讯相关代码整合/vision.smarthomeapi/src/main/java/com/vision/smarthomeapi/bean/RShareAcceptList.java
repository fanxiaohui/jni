package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class RShareAcceptList extends Bean {

    private RShareAcceptItem[] list;

    public RShareAcceptList(RShareAcceptItem[] list){
        this.list = list;
    }

    public RShareAcceptItem[] getList() {
        return list;
    }

    public void setList(RShareAcceptItem[] list) {
        this.list = list;
    }
}
