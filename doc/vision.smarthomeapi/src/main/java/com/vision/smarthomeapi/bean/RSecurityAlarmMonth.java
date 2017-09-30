package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * Created by zhaoqing on 2016/5/27.
 */
public class RSecurityAlarmMonth extends Bean {
    private  List<String> months;

    public RSecurityAlarmMonth( List<String> months){
        this.months = months;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }
}
