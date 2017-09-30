package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityQuestion extends Bean {
    private int id;
    private String name;
    private int questionnaireId;
    private int state;
    /**
     * 类型  0单选 1多选
     */
    private int type;
    private int createId;
    private long createTime;
    private RSecurityAnswer[] answerList;

    public RSecurityQuestion(int id,String name,int state,int type,int createId,long createTime ,RSecurityAnswer[] answerList){
        this.id = id;
        this.name = name;
        this.state = state;
        this.type = type;
        this.createId = createId;
        this.createTime = createTime;
        this.answerList = answerList;

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

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public RSecurityAnswer[] getAnswerList() {
        return answerList;
    }

    public void setAnswerList(RSecurityAnswer[] answerList) {
        this.answerList = answerList;
    }
}
