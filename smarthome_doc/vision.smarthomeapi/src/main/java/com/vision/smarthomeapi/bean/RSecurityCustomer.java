package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/8.
 */
public class RSecurityCustomer extends Bean {
    /**
     * 用户id
     */
    private int id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 初始账号
     */
    private String initAccount;
    /**
     * 个性账号
     */
    private String account;
    /**
     * 密码
     */
    private String password ;
    /**
     * 紧急联系人
     */
    private String emgUser;
    /**
     * 紧急联系人电话
     */
    private String emgPhone;
    /**
     * 备用联系人
     */
    private String standbyUser;
    /**
     * 备用联系人电话
     */
    private String standbyPhone;
    /**
     * 小区
     */
    private int districtId;
    private String districtName;
    /**
     * 楼
     */
    private  int floorId;
    private String floorName;
    /**
     * 单元
     */
    private int unitId;
    private String unitName;
    /**
     * 房间
     */
    private int roomId;
    private String roomName;
    /**
     * 地址
     */
    private String address;

    private int locationId;
    private int state;
    private int createId;
    private long createTime;
    private long updateTime;
    private String originalPwd;

    RSecurityCustomer(
            String account,
            String address,
            int createId,
            long createTime,
            int districtId,
            String districtName,
            String emgPhone,
            String emgUser,
            int id,
            String initAccount,
            int locationId,
            String name,
            String password ,
            String phone,
            String standbyPhone,
            String standbyUser,
            int state,
            long updateTime,
            String email,
            int floorId,
            String floorName,
            int unitId,
            String unitName,
            int roomId,
            String roomName,
            String originalPwd
            ){


        this.account= account;
        this.address= address;
        this. createId= createId;
        this. createTime= createTime;
        this. districtId= districtId;
        this.districtName=districtName ;
        this. emgPhone= emgPhone;
        this.emgUser= emgUser;
        this. id= id;
        this.initAccount=initAccount ;
        this. locationId= locationId;
        this.name= name;
        this.password =password ;
        this.phone= phone;
        this.standbyPhone= standbyPhone;
        this.standbyUser= standbyUser;
        this. state= state;
        this. updateTime=updateTime ;
        this.email = email;
        this.floorId = floorId;
        this.floorName = floorName;
        this.unitId = unitId;
        this.unitName = unitName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.originalPwd = originalPwd;

    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getEmgPhone() {
        return emgPhone;
    }

    public void setEmgPhone(String emgPhone) {
        this.emgPhone = emgPhone;
    }

    public String getEmgUser() {
        return emgUser;
    }

    public void setEmgUser(String emgUser) {
        this.emgUser = emgUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitAccount() {
        return initAccount;
    }

    public void setInitAccount(String initAccount) {
        this.initAccount = initAccount;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStandbyPhone() {
        return standbyPhone;
    }

    public void setStandbyPhone(String standbyPhone) {
        this.standbyPhone = standbyPhone;
    }

    public String getStandbyUser() {
        return standbyUser;
    }

    public void setStandbyUser(String standbyUser) {
        this.standbyUser = standbyUser;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getOriginalPwd() {
        return originalPwd;
    }

    public void setOriginalPwd(String originalPwd) {
        this.originalPwd = originalPwd;
    }
}
