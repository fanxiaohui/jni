package com.vision.smarthomeapi.bean;


/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityQuestionnaire extends Bean{

    private int id;
    private String name;
    private int state;
    private int createId;
    private long createTime;
    private RSecurityQuestion[] questionList;

    public RSecurityQuestionnaire(int id, String name, int state, int createId, long createTime , RSecurityQuestion[] questionList){
        this.id = id;
        this.name = name;
        this.state = state;
        this.createId = createId;
        this.createTime = createTime;
        this.questionList = questionList;

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

    public RSecurityQuestion[] getQuestionList() {
        return questionList;
    }

    public void setQuestionList(RSecurityQuestion[] questionList) {
        this.questionList = questionList;
    }
}
