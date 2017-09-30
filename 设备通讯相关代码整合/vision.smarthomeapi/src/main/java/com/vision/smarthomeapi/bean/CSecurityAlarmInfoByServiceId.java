package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 根据服务ID获取设备报警详情
 * Created by zhaoqing on 2016/5/17.
 */
public class CSecurityAlarmInfoByServiceId extends Bean {

    private String token;
    /**
     * 服务类型 1:用电 2:用水 3:气体 4:火灾 5:防盗 6:辅助
     */
    private int serviceId;


    public CSecurityAlarmInfoByServiceId(String token,int serviceId){
        this.token = token;
        this.serviceId = serviceId;
        this.urlOrigin = Constant.UrlOrigin.security_alarmInfo_byServiceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
}
