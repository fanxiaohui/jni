package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvNotes extends Bean{

    private int id;
    private int deviceId;
    private int customerId;
    private int state;
    private int openId;
    private String openValue;
    private long openTime;
    private int type;
    private String deviceName;
    private int headId;
    private String name;



    public RLetvNotes( int id,
                       int deviceId,
                       int customerId,
                       int state,
                       int openId,
                       String openValue,
                       long openTime,
                       int type,
                       String deviceName,
                       int headId,
                       String name){

        this.id=id;
        this.deviceId=deviceId;
        this.customerId=customerId;
        this.state=state;
        this.openId=openId;
        this.openValue=openValue;
        this.openTime=openTime;
        this.type=type;
        this.deviceName=deviceName;
        this.headId=headId;
        this.name=name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOpenId() {
        return openId;
    }

    public void setOpenId(int openId) {
        this.openId = openId;
    }

    public String getOpenValue() {
        return openValue;
    }

    public void setOpenValue(String openValue) {
        this.openValue = openValue;
    }

    public long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(long openTime) {
        this.openTime = openTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
