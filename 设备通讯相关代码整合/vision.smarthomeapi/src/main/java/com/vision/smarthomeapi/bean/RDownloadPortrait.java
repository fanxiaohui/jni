package com.vision.smarthomeapi.bean;

/**
 * 用户头像下载
 */
public class RDownloadPortrait extends Bean {

    private byte[] pic;

    public RDownloadPortrait(){

    }

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    /**
     * http请求返回码处理
     *
     * @return 处理方式
     */
    public int mode() {
        int rValue = super.mode();
        if (rValue != Bean.ERROR){
            return rValue;
        }

        return Bean.ERROR;
    }
}

