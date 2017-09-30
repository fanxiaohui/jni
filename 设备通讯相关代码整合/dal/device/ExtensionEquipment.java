package com.vision.smarthome.dal.device;

import com.vision.smarthomeapi.dal.data.SmartDevice;

/**
 * 电子门磁
 * Created by zhaoqing on 2016/1/21.
 */
public class ExtensionEquipment extends SmartDevice  {



    public ExtensionEquipment(){

        initSmartDeviceData(this);

    }

    @Override
    public boolean sendDeviceStatus() {
        return false;
    }

    @Override
    public boolean parseDeviceStatus(byte[] data) {
        return false;
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
