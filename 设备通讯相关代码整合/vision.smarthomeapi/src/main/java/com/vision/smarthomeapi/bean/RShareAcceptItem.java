package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class RShareAcceptItem extends Bean {

    private int shareId;
    private String shareName;
    private String shareAccount;
    private RShareAcceptDeviceItem[] devices;


    public RShareAcceptItem( int shareId,
                             String shareName,
                             String shareAccount,
                             RShareAcceptDeviceItem[] devices){

        this.shareId = shareId;
        this.shareName = shareName;
        this.shareAccount = shareAccount;
        this.devices = devices;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public String getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(String shareAccount) {
        this.shareAccount = shareAccount;
    }

    public RShareAcceptDeviceItem[] getDevices() {
        return devices;
    }

    public void setDevices(RShareAcceptDeviceItem[] devices) {
        this.devices = devices;
    }
}
