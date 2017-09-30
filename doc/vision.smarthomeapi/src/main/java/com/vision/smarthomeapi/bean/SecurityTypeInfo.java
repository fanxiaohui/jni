package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/1/5.
 */
public class SecurityTypeInfo {

    private int id;
    /**
     * 型号
     */
    private String devName;
    /**
     * 所属设备类型id(原设备类型Type)
     */
    private int devTypeId;
    /**
     * version设备型号编码(原设备类型Version)
     */
    private int code;
    /**
     * mode型号
     */
    private String versionCode;
    /**
     * 是否支持2级联动
     */
    private int isTwoLinkage;
    /**
     * 是否支持3级联动
     */
    private int isThreeLinkage;
    /**
     * 是否支持布防撤防
     */
    private int isDefenseSupport;
    /**
     * 是否是原设备
     */
    private int isSource;
    /**
     * 是否可扩展
     */
    private int isExtend;
    /**
     * 产商编码
     */
    private int manufacturerEncoding;
    /**
     * 通讯类型
     */
    private int communicationType;
    /**
     * 功能描述
     */
    private String info;
    /**
     * 图标
     */
    private String deviceImg;
    /**
     * 异常图标
     */
    private String deviceImgFault;

    /**
     * '0:不可控 1：快捷 2：内页'
     */
    private int controlType;

    public SecurityTypeInfo(int id,
                            String devName,
                            int devTypeId,
                            int code,
                            String versionCode,
                            int isTwoLinkage,
                            int isThreeLinkage,
                            int isDefenseSupport,
                            int isSource,
                            int isExtend,
                            int communicationType,
                            int manufacturerEncoding,
                            String info,
                            String deviceImg, int controlType,String deviceImgFault
    ) {
        this.id = id;
        this.devName = devName;
        this.devTypeId = devTypeId;
        this.code = code;
        this.versionCode = versionCode;
        this.isTwoLinkage = isTwoLinkage;
        this.isThreeLinkage = isThreeLinkage;
        this.isDefenseSupport = isDefenseSupport;
        this.isSource = isSource;
        this.isExtend = isExtend;
        this.communicationType = communicationType;
        this.manufacturerEncoding = manufacturerEncoding;
        this.info = info;
        this.deviceImg = deviceImg;
        this.controlType = controlType;
        this.deviceImgFault = deviceImgFault;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(int communicationType) {
        this.communicationType = communicationType;
    }

    public int getManufacturerEncoding() {
        return manufacturerEncoding;
    }

    public void setManufacturerEncoding(int manufacturerEncoding) {
        this.manufacturerEncoding = manufacturerEncoding;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getDevTypeId() {
        return devTypeId;
    }

    public void setDevTypeId(int devTypeId) {
        this.devTypeId = devTypeId;
    }

    public int getIsTwoLinkage() {
        return isTwoLinkage;
    }

    public void setIsTwoLinkage(int isTwoLinkage) {
        this.isTwoLinkage = isTwoLinkage;
    }

    public int getIsThreeLinkage() {
        return isThreeLinkage;
    }

    public void setIsThreeLinkage(int isThreeLinkage) {
        this.isThreeLinkage = isThreeLinkage;
    }

    public int getIsDefenseSupport() {
        return isDefenseSupport;
    }

    public void setIsDefenseSupport(int isDefenseSupport) {
        this.isDefenseSupport = isDefenseSupport;
    }

    public int getIsSource() {
        return isSource;
    }

    public void setIsSource(int isSource) {
        this.isSource = isSource;
    }

    public int getIsExtend() {
        return isExtend;
    }

    public void setIsExtend(int isExtend) {
        this.isExtend = isExtend;
    }

    public String getDeviceImg() {
        return deviceImg;
    }

    public void setDeviceImg(String deviceImg) {
        this.deviceImg = deviceImg;
    }

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    public String getDeviceImgFault() {
        return deviceImgFault;
    }

    public void setDeviceImgFault(String deviceImgFault) {
        this.deviceImgFault = deviceImgFault;
    }
}
