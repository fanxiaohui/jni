package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/17.
 */
public class RSecurityDeviceAlarmList extends Bean {

    public RSecurityDevice[] deviceList;

    public RSecurityDeviceAlarmList(RSecurityDevice[] deviceList){
        this.deviceList = deviceList;
    }

    public RSecurityDevice[] getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(RSecurityDevice[] deviceList) {
        this.deviceList = deviceList;
    }
}
