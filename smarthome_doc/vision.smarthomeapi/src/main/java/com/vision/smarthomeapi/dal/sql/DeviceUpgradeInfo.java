package com.vision.smarthomeapi.dal.sql;


import com.vision.smarthomeapi.sqlutil.crud.DataSupport;

/**
 * Created by zhaoqing on 2015/10/18.
 */
public class DeviceUpgradeInfo extends DataSupport {

    private int sqlID;
    /**
     * 型号 类型_设备芯片型号
     */
    private String upgradeUrl;
    /**
     * 设备软件版本
     */
    private String upgradeVer;
    /**
     * 最后更新时间
     */
    private String upgradeDate;

    /**
     * 设备类型
     */
    private String upgradeType;

    /**
     * 本地保存地址
     */
    private String fileName;

    public int getSqlID() {
        return sqlID;
    }

    public void setSqlID(int sqlID) {
        this.sqlID = sqlID;
    }

    public String getUpgradeUrl() {
        return upgradeUrl;
    }

    public void setUpgradeUrl(String upgradeUrl) {
        this.upgradeUrl = upgradeUrl;
    }

    public String getUpgradeVer() {
        return upgradeVer;
    }

    public void setUpgradeVer(String upgradeVer) {
        this.upgradeVer = upgradeVer;
    }

    public String getUpgradeDate() {
        return upgradeDate;
    }

    public void setUpgradeDate(String upgradeDate) {
        this.upgradeDate = upgradeDate;
    }

    public String getUpgradeType() {
        return upgradeType;
    }

    public void setUpgradeType(String upgradeType) {
        this.upgradeType = upgradeType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public String toString() {
        return "DeviceUpgradeInfo{" +
                "sqlID=" + sqlID +
                ", upgradeUrl='" + upgradeUrl + '\'' +
                ", upgradeVer='" + upgradeVer + '\'' +
                ", upgradeDate='" + upgradeDate + '\'' +
                ", upgradeType='" + upgradeType + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
