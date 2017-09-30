package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.util.FileIO;

/**
 * Created by zhaoqing on 2016/1/7.
 */
public class RBean {
    /**
     * 用于区分Http返回码
     */
    public static int ERROR = 0;
    public static int OK = 1;
    public static int TOAST = 2;
    public static int DIALOG = 3;
    public static int UICHANGE = 4;
    public static int LOCATTOAST = 5;

    private String code;
    private String expires;
    private Object data;
    private String error = "";

    public String getCode() {
        return code;
    }

    public String getExpires() {
        return expires;
    }

    public Object getData() {
        return data;
    }

    public String getError() {
        return error;
    }

    public int mode() {
        switch (code) {
            case "200":
                return RBean.OK;

            case "411":
                // reason = "key不存在";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "412":
                // reason = "验签错误";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "413":
                // reason = "refresh_token不存在";
                FileIO.getShareFielIo(Controller.getContext()).delFile("token");
                FileIO.getShareFielIo(Controller.getContext()).delFile("refresh_token");
                SecurityUserManage.token = null;

                //重新获取token
                SecurityUserManage.getShare().getToken();
                return RBean.LOCATTOAST;

            case "414":
                // reason = "token不存在";
                FileIO.getShareFielIo(Controller.getContext()).delFile("token");
                FileIO.getShareFielIo(Controller.getContext()).delFile("refresh_token");
                SecurityUserManage.token = null;

                //重新获取token
                SecurityUserManage.getShare().getToken();
                return RBean.LOCATTOAST;

            case "415":
                // reason = "token不匹配";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "416":
                // reason = "token已过期";
                //OutPutMessage.showToast(error);
                SecurityUserManage.getShare().cancelLogin();
                SecurityUserManage.getShare().startActivity();
                return RBean.LOCATTOAST;

            case "417":
                // reason = "缺少认证参数：token";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "403":
                // reason = "用户未登录";
                //OutPutMessage.showToast(error);
                SecurityUserManage.getShare().cancelLogin();
                SecurityUserManage.getShare().startActivity();
                return Bean.LOCATTOAST;

            case "431":
                // reason = "参数不能为空";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "432":
                // reason = "参数不匹配";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "400":
                // reason = "客户端错误";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "500":
                // reason = "服务器端错误";
                //OutPutMessage.showToast(error);
                return RBean.LOCATTOAST;

            case "30001":
            case "30002":
            case "30003":
            case "30004":
            case "40001":
            case "40003":
            case "40004":
            case "40005":
            case "40006":
            case "40008":
            case "40009":
            case "40010":
            case "40011":
            case "40012":
            case "4000301":
            case "555":
            case "8890":
            case "1411":
            case "4240":
                return RBean.TOAST;
        }
        return Bean.ERROR;
    }
}
