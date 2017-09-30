package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class RLetvProducts extends RBean {
    private int id;
    private String description;
    private float price;
    private int days;
    private int status;

    RLetvProducts(    int id,
                      String description,
                      float price,
                      int days,
                      int status){

        this.id = id;
        this.description = description;
        this.price = price;
        this.days = days;
        this.status = status;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
