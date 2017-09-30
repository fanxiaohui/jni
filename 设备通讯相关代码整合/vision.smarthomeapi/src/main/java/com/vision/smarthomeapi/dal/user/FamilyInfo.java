package com.vision.smarthomeapi.dal.user;

import android.graphics.Bitmap;

import com.vision.smarthomeapi.sqlutil.crud.DataSupport;


/**
 * 家庭成员信息
 * Created by yangle on 2015/10/23.
 */
public class FamilyInfo extends DataSupport {

    /**
     * 主键id
     */
    private int id;
    /**
     * 当前用户id
     */
    private String userId;
    /**
     * 家庭成员Id
     */
    private String uId;
    /**
     * 家庭成员用户名
     */
    private String username;
    /**
     * 家庭成员昵称
     */
    private String nick;
    /**
     * 家庭成员备注
     */
    private String remark;
    /**
     * 家庭成员绑定手机号
     */
    private String mobile;
    /**
     * 家庭成员绑定邮箱
     */
    private String email;
    /**
     * 从网络获取的家庭成员头像标签
     */
    private String facestamp;
    /**
     * 从本地获取的家庭成员头像标签
     */
    private String facestampLocal;
    /**
     * 家庭成员头像
     */
    public Bitmap bitmap;

    public boolean isShow;


    public FamilyInfo() {
    }

    public FamilyInfo(String userId, String uId, String username, String nick, String remark,
                      String mobile, String email, String facestamp, String facestampLocal) {
        this.userId = userId;
        this.uId = uId;
        this.username = username;
        this.nick = nick;
        this.remark = remark;
        this.mobile = mobile;
        this.email = email;
        this.facestamp = facestamp;
        this.facestampLocal = facestampLocal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getuId() {
        return uId == null ? "" : uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick == null ? "" : nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getRemark() {
        return remark == null ? "" : remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email == null ? "" : email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacestamp() {
        return facestamp == null ? "" : facestamp;
    }

    public void setFacestamp(String facestamp) {
        this.facestamp = facestamp;
    }

    public String getFacestampLocal() {
        return facestampLocal == null ? "" : facestampLocal;
    }

    public void setFacestampLocal(String facestampLocal) {
        this.facestampLocal = facestampLocal;
    }

    @Override
    public String toString() {
        return "FamilyInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", uId='" + uId + '\'' +
                ", username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", remark='" + remark + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", facestamp='" + facestamp + '\'' +
                ", facestampLocal='" + facestampLocal + '\'' +
                ", bitmap=" + bitmap +
                '}';
    }
}
