package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class RShareList extends Bean {
    private RShareListItem[] list;

    public RShareList(RShareListItem[] list){
        this.list = list;

    }

    public RShareListItem[] getList() {
        return list;
    }

    public void setList(RShareListItem[] list) {
        this.list = list;
    }
}
