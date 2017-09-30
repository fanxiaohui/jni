package com.vision.smarthome.dal.impl;

import java.util.List;

/**
 * 设备告警
 */
public interface IDeviceAlarm {

    /**
     * 获取告警信息
     * @return 当前设备多条告警
     */
    public List<String> getAlarmInfo();

    /**
     * 获取当前告警是否解决状态
     * @return
     */
    public boolean isAlarmSolved();
}
