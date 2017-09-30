package com.vision.smarthomeapi.bean;

/**
 * 获取门铃的红外推送策略
 * Created by yang on 2017/1/22.
 */
public class RSecurityGetPushStrategy extends Bean {

    private PushStrategy pushStrategy;

    public class PushStrategy {
        /**
         * ID
         */
        private int id;
        /**
         * 设备ID
         */
        private int devId;
        /**
         * 0.不推送 1.推送 2.近期内不推送
         */
        private int pushStra;
        /**
         * 时间
         */
        private long expireTime;
        /**
         * 设备MAC
         */
        private String devMac;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDevId() {
            return devId;
        }

        public void setDevId(int devId) {
            this.devId = devId;
        }

        public int getPushStra() {
            return pushStra;
        }

        public void setPushStra(int pushStra) {
            this.pushStra = pushStra;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        public String getDevMac() {
            return devMac;
        }

        public void setDevMac(String devMac) {
            this.devMac = devMac;
        }
    }

    public PushStrategy getPushStrategy() {
        return pushStrategy;
    }

    public void setPushStrategy(PushStrategy pushStrategy) {
        this.pushStrategy = pushStrategy;
    }
}
