package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * Created by zhaoqing on 2016/1/25.
 */
public class Devli extends Bean{

    private List<Defense> list;

    public  Devli(List<Defense> list){
        this.list = list;
    }

    public List<Defense> getList() {
        return list;
    }

    public void setList(List<Defense> list) {
        this.list = list;
    }
}
