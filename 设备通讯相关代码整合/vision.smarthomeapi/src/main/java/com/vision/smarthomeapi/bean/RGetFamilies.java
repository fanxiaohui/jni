package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.user.FamilyInfo;

import java.util.ArrayList;

/**
 * 获取用户所属家庭
 */
public class RGetFamilies extends Bean {

    private String fId;
    private ArrayList<FamilyInfo> members;

    public RGetFamilies(String status, String statusMsg, String fId, ArrayList<FamilyInfo> members) {
        this.status = status;
        this.statusMsg = statusMsg;
        this.fId = fId;
        this.members = members;
    }

    public String getfId() {
        return fId;
    }

    public void setfId(String fId) {
        this.fId = fId;
    }

    public ArrayList<FamilyInfo> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<FamilyInfo> members) {
        this.members = members;
    }

    /**
     * http返回值处理
     *
     * @return 通知类型
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue != Bean.ERROR) {
            return rValue;
        }
        switch (status) {
        }
        return Bean.ERROR;
    }
}

