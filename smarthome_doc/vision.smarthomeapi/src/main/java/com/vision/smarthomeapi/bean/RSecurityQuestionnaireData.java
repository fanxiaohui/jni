package com.vision.smarthomeapi.bean;


/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityQuestionnaireData extends Bean{
    private RSecurityQuestionnaire[] questionnaireList;

    public RSecurityQuestionnaireData(RSecurityQuestionnaire[] questionnaireList){
        this.questionnaireList = questionnaireList;

    }

    public RSecurityQuestionnaire[] getQuestionnaireList() {
        return questionnaireList;
    }

    public void setQuestionnaireList(RSecurityQuestionnaire[] questionnaireList) {
        this.questionnaireList = questionnaireList;
    }
}
