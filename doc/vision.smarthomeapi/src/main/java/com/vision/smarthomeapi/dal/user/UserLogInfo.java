package com.vision.smarthomeapi.dal.user;


import com.vision.smarthomeapi.sqlutil.crud.DataSupport;

/**
 * 用户日志信息
 * Created by yangle on 2015/9/21.
 */
public class UserLogInfo extends DataSupport {

    /**
     * 主键id
     */
    private int id;
    /**
     * 用户id
     */
    private String uId;
    /**
     * 日志id
     */
    private String logId;
    /**
     * 日志分类
     */
    private String category;
    /**
     * 日志内容
     */
    private String contents;
    /**
     * 日志记录时间
     */
    private String createTime;

    public UserLogInfo() {
    }

    public UserLogInfo(String uId, String logId, String category, String createTime, String contents) {
        this.uId = uId;
        this.logId = logId;
        this.category = category;
        this.createTime = createTime;
        this.contents = contents;
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

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserLogInfo{" +
                "id='" + id + '\'' +
                ", uId='" + uId + '\'' +
                ", logId='" + logId + '\'' +
                ", category='" + category + '\'' +
                ", contents='" + contents + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
