package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvCommunicationList extends Bean{

    public RLetvCommunication[] list;

    public RLetvCommunicationList(RLetvCommunication[] list){
        this.list = list;
    }

    public RLetvCommunication[] getList() {
        return list;
    }

    public void setList(RLetvCommunication[] list) {
        this.list = list;
    }
}
