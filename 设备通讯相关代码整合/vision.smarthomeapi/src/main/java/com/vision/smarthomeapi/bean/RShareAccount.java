package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/4.
 */

public class RShareAccount extends Bean {

    private int id;
    private int shareId;
    private int deviceId;
    private int revicerId;
    private long createTime;
    private int state;
    private String revicerNick;
    private long sureTime;
    private String mac;

    public RShareAccount( int id,
                          int shareId,
                          int deviceId,
                          int revicerId,
                          long createTime,
                          int state,
                          String revicerNick,
                          long sureTime,
                          String mac){


        this.id = id;
        this.shareId = shareId;
        this.deviceId = deviceId;
        this.revicerId = revicerId;
        this.createTime = createTime ;
        this.state = state;
        this.revicerNick = revicerNick;
        this.sureTime = sureTime;
        this.mac = mac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRevicerNick() {
        return revicerNick;
    }

    public void setRevicerNick(String revicerNick) {
        this.revicerNick = revicerNick;
    }

    public long getSureTime() {
        return sureTime;
    }

    public void setSureTime(long sureTime) {
        this.sureTime = sureTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
