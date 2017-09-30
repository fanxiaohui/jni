package com.vision.smarthome.dal.device;

import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;

/**
 * 电控锁
 * Created by yangle on 2017/3/11.
 */
public class ElectricLock extends SmartDevice {

    public ElectricLock() {
        initSmartDeviceData(this);
    }

    @Override
    public boolean sendDeviceStatus() {
        return false;
    }

    @Override
    public boolean parseDeviceStatus(byte[] data) {
        OutPutMessage.LogCatInfo("电控锁接收数据", "date：" + ByteUtil.byteArrayToHexString(data));
        getSmartDeviceLogic().setDoorState(ByteUtil.byteToInt(data[0]));
        return true;
    }

    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);
        switch (msgID) {

            default:
                break;
        }
    }
}
