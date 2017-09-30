package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * 获取用户告警状态
 * Created by yang on 2017/2/17.
 */
public class RSecurityAlarmStatus extends Bean {

    private List<RAlarmInfo> alarmServiceList;

    public List<RAlarmInfo> getAlarmServiceList() {
        return alarmServiceList;
    }

    public void setAlarmServiceList(List<RAlarmInfo> alarmServiceList) {
        this.alarmServiceList = alarmServiceList;
    }
}
