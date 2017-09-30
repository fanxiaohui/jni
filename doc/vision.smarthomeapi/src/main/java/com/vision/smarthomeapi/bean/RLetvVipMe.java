package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class RLetvVipMe extends RBean {


    private long viptime;
    private String name;
    private Long account;

    public RLetvVipMe(Long viptime,String name,long account){

        this.viptime = viptime;
        this.name = name;
        this.account = account;
    }

    public Long getViptime() {
        return viptime;
    }

    public void setViptime(Long viptime) {
        this.viptime = viptime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getAccount() {
        return account;
    }

    public void setAccount(long account) {
        this.account = account;
    }
}
