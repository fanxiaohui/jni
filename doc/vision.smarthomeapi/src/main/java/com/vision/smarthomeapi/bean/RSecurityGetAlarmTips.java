package com.vision.smarthomeapi.bean;

import java.util.ArrayList;

/**
 * 获取告警类型的提示
 * Created by yangle on 2017/2/7.
 */
public class RSecurityGetAlarmTips extends Bean {

    private ArrayList<Tips> tips;

    public class Tips {
        /**
         * 告警类型的ID
         */
        private int id;
        /**
         * 告警类型对应提示内容
         */
        private String tips;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTips() {
            return tips;
        }

        public void setTips(String tips) {
            this.tips = tips;
        }
    }

    public ArrayList<Tips> getTips() {
        return tips;
    }

    public void setTips(ArrayList<Tips> tips) {
        this.tips = tips;
    }
}
