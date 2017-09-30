package com.vision.smarthomeapi.dal.data;


import com.smarthome.head.SmartHomeData;
import com.vision.smarthomeapi.bean.RSecurityDevice;
import com.vision.smarthomeapi.bean.SecurityTypeInfo;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.DeviceUpgradeManage;
import com.vision.smarthomeapi.bll.manage.ResendQueueManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.DeviceSendHeadData;
import com.vision.smarthomeapi.dal.SmartDeviceHeartbeatThread;
import com.vision.smarthomeapi.dal.function.AlarmData;
import com.vision.smarthomeapi.dal.function.UpdateData;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.net.WebSocketChannel;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.MD5ToText;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PhoneUtil;
import com.vision.smarthomeapi.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用来解析设备共有数据的类
 */
public class SmartDeviceLogic {

    public static byte[] PASS_WORD = new byte[]{};

    /**
     * 设备密钥 共享设备使用
     */
    private String ctlpwd;



    /**
     * 升级状态
     */
    private int CPUupdate_state = 0;
    private int MCUupdate_state = 0;


    public int getCPUupdate_state() {
        return CPUupdate_state;
    }

    public void setCPUupdate_state(int CPUupdate_state) {
        this.CPUupdate_state = CPUupdate_state;
    }

    public int getMCUupdate_state() {
        return MCUupdate_state;
    }

    public void setMCUupdate_state(int MCUupdate_state) {
        this.MCUupdate_state = MCUupdate_state;
    }

    public int getSqlID() {
        return sqlID;
    }

    private SmartDevice smartDevice;

    //心跳线程
    private SmartDeviceHeartbeatThread smartDeviceHeartbeatThread;
    //发送大包号自增
    private volatile int sequence = 0;
    //接收大包序号
    private volatile int receiveSequence;

    private boolean isSendInfo;
    //广域网设备ID
    private String deviceId;

    private String ipPort;

    /**
     * 设备的版本 决定是UDP还是tcp通讯  1：udp 2：tcp
     */
    private String deviceVer = "";

    private String deviceNewAppVer;
    //芯片型号
    private short deviceChipType;

//    //密码
//    private String devicePassword;
    /**
     * 包含设备类型和设备型号的id
     */
    private int deviceTypeId;
    //设备类型
    private int deviceType;

    //设备型号
    private int devVersion;


    private long srcID;
    //是否存活心跳
    private boolean isReceive;
    //设备名称
    private String deviceName;
    //设备IP
    private String deviceIp;
    //网关mac,用来大包序号管理查找设备时用的
    private String gatewayMac;

    //用户ID
    private String userID = "";

    //是否显示
    private boolean isVisible;
    /**
     * 网络模式
     */
    private int netMode;
    //所属用户ID
    private String ownerUserID;
    //广域网连接地址
    private String deviceWanAddr;

    /**
     * 是否可以进行时间控制，由错误码2011控制
     */
    private Boolean TimeControl = true;

    public Boolean getTimeControl() {
        return TimeControl;
    }

    public void setTimeControl(Boolean timeControl) {
        TimeControl = timeControl;
    }

    public int getDeviceUpdateType() {
        return deviceUpdateType;
    }

    public void setDeviceUpdateType(int deviceUpdateType) {
        this.deviceUpdateType = deviceUpdateType;
    }

    private int deviceUpdateType;


    public int getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(int devVersion) {
        this.devVersion = devVersion;
    }

    public boolean isExistChildDevice() {
        return isExistChildDevice;
    }

    public void setExistChildDevice(boolean existChildDevice) {
        isExistChildDevice = existChildDevice;
    }

    //是否存在子设备
    private boolean isExistChildDevice;

    /**
     * 通讯型号
     */
    private int communicationType;
    /**
     * 厂商编码
     */
    private int manufacturerEncoding;
    /**
     * 状态 1未登记 2有效 3待安装 4停用 5超期 6测试 7禁用
     */
    private int state;
    /**
     * 布防撤防
     */
    private int defense;
    /**
     * 该设备是否是布撤防设备
     */
    private int getDefenseSupport;

    /**
     * 是否第一次登录平台 0是  1否
     */
    private int isfirstLogin;
    /**
     * 是否支持二级联动功能 0:支持 1:不支持
     */
    private int isTwoLinkage;


    private Map<Integer, byte[]> netWorkMap;


    //本地是否存在
    private boolean isSQLExist;
    /**
     * 是否读取子设备列表
     */
    private boolean isSubDeviceList;

    /**
     * 告警信息
     */
    private AlarmData alarmData;


    private UpdateData updateData;

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
     * 设备名称
     */
    private String name;



//
//    /**
//     * 备注名称
//     */
//    private String remarksName;

    // 设备状态 1.报警 2.故障 3.离线 4.在线
    private int deviceState;

    /**
     * 型号名称中文
     */
    private String devName;
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
     * 分享状态  1分享
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
     * 关闭状态：0是关闭，1是开启
     */
    private int doorState;
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


    /**
     * controlType '0:不可控 1：快捷 2：内页',
     */
    private int controlType;

    /**
     * 针对猫眼门锁绑定功能 修改设备列表接口新增返回字段bindDeviceId，如果有绑定则会返回绑定的那个设备id
     */
    private int bindDeviceId = 0;



    /**
     * 发送大包号自增
     */
    public void addBigPacketNumber() {
        if (sequence < Integer.MAX_VALUE) {
            sequence++;
        } else {
            sequence = 0;
        }
    }

    public int getDeviceState() {
        return deviceState;
    }

    public void setDeviceState(int deviceState) {
        this.deviceState = deviceState;
    }

    /**
     * 发送获取大包号
     */
    public int getSendPacketNumber() {
        return sequence;
    }


    public void setReceiveSequence(int receiveSequence) {
        this.receiveSequence = receiveSequence;
    }


    public int getSequence() {
        return sequence;
    }

    public int getReceiveSequence() {
        return receiveSequence;
    }

    //会话ID
    private byte[] sessionId;


    //发送小包号
    private HashMap<String, Integer> mPacketNumber;
    //应答小包号
    private HashMap<String, Integer> mReceivePacketNumber;


    //状态机
    private SmartDeviceState smartDeviceState;

    /**
     * 设备地址
     */
    private String addName;


    public SmartDeviceLogic(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
        netWorkMap = new ConcurrentHashMap<>();
        isSendInfo = false;
        alarmData = new AlarmData(smartDevice);
        updateData = new UpdateData(smartDevice);
    }


