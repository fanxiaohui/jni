package com.vision.smarthomeapi.bean;

import java.util.List;

/**
 * Created by zhaoqing on 2016/5/25.
 */
public class CSecuritySubmitAnswerVo extends Bean {

    List<CSecuritySubmitAnswerRecord> list;

    public CSecuritySubmitAnswerVo(List<CSecuritySubmitAnswerRecord> list){
        this.list = list;
    }

    public List<CSecuritySubmitAnswerRecord> getList() {
        return list;
    }

    public void setList(List<CSecuritySubmitAnswerRecord> list) {
        this.list = list;
    }
}
