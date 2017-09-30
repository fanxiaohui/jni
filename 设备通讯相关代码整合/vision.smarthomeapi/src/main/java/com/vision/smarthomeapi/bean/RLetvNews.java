package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvNews extends Bean{

    public String messageTitle;
    public String messageContent;
    public long sendTime;
    public String imgPath;


    public RLetvNews(String messafeTitle,String messageContent,long sendTime,String imgPath){

        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
        this.sendTime = sendTime;
        this.imgPath = imgPath;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getSendTime() {
        return sendTime;
    }

    public void setSendTime(long sendTime) {
        this.sendTime = sendTime;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
