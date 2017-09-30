package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/13.
 */

public class RLetvArea extends Bean {


    public int id;
    public int areaCode;
    public String areaName;
    public int parentCode;
    public int level;

    public RLetvArea(int id,int areaCode,String areaName,int parentCode,int level){

        this.id = id;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.parentCode = parentCode;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getParentCode() {
        return parentCode;
    }

    public void setParentCode(int parentCode) {
        this.parentCode = parentCode;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
