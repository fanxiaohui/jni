package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/7.
 */

public class RLetvVipBm extends RBean {

    private String orderNo;
    private double price;
    private String desc;
    private int days;
    private Long viptime;


    public RLetvVipBm(String orderNo,double price,String desc,int days,Long viptime){
        this.orderNo = orderNo;
        this.price = price;
        this.desc = desc;
        this.days = days;
        this.viptime = viptime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public Long getViptime() {
        return viptime;
    }

    public void setViptime(Long viptime) {
        this.viptime = viptime;
    }
}