    public AlarmData getAlarmData() {
        return alarmData;
    }

    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }

    /**
     * 初始化状态机
     *
     * @param smartDevice
     */
    public void initSmartDeviceState(SmartDevice smartDevice) {
        smartDeviceState = SmartDeviceState.INIT_DEVICE;
        smartDeviceState.deviceStateEntrance(smartDevice);
    }


    /**
     * 解析局域网广播数据
     *
     * @param deviceData
     */
    public void parseLanDeviceInfo(byte[] deviceData) {


        ByteBuffer data = ByteBuffer.allocate(deviceData.length).order(ByteOrder.LITTLE_ENDIAN);
        data.put(deviceData, 0, deviceData.length);

        int _byte_5 = data.get(0);//Byte 3
        int _byte_5_bit_1_3 = (_byte_5 & 0x07); //加密算法 1.DES算法
        int _byte_5_bit_4 = (_byte_5 & 0x08) >> 3; //子设备 0-没有、1-有
        int _byte_5_bit_5 = (_byte_5 & 0x10) >> 4; //设备是否登录云平台 0-失败、1-成功
        int _byte_5_bit_6 = (_byte_5 & 0x20) >> 5; //局域网组(当前局域网下) 取值0：未加入，1：已加入
        int _byte_5_bit_7 = (_byte_5 & 0x40) >> 6; //默认密码 0-不是、1-是
        int _byte_5_bit_8 = (_byte_5 & 0x80) >> 7; //用户绑定 取值0：未绑定，1：已绑定

        int _byte_6 = data.get(1);
        int _byte_6_bit_1_4 = (_byte_6 & 0x0F); //通讯类型bit 1 - 4
        int _byte_6_bit_5 = (_byte_6 & 0x30) >> 4; //当前模式
        int _byte_6_bit_8 = (_byte_6 & 0x80) >> 7; //升级标记

        short _byte_7_8 = data.getShort(2); //芯片型号 2字节

        short _byte_9_10 = data.getShort(4);//硬件软件版本号 2字节

        long _byte_11_18 = data.getLong(6);//MAC地址 8字节

        String mac = ByteUtil.longToMacStr(_byte_11_18);
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, "mac :" + mac);

        if (_byte_5_bit_4 == 1) {
            this.isExistChildDevice = true;
        }
