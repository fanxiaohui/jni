package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2017/5/15.
 */

public class RLetvOrderDetail {


    private int id;
    private int type;
    private String code;
    private long createTime;
    private int state;
    private String facilitatorName;
    private String facilitatorPhone;
    private long serviceTime;
    private String devCode;
    private int area1;
    private int area2;
    private int area3;
    private String serviceAddress;
    private String customerName;
    private String contactWay;
    private String questionDesc;
    private String imgPaths;
    private String comstr;
    private long comTime;
    private int qualityScore;
    private int serviceScore;
    private int trainScore;
    private String assessContent;
    private String assessImgs;
    private int relationType;
    private String relationOrder;
    private String reciverAddress;
    private String reciverUser;
    private String reciverContact;
    private String logistics;
    private String logisticsCompany;
    private long hopeTime;
    private String sureTdCode;


    public RLetvOrderDetail(int id, int type, String code, long createTime, int state,
                               String facilitatorName, String facilitatorPhone, long serviceTime,
                               String devCode, int area1, int area2, int area3, String serviceAddress,
                               String customerName, String contactWay, String questionDesc,
                               String imgPaths, String comstr, long comTime, int qualityScore,
                               int serviceScore, int trainScore, String assessContent,
                               String assessImgs, int relationType, String relationOrder,
                               String reciverAddress,String reciverUser, String reciverContact,
                               String logistics, String logisticsCompany, long hopeTime,String sureTdCode) {

        this.id = id;
        this.type = type;
        this.code = code;
        this.createTime = createTime;
        this.state = state;
        this.facilitatorName = facilitatorName;
        this.facilitatorPhone = facilitatorPhone;
        this.serviceTime = serviceTime;
        this.devCode = devCode;
        this.area1 = area1;
        this.area2 = area2;
        this.area3 = area3;
        this.serviceAddress = serviceAddress;
        this.customerName = customerName;
        this.contactWay = contactWay;
        this.questionDesc = questionDesc;
        this.imgPaths = imgPaths;
        this.comstr = comstr;
        this.comTime = comTime;
        this.qualityScore = qualityScore;
        this.serviceScore = serviceScore;
        this.trainScore = trainScore;
        this.assessContent = assessContent;
        this.assessImgs = assessImgs;
        this.relationType = relationType;
        this.relationOrder = relationOrder;
        this.reciverAddress = reciverAddress;
        this.reciverUser = reciverUser;
        this.reciverContact = reciverContact;
        this.logistics = logistics;
        this.logisticsCompany = logisticsCompany;
        this.hopeTime = hopeTime;
        this.sureTdCode = sureTdCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getState() {
        return state;
    }

    public String getStateString() {
        //0 未接单 1已接单 2 处理中 3待回复 4已解决 5 已回访

        switch (state) {
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

    public void setState(int state) {
        this.state = state;
    }

    public String getFacilitatorName() {
        return facilitatorName;
    }

    public void setFacilitatorName(String facilitatorName) {
        this.facilitatorName = facilitatorName;
    }

    public String getFacilitatorPhone() {
        return facilitatorPhone;
    }

    public void setFacilitatorPhone(String facilitatorPhone) {
        this.facilitatorPhone = facilitatorPhone;
    }

    public long getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public int getArea1() {
        return area1;
    }

    public void setArea1(int area1) {
        this.area1 = area1;
    }

    public int getArea2() {
        return area2;
    }

    public void setArea2(int area2) {
        this.area2 = area2;
    }

    public int getArea3() {
        return area3;
    }

    public void setArea3(int area3) {
        this.area3 = area3;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setServiceAddress(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactWay() {
        return contactWay;
    }

    public void setContactWay(String contactWay) {
        this.contactWay = contactWay;
    }

    public String getQuestionDesc() {
        return questionDesc;
    }

    public void setQuestionDesc(String questionDesc) {
        this.questionDesc = questionDesc;
    }

    public String getImgPaths() {
        return imgPaths;
    }

    public void setImgPaths(String imgPaths) {
        this.imgPaths = imgPaths;
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

    public int getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(int qualityScore) {
        this.qualityScore = qualityScore;
    }

    public int getServiceScore() {
        return serviceScore;
    }

    public void setServiceScore(int serviceScore) {
        this.serviceScore = serviceScore;
    }

    public int getTrainScore() {
        return trainScore;
    }

    public void setTrainScore(int trainScore) {
        this.trainScore = trainScore;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent;
    }

    public String getAssessImgs() {
        return assessImgs;
    }

    public void setAssessImgs(String assessImgs) {
        this.assessImgs = assessImgs;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public String getRelationOrder() {
        return relationOrder;
    }

    public void setRelationOrder(String relationOrder) {
        this.relationOrder = relationOrder;
    }

    public String getReciverAddress() {
        return reciverAddress;
    }

    public void setReciverAddress(String reciverAddress) {
        this.reciverAddress = reciverAddress;
    }

    public String getReciverUser() {
        return reciverUser;
    }

    public void setReciverUser(String reciverUser) {
        this.reciverUser = reciverUser;
    }

    public String getReciverContact() {
        return reciverContact;
    }

    public void setReciverContact(String reciverContact) {
        this.reciverContact = reciverContact;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public long getHopeTime() {
        return hopeTime;
    }

    public void setHopeTime(long hopeTime) {
        this.hopeTime = hopeTime;
    }

    public String getSureTdCode() {
        return sureTdCode;
    }

    public void setSureTdCode(String sureTdCode) {
        this.sureTdCode = sureTdCode;
    }
}
