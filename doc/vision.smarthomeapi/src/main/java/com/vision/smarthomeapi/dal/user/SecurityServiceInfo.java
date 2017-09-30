package com.vision.smarthomeapi.dal.user;

/**
 * 服务信息
 * Created by zhaoqing on 2015/12/30.
 */
public class SecurityServiceInfo {

    /**
     * 服务id
     */
    private  int id;
    private String serviceId;
    private String serviceName;
    private long startTime;
    private long deadline;
    private long updateTime;
    private int state;//0启用 1停用


    public SecurityServiceInfo(int id, String serviceId, String serviceName, long deadline, int state){
        this.id = id;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.deadline = deadline;
        this.state = state;



    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }
}
