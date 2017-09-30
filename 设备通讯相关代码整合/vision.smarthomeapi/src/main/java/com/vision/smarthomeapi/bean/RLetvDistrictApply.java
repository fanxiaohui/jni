package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/23.
 */

public class RLetvDistrictApply extends RBean {

    private int districtId;
    private String districtName;
    private String city;
    private String phone;
    private String propertyName;
    private long createTime;
    private int applyState;
    private int toastState;

    public RLetvDistrictApply(  int districtId,
                                String districtName,
                                String city,
                                String phone,
                                String propertyName,
                                long createTime,
                                int applyState,int toastState){

        this.districtId = districtId;
        this.districtName =districtName;
        this.city =city;
        this.phone =phone;
        this.propertyName =propertyName;
        this.createTime =createTime;
        this.applyState =applyState;
        this.toastState = toastState;

    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
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

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getApplyState() {
        return applyState;
    }

    public void setApplyState(int applyState) {
        this.applyState = applyState;
    }

    public int getToastState() {
        return toastState;
    }

    public void setToastState(int toastState) {
        this.toastState = toastState;
    }
}
