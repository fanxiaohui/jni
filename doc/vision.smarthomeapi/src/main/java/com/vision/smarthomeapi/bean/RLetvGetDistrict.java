package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/23.
 */

public class RLetvGetDistrict extends RBean {

    private int id;
    private String districtName;
    private String city;
    private String phone;
    private String address;
    private String propertyName;

    public RLetvGetDistrict(int id,
                            String districtName,
                            String city,
                            String phone,
                            String address,
                            String propertyName){


        this.id = id;
        this.districtName = districtName;
        this.city = city;
        this.phone = phone;
        this.address = address;
        this.propertyName = propertyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
