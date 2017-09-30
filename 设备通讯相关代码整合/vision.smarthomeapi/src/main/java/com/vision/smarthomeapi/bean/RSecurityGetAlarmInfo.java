package com.vision.smarthomeapi.bean;

/**
 * 获取用户告警信息
 * Created by yangle on 2017/2/7.
 */
public class RSecurityGetAlarmInfo extends Bean {

    private RAlarmInfo alarmInfo;

    public RAlarmInfo getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(RAlarmInfo alarmInfo) {
        this.alarmInfo = alarmInfo;
    }
}
