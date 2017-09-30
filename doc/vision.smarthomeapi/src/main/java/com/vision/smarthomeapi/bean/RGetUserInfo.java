package com.vision.smarthomeapi.bean;

/**
 * 获取用户信息
 */
public class RGetUserInfo extends Bean {

    private String uId;
    private String nick;
    private String mobile;
    private String family;
    private String facestamp;

    public RGetUserInfo(String urlOrigin, String statusMsg, String uId, String nick,
                        String mobile, String family, String status, String facestamp) {
        this.urlOrigin = urlOrigin;
        this.statusMsg = statusMsg;
        this.uId = uId;
        this.nick = nick;
        this.mobile = mobile;
        this.family = family;
        this.status = status;
        this.facestamp = facestamp;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamily() {
        return family;
    }

    public String getFacestamp() {
        return facestamp;
    }

    public void setFacestamp(String facestamp) {
        this.facestamp = facestamp;
    }

    /**
     * http请求返回码处理
     *
     * @return 处理方式
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

    @Override
    public String toString() {
        return "RGetUserInfo{" +
                "urlOrigin='" + urlOrigin + '\'' +
                ", statusMsg='" + statusMsg + '\'' +
                ", uId='" + uId + '\'' +
                ", nick='" + nick + '\'' +
                ", facestamp='" + facestamp + '\'' +
                ", mobile='" + mobile + '\'' +
                ", family='" + family + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

