package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class RShareAcceptDeviceItem extends Bean {


    private int deviceId;
    private String deviceImg;
    private String deviceName;
    private String deviceType;
    private long shareTime;
    private String mac;

    public RShareAcceptDeviceItem(    int deviceId,
                                      String deviceImg,
                                      String deviceName,
                                      String deviceType,
                                      long shareTime,
                                      String mac){

        this.deviceId = deviceId;
        this.deviceImg = deviceImg;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.shareTime = shareTime;
        this.mac = mac;
    }


    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceImg() {
        return deviceImg;
    }

    public void setDeviceImg(String deviceImg) {
        this.deviceImg = deviceImg;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public long getShareTime() {
        return shareTime;
    }

    public void setShareTime(long shareTime) {
        this.shareTime = shareTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
