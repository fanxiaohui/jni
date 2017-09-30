package com.vision.smarthomeapi.dal.user;


import com.vision.smarthomeapi.sqlutil.crud.DataSupport;

/**
 * 首页日志显示数据
 * Created by yangle on 2015/9/21.
 */
public class HomeUserLogInfo extends DataSupport {

    /**
     * 主键
     */
    private int id;
    /**
     * 用户id
     */
    private String uId;
    /**
     * 日志信息
     */
    private String msg;
    /**
     * 日志类型
     */
    private int logTyle;

    public HomeUserLogInfo() {
    }

    public HomeUserLogInfo(String uId, String msg, int logTyle) {
        this.uId = uId;
        this.msg = msg;
        this.logTyle = logTyle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getLogTyle() {
        return logTyle;
    }

    public void setLogTyle(int logTyle) {
        this.logTyle = logTyle;
    }

    @Override
    public String toString() {
        return "HomePageLog{" +
                "msg='" + msg + '\'' +
                ", logTyle=" + logTyle +
                '}';
    }
}

