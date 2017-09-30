package com.vision.smarthomeapi.bean;

/**
 * 用户登录
 */
public class RUserLogin extends Bean {

    private String cookie;
    private String userId;
    //账号解锁倒计时，单位秒（002E有此值）
    private int ttl;
    //账号登录剩余次数（0021有此值）
    private String leftLoginTimes;

    public RUserLogin(String urlOrigin, String status, String statusMsg, String cookie, String userId, int ttl, String leftLoginTimes) {
        this.urlOrigin = urlOrigin;
        this.status = status;
        this.statusMsg = statusMsg;
        this.cookie = cookie;
        this.userId = userId;
        this.ttl = ttl;
        this.leftLoginTimes = leftLoginTimes;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLeftLoginTimes() {
        return leftLoginTimes;
    }

    public void setLeftLoginTimes(String leftLoginTimes) {
        this.leftLoginTimes = leftLoginTimes;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
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
            case "0018":
                statusMsg = "请填写密码";
                return Bean.TOAST;

            case "0019":
                statusMsg = "请填写账号";
                return Bean.TOAST;

            case "0020":
                statusMsg = "账号不存在";
                return Bean.TOAST;

            case "0021":
                if (!leftLoginTimes.equals("0")) {
                    statusMsg = "密码有误，您还有" + leftLoginTimes + "次登录机会";
                } else {
                    statusMsg = "该账号被锁定24小时";
                }
                return Bean.TOAST;

            case "002E":
                String timeString = "";
                if (ttl < 3600) {
                    timeString = ttl / 60 + "分钟";
                } else {
                    timeString = ttl / 3600 + "小时";
                }
                statusMsg = "该账号被锁定，请大约" + timeString + "后再试";
                return Bean.TOAST;

            default:
                break;
        }
        return Bean.ERROR;
    }
}

