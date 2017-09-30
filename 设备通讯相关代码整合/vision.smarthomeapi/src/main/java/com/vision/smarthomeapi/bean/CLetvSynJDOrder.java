package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/14.
 */

public class CLetvSynJDOrder extends Bean {

    public String token;
    public String jdcode;


    public CLetvSynJDOrder(String token,String jdcode){

        this.urlOrigin = Constant.UrlOrigin.letv_syn_jdorder;
        this.token = token;
        this.jdcode = jdcode;

    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getJdcode() {
        return jdcode;
    }

    public void setJdcode(String jdcode) {
        this.jdcode = jdcode;
    }
}
