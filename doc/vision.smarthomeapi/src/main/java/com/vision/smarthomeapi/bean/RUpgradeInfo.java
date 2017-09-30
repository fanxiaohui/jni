package com.vision.smarthomeapi.bean;

/**
 * 升级信息
 */
public class RUpgradeInfo extends Bean {


    private String fileName;
    private String curVer;
    private String minVer;
    private String url;
    private String md5;
    private String devType;
    private String mac;
    private String mode;//什么模式升级

    public RUpgradeInfo( String curVer, String minVer, String url, String md5, String devType, String fileName,String mac,String mode) {
        super();
        this.curVer = curVer;
        this.minVer = minVer;
        this.url = url;
        this.md5 = md5;
        this.devType = devType;
        this.fileName = fileName;
        this.mac = mac;
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getCurVer() {
        return curVer;
    }

    public void setCurVer(String curVer) {
        this.curVer = curVer;
    }

    public String getMinVer() {
        return minVer;
    }

    public void setMinVer(String minVer) {
        this.minVer = minVer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "RUpgradeInfo{" +

                ", curVer='" + curVer + '\'' +
                ", deviceType='" + devType + '\'' +
                ", fileName='" + fileName + '\'' +
                ", md5='" + md5 + '\'' +
                ", minVer='" + minVer + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

