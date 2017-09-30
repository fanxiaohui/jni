package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/6/29.
 */
public class RCheckDeviceVersion extends Bean {
    private RUpgradeInfo version;
    public RCheckDeviceVersion(RUpgradeInfo version){
        this.version = version;
    }

    public RUpgradeInfo getVersion() {
        return version;
    }

    public void setVersion(RUpgradeInfo version) {
        this.version = version;
    }
}
