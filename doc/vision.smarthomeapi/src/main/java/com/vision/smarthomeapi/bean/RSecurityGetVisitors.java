package com.vision.smarthomeapi.bean;

import java.util.ArrayList;

/**
 * 获取用户访客记录
 * Created by yangle on 2017/1/22.
 */
public class RSecurityGetVisitors extends Bean {

    private ArrayList<AlarmInfo> alarmList;

    public class AlarmInfo {
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
         * 告警类型 0.红外 1.门铃
         */
        private int alarmSrc;
        /**
         * 报警时间
         */
        private int alarmTime;
        /**
         * 猫眼拍摄的图片
         */
        private String picUrl;
        /**
         * 服务ID
         */
        private int serviceId;
        /**
         * 设备名称
         */
        private String devName;
        /**
         * 设备版本
         */
        private String devVersion;
        /**
         * 设备图片URL
         */
        private String devImgUrl;

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

        public int getAlarmSrc() {
            return alarmSrc;
        }

        public void setAlarmSrc(int alarmSrc) {
            this.alarmSrc = alarmSrc;
        }

        public int getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(int alarmTime) {
            this.alarmTime = alarmTime;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
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

        @Override
        public String toString() {
            return "AlarmInfo{" +
                    "id=" + id +
                    ", deviceId=" + deviceId +
                    ", customerId=" + customerId +
                    ", alarmSrc=" + alarmSrc +
                    ", alarmTime=" + alarmTime +
                    ", picUrl='" + picUrl + '\'' +
                    ", serviceId=" + serviceId +
                    ", devName='" + devName + '\'' +
                    ", devVersion='" + devVersion + '\'' +
                    ", devImgUrl='" + devImgUrl + '\'' +
                    '}';
        }
    }

    public ArrayList<AlarmInfo> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(ArrayList<AlarmInfo> alarmList) {
        this.alarmList = alarmList;
    }
}
