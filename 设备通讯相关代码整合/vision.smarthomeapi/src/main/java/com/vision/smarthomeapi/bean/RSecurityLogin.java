package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RSecurityLogin extends Bean {

    /**
     * 是否设置安全码
     * 0.没有安全码 1.有安全码
     */
    private int isSecCode;
    /**
     * 温馨提示
     */
    private int showBind;
    /**
     * 用户信息
     */
    private RSecurityUser user;
    /**
     * 小区信息
     */
    private RSecurityDistrict district;

    public RSecurityLogin(int showBind,RSecurityUser user, RSecurityDistrict district) {
        this.showBind = showBind;
        this.user = user;
        this.district = district;
    }

    public int getShowBind() {
        return showBind;
    }

    public void setShowBind(int showBind) {
        this.showBind = showBind;
    }

    public int getIsSecCode() {
        return isSecCode;
    }

    public void setIsSecCode(int isSecCode) {
        this.isSecCode = isSecCode;
    }

    public RSecurityUser getUser() {
        return user;
    }

    public void setUser(RSecurityUser user) {
        this.user = user;
    }

    public RSecurityDistrict getDistrict() {
        return district;
    }

    public void setDistrict(RSecurityDistrict district) {
        this.district = district;
    }
}
