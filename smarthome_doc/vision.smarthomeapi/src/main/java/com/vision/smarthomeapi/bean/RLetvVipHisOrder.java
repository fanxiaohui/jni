package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/9.
 */

public class RLetvVipHisOrder extends RBean {
    //消费时间
    //订单编号
    //消费金额
    //消费状态
    //天数
    //到期时间

    private long payTime;
    private String orderNo;
    private double payMoney;
    private int payStatus;
    private int days;
    private long viptime;

    RLetvVipHisOrder(long payTime,
                     String orderNo,
                     double payMoney,
                     int payStatus,
                     int days,
                     long viptime){
        this.payTime=payTime;
        this.orderNo=orderNo;
        this.payMoney=payMoney;
        this.payStatus=payStatus;
        this.days=days;
        this.viptime=viptime;
    }

    public long getPayTime() {
        return payTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(double payMoney) {
        this.payMoney = payMoney;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public long getViptime() {
        return viptime;
    }

    public void setViptime(long viptime) {
        this.viptime = viptime;
    }
}
