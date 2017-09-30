package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * 申请绑定物业
 * Created by zhaoqing on 2017/5/23.
 */

public class CLetvApplyDistrict extends Bean {

    private String token;
    private int id;

    public CLetvApplyDistrict(int id,String token){
        this.urlOrigin = Constant.UrlOrigin.letv_add_district_apply;
        this.id = id;
        this.token = token;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
