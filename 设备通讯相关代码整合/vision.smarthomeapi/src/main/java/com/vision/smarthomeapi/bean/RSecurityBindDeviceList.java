package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/8.
 */
public class RSecurityBindDeviceList extends Bean {

    public RSecurityDevice[] list;

    public RSecurityBindDeviceList(RSecurityDevice[] list){
        this.list = list;
    }

    public RSecurityDevice[] getList() {
        return list;
    }

    public void setList(RSecurityDevice[] list) {
        this.list = list;
    }
}
