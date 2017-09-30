package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityDeviceCanModify extends Bean {

    private SecurityDeviceCanModify[] list;

    public RSecurityDeviceCanModify(SecurityDeviceCanModify[] list){
        this.list = list;
    }

    public SecurityDeviceCanModify[] getList() {
        return list;
    }

    public void setList(SecurityDeviceCanModify[] list) {
        this.list = list;
    }
}
