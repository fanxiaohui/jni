package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.user.SecurityServiceInfo;

/**
 * Created by zhaoqing on 2016/1/8.
 */
public class RSecurityUserInfo extends Bean {

    private SecurityServiceInfo[] serviceList;

    private RSecurityCustomer customer;

    RSecurityUserInfo(SecurityServiceInfo[] serviceList, RSecurityCustomer customer){
        this.serviceList = serviceList;
        this.customer = customer;

    }

    public SecurityServiceInfo[] getServiceList() {
        return serviceList;
    }

    public void setServiceList(SecurityServiceInfo[] serviceList) {
        this.serviceList = serviceList;
    }

    public RSecurityCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(RSecurityCustomer customer) {
        this.customer = customer;
    }
}
