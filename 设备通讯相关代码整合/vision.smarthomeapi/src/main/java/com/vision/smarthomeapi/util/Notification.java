package com.vision.smarthomeapi.util;

import com.vision.smarthomeapi.bean.RBean;

import java.util.Map;

public class Notification {

    public String name;
    public Object sender;
    public Map<String, Object> info;

    public Object arg;
    public RBean rBean;

    public Notification(String name, Object sender, Map<String, Object> info, Object arg) {
        super();
        this.name = name;
        this.sender = sender;
        this.info = info;
        this.arg = arg;
    }
    public Notification(String name, Object sender, Map<String, Object> info, Object arg,RBean rBean) {
        super();
        this.name = name;
        this.sender = sender;
        this.info = info;
        this.arg = arg;
        this.rBean = rBean;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "name='" + name + '\'' +
                ", sender=" + sender +
                ", info=" + info +
                ", arg=" + arg +
                ", rBean=" + rBean +
                '}';
    }
}
