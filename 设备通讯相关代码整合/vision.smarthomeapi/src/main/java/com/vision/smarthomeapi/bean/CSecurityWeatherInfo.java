package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2016/5/21.
 */
public class CSecurityWeatherInfo extends Bean {

    private String location;

    public CSecurityWeatherInfo(String location){
        this.location = location;
        this.urlOrigin = Constant.UrlOrigin.security_weather_info;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
