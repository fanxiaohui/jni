package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityStateReport extends Bean {

    private RdeviceStateVo deviceStateVo;

    public RSecurityStateReport(RdeviceStateVo deviceStateVo){
        this.deviceStateVo = deviceStateVo;
    }

    public RdeviceStateVo getDeviceStateVo() {
        return deviceStateVo;
    }

    public void setDeviceStateVo(RdeviceStateVo deviceStateVo) {
        this.deviceStateVo = deviceStateVo;
    }
}
