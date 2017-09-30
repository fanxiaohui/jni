package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityAnswer extends Bean {
    private int id;
    private String name;
    private int questionId;
    /**
     * 是否是正确答案 0是 1不是
     */
    private int isTrue;
    private int state;
    private int createId;
    private long createTime;

    public RSecurityAnswer(int id,String name,int state,int createId,long createTime , int isTrue){
        this.id = id;
        this.name = name;
        this.state = state;
        this.createId = createId;
        this.createTime = createTime;
        this.isTrue = isTrue;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getIsTrue() {
        return isTrue;
    }

    public void setIsTrue(int isTrue) {
        this.isTrue = isTrue;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
