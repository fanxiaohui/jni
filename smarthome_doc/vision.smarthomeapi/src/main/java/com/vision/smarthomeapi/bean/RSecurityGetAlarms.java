package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/1/21.
 */

public class RSecurityGetAlarms extends RBean {

    private RAlarmInfo[] alarmList;
    private RSecurityDistrict district;

    public RSecurityGetAlarms(RAlarmInfo[] alarmList,RSecurityDistrict district){
        this.alarmList = alarmList;
        this.district = district;
    }

    public RAlarmInfo[] getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(RAlarmInfo[] alarmList) {
        this.alarmList = alarmList;
    }

    public RSecurityDistrict getDistrict() {
        return district;
    }

    public void setDistrict(RSecurityDistrict district) {
        this.district = district;
    }
}
