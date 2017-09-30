package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.dal.data.Constant;

/**
 * Created by zhaoqing on 2017/5/23.
 */

public class CLetvGetDistrictApply extends Bean {

    private String token;

    public CLetvGetDistrictApply(String token){
        this.urlOrigin = Constant.UrlOrigin.letv_get_district_apply;
        this.token = token;

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
