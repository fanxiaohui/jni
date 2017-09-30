package com.vision.smarthomeapi.bean;

/**
 * 二维码用户信息
 * Created by Administrator on 2015/10/22.
 */
public class CodeInfo extends Bean {

    private String uId;
    private String mobile;
    private String nick;

    public CodeInfo(String uId, String mobile, String nick) {
        this.uId = uId;
        this.mobile = mobile;
        this.nick = nick;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "{\"" + "uId" + "\":" + "\"" + uId + "\"," +
                "\"" + "mobile" + "\":" + "\"" + mobile + "\"," +
                "\"" + "nick" + "\":" + "\"" + nick + "\"}";
    }
}


