package com.vision.smarthomeapi.dal.sql;


import com.vision.smarthomeapi.sqlutil.crud.DataSupport;

/**
 * 本地保存设备类
 */
public class SmartDeviceSQL extends DataSupport {

    private int id;

    private int deviceType;

    private int devVersion;

    private String deviceName;

    private String mac;

    private int zigBee;

    private int deviceNumber;

    private String userId;

    private String ownerUserID;

    private boolean isSQLExist;

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        this.isExist = exist;
    }

    private boolean isExist;

    public String getOwnerUserID() {
        return ownerUserID == null ? "" : ownerUserID;
    }

    public void setOwnerUserID(String ownerUserID) {
        this.ownerUserID = ownerUserID;
    }





    public void setSQLExist(boolean SQLExist) {
        this.isSQLExist = SQLExist;
    }

    public boolean isSQLExist() {
        return isSQLExist;
    }

    /**
     * 登录用户ID
     *
     * @return 本地保存唯一ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 登录用户ID
     *
     * @param userId 主键ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 主键ID
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * 主键ID
     *
     * @return
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取设备类型
     *
     * @return
     */
    public int getDeviceType() {
        return deviceType;
    }

    /**
     * 设置设备类型
     *
     * @param deviceType
     */
    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(int devVersion) {
        this.devVersion = devVersion;
    }

    /**
     * 获取设备名称
     *
     * @return
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * 设置设备名称
     *
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * 获取设备MAC
     *
     * @return
     */
    public String getMac() {
        return mac;
    }

    /**
     * 设置设备mac
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }


    /**
     * 设备使用次数
     *
     * @return
     */
    public int getDeviceNumber() {
        return deviceNumber;
    }

    /**
     * 设备使用次数
     *
     * @param deviceNumber
     */
    public void setDeviceNumber(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }


    public int getZigBee() {
        return zigBee;
    }

    public void setZigBee(int zigBee) {
        this.zigBee = zigBee;
    }


    @Override
    public String toString() {
        return "SQLSmartDevice{" +
                "id=" + id +
                ", deviceType=" + deviceType +
                ", deviceName='" + deviceName + '\'' +
                ", mac='" + mac + '\'' +
                ", deviceNumber=" + deviceNumber +
                ", userId='" + userId + '\'' +
                '}';
    }
}
