package com.vision.smarthomeapi.bean;

/**
 * 用户头像上传
 */
public class RUploadPortrait extends Bean {

    public RUploadPortrait(String status, String statusMsg) {
        this.status = status;
        this.statusMsg = statusMsg;
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
        }
        return Bean.ERROR;
    }
}

