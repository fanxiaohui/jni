package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/3/15.
 */
public class RSecurityCheckversion extends Bean {

    private int flag;
    private String downLoadUrl;

    public RSecurityCheckversion(int flag,String downLoadUrl){
        this.flag = flag;
        this.downLoadUrl = downLoadUrl;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }
}
