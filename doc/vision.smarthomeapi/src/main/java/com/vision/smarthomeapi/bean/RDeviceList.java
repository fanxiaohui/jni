package com.vision.smarthomeapi.bean;

import java.util.List;

public class RDeviceList extends Bean {

    private List<WanDevice> bindedDevices;

    public RDeviceList(String urlOrigin, String status, String statusMsg, List<WanDevice> bindedDevices) {
        super();
        this.urlOrigin = urlOrigin;
        this.status = status;
        this.statusMsg = statusMsg;
        this.bindedDevices = bindedDevices;
    }

    public List<WanDevice> getBindDevices() {
        return bindedDevices;
    }

    public void setBindDevices(List<WanDevice> bindDevices) {
        this.bindedDevices = bindDevices;
    }

    /**
     * http请求返回码处理
     *
     * @return 处理方式
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue == Bean.OK) {
//            OutPutMessage.LogCatInfo(WebSocketChannel.TAG, "广域网列表--->" + bindedDevices);
//            SmartDeviceManage.defaultManager().parseWanNetDeviceList(bindedDevices);//初始化数据并添加到设备列表中
        }
        if (rValue != Bean.ERROR) {
            return rValue;
        }
        switch (status) {
        }
        return Bean.ERROR;
    }


    public class WanDevice {

        private String mac;
        private String deviceId;
        private String pwd;
        private String deviceName;
        private String deviceType;
        private String deviceAddr;
        private String owner;

        //"deviceAddr":"193.168.1.171:1029","deviceId":"-1","deviceName":"","deviceType":"2","mac":"806026430c000000","owner":"1"
        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getDeviceAddr() {
            return deviceAddr;
        }

        public void setDeviceAddr(String deviceAddr) {
            this.deviceAddr = deviceAddr;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getMac() {
            return mac.toUpperCase();
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }


        @Override
        public String toString() {
            return "WanDevice{" +
                    "mac='" + mac + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", pwd='" + pwd + '\'' +
                    ", deviceName='" + deviceName + '\'' +
                    ", deviceType='" + deviceType + '\'' +
                    ", deviceAddr='" + deviceAddr + '\'' +
                    ", ownerl='" + owner + '\'' +
                    '}';
        }
    }


}

