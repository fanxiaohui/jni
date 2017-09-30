package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvNotesList extends Bean{

    public RLetvNotes[] list;

    public RLetvNotesList(RLetvNotes[] list){
        this.list = list;
    }

    public RLetvNotes[] getList() {
        return list;
    }

    public void setList(RLetvNotes[] list) {
        this.list = list;
    }
}
