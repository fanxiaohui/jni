package com.vision.smarthomeapi.bean;

/**
 * Created by zhaoqing on 2016/1/8.
 */
public class RSecurityDevice extends Bean {

    /**
     * 设备id
     */
    private int id;
    /**
     * mac
     */
    private String mac;
    /**
     * 状态 1未登记 2有效 3待安装 4停用 5超期 6测试 7禁用
     */
    private int state;
    /**
     * 布防撤防 1 布防 2 撤防
     */
    private int defense;
    /**
     * MAC地址(正确的，16位十六进制mac地址)
     */
    private String macMatch;
    /**
     * 设备位置
     */
    private String name;
    /**
     * 设备类型
     */
    private int devType;
    /**
     * 型号名称中文
     */
    private String devName;
    /**
     * 设备型号英文
     */
    private String devVersion;
    /**
     * 通讯型号
     */
    private int communicationType;
    /**
     * 厂商编码
     */
    private int manufacturerEncoding;
    /**
     * 公网IP和端口号
     */
    private String ipPort;
    /**
     * 是否支持布撤防功能 0支持 1不支持
     */
    private int defenseSupport;
    /**
     * 是否第一次登录平台 0是  1否
     */
    private int isfirstLogin;
    /**
     * 设备类型名称
     */
    private String devtypeName;
    /**
     * 型号名称
     */
    private String versionName;
    /**
     * 是否支持二级联动功能 0:支持 1:不支持
     */
    private int isTwoLinkage;
    /**
     * 原始类型
     */
    private int orginVersion;
    /**
     * 图标
     */
    private String devImgUrl;
    /**
     * 异常图标
     */
    private String devImgUrlFault;
    /**
     * 有无用电报告
     */
    private int electricReport;
    /**
     * 分享状态
     */
    private int shareType;
    /**
     * 分享者账号
     */
    private String shareAccount;
    /**
     * 分享者名字
     */
    private String shareName;

    /**
     * 关闭状态 0关闭1开启
     */
    private int doorState;
    /**
     * 使用方式 1租用 2购买
     */
    private int useType;
    /**
     * 租用开始时间
     */
    private long startTime;
    /**
     * 租用截止时间
     */
    private long deadline;
    /**
     * 报警时间
     */
    private long alarmTime;
    /**
     * 当前时间
     */
    private long localTime;
    /**
     * 持续时间
     */
    private long durationTime;
    /**
     * 报警类型
     */
    private String alarmType;
    /**
     * 温馨提示
     */
    private String tips;
    /**
     * 报警记录的id
     */
    private int recordId;
    /**
     * 是否是原设备
     * 0.不是 1.是 2.不支持2级联动
     */
    private int isSource;
    /**
     * 故障状态
     */
    private int faultState;
    /**
     * 绑定状态(0:已绑定 1:未绑定)
     */
    private int bindStatus;

    /**
     * 报警状态 0:正常 1:报警 2:告警状态恢复 3：持续报警
     */
    private int alarmStatus;

    /**
     * 二维码
     */
    private String qrcode;
    /**
     * 固件版本
     */
    private String pid;
    /**
     * 	0：无需升级 1：需要升级 （只针对门锁和猫眼）
     */
    private int upgrade;

    private int controlType;

    private int bindDeviceId;

    /**
     * 设备密钥
     */
    private String ctlpwd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public String getMacMatch() {
        return macMatch;
    }

    public void setMacMatch(String macMatch) {
        this.macMatch = macMatch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDevType() {
        return devType;
    }

    public void setDevType(int devType) {
        this.devType = devType;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(String devVersion) {
        this.devVersion = devVersion;
    }

    public int getCommunicationType() {
        return communicationType;
    }

    public void setCommunicationType(int communicationType) {
        this.communicationType = communicationType;
    }

    public int getManufacturerEncoding() {
        return manufacturerEncoding;
    }

    public void setManufacturerEncoding(int manufacturerEncoding) {
        this.manufacturerEncoding = manufacturerEncoding;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public int getDefenseSupport() {
        return defenseSupport;
    }

    public void setDefenseSupport(int defenseSupport) {
        this.defenseSupport = defenseSupport;
    }

    public int getIsfirstLogin() {
        return isfirstLogin;
    }

    public void setIsfirstLogin(int isfirstLogin) {
        this.isfirstLogin = isfirstLogin;
    }

    public String getDevtypeName() {
        return devtypeName;
    }

    public void setDevtypeName(String devtypeName) {
        this.devtypeName = devtypeName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getIsTwoLinkage() {
        return isTwoLinkage;
    }

    public void setIsTwoLinkage(int isTwoLinkage) {
        this.isTwoLinkage = isTwoLinkage;
    }

    public int getOrginVersion() {
        return orginVersion;
    }

    public void setOrginVersion(int orginVersion) {
        this.orginVersion = orginVersion;
    }

    public String getDevImgUrl() {
        return devImgUrl;
    }

    public void setDevImgUrl(String devImgUrl) {
        this.devImgUrl = devImgUrl;
    }

    public String getDevImgUrlFault() {
        return devImgUrlFault;
    }

    public void setDevImgUrlFault(String devImgUrlFault) {
        this.devImgUrlFault = devImgUrlFault;
    }

    public int getElectricReport() {
        return electricReport;
    }

    public void setElectricReport(int electricReport) {
        this.electricReport = electricReport;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public String getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(String shareAccount) {
        this.shareAccount = shareAccount;
    }

    public String getShareName() {
        return shareName;
    }

    public void setShareName(String shareName) {
        this.shareName = shareName;
    }

    public int getDoorState() {
        return doorState;
    }

    public void setDoorState(int doorState) {
        this.doorState = doorState;
    }

    public int getUseType() {
        return useType;
    }

    public void setUseType(int useType) {
        this.useType = useType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getDeadline() {
        return deadline;
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public long getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(long alarmTime) {
        this.alarmTime = alarmTime;
    }

    public long getLocalTime() {
        return localTime;
    }

    public void setLocalTime(long localTime) {
        this.localTime = localTime;
    }

    public long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(long durationTime) {
        this.durationTime = durationTime;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getIsSource() {
        return isSource;
    }

    public void setIsSource(int isSource) {
        this.isSource = isSource;
    }

    public int getFaultState() {
        return faultState;
    }

    public void setFaultState(int faultState) {
        this.faultState = faultState;
    }

    public int getBindStatus() {
        return bindStatus;
    }

    public void setBindStatus(int bindStatus) {
        this.bindStatus = bindStatus;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(int alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public int getControlType() {
        return controlType;
    }

    public void setControlType(int controlType) {
        this.controlType = controlType;
    }

    public int getBindDeviceId() {
        return bindDeviceId;
    }

    public void setBindDeviceId(int bindDeviceId) {
        this.bindDeviceId = bindDeviceId;
    }

    public String getCtlpwd() {
        return ctlpwd;
    }

    public void setCtlpwd(String ctlpwd) {
        this.ctlpwd = ctlpwd;
    }
}
