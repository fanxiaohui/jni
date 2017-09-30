package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/9/5.
 */

public class RShareListItem extends RBean {

    private int revicerId;
    private String revicerName;
    private  String revicerAccount;
    private String revicerNick;
    private int state;
    private long createTime;
    private long sureTime;

    public RShareListItem(int revicerId,
                          String revicerName,
                          String revicerAccount,
                          String revicerNick,
                          int state,
                          long createTime,
                          long sureTime){
        this.revicerId = revicerId;
        this.revicerName = revicerName;
        this.revicerAccount = revicerAccount;
        this.revicerNick = revicerNick;
        this.state = state;
        this.createTime = createTime;
        this.sureTime = sureTime;

    }

    public int getRevicerId() {
        return revicerId;
    }

    public void setRevicerId(int revicerId) {
        this.revicerId = revicerId;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getSureTime() {
        return sureTime;
    }

    public void setSureTime(long sureTime) {
        this.sureTime = sureTime;
    }
}
