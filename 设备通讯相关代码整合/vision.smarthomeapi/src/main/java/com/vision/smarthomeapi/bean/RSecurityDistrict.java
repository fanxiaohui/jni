package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityDistrict extends Bean{

    private  int id;//	小区id
    private String dname;//小区名称
    private String phone;//小区紧急电话

    public RSecurityDistrict(int id, String dname, String phone){
        this.id = id;
        this.dname = dname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
