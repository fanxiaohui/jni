package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class CLetvCheckReciverLock extends Bean {

    public String token;
    public String tdCode;


    public CLetvCheckReciverLock(String token, String tdCode){

        this.urlOrigin = Constant.UrlOrigin.letv_check_reciver_lock;
        this.token = token;
        this.tdCode = tdCode;

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTdCode() {
        return tdCode;
    }

    public void setTdCode(String tdCode) {
        this.tdCode = tdCode;
    }
}
