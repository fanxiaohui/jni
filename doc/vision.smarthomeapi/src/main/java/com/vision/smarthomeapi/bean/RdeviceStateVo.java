package com.vision.smarthomeapi.bean;

import java.util.ArrayList;

/**
 * Created by zhaoqing on 2016/9/21.
 */
public class RdeviceStateVo extends Bean {

    private long start;
    private int space;
    private int count;
    private String[] data;

    public RdeviceStateVo(long start ,int space,int count,String[] data){
        this.start = start;
        this.space = space;
        this.count = count;
        this.data = data;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
