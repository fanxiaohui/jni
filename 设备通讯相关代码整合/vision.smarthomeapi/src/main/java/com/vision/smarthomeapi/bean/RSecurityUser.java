package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityUser {

    private int id;//用户id
    private String name;		//用户名称
    private String initAccount;	//	初始账号
    private String account;//个性账号
    private String ctlpwd;

    public RSecurityUser(int id, String name, String initAccount, String account,String ctlpwd){

        this.id = id;
        this.name = name;
        this.initAccount = initAccount;
        this.account = account;
        this.ctlpwd = ctlpwd;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitAccount() {
        return initAccount;
    }

    public void setInitAccount(String initAccount) {
        this.initAccount = initAccount;
    }

    public String getCtlpwd() {
        return ctlpwd;
    }

    public void setCtlpwd(String ctlpwd) {
        this.ctlpwd = ctlpwd;
    }
}
