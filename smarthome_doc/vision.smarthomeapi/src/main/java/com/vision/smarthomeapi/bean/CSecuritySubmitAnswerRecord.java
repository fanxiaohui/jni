package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * Created by zhaoqing on 2016/5/25.
 */
public class CSecuritySubmitAnswerRecord {

    private int questionnaireId;
    private int questionId;
    private List<Integer> answerIds;

    public CSecuritySubmitAnswerRecord(int questionnaireId,int questionId,List<Integer> answerIds){
        this.questionnaireId = questionnaireId;
        this.questionId = questionId;
        this.answerIds = answerIds;
    }

    public int getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(int questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<Integer> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(List<Integer> answerIds) {
        this.answerIds = answerIds;
    }
}
