package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/23.
 */

public class RLetvGetDistrictApply extends RBean {

    private RLetvDistrictApply districtApply;
    public RLetvGetDistrictApply(RLetvDistrictApply districtApply){

        this.districtApply = districtApply;

    }

    public RLetvDistrictApply getDistrictApply() {
        return districtApply;
    }

    public void setDistrictApply(RLetvDistrictApply districtApply) {
        this.districtApply = districtApply;
    }
}
