package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.user.UserLogInfo;

import java.util.ArrayList;

/**
 * 获取用户日志
 * Created by yangle on 2015/9/21.
 */
public class RGetUserLog extends Bean {

    /**
     * 用户日志集合
     */
    private ArrayList<UserLogInfo> userlogs;

    public RGetUserLog(ArrayList<UserLogInfo> userlogs) {
        this.userlogs = userlogs;
    }

    public ArrayList<UserLogInfo> getUserlogs() {
        return userlogs;
    }

    public void setUserlogs(ArrayList<UserLogInfo> userlogs) {
        this.userlogs = userlogs;
    }

    @Override
    public String toString() {
        return "RGetUserLog{" +
                "userlogs=" + userlogs +
                '}';
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

