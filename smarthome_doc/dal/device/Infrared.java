package com.vision.smarthome.dal.device;


import com.vision.smarthomeapi.dal.data.SmartDevice;

/**
 * 红外
 */
public final class Infrared extends SmartDevice {


    public Infrared() {
        super();
        initSmartDeviceData(this);
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

            default:

                break;
        }
    }




}
