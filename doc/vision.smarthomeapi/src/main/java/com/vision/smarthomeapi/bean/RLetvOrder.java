package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/12.
 */

public class RLetvOrder extends Bean{

    private int id;
    private long createTime;
    private String code;
    private int type;
    private int state;
    private String devCode;
    private String comstr;
    private long comTime;



    public RLetvOrder(
            int id ,
            long createTime ,
            String code ,
            int type ,
            int state ,
            String devCode ,
            String comstr ,
            long comTime
    ){

        this.id = id;
        this.createTime = createTime;
        this.code = code;
        this.type = type;
        this.state = state;
        this.devCode = devCode;
        this.comstr = comstr;
        this.comTime = comTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateString(){
        //0 未接单 1已接单 2 处理中 3待回复 4已解决 5 已回访

        switch (state){
            case 0:
                return "未接单";

            case 1:
                return "已接单";


            case 2:
                return "处理中";

            case 3:
                return "待回复";

            case 4:
                return "已解决";

            case 5:
                return "已回访";

            default:
                return "未接单";
        }
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getComstr() {
        return comstr;
    }

    public void setComstr(String comstr) {
        this.comstr = comstr;
    }

    public long getComTime() {
        return comTime;
    }

    public void setComTime(long comTime) {
        this.comTime = comTime;
    }
}
