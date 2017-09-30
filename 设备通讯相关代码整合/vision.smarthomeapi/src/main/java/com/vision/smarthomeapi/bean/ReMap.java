package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/21.
 */

public class ReMap extends RBean{

    private String location;
    private String street;
    private String province;
    private String district;
    private String city;
    private String serviceAddress;


    public ReMap(String location,String street,String province,String district,String city,String serviceAddress){

        this.location = location;
        this.street = street;
        this.province = province;
        this.district = district;
        this.city = city;
        this.serviceAddress = serviceAddress;


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
