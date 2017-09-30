package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/1/21.
 */
public class RAlarmInfo {
    /**
     * ID
     */
    private int id;
    /**
     * 用户ID
     */
    private int customerId;
    /**
     * 设备ID
     */
    private int deviceId;
    /**
     * 告警类型ID
     */
    private int alarmTypeId;
    /**
     * 告警类型
     */
    private String alarmType;
    /**
     * 报警时间
     */
    private long alarmTime;
    /**
     * 报警状态
     * 1.告警 2.消警
     */
    private int alarmStatus;
    /**
     * 服务ID
     */
    private int serviceId;
    /**
     * 报警提示
     */
    private int alarmTip;
    /**
     * 设备名称
     */
    private String devName;
    /**
     * 设备类型
     */
    private int devType;
    /**
     * 设备版本
     */
    private String devVersion;
    /**
     * 设备图片URL
     */
    private String devImgUrl;

    private String mac;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(int alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

    public String getAlarmType() {
        return alarmType == null ? "" : alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getAlarmTip() {
        return alarmTip;
    }

    public void setAlarmTip(int alarmTip) {
        this.alarmTip = alarmTip;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getDevType() {
        return devType;
    }

    public void setDevType(int devType) {
        this.devType = devType;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(String devVersion) {
        this.devVersion = devVersion;
    }

    public String getDevImgUrl() {
        return devImgUrl;
    }

    public void setDevImgUrl(String devImgUrl) {
        this.devImgUrl = devImgUrl;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
