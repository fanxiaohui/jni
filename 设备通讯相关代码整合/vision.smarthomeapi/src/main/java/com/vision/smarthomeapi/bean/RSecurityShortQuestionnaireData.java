package com.vision.smarthomeapi.bean;


/**
 * Created by zhaoqing on 2016/5/18.
 */
public class RSecurityShortQuestionnaireData extends Bean{
    private RSecurityQuestionnaire questionnaire;

    public RSecurityShortQuestionnaireData(RSecurityQuestionnaire questionnaire){
        this.questionnaire = questionnaire;

    }

    public RSecurityQuestionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(RSecurityQuestionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }
}
