package com.vision.smarthomeapi.bean;

/**
 * 接收平台推送到手机的消息
 */
public class RMessageInfo extends Bean {

    private String hostId;
    private String hostNick;
    private String mId;
    private String mNick;

    public RMessageInfo(String hostId, String hostNick, String mId, String mNick) {
        this.hostId = hostId;
        this.hostNick = hostNick;
        this.mId = mId;
        this.mNick = mNick;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostNick(String hostNick) {
        this.hostNick = hostNick;
    }

    public String getHostNick() {
        return hostNick;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public void setmNick(String mNick) {
        this.mNick = mNick;
    }

    public String getmNick() {
        return mNick;
    }

    @Override
    public String toString() {
        return "RMessageInfo{" +
                "hostId='" + hostId + '\'' +
                ", hostNick='" + hostNick + '\'' +
                ", mId='" + mId + '\'' +
                ", mNick='" + mNick + '\'' +
                '}';
    }
}

