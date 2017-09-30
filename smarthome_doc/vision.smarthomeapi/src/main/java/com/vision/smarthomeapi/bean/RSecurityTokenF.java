package com.vision.smarthomeapi.bean;


public class RSecurityTokenF extends Bean {

    private RSecurityToken authResponse;

    public RSecurityTokenF( RSecurityToken authResponse) {
        this.authResponse = authResponse;
    }

    public RSecurityToken getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(RSecurityToken authResponse) {
        this.authResponse = authResponse;
    }
}

