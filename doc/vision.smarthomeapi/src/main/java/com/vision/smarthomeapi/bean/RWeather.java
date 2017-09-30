package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * Created by zhaoqing on 2016/5/21.
 */
public class RWeather extends Bean {

    private List<RWeatherInfo> weatherInfs;
    private String dressAdvise;
    private String washCarAdvise;
    private String coldAdvise;
    private String sportsAdvise;
    private String ultravioletRaysAdvise;

    public RWeather(List<RWeatherInfo> weatherInfs,String dressAdvise, String washCarAdvise
    ,String coldAdvise,String sportsAdvise,String ultravioletRaysAdvise){
        this.weatherInfs = weatherInfs;
        this.dressAdvise = dressAdvise;
        this.washCarAdvise = washCarAdvise;
        this.coldAdvise = coldAdvise;
        this.sportsAdvise = sportsAdvise;
        this.ultravioletRaysAdvise = ultravioletRaysAdvise;

    }

    public List<RWeatherInfo> getWeatherInfs() {
        return weatherInfs;
    }

    public void setWeatherInfs(List<RWeatherInfo> weatherInfs) {
        this.weatherInfs = weatherInfs;
    }

    public String getDressAdvise() {
        return dressAdvise;
    }

    public void setDressAdvise(String dressAdvise) {
        this.dressAdvise = dressAdvise;
    }

    public String getWashCarAdvise() {
        return washCarAdvise;
    }

    public void setWashCarAdvise(String washCarAdvise) {
        this.washCarAdvise = washCarAdvise;
    }

    public String getColdAdvise() {
        return coldAdvise;
    }

    public void setColdAdvise(String coldAdvise) {
        this.coldAdvise = coldAdvise;
    }

    public String getSportsAdvise() {
        return sportsAdvise;
    }

    public void setSportsAdvise(String sportsAdvise) {
        this.sportsAdvise = sportsAdvise;
    }

    public String getUltravioletRaysAdvise() {
        return ultravioletRaysAdvise;
    }

    public void setUltravioletRaysAdvise(String ultravioletRaysAdvise) {
        this.ultravioletRaysAdvise = ultravioletRaysAdvise;
    }
}