//        this.devicePassword = PASS_WORD;
        this.netMode = _byte_6_bit_5;
        this.deviceMac = mac;

        updateData.setDeviceChipType(_byte_7_8);
        updateData.setAppVersionCPU(_byte_9_10);
        updateData.setDeviceUpdateMode(_byte_6_bit_8 == 1 ? true : false);//如果为
        OutPutMessage.LogCatInfo("设备数据解析", "数据长度 :" + data.array().length);
        if (data.array().length == 16) {
            short _byte_19_20 = data.getShort(14);//单片机版本号
            updateData.setAppVersionMCU(_byte_19_20);
        }
        updateData.setDeviceUpdateType(deviceUpdateType);
        OutPutMessage.LogCatInfo("局域网接收设备数据解析", "设备类型 :" + Integer.toHexString(updateData.getDeviceUpdateType()));
        OutPutMessage.LogCatInfo("局域网接收设备数据解析", "MCU :" + updateData.getAppVersionMCU());
        OutPutMessage.LogCatInfo("局域网接收设备数据解析", "CPU :" + updateData.getAppVersionCPU());
        OutPutMessage.LogCatInfo("局域网接收设备数据解析", "数据mac :" + mac);
    }

    /**
     * 添加网络状态，并根据状态去自动登录
     *
     * @param net 网络标记
     */
    public void addNetWork(int net, Boolean islogin) {
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, this.smartDeviceState + "    发送登录11：" + deviceMac + ",登录设备的标记" + deviceNetWork + "和net-->" + net);
        this.deviceNetWork |= net;

        if (!islogin) {
            return;
        }

        boolean needSendLogin = false;
        if (this.smartDeviceState == SmartDeviceState.DISCONNECT_DEVICE
                || deviceNetWork > net || deviceNetWork == Constant.NetWork.GATEWAY_NET) {
            needSendLogin = true;
        } else if (this.smartDeviceState == SmartDeviceState.CONNECT_DEVICE) {
            smartDeviceState.deviceEvent(smartDevice, SmartDeviceConstant.EventStatus.DEVICE_INFO);
        }

        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, deviceMac + "是否需要发送登录：" + needSendLogin);
        loginDevice(needSendLogin, net);
    }


    /**
     * 登录设备
     *
     * @param needSendLogin 是否登录
     * @param net           NetWork通道
     *                      调用之前必须赋net值
     */
    public void loginDevice(boolean needSendLogin, int net) {

        OutPutMessage.LogCatInfo("登录==", net+"  "+deviceMac + "  " + needSendLogin + "   " + deviceVer + "   " + ipPort);


        if (needSendLogin) {
            switch (net) {
                case Constant.NetWork.LAN_NET:
                   this.setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);//切换登录中
                    OutPutMessage.LogCatInfo(WebSocketChannel.TAG, deviceVer+"局域网登录==中:" + deviceMac + "网络类型" + net + "!!!" + deviceWanAddr);
                    break;
                case Constant.NetWork.WAN_NET:
                    if (deviceVer.equals("1")) {
                        this.setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);//切换登录中
                        OutPutMessage.LogCatInfo(WebSocketChannel.TAG, deviceVer+"广域网登录==中:" + deviceMac + "网络类型" + net + "!!!" + deviceWanAddr);
                    }

                    break;
                case Constant.NetWork.GATEWAY_NET:
                    if (getGatewayMac() != null) {
                        OutPutMessage.LogCatInfo("发送子设备登录", "网关MAC:" + getGatewayMac());
                        this.setSmartDeviceStateManager(SmartDeviceState.CONNECT_DEVICE);
                    }
                    break;
                case Constant.NetWork.ZIG_BEE_NET:
                    this.setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);//切换登录中
                    break;
            }
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, "发送登录22 ：" + deviceMac + ",登录设备");
        }
    }

    /**
     * 设置设备是否显示
     */
    public void updateVisible() {
        if (this.isSQLExist) {//用户登录属于拥有者
            this.isVisible = true;
        } else {
            this.isVisible = false;
        }
    }

    /**
     * 删除通讯状态
     *
     * @param net 网络标记
     */
    public void deleteNetWork(int net, boolean isLogin) {
        deviceNetWork &= ~net;
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, deviceMac + "删除通讯状态" + deviceNetWork + "isLogin" + isLogin);

        if (isLogin) {
            loginNetWork();
        }
    }

    private void loginNetWork() {
        if (deviceNetWork != 0) {
            setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);
        } else if (this.smartDeviceState != SmartDeviceState.DISCONNECT_DEVICE) {
            setSmartDeviceStateManager(SmartDeviceState.DISCONNECT_DEVICE);
        }
    }

    /**
     * 删除上次netWork,并重新登录
     */
    public int deleteHighNetWork() {
        int temp = 1;
        while (temp <= Constant.NetWork.MAX_NETWORK) {
            if (isNetWork(temp)) {//如果以前包含此net标记，则删除并重新登录
                deleteNetWork(temp, true);
                return deviceNetWork;
            }
            temp = temp << 1;
        }
        return 0;
    }

    /**
     * 添加设备状态
     *
     * @param state 设备状态码
     */
    public void addDeviceState(int state) {
        this.operatingState |= state;
    }

    /**
     * 删除设备状态
     *
     * @param state 设备状态码
     */
    public void deleteDeviceState(int state) {
        this.operatingState &= ~state;
    }


    /**
     * 当前设备是否包含设备状态
     *
     * @param state
     * @return
     */
    public boolean isDeviceState(int state) {
        int dnw = operatingState;
        dnw &= state;
        if (dnw != 0) {
            return true;
        }
        return false;
    }

    /**
     * 本地数据库保存id
     */
    private int sqlID;

    // 广域网 局域网
    private int deviceNetWork;

    //MAC地址
    private String deviceMac;
    //当前设备多种状态值
    private int operatingState;
    //排序
    private int sortState;


    /**
     * 获取当前状态机
     *
     * @return
     */
    public SmartDeviceState getSmartDeviceState() {
        return smartDeviceState;
    }

    /**
     * 获取设备MAC
     */
    public String getDeviceMac() {
        return deviceMac;
    }

    /**
     * 设置设备MAC
     */
    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public void setIsReceive(boolean isReceive) {
        this.isReceive = isReceive;
    }

    /**
     * 添加子设备到网关
     *
     * @param mac  子设备
     * @param type 网络类型
     * @return
     */
    public boolean addSubDevice(String mac, byte type) {
        ByteBuffer mByteBuffer = ByteBuffer.allocate(17).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        mByteBuffer.put(ByteUtil.hexStr2Byte(mac));
        byte[] bytes = new byte[8];
        mByteBuffer.put(bytes);
        mByteBuffer.put(type);
        byte[] data = mByteBuffer.array();
        sendControllerByte(SmartDeviceConstant.MsgID.ADD_CHILD_DEVICE, data);
        OutPutMessage.LogCatInfo("增加子设备", mac + "");
        return false;
    }


    /**
     * 当前网络类型
     *
     * @return
     */
    public int getDeviceNetWork() {
        return deviceNetWork;
    }

    /**
     * 设置当前网络类型
     *
     * @param deviceNetWork
     */
    public void setDeviceNetWork(int deviceNetWork) {
        OutPutMessage.LogCatInfo("解绑设备",deviceNetWork+"");
        this.deviceNetWork = deviceNetWork;
    }


    /**
     * 是否包含状态位
     *
     * @param net
     * @return
     */
    public boolean isNetWork(int net) {
        OutPutMessage.LogCatInfo("解绑设备",deviceNetWork+"  "+net);
        int dnw = deviceNetWork;
        dnw &= net;
        if (dnw != 0) {
            return true;
        }
        return false;
    }


    /**
     * 解析广域网数据
     * 赋值
     *
     * @param wanDevice 广域网设备
     */
    public void parseWanDeviceInfo(RSecurityDevice wanDevice) {
        if (wanDevice == null) {
            return;
        }
        this.isSQLExist = true;
        this.deviceId = wanDevice.getId() + "";

        this.ipPort = wanDevice.getIpPort();
        this.deviceMac = wanDevice.getMac().toUpperCase();
        this.state = wanDevice.getState();
        this.defense = wanDevice.getDefense();
        this.getDefenseSupport = wanDevice.getDefenseSupport();
        this.deviceTypeId = wanDevice.getDevType();
        SecurityTypeInfo securityTypeInfo = SmartDeviceManage.getTypeMapForId().get(wanDevice.getDevType());
        if (securityTypeInfo != null) {
            this.devVersion = securityTypeInfo.getCode();
            this.deviceType = securityTypeInfo.getDevTypeId();
        }
        this.communicationType = wanDevice.getCommunicationType();
        this.manufacturerEncoding = wanDevice.getManufacturerEncoding();
        this.isfirstLogin = wanDevice.getIsfirstLogin();
        this.isTwoLinkage = wanDevice.getIsTwoLinkage();
        this.devName = wanDevice.getDevName();
        this.orginVersion = wanDevice.getOrginVersion();
        this.devImgUrl = wanDevice.getDevImgUrl();
        this.devImgUrlFault = wanDevice.getDevImgUrlFault();
        this.electricReport = wanDevice.getElectricReport();
        this.shareAccount = wanDevice.getShareAccount();
        this.shareType = wanDevice.getShareType();
        this.shareName = wanDevice.getShareName();

        this.doorState = wanDevice.getDoorState();
        this.userID = StringUtil.getUserID();
        this.useType = wanDevice.getUseType();
        this.startTime = wanDevice.getStartTime();
        this.deadline = wanDevice.getDeadline();
        this.name = wanDevice.getName();
        this.isSource = wanDevice.getIsSource();
        this.faultState = wanDevice.getFaultState();
        this.bindStatus = wanDevice.getBindStatus();
        this.alarmStatus = wanDevice.getAlarmStatus();
        this.pid = wanDevice.getPid();
        this.upgrade = wanDevice.getUpgrade();
        this.qrcode = wanDevice.getQrcode();
        this.controlType = wanDevice.getControlType();
        this.bindDeviceId = wanDevice.getBindDeviceId();
        this.ctlpwd = wanDevice.getCtlpwd();
    }

    /**
     * 发送设备登陆码
     */
    public byte[] sendDeviceLoginData(boolean isResend) {
        byte head = 0x04;
        long checkID = 0;

        if (isNetWork(Constant.NetWork.LAN_NET)) {

            OutPutMessage.LogCatInfo(WebSocketChannel.TAG, "局域网登录==:" + deviceMac );
            if (!StringUtil.getUserID().equals("")) {
                String srcId = StringUtil.getUserID();
                checkID = Long.parseLong(srcId, 10);
                //Log.i("转发头测试",s+"------------->");
                head |= 0x01;
            } else {
                String lanSrc = PhoneUtil.getMacAddress(Controller.defaultController().getContext());
                checkID = Long.parseLong(lanSrc, 16);
            }
        } else if (isNetWork(Constant.NetWork.GATEWAY_NET)) {
            if (!StringUtil.getUserID().equals("")) {
                String srcId = StringUtil.getUserID();
                checkID = Long.parseLong(srcId, 10);
                //Log.i("转发头测试",s+"------------->");
                head |= 0x01;
            } else {
                String lanSrc = PhoneUtil.getMacAddress(Controller.defaultController().getContext());
                checkID = Long.parseLong(lanSrc, 16);
            }
        } else if (isNetWork(Constant.NetWork.WAN_NET)) {
            OutPutMessage.LogCatInfo(WebSocketChannel.TAG, "广域网登录==:" + deviceMac );
            if (!StringUtil.getUserID().equals("")) {
                String srcId = StringUtil.getUserID();
                checkID = Long.parseLong(srcId, 10);
                //Log.i("转发头测试",s+"------------->");
                head |= 0x01;
            } else {
                String lanSrc = PhoneUtil.getMacAddress(Controller.defaultController().getContext());
                OutPutMessage.LogCatInfo("ZIFI源", "局域网:----------------->" + lanSrc + "-->当前checkID:" + checkID);
                checkID = Long.parseLong(lanSrc, 16);
            }
        } else if (isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
            String lanSrc = NetworkManager.defaultNetworkManager().zigBeeSrc();
            OutPutMessage.LogCatInfo("ZIFI源", "ZigBEE:----------------->" + lanSrc + "-->当前checkID:" + checkID);
            checkID = ByteUtil.macToLong(lanSrc);
        }

        OutPutMessage.LogCatInfo("ZIFI源", getDeviceMac() + "-->当前checkID:" + checkID);

        byte[] checkIDByte = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(checkID).array();
//        String password = devicePassword;
        //TODO 密码写死，多次登录请求，容易出现null指针
        byte[] devicePW = PASS_WORD;
        if (getShareType() == 1){
            devicePW = ByteUtil.hexStr2Byte(getCtlpwd());
        }

        OutPutMessage.LogCatInfo("设备用密钥", ByteUtil.byteArrayToHexString(devicePW));

        OutPutMessage.LogCatInfo("设备私钥转数组", ByteUtil.byteArrayToHexString(devicePW));
//        byte[] md5 = MD5ToText.MD5Encodebyte(PASS_WORD);
        byte[] md5 = MD5ToText.byteArrayMd5(devicePW);
        OutPutMessage.LogCatInfo("设备私钥一次MD5", ByteUtil.byteArrayToHexString(md5));
        byte[] splice = new byte[md5.length + 8];
        System.arraycopy(md5, 0, splice, 0, md5.length);
        System.arraycopy(checkIDByte, 0, splice, md5.length, 8);
        OutPutMessage.LogCatInfo("设备私钥拼接", ByteUtil.byteArrayToHexString(splice));
        md5 = MD5ToText.byteArrayMd5(splice);
        OutPutMessage.LogCatInfo("设备私钥二次MD5", ByteUtil.byteArrayToHexString(md5));


        byte[] bytes = ByteBuffer.allocate(25).put(head).put(checkIDByte).put(md5).array();
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, getDeviceMac() + "-->当前网络标记:" + getDeviceNetWork() + "--->" + ByteUtil.byteArrayToHexString(bytes, true));
        if (isResend) {

            //7.21开会决定局域网不登录
//            if (isNetWork(Constant.NetWork.WAN_NET)) {

                OutPutMessage.LogCatInfo("设备最终登录", deviceMac + "    " + deviceNetWork + "   " );
                sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_LOGIN, bytes);
