package com.vision.smarthomeapi.bean;

import java.util.List;

public class RGetCurrentAlarms extends Bean {

    private List<RCurrentAlarm> alarmList;

    public RGetCurrentAlarms(String urlOrigin, String status, String statusMsg, List<RCurrentAlarm> alarmList) {
        super();
        this.urlOrigin = urlOrigin;
        this.status = status;
        this.statusMsg = statusMsg;
        this.alarmList = alarmList;
    }

    public List<RCurrentAlarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<RCurrentAlarm> alarmList) {
        this.alarmList = alarmList;
    }
}

