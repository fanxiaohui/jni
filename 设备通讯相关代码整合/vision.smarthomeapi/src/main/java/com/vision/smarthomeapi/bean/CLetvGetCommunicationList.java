package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class CLetvGetCommunicationList extends Bean {

    public String token;
    public int orderId;


    public CLetvGetCommunicationList(String token, int orderId){

        this.urlOrigin = Constant.UrlOrigin.letv_get_communication_list;
        this.token = token;
        this.orderId = orderId;

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