//            }
        }
        return bytes;
    }

    /**
     * 发送—>通知平台设备下线,协议0xC7
     *
     * @return
     */
    public boolean sendDeviceNotOnline() {

        long wsDstID = Long.valueOf(getDeviceId());
        byte[] data = ByteUtil.longToLittleEndianByteArray(wsDstID);

        OutPutMessage.LogCatInfo("心跳下线协议==", smartDevice.getSmartDeviceLogic().getDeviceName() + "下线数据：" + ByteUtil.byteArrayToHexString(data));
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_NOT_ONLINE, data, SmartDeviceConstant.OperateState.No_Read,
                SmartDeviceConstant.OperateState.No_Resend, SmartDeviceConstant.OperateState.No_ACk);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 发送——>获取参数 协议0X52
     */
    public boolean getDeviceParameter(){

        OutPutMessage.LogCatInfo("电暖炉", "读取参数" );

        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_PARAMETER_SET, null, SmartDeviceConstant.OperateState.Read);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 发送->运行状态查询,协议0x72
     */
    public boolean sendQueryDeviceInfo() {
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_RUN_STATE, null, SmartDeviceConstant.OperateState.Read);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 发送->报警状态查询,协议0x73
     */
    public boolean sendQueryDeviceAlarm() {
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_FAULT_STATE, null, SmartDeviceConstant.OperateState.Read);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 发送->故障状态查询,协议0x74
     */
    public boolean sendQueryDeviceFaulty() {
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_ALARM_STATE, null, SmartDeviceConstant.OperateState.Read);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 存储设备推送小包号
     *
     * @param msgID  消息ID
     * @param number 记录推送的包号
     * @return
     */
    public void setReceivePacketNumber(int msgID, int number) {
        if (mReceivePacketNumber == null) {
            mReceivePacketNumber = new HashMap();
        }
        mReceivePacketNumber.put("" + msgID, Integer.valueOf(number));
    }

    /**
     * 获取小包号
     *
     * @param serviceID
     * @return
     */
    public short getSendPacketNumber(int serviceID) {
        if (mPacketNumber == null) {
            mPacketNumber = new HashMap();
        }
        Integer number = mPacketNumber.get("" + serviceID);
        if (number != null) {
            return number.shortValue();
        } else {
            return 0;
        }
    }

    /**
     * 发送->心跳,协议0x70
     */
    public boolean sendHeartbeatData() {
        this.isReceive = true;
        return sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CONTROL_HEARTBEAT, null,
                SmartDeviceConstant.OperateState.No_Read,
                SmartDeviceConstant.OperateState.No_Resend,
                SmartDeviceConstant.OperateState.No_ACk);
    }

    /**
     * 添加小包号
     *
     * @param serviceID
     */
    public void setSendPacketNumber(short serviceID) {
        if (mPacketNumber == null) {
            mPacketNumber = new HashMap();
        }
        Integer number = mPacketNumber.get("" + serviceID);
        if (number != null) {
            int i = number.intValue();
            i++;
            if (i >= Byte.MAX_VALUE) {
                i = 0;
            }
            mPacketNumber.put("" + serviceID, Integer.valueOf(i));
        } else {
            mPacketNumber.put("" + serviceID, Integer.valueOf(0));
        }
    }

    /**
     * 获取设备推送小包号
     *
     * @param msgID 获取消息ID
     * @return
     */
    public short getReceivePacketNumber(int msgID) {
        if (mReceivePacketNumber == null) {
            mReceivePacketNumber = new HashMap();
        }
        Integer number = mReceivePacketNumber.get("" + msgID);
        if (number != null) {
            return number.shortValue();
        } else {
            return 0;
        }
    }


    /**
     * 清理小包号
     */
    public void clearSendPacketNumber() {
        if (mPacketNumber != null) {
            mPacketNumber.clear();
        }
    }


    //密钥
    private byte[] secretKey;


    /**
     * 用户登录返回解析
     *
     * @param data
     * @return
     */
    public void parseDeviceLogin(byte[] data) {
        ByteBuffer _data = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        _data.put(data, 0, data.length);
        short byte_5_6 = _data.getShort(0);//Session ID
        ByteBuffer bb = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(byte_5_6);
        // smartDevice.setSessionId(bb.array());
        this.sessionId = bb.array();
        byte[] bytes = new byte[data.length - 2];//Byte 7～Byte 22	临时密钥
        System.arraycopy(_data.array(), 2, bytes, 0, bytes.length);
//        smartDevice.setSecretKey(bytes);//密钥
        this.secretKey = bytes;
    }

    /**
     * 用户登录返回解析
     *
     * @param data
     * @return
     */
    public void parseDeviceLogin(byte[] data, int netWork) {

        OutPutMessage.LogCatInfo("登录回复", deviceMac + "   " + netWork);
        ByteBuffer _data = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        _data.put(data, 0, data.length);
        short byte_5_6 = _data.getShort(0);//Session ID
        ByteBuffer bb = ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(byte_5_6);
        this.sessionId = bb.array();
        this.netWorkMap.put(netWork, sessionId);
        byte[] bytes = new byte[data.length - 2];//Byte 7～Byte 22	临时密钥
        System.arraycopy(_data.array(), 2, bytes, 0, bytes.length);
        this.secretKey = bytes;
        if (smartDeviceState != SmartDeviceState.CONNECT_DEVICE) {
            OutPutMessage.LogCatInfo("登录回复", deviceMac + " !!@@@@@@@@@@@@@@@@@ " + netWork);
            setSmartDeviceStateManager(SmartDeviceState.CONNECT_DEVICE);//登录设备
        }
    }

    /**
     * 获取密钥
     *
     * @return
     */
    public byte[] getSecretKey() {
        return secretKey == null ? new byte[16] : secretKey;
    }

    /**
     * 恢复出厂
     *
     * @return
     */
    public void sendDeviceReset() {
        byte[] resetByte = new byte[1];
        resetByte[0] = 1;
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_RESTORE_DEFAULT, resetByte);
    }

    /**
     * 发送子设备列表
     *
     * @param index
     * @return
     */
    public void sendSubDeviceListInfo(byte index) {
        byte[] mByte = new byte[1];
        mByte[0] = index;
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CHILD_DEVICE_LIST, mByte);
    }

    /**
     * 解析子设备列表
     *
     * @param data
     * @return
     */
    public boolean parseSubDeviceList(byte[] data) {
        if (data == null) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        if (data.length == 0) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SmartDeviceManage smartDeviceManage = SmartDeviceManage.defaultManager();
        ByteBuffer bb = ByteBuffer.wrap(data).order(java.nio.ByteOrder.LITTLE_ENDIAN);
        byte index = bb.get(0);//子设备数量
        if (index == ((bb.array().length - 1) / 17)) {
//            Byte 14～Byte 17 设备类型
//            Byte 18～Byte 19 芯片型号
//            Byte 20～Byte 21 设备版本号
//             Byte22 低功耗设备
            //获取当前网关的子设备
            List<SmartDevice> childSmartDeviceList = smartDeviceManage.findGatewayChildDeviceList(deviceMac);
            for (int i = 0; i < childSmartDeviceList.size(); i++) {
                SmartDevice s = childSmartDeviceList.get(i);
                //如果是已连接状态，先重置为离线状态
                if (s.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                    s.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.DISCONNECT_DEVICE);
                }
            }
            for (int i = 0; i < index; i++) {
                int num = i * (8 + 4 + 2 + 2 + 1);
                long _byte_1_8 = bb.getLong(num + 1);
                int _byte_9_12 = bb.getInt(num + 9);//设备类型
                short _byte_13_14 = bb.getShort(num + 13);//芯片型号
                short _byte_15_16 = bb.getShort(num + 15);//设备版本号
                int _byte_17 = bb.get(num + 17);//低功耗设备
                byte[] typeByte = ByteUtil.intToByte(_byte_9_12);
                int one = typeByte[0];
                int isGateway = typeByte[1];
                int deviceType = typeByte[2];
                int deviceVesion = typeByte[3];
                String mac = ByteUtil.longToMacStr(_byte_1_8);
                //如果包含，说明本地存储过
                if (smartDeviceManage.getDeviceHashMap().containsKey(mac)) {
                    SmartDevice childDevice = smartDeviceManage.getDeviceHashMap().get(mac);
                    SmartDeviceLogic smartDeviceLogic = childDevice.getSmartDeviceLogic();
                    if (smartDeviceLogic != null) {
                        smartDeviceLogic.setGatewayMac(deviceMac);
                        smartDeviceLogic.setDeviceMac(mac);
                        UpdateData updateData = smartDeviceLogic.getUpdateData();
                        updateData.setDeviceChipType(_byte_13_14);
                        updateData.setAppVersionCPU(_byte_15_16);
                        updateData.setDeviceUpdateType(ByteUtil.deviceByteFourValidation(typeByte));
                        smartDeviceLogic.setDeviceIp(deviceIp);
                        smartDeviceLogic.setSecretKey(secretKey);
//                        smartDeviceLogic.setDevicePassword(PASS_WORD);
                        smartDeviceManage.addDevice(childDevice, Constant.NetWork.GATEWAY_NET);
                    }
                }
            }
        }
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 解析  报警信息
     *
     * @param data
     * @return
     */
    public boolean parseAlarmInfo(byte[] data) {
        boolean state = alarmData.parseAlarmData(data);
        OutPutMessage.LogCatInfo("告警", deviceMac + "解析后长度" + alarmData.getAlarmInfo().size());
        if (alarmData.getAlarmInfo().size() > 0) {
            smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.ALARM);//告警状态
        } else {
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.ALARM);
        }
        return state;

    }


    /**
     * 发送->请求升级,协议0x75
     *
     * @return
     */
    public boolean sendDeviceRequestUpgrade() {
        byte[] data = updateData.sendUpdateInfo();
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级发送 ：" + ByteUtil.byteArrayToHexString(data, true));
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_UPDATE_INFO, data);
        return true;
    }

    /**
     * 解析->请求升级,协议0x75
     *
     * @return
     */
    public boolean parseDeviceRequestUpgrade(byte[] data) {
        updateData.parseUpdateInfo(data);
        return true;
    }

    /**
     * 发送->升级数据,协议0x76
     *
     * @return
     */
    public boolean sendDeviceRequestData() {
        byte[] data = updateData.sendUpdateData();
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始sendDeviceRequestData:" + ByteUtil.byteArrayToHexString(data, true));
        sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_UPDATE_DATA, data);
        return true;
    }

    /**
     * 解析->升级数据,协议0x76
     *
     * @return
     */
    private boolean parseUpdateData(byte[] data) {
        if (data.length < 2) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        updateData.parserUpdateData(data);
        return true;
    }

    /**
     * 解析返回码
     */
    public int parseStateReturnCode(SmartHomeData smartHomeData) {
        int i = ByteUtil.deviceByteFourValidation(smartHomeData.data);
        OutPutMessage.LogCatInfo("回复连接数据", i + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        OutPutMessage.LogCatInfo("parseStateReturnCode", "回复解析:" + getDeviceMac() + "-->消息ID:" + Integer.toHexString(smartHomeData.msgID) + "!!!!-->数据：" + ByteUtil.byteArrayToHexString(smartHomeData.data, true));
        switch (i) {
            case 0:
                byte[] data = new byte[smartHomeData.data.length - 4];//数据长度，去掉头4消息值
                System.arraycopy(smartHomeData.data, 4, data, 0, data.length);//复制到新数组中，交给设备去处理
                OutPutMessage.LogCatInfo("parseStateReturnCode", ByteUtil.byteArrayToHexString(data, true));
                smartDevice.parseNetworkData(smartHomeData.isRead, data, smartHomeData.msgID);
                return SmartDeviceConstant.PARSE_CODE_OK;
            case 401:
                //TODO 如果切换状态机，则可能导致上一次NET状态丢失

                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 404://未找到子设备，如果以前广播接收过的话，去尝试连接一次

                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 406:

                return SmartDeviceConstant.PARSE_CODE_ERROR;

            case -1:
                OutPutMessage.showToast("系统错误,请求设备服务失败");
                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2001://设备未找到

                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2002://子设备不线
                this.returnCode = 2002;
                //OutPutMessage.showToast(StringUtil.getMacAfterFour(getDeviceMac()) + "，无法连接设备");
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.ADD_DEVICE_GATEWAY, null, smartDevice);
                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2003://设置数量达到上限

                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2004://设备连接数达到上限

                //  OutPutMessage.showToast(StringUtil.getMacAfterFour(getDeviceMac()) + ",当前设备连接数,已达到上限...");
                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2005://子设备管理数量达
                // OutPutMessage.showToast(StringUtil.getMacAfterFour(getDeviceMac()) + "子设备管理数量达到上限");
                this.returnCode = 2005;
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.ADD_DEVICE_GATEWAY, null, smartDevice);

                return SmartDeviceConstant.PARSE_CODE_ERROR;
            case 2006://未配对

                return SmartDeviceConstant.PARSE_CODE_ERROR;

            case 2011:

                TimeControl = false;

                return SmartDeviceConstant.PARSE_CODE_ERROR;

        }
        return SmartDeviceConstant.PARSE_CODE_NO_EXIST;
    }

    private int returnCode = 0;

    /**
     * 回复状态码
     *
     * @return
     */
    public int getStateReturnCode() {
        return returnCode;
    }

    /**
     * 解析设备推送
     */
    public boolean parseResponseNoAckData(SmartHomeData smartHomeData) {
        OutPutMessage.LogCatInfo("parseResponseNoAckData", "回复解析:" + getDeviceMac() + "-->消息ID:" + Integer.toHexString(smartHomeData.msgID) + "大包号" + smartHomeData.sequence + "小包号" + smartHomeData.dataSequ + "!!!!-->数据：" + smartHomeData.dataSequ);

        //同步指令不回复。。。因为不是设备推过来的
        if (smartHomeData.msgID != SmartDeviceConstant.MsgID.DEVICE_STATE_SYNC) {
            sendResponseData(smartHomeData.msgID);
        }
        OutPutMessage.LogCatInfo("parseResponseNoAckData", "回复解析:" + getDeviceMac() + "-->消息ID:" + Integer.toHexString(smartHomeData.msgID) + "!!!!-->数据：" + ByteUtil.byteArrayToHexString(smartHomeData.data, true));
        switch (smartHomeData.msgID) {
            case SmartDeviceConstant.MsgID.ADD_CHILD_DEVICE://0x78单独解析
                parseStateReturnCode(smartHomeData);
            default:
                smartDevice.parseNetworkData(smartHomeData.isRead, smartHomeData.data, smartHomeData.msgID);
                break;
        }
        return true;
    }

    /**
     * 功能描述：客户端应答
     *
     * @param msgID 消息id
     */
    private boolean sendResponseData(short msgID) {
        int code = 0;
        byte[] content = ByteBuffer.allocate(4).putInt(code).array();
        return sendControllerNoAckByte(msgID, content);
    }


    /**
     * 解析公共消息数据
     *
     * @param isRead 是否已读
     * @param data   数据体
     * @param msgID  消息ID
     */
    public void parsePublicNetWorkData(boolean isRead, byte[] data, int msgID) {
        OutPutMessage.LogCatInfo("设备数据接收", "设备MAC地址:" + getDeviceMac() + "!!! 消息ID" + Integer.toHexString(msgID) + "!! 数据内容:" + ByteUtil.byteArrayToHexString(data, true));
        switch (msgID) {
//            case SmartDeviceConstant.MsgID.DEVICE_LOGIN://登录设备认证
//                parseDeviceLogin(data);
//                break;
            case SmartDeviceConstant.MsgID.DEVICE_CONTROL_RESTART://重启设备
                OutPutMessage.showToast(deviceName + ",设备重启成功,请稍后连接");
                break;
            case SmartDeviceConstant.MsgID.DEVICE_STATE_SYNC://运行状态同步
                if (smartDevice.parseDeviceStatus(data)) {
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICES_INFO_SYNCHRONOUS, null, smartDevice);
                }
                break;
            case SmartDeviceConstant.MsgID.DEVICE_PARAMETER_SET://参数读取
                if (isRead) {
                    if (smartDevice.parseDeviceParameter(data)) {
                        NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICES_PARAMETER_SYNCHRONOUS, null, smartDevice);
                    }
                }
                break;

            case SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE:
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICES_INFO_SYNCHRONOUS, null, smartDevice);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_FAULT_STATE://报警状态查询
            case SmartDeviceConstant.MsgID.DEVICE_ALARM_SYNC://报警状态同步
                if (parseAlarmInfo(data)) {
                    OutPutMessage.LogCatInfo("告警", deviceMac + "通知刷新" + alarmData.getAlarmInfo().size());
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_MESSAGE_ALARM_INFO, null, smartDevice);
                }
                break;
            case SmartDeviceConstant.MsgID.DEVICE_FAULT_SYNC: // 设备故障信息同步
                OutPutMessage.LogCatInfo("设备故障信息同步", "data：" + ByteUtil.byteArrayToHexString(data));
                if (data.length == 2) {
                    setFaultState(ByteUtil.bytesToshort(data));
                    OutPutMessage.LogCatInfo("设备故障信息同步", "faultState：" + ByteUtil.bytesToshort(data) + "");
                } else {
                    setFaultState(0);
                }
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_FAULT_SYNC, null, smartDevice);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_RESTORE_DEFAULT://恢复出厂设置
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.ACTIVITY_RESTORE_DEFAULT, null, smartDevice);
                break;

            case SmartDeviceConstant.MsgID.DEVICE_CONTROL_HEARTBEAT://心跳
                parseByteHeartbeat(data);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_RUN_STATE://运行状态查询
                if (smartDevice.parseDeviceStatus(data)) {
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICES_INFO_SYNCHRONOUS, null, smartDevice);
                }
                break;
            case SmartDeviceConstant.MsgID.DEVICE_CHILD_DEVICE_LIST://获取子设备列表
                parseSubDeviceList(data);
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_CHILD_LINE://子设备上、下线通知
                OutPutMessage.LogCatInfo("sendSubDeviceListInfo", "发送子设备请求---->主动请求调用");
                sendSubDeviceListInfo((byte) 0);
                break;
            case SmartDeviceConstant.MsgID.ADD_CHILD_DEVICE://添加设备成功
                if (isRead) {//添加子设备主动推送
                    this.returnCode = 0;
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.ADD_DEVICE_GATEWAY, null, smartDevice);
                }
                break;
            case SmartDeviceConstant.MsgID.DEVICE_UPDATE_INFO: //升级请求，返回4个0
                parseDeviceRequestUpgrade(data);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_UPDATE_DATA://升级请求，返回块号
                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "接收DEVICE_UPDATE_DATA:" + ByteUtil.byteArrayToHexString(data, true));
                parseUpdateData(data);
                break;
        }
    }


    /**
     * 心跳回应解析
     *
     * @return
     */
    public boolean parseByteHeartbeat(byte[] data) {
        if (data.length < 5) {
            //根据协议，如果心跳回应长度小于5失败
            isReceive = true;
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        isReceive = false;
        byte[] secondByte = new byte[4];
        System.arraycopy(data, 0, secondByte, 0, secondByte.length);
        //设备无间断工作时间（秒）
        int second = ByteUtil.bytesToInt(secondByte);
        //信号强度
        byte RSSIByte = data[4];
        if (smartDeviceHeartbeatThread != null) {
            smartDeviceHeartbeatThread.setResendTimes(0);
        }
        return SmartDeviceConstant.PARSE_OK;

    }

    /**
     * 开启心跳
     */
    public void startHeartbeatThread() {
        if (smartDeviceHeartbeatThread == null) {
            smartDeviceHeartbeatThread = new SmartDeviceHeartbeatThread(smartDevice);
            smartDeviceHeartbeatThread.start();
            smartDeviceHeartbeatThread.setName("HEART");
        }
    }

    /**
     * 断开心跳
     */
    public void stopHeartbeatThread() {
        if (smartDeviceHeartbeatThread != null) {
            smartDeviceHeartbeatThread.interrupt();//关闭线程
            smartDeviceHeartbeatThread = null;
            OutPutMessage.LogCatInfo(SmartDeviceHeartbeatThread.TAG, "设备名称：" + getDeviceMac() + "!!断开心跳");
        }
    }

    /**
     * 暂停心跳
     */
    public void pauseHeartbeatThread() {
        if (smartDeviceHeartbeatThread != null) {
            smartDeviceHeartbeatThread.setPause(true);
        }
    }

    /**
     * 继续心跳
     */
    public void continueHeartbeatThread() {
        if (smartDeviceHeartbeatThread != null) {
            smartDeviceHeartbeatThread.setPause(false);
        }
    }


    /**
     * 发送数据
     *
     * @param msgID    协议ID
     * @param data     数据内容
     * @param isRead   是否写数据
     * @param isResend 是否重发数据
     * @param isAck    是否为应答帧
     * @return
     */
    public final boolean sendControllerByte(short msgID, byte[] data, boolean isRead, boolean isResend, boolean isAck) {
        setSendPacketNumber(msgID);
        DeviceSendHeadData deviceSendHeadData = new DeviceSendHeadData(smartDevice);
        deviceSendHeadData.sendHeadData(data, isRead, isAck, msgID);//组合数据，赋值给原有byte，发送出去
        if (this.isNetWork(Constant.NetWork.WAN_NET_PROXY) && msgID != 3 && msgID != 0x75 && msgID != 0x76) {
            NetworkMessage networkMessage = new NetworkMessage(true, deviceSendHeadData.getHeadData(), deviceSendHeadData.getMac(),
                    deviceSendHeadData.getNetWork(), deviceSendHeadData.getDeviceIP());
            Controller.defaultController().sendCode(networkMessage);//发送消息

            //是否重发
            if (isResend) {
                ResendQueueManage.defaultQueue().add(deviceSendHeadData, 3);
            }
            return true;
        }
        OutPutMessage.LogCatInfo("发送升级","发送升级---》" + ByteUtil.byteToHex((byte) msgID));
        return smartDeviceState.deviceSendData(deviceSendHeadData, isResend);
    }


    /**
     * 设置设备状态机
     *
     * @param smartDeviceStateManager 状态机参数
     */
    public void setSmartDeviceStateManager(SmartDeviceState smartDeviceStateManager) {
        this.smartDeviceState.deviceStateExit(smartDevice);//先退出以前状态
        this.smartDeviceState = smartDeviceStateManager;//更新
        this.smartDeviceState.deviceStateEntrance(smartDevice);//并登录
    }


    /**
     * 发送数据
     *
     * @param msgID    协议ID
     * @param mac      设备mac
     * @param isRead   是否写数据
     * @param isResend 是否重发数据
     * @param isAck    是否为应答帧
     * @return
     */
    public final boolean sendControllerByte(short msgID, long mac, boolean isRead, boolean isResend, boolean isAck) {

            setSendPacketNumber(msgID);//写入小号
            DeviceSendHeadData deviceSendHeadData = new DeviceSendHeadData(smartDevice);
            deviceSendHeadData.sendHeadData(mac, isRead, isAck, msgID);//组合数据，赋值给原有byte，发送出去
            return smartDeviceState.deviceSendData(deviceSendHeadData, isResend);

    }

    /**
     * 发送数据,默认重发和写数据
     *
     * @param msgID 协议ID
     * @param data  数据内容
     * @return
     */
    public final boolean sendControllerByte(short msgID, byte[] data) {
        return sendControllerByte(msgID, data, SmartDeviceConstant.OperateState.No_Read,
                SmartDeviceConstant.OperateState.Resend, SmartDeviceConstant.OperateState.No_ACk);
    }

    /**
     * 发送数据,默认重发和写数据
     *
     * @param msgID 协议ID
     * @return
     */
    public final boolean sendControllerByte(short msgID, long mac, boolean isRead) {
        return sendControllerByte(msgID, mac, isRead,
                SmartDeviceConstant.OperateState.Resend, SmartDeviceConstant.OperateState.No_ACk);
    }

    /**
     * 发送数据,消息推送应答
     *
     * @param msgID 协议ID
     * @param data  数据内容
     * @return
     */
    public final boolean sendControllerNoAckByte(short msgID, byte[] data) {
        return sendControllerByte(msgID, data, SmartDeviceConstant.OperateState.No_Read,
                SmartDeviceConstant.OperateState.No_Resend, SmartDeviceConstant.OperateState.ACk);
    }

    /**
     * 发送数据,默认重发
     *
     * @param msgID  协议ID
     * @param data   数据内容
     * @param isRead 是否写数据
     * @return
     */
    public final boolean sendControllerByte(short msgID, byte[] data, boolean isRead) {
        return sendControllerByte(msgID, data, isRead,
                SmartDeviceConstant.OperateState.Resend, SmartDeviceConstant.OperateState.No_ACk);
    }

    public boolean isSendInfo() {
        return isSendInfo;
    }

    public void setSendInfo(boolean sendInfo) {
        isSendInfo = sendInfo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        if (ipPort != null) {
            String[] verip = ipPort.split("\\|");
            OutPutMessage.LogCatInfo("ipPort分割登陆", "长度" + verip.length + "   " + ipPort);

            if (verip.length == 2) {
                this.deviceVer = verip[0];
                this.ipPort = verip[1];
            } else {
                this.ipPort = ipPort;
            }
        } else {
            this.ipPort = ipPort;
        }

        OutPutMessage.LogCatInfo("ipPort分割登陆==", "   deviceVer:" + deviceVer + "   ipPort:" + ipPort);
    }

    public String getDeviceVer() {
        return deviceVer;
    }

    public void setDeviceVer(String deviceVer) {
        this.deviceVer = deviceVer;
    }

    public String getDeviceNewAppVer() {
        return deviceNewAppVer;
    }

    public void setDeviceNewAppVer(String deviceNewAppVer) {
        this.deviceNewAppVer = deviceNewAppVer;
    }

    public short getDeviceChipType() {
        return deviceChipType;
    }

    public void setDeviceChipType(short deviceChipType) {
        this.deviceChipType = deviceChipType;
    }

    public byte[] getSessionId() {
        return sessionId;
    }

    public void setSessionId(byte[] sessionId) {
        this.sessionId = sessionId;
    }

    public Map<Integer, byte[]> getNetWorkMap() {
        return netWorkMap;
    }

    public void setNetWorkMap(Map<Integer, byte[]> netWorkMap) {
        this.netWorkMap = netWorkMap;
    }

    public HashMap<String, Integer> getmPacketNumber() {
        return mPacketNumber;
    }

    public void setmPacketNumber(HashMap<String, Integer> mPacketNumber) {
        this.mPacketNumber = mPacketNumber;
    }

    public HashMap<String, Integer> getmReceivePacketNumber() {
        return mReceivePacketNumber;
    }

    public void setmReceivePacketNumber(HashMap<String, Integer> mReceivePacketNumber) {
        this.mReceivePacketNumber = mReceivePacketNumber;
    }

    public void setSecretKey(byte[] secretKey) {
        this.secretKey = secretKey;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getDeviceTypeId() {
        return deviceTypeId;
    }

    public void setDeviceTypeId(int deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public long getSrcID() {
        return srcID;
    }

    public void setSrcID(long srcID) {
        this.srcID = srcID;
    }

    public boolean isReceive() {
        return isReceive;
    }

    public void setReceive(boolean receive) {
        isReceive = receive;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public String getGatewayMac() {
        return gatewayMac;
    }

    public void setGatewayMac(String gatewayMac) {
        this.gatewayMac = gatewayMac;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getNetMode() {
        return netMode;
    }

    public void setNetMode(int netMode) {
        this.netMode = netMode;
    }

    public String getOwnerUserID() {
        return ownerUserID == null ? "" : ownerUserID;
    }

    public void setOwnerUserID(String ownerUserID) {
        this.ownerUserID = ownerUserID;
    }

    public String getDeviceWanAddr() {
        return deviceWanAddr;
    }

    public void setDeviceWanAddr(String deviceWanAddr) {
        this.deviceWanAddr = deviceWanAddr;
    }

    public boolean isSubDeviceList() {
        return isSubDeviceList = communicationType == SmartDeviceConstant.CommunicationType.ACTIVE_DEVICE ? true : false;
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

    public int getGetDefenseSupport() {
        return getDefenseSupport;
    }

    public void setGetDefenseSupport(int getDefenseSupport) {
        this.getDefenseSupport = getDefenseSupport;
    }

    public int getIsTwoLinkage() {
        return isTwoLinkage;
    }

    public void setIsTwoLinkage(int isTwoLinkage) {
        this.isTwoLinkage = isTwoLinkage;
    }

    public int getIsfirstLogin() {
        return isfirstLogin;
    }

    public void setIsfirstLogin(int isfirstLogin) {
        this.isfirstLogin = isfirstLogin;
    }

    public UpdateData getUpdateData() {
        return updateData;
    }

    public boolean isSQLExist() {
        return isSQLExist;
    }

    public void setSQLExist(boolean SQLExist) {
        this.isSQLExist = SQLExist;
    }

    public void setSortState(int sortState) {
        this.sortState = sortState;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDoorState() {
        return doorState;
    }

    public void setDoorState(int doorState) {
        this.doorState = doorState;
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

    public void setElectricReport(int electricReport) {
        this.electricReport = electricReport;
    }

    public int getOrginVersion() {
        return orginVersion;
    }

    public void setOrginVersion(int orginVersion) {
        this.orginVersion = orginVersion;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
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

    @Override
    public String toString() {
        return "SmartDeviceLogic{" +
                ", communicationType=" + communicationType +
                ", isVisible=" + isVisible +
                ", deviceType=" + deviceType +
                ", state=" + state +
                ", isSQLExist=" + isSQLExist +
                ", deviceMac=" + deviceMac +
                ", deviceNetWork=" + deviceNetWork +
                ", isSubDeviceList=" + isSubDeviceList +
                '}';
    }
}
