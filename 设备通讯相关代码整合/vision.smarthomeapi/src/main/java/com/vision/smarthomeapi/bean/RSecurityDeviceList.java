package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/8.
 */
public class RSecurityDeviceList extends Bean {

    public RSecurityDevice[] deviceList;

    public RSecurityDeviceList(RSecurityDevice[] deviceList){
        this.deviceList = deviceList;
    }

    public RSecurityDevice[] getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(RSecurityDevice[] deviceList) {
        this.deviceList = deviceList;
    }
}
