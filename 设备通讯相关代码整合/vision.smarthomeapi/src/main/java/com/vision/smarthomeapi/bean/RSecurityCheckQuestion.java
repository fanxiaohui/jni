package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityCheckQuestion extends Bean {
    /**
     * 0已做过问卷 1未做过问卷
     */
    private int flag;
    public RSecurityCheckQuestion(int flag){
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
