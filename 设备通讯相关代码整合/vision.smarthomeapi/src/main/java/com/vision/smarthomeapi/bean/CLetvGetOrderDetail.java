package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class CLetvGetOrderDetail extends Bean {

    public String token;
    public int id;


    public CLetvGetOrderDetail(String token, int id){

        this.urlOrigin = Constant.UrlOrigin.letv_get_orderDetail;
        this.token = token;
        this.id = id;

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
