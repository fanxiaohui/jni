package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/8/2.
 */

public class RCurrentAlarm extends RBean{
    //共有的
    /**
     * id
     */
    private int id;
    /**
     * 设备ID
     */
    private int deviceId;
    /**
     * 用户ID
     */
    private int customerId;
    /**
     * 告警时间
     */
    private long alarmTime;
    /**
     * 服务ID
     */
    private int serviceId;
    /**
     * 设备名称
     */
    private String devName;
    /**
     * 设备型号
     */
    private String devVersion;
    private String devImgUrl;
    /**
     * mac
     */
    private String mac;

    //其他设备的
    /**
     * 告警类型ID
     */
    private int alarmTypeId;
    /**
     * 告警类型
     */
    private String alarmType;
    /**
     * 告警状态 1告警 2消警
     */
    private int alarmStatus;
    private int alarmTip;


    //猫眼独有的
    private int alarmSrc;
    /**
     * 猫眼告警图片
     */
    private String picUrl;


    public RCurrentAlarm(){

    }
    public RCurrentAlarm(
            int id,
            int deviceId,
            int customerId,
            int alarmTime,
            int serviceId,
            String devName,
            String devVersion,
            String devImgUrl,
            int alarmTypeId,
            String alarmType,
            int alarmStatus,
            int alarmTip,
            int alarmSrc,
            String picUrl,
            String mac){

        //共有的
        this.id = id;
        this.deviceId = deviceId;
        this.customerId = customerId;
        this.alarmTime = alarmTime;
        this.serviceId = serviceId;
        this.devName = devName;
        this.devVersion = devVersion;
        this.devImgUrl = devImgUrl;
        this.mac = mac;

        //其他设备的
        this.alarmTypeId = alarmTypeId;
        this.alarmType = alarmType;
        this.alarmStatus = alarmStatus;
        this.alarmTip = alarmTip;


        //猫眼独有的
        this.alarmSrc = alarmSrc;
        this.picUrl = picUrl;




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

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
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

    public int getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(int alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public int getAlarmTip() {
        return alarmTip;
    }

    public void setAlarmTip(int alarmTip) {
        this.alarmTip = alarmTip;
    }

    public int getAlarmSrc() {
        return alarmSrc;
    }

    public void setAlarmSrc(int alarmSrc) {
        this.alarmSrc = alarmSrc;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
