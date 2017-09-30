package com.vision.smarthomeapi.bean;

/**
 * 获取猫眼的告警详情
 * Created by yangle on 2017/1/22.
 */
public class RSecurityGetCatEyeInfo extends Bean {

    private AlarmInfo alarmInfo;

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
         * 告警类型
         */
        private String alarmType;
        /**
         * 告警类型 19.红外 20.门铃
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

        private String mac;

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

        public String getAlarmType() {
            return alarmType;
        }

        public void setAlarmType(String alarmType) {
            this.alarmType = alarmType;
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

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }
    }

    public AlarmInfo getAlarmInfo() {
        return alarmInfo;
    }

    public void setAlarmInfo(AlarmInfo alarmInfo) {
        this.alarmInfo = alarmInfo;
    }
}
