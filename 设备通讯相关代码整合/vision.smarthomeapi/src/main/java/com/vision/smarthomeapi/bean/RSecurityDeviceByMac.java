package com.vision.smarthomeapi.bean;


public class RSecurityDeviceByMac extends Bean {

    public RSecurityDevice device;

    public RSecurityDeviceByMac(RSecurityDevice device){
        this.device = device;
    }

    public RSecurityDevice getDevice() {
        return device;
    }

    public void setDevice(RSecurityDevice device) {
        this.device = device;
    }
}
