package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/6/6.
 */

public class CLetvVipGo extends Bean {
    private String no;
    private String pt;
    private String token;
    public CLetvVipGo(String token, String no,String pt){
        this.token = token;
        this.pt = pt;
        this.no = no;
        this.urlOrigin = Constant.UrlOrigin.letv_vip_go;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPt() {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
