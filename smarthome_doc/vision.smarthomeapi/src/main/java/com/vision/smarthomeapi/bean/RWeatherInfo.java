package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/21.
 */
public class RWeatherInfo extends Bean {
    private String location;
    private String date;
    private String week;
    private String tempertureOfDay;
    private String tempertureNow;
    private String wind;
    private String weather;
    private String dayPictureUrl;
    private String nightPictureUrl;
    private String pmTwoPointFive;

    public RWeatherInfo(String location,String date,String week
    ,String tempertureOfDay, String tempertureNow,String wind,String weather
                        ,String dayPictureUrl,String nightPictureUrl,String pmTwoPointFive
    ){
        this.location = location;
        this.date = date;
        this.week = week;
        this.tempertureOfDay = tempertureOfDay;
        this.tempertureNow = tempertureNow;
        this.wind = wind;
        this.weather = weather;
        this.dayPictureUrl = dayPictureUrl;
        this.nightPictureUrl = nightPictureUrl;
        this.pmTwoPointFive = pmTwoPointFive;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getTempertureOfDay() {
        return tempertureOfDay;
    }

    public void setTempertureOfDay(String tempertureOfDay) {
        this.tempertureOfDay = tempertureOfDay;
    }

    public String getTempertureNow() {
        return tempertureNow;
    }

    public void setTempertureNow(String tempertureNow) {
        this.tempertureNow = tempertureNow;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDayPictureUrl() {
        return dayPictureUrl;
    }

    public void setDayPictureUrl(String dayPictureUrl) {
        this.dayPictureUrl = dayPictureUrl;
    }

    public String getNightPictureUrl() {
        return nightPictureUrl;
    }

    public void setNightPictureUrl(String nightPictureUrl) {
        this.nightPictureUrl = nightPictureUrl;
    }

    public String getPmTwoPointFive() {
        return pmTwoPointFive;
    }

    public void setPmTwoPointFive(String pmTwoPointFive) {
        this.pmTwoPointFive = pmTwoPointFive;
    }
}
