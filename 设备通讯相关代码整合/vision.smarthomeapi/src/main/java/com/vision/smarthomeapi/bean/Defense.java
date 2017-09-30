package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/25.
 */
public class Defense {
    private Long id;
    private int state;

    public Defense(Long id,int state){
        this.id = id;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
