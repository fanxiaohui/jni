package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvNewsList extends Bean{

    public RLetvNews[] list;

    public RLetvNewsList(RLetvNews[] list){
        this.list = list;
    }

    public RLetvNews[] getList() {
        return list;
    }

    public void setList(RLetvNews[] list) {
        this.list = list;
    }
}
