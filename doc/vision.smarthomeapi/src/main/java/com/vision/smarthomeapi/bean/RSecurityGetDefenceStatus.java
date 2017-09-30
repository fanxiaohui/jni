package com.vision.smarthomeapi.bean;

/**
 * 获取用户的布撤防状态
 * Created by yangle on 2017/1/22.
 */
public class RSecurityGetDefenceStatus extends Bean {

    /**
     * 0.没有布撤防设备 1.布防 2.撤防
     */
    private int defenceState;

    public int getDefenceState() {
        return defenceState;
    }

    public void setDefenceState(int defenceState) {
        this.defenceState = defenceState;
    }
}

