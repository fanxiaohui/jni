package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class RLetvSynJDOrder extends Bean {

    RLetvOrder LorderVo;

    public RLetvSynJDOrder(RLetvOrder LorderVo){
        this.LorderVo = LorderVo;


    }

    public RLetvOrder getLorderVo() {
        return LorderVo;
    }

    public void setLorderVo(RLetvOrder lorderVo) {
        LorderVo = lorderVo;
    }
}
