package com.vision.smarthomeapi.bean;


import com.vision.smarthomeapi.util.OutPutMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RSecurityToken extends Bean {

    private String token;
    private String refresh_token;
    private long timeout;

    public RSecurityToken(String token, String refresh_token, long timeout) {
        this.token = token;
        this.refresh_token = refresh_token;
        this.timeout = timeout;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    /**
     * http请求返回码处理
     *
     * @return 处理方式
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue == Bean.OK) {
            String time = dateTimeToString(new Date(System.currentTimeMillis()));
            OutPutMessage.LogCatInfo("返回时间",time + "!!!!!!!!!");
        }

        return 0;
    }

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 当前日期
     */
    public String dateTimeToString(Date date) {
        return dateTimeToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param date 当前日期
     */
    public String dateTimeToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

}

