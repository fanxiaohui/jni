package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/6.
 */

public class RShareManageList extends Bean {
    private RShareManageListItem[] list;

    public RShareManageList(RShareManageListItem[] list){
        this.list = list;
    }

    public RShareManageListItem[] getList() {
        return list;
    }

    public void setList(RShareManageListItem[] list) {
        this.list = list;
    }
}
