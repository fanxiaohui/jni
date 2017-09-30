package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityGetQuestion extends Bean {

    private String url;

    public RSecurityGetQuestion(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
