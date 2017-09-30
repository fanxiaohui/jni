package com.vision.smarthome.dal.device;

import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.function.AlarmData;
import com.vision.smarthomeapi.util.NotificationManager;

/**
 * 门磁
 */
public final class Magnetometer extends SmartDevice {

    private AlarmData alarmData;

    public Magnetometer() {
        super();
        initSmartDeviceData(this);
        alarmData = new AlarmData(this);
    }

    @Override
    public boolean sendDeviceStatus() {
        return true;
    }

    @Override
    public boolean parseDeviceStatus(byte[] data) {
        return true;
    }

    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);
        switch (msgID) {
            case SmartDeviceConstant.MsgID.DEVICE_ALARM_SYNC://告警信息
                if (parseAlarmInfo(data)) {
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_MESSAGE_ALARM_INFO, null, this);
                }
                break;
            default:

                break;
        }
    }


    /**
     * 解析设备异常
     *
     * @param data
     * @return
     */
    private boolean parseFaultInfo(byte[] data) {
        return alarmData.parseFaultInfo(data);
    }

    /**
     * 解析->报警状态同步,协议0x31
     *
     * @param data
     * @return
     */

    public boolean parseAlarmInfo(byte[] data) {
        return alarmData.parseAlarmData(data);
    }
}
