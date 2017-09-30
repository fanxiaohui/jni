package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/21.
 */
public class RSecurityWeatherInfo extends Bean {
     private RWeather weather;

    public RSecurityWeatherInfo(RWeather weather){
        this.weather = weather;
    }

    public RWeather getWeather() {
        return weather;
    }

    public void setWeather(RWeather weather) {
        this.weather = weather;
    }
}
