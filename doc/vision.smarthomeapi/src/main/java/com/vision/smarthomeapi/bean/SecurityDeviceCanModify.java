package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/1/5.
 */
public class SecurityDeviceCanModify {


    private int id;

    private int versionId;
    private String versionName;

    /**
     * mode型号
     */
    private String versioncode;
    /**
     * type设备类型
     */
    private int devId;
    /**
     * version设备型号编码
     */
    private int code;

    /**
     * 通讯类型
     */
    private int communicationType;
    /**
     * 产商编码
     */
    private int manufacturerEncoding;
    /**
     * 功能描述
     */
    private String info;


    public SecurityDeviceCanModify(int id,
                                   int versionId,
                                   String versionName,
                                   String versioncode,
                                   int devId,
                                   int code,
                                   int communicationType,
                                   int manufacturerEncoding,
                                   String info){
        this.id = id;
        this.versionId = versionId;
        this.versionName = versionName;
        this.versioncode = versioncode;
        this.devId = devId;
        this.code = code;
        this.communicationType = communicationType;
        this.manufacturerEncoding = manufacturerEncoding;
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public int getDevId() {
        return devId;
    }

    public void setDevId(int devId) {
        this.devId = devId;
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
}
