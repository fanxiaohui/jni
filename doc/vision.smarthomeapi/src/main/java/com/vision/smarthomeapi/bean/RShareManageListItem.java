package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/9/6.
 */

public class RShareManageListItem extends Bean {
    private int id;
    private int shareId;
    private int revicerId;
    private int deviceId;
    private int type;
    private long shareTime;
    private long bindTime;
    private long unbindTime;
    private long latestVisitTime;
    private long visitDuration;
    private int visitNum;
    private int shareNum;
    private String revicerName;
    private String revicerAccount;
    private String revicerNick;
    private String mac;
    private String deviceName;
    private String deviceType;
    private int devType;

    public RShareManageListItem( int id,
                                 int shareId,
                                 int revicerId,
                                 int deviceId,
                                 int type,
                                 long shareTime,
                                 long bindTime,
                                 long unbindTime,
                                 long latestVisitTime,
                                 long visitDuration,
                                 int visitNum,
                                 int shareNum,
                                 String revicerName,
                                 String revicerAccount,
                                 String revicerNick,
                                 String mac,
                                 String deviceName,
                                 String deviceType,
                                 int devType
    ){

        this.id = id;
        this.shareId = shareId;
        this.revicerId = revicerId;
        this.deviceId = deviceId;
        this.type = type;
        this.shareTime = shareTime;
        this.bindTime = bindTime;
        this.unbindTime = unbindTime;
        this.latestVisitTime = latestVisitTime;
        this.visitDuration = visitDuration;
        this.visitNum = visitNum;
        this.shareNum = shareNum;
        this.revicerName = revicerName;
        this.revicerAccount = revicerAccount;
        this.revicerNick = revicerNick;
        this.mac = mac;
        this.deviceName = deviceName;
        this.deviceType = deviceType;
        this.devType = devType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getShareTime() {
        return shareTime;
    }

    public void setShareTime(long shareTime) {
        this.shareTime = shareTime;
    }

    public long getBindTime() {
        return bindTime;
    }

    public void setBindTime(long bindTime) {
        this.bindTime = bindTime;
    }

    public long getUnbindTime() {
        return unbindTime;
    }

    public void setUnbindTime(long unbindTime) {
        this.unbindTime = unbindTime;
    }

    public long getLatestVisitTime() {
        return latestVisitTime;
    }

    public void setLatestVisitTime(long latestVisitTime) {
        this.latestVisitTime = latestVisitTime;
    }

    public long getVisitDuration() {
        return visitDuration;
    }

    public void setVisitDuration(long visitDuration) {
        this.visitDuration = visitDuration;
    }

    public int getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(int visitNum) {
        this.visitNum = visitNum;
    }

    public int getShareNum() {
        return shareNum;
    }

    public void setShareNum(int shareNum) {
        this.shareNum = shareNum;
    }

    public String getRevicerName() {
        return revicerName;
    }

    public void setRevicerName(String revicerName) {
        this.revicerName = revicerName;
    }

    public String getRevicerAccount() {
        return revicerAccount;
    }

    public void setRevicerAccount(String revicerAccount) {
        this.revicerAccount = revicerAccount;
    }

    public String getRevicerNick() {
        return revicerNick;
    }

    public void setRevicerNick(String revicerNick) {
        this.revicerNick = revicerNick;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public int getDevType() {
        return devType;
    }

    public void setDevType(int devType) {
        this.devType = devType;
    }
}
