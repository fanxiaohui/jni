package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityTypeInfo extends Bean {

    private SecurityTypeInfo[] list;

    public RSecurityTypeInfo(SecurityTypeInfo[] list) {
        this.list = list;
    }

    public SecurityTypeInfo[] getList() {
        return list;
    }

    public void setList(SecurityTypeInfo[] list) {
        this.list = list;
    }
}
