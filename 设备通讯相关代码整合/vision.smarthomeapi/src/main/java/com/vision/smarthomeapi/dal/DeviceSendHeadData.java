package com.vision.smarthomeapi.dal;

import com.smarthome.head.SmartHomeConstant;
import com.smarthome.head.SmartHomeData;
import com.smarthome.head.SmartHomeHead;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.data.SmartDeviceLogic;
import com.vision.smarthomeapi.dal.data.SmartDeviceState;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PhoneUtil;
import com.vision.smarthomeapi.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 设备添加数据头
 */
public class DeviceSendHeadData {
    //2-密证，
    private final String TAG = "发送数据";


    private String deviceIP;

    private SmartDevice smartDevice;

    private int netWork;

    private String mac;

    private byte[] headData;
    //数据格式
    private byte dataFormat;
    //时间，秒级数值
    private long time;

    private long srcID;

    private boolean isRead;
    //消息ID
    private short msgID;
    //1为true 数据的最后一包 data的长度/1024
    private boolean isFin;
    //true为应答帧 false不是应答帧
    private boolean isAck;
    //会话ID，登录设备后由设备返回
    private byte[] sessionId;

    private byte opcode;
    //设备类型
    private short devType;
    //目标设备mac
    private long dstID = 0;

    private long wsDstID = 0;
    //大包序号
    private int result;
//    //设备密码
//    private String passWord;
    //子设备mac
    private String childDeviceMac;
    //是否添加子设备
    private boolean isChildDevice;


    /**
     * 发送数据时使用此构造方法
     *
     * @param smartDevice
     */
    public DeviceSendHeadData(SmartDevice smartDevice) {
        this(smartDevice, false, null);
    }

    /**
     * 添加子设备
     *
     * @param smartDevice    网关设备
     * @param isChildDevice  是否为子设备
     * @param childDeviceMac 子设备mac
     */
    public DeviceSendHeadData(SmartDevice smartDevice, boolean isChildDevice, String childDeviceMac) {
        this.smartDevice = smartDevice;
        this.deviceIP = smartDevice.getSmartDeviceLogic().getDeviceIp();
        if (isChildDevice && childDeviceMac != null) {
            this.childDeviceMac = childDeviceMac;
            this.isChildDevice = isChildDevice;
        }
        this.mac = smartDevice.getSmartDeviceLogic().getDeviceMac();
    }

    public DeviceSendHeadData(String deviceIP) {
        this.deviceIP = deviceIP;
    }

    /**
     * 添加参数
     *
     * @param _msgID
     * @param _isRead
     * @param _isAck
     */
    private void fillParameter(short _msgID, boolean _isRead, boolean _isAck) {

        this.isFin = true;

        this.isRead = _isRead;

        this.isAck = _isAck;

        //设备消息ID
        msgID = _msgID;


        this.time = System.currentTimeMillis() / 1000;

        this.srcID = getSrcID();
    }

    /**
     * 发送广播数据
     *
     * @return
     */
    public byte[] sendLanRetrieveHeadData() {

        short msgID = SmartDeviceConstant.MsgID.LAN_RETRIEVE;

        fillParameter(msgID, false, false);

        this.dstID = 0xFFFFFFFF;

        this.headData = addHead(Constant.NetWork.LAN_NET, null);

        return headData;
    }

    /**
     * 发送广播数据
     *
     * @return
     */
    public byte[] sendLanRetrieveHeadData(byte[] deviceMac) {

        short msgID = SmartDeviceConstant.MsgID.LAN_RETRIEVE;

        fillParameter(msgID, false, false);

        ByteBuffer bb = ByteBuffer.wrap(deviceMac).order(ByteOrder.LITTLE_ENDIAN);
        this.dstID = bb.getLong(0);

        this.headData = addHead(Constant.NetWork.LAN_NET, null);

        return headData;
    }



    private void fillDeviceParameter(SmartDevice device) {


        this.devType = (short) device.getSmartDeviceLogic().getDeviceType();

//        this.passWord = device.getSmartDeviceLogic().getDevicePassword();

//        if (isAck) {//如果应答帧，使用发过来的大包号
//
//        } else {//使用自增包号
//
//        }
        if (isAck) {//如果应答帧，使用发过来的大包号
            this.result = device.getSmartDeviceLogic().getReceiveSequence();
            this.dadaSeuq = device.getSmartDeviceLogic().getReceivePacketNumber(msgID);
            OutPutMessage.LogCatInfo("包序号打印", "result应答推送：" +  mac + "小包号  "+dadaSeuq+"!!! 大包序号应答: " + result   + "!!!消息ID：" + Integer.toHexString(msgID));
        } else {//使用自增包号
            device.getSmartDeviceLogic().addBigPacketNumber();//网关大包序号+1；
            this.result = device.getSmartDeviceLogic().getSequence();
            this.dadaSeuq = device.getSmartDeviceLogic().getSendPacketNumber(msgID);
            OutPutMessage.LogCatInfo("包序号打印", "result发送控制：" +  mac +"小包号  "+dadaSeuq +"!!! 大包序号发送: " + result   + "!!!消息ID：" + Integer.toHexString(msgID));
        }

     //   device.getSmartDeviceLogic().addBigPacketNumber();//网关大包序号+1；
      //  this.result = device.getSmartDeviceLogic().getSequence();

     //   this.dadaSeuq = device.getSmartDeviceLogic().getSendPacketNumber(msgID);

        this.opcode = getOpcode(device);
        OutPutMessage.LogCatInfo(TAG, "OPCODE：" + opcode);
        byte[] deviceMac = null;
        if (isChildDevice) {//如果获取到是子设备，则目标ID填写子设备mac去请求数据
            deviceMac = ByteUtil.hexStr2Byte(childDeviceMac);
        } else if (!smartDevice.getSmartDeviceLogic().getDeviceMac().equals("")) {
            deviceMac = ByteUtil.hexStr2Byte(smartDevice.getSmartDeviceLogic().getDeviceMac());
        }
        ByteBuffer bb = ByteBuffer.wrap(deviceMac).order(ByteOrder.LITTLE_ENDIAN);
        this.dstID = bb.getLong(0);



        this.srcID = device.getSmartDeviceLogic().getSrcID();
    }

    /**
     * 组装设备协议包头
     *
     * @return
     */
    public byte[] sendHeadData(byte[] _data, boolean _isRead, boolean _isAck, short _msgID) {


        //添加固定参数
        fillParameter(_msgID, _isRead, _isAck);


        //添加设备信息
        SmartDevice device = findDeviceInfo();
        OutPutMessage.LogCatInfo(" findDeviceInfo", "---->设备的网络标记 sendHeadData");
        if (device != null) {

            fillDeviceParameter(device);
            //数据体
            this.data = _data;



            this.headData = addHead(netWork, device);
        }

        return headData;
    }


    /**
     * 组装设备协议包头
     *
     * @return
     */
    public byte[] sendHeadData(long mac, boolean _isRead, boolean _isAck, short _msgID) {
        //添加固定参数
        fillParameter(_msgID, _isRead, _isAck);
        //添加设备信息
        SmartDevice device = findDeviceInfo();

        if (device != null) {

            fillDeviceParameter(device);

            this.dstID = mac;

            this.headData = addHead(netWork, device);
        }

        return headData;
    }

    /**
     * 发送代理包数据
     *
     * @return
     */
    public byte[] sendWanHeadData(byte[] data) {

        short msgID = SmartDeviceConstant.MsgID.WAN_ONLINE;

        fillParameter(msgID, false, false);

        ByteBuffer bb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        this.data = bb.array();

        this.opcode = SmartHomeConstant.OPCode.WAN_PLATFORM;

        this.headData = addHead(Constant.NetWork.WAN_NET, null);

        return headData;
    }

    /**
     * 添加头数据
     *
     * @param net
     * @return
     */
    private byte[] addHead(int net, SmartDevice smartDevice) {
        SmartHomeData smartHoneData = new SmartHomeData();
        smartHoneData.isFin = isFin;
        smartHoneData.isRead = isRead;
        smartHoneData.isACK = isAck;
        smartHoneData.dataFormat = SmartHomeConstant.BINARY;
        if (smartDevice != null && smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
            smartHoneData.keyLevel = SmartHomeConstant.Key.LOGIN_SECRET_KEY;
        } else {
            smartHoneData.keyLevel = SmartHomeConstant.Key.RANDOM_SECRET_KEY;
        }
//        if (smartDevice != null && smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
//            smartHoneData.keyLevel = SmartHomeData.Key.NO_SECRET_KEY;
//        } else {
//           smartHoneData.keyLevel = SmartHomeData.Key.NO_SECRET_KEY;
//        }

        smartHoneData.encryptType = SmartHomeConstant.Encrypt.ENCRYPT_TYPE_AES;
        smartHoneData.opcode = opcode;
        smartHoneData.msgID = msgID;
        smartHoneData.dataSequ = dadaSeuq;

        if (smartHoneData.opcode == 2 || smartHoneData.opcode == 5) {
            smartHoneData.sessionId = ByteBuffer.allocate(8).array();
        } else {
            smartHoneData.sessionId = this.sessionId;
            if(smartHoneData.sessionId == null || smartHoneData.sessionId .length != 2) {

                OutPutMessage.LogCatInfo("sessionId错误","opcode:"+opcode+"sessionId:"+ByteUtil.byteArrayToHexString(smartHoneData.sessionId));
                smartHoneData.sessionId = ByteBuffer.allocate(2).array();
            }
        }
        smartHoneData.sequence = result;
        smartHoneData.time = time;
        smartHoneData.dstID = dstID;
        smartHoneData.wsDstID = wsDstID;

        smartHoneData.srcID = srcID;
        smartHoneData.data = data;
        smartHoneData.code = 0;
        smartHoneData.datID = 0;
        OutPutMessage.LogCatInfo("发送数据", "数据是否加密：" + ByteUtil.byteArrayToHexString(smartHoneData.data, true));
        byte[] sendData = null;
        OutPutMessage.LogCatInfo("发送数据", "网络类型--》" + net);

        OutPutMessage.LogCatInfo("发送数据", "opcode--》" + smartHoneData.opcode+" sessionId---->  "+ByteUtil.byteArrayToHexString(smartHoneData.sessionId, true));

        OutPutMessage.LogCatInfo("添加头数据","消息ID:"+smartHoneData.msgID+"    网络类型:"+net);
        OutPutMessage.LogCatInfo("添加头数据","smartDevice:"+smartDevice);

        byte[] devicePW = SmartDeviceLogic.PASS_WORD;
        if (smartDevice != null){
            OutPutMessage.LogCatInfo("添加头数据","是否是分享设备:"+(smartDevice.getSmartDeviceLogic().getShareType() == 1));
            if (smartDevice.getSmartDeviceLogic().getShareType() == 1){


                devicePW = ByteUtil.hexStr2Byte(smartDevice.getSmartDeviceLogic().getCtlpwd());
                OutPutMessage.LogCatInfo("添加头数据","设备自己的密钥:"+ByteUtil.byteArrayToHexString(devicePW));
            }
        }

        OutPutMessage.LogCatInfo("添加头数据","最终密钥:"+ByteUtil.byteArrayToHexString(devicePW));



        switch (net) {
            case Constant.NetWork.LAN_NET:
                netWork = Constant.NetWork.LAN_NET;
                if (smartDevice != null && smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                    OutPutMessage.LogCatInfo(TAG, "发送局域网数据密钥：" + ByteUtil.byteArrayToHexString(devicePW));
                    //OutPutMessage.LogCatInfo("IDeviceGateway", "子设备源ID--11111111111111-->" + ByteUtil.longToMacStr(dstID));
                    OutPutMessage.LogCatInfo(TAG, "sessionId:Lan--->" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "密钥长度：" + ByteUtil.byteArrayToHexString(sessionId, true));
                    //byte[] bb = smartDevice.getSecretKey().clone();
                    sendData = SmartHomeHead.addHead(smartHoneData, devicePW, smartDevice.getSmartDeviceLogic().getSecretKey());
                    OutPutMessage.LogCatInfo(TAG, "MAC:" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "密钥长度：" + ByteUtil.byteArrayToHexString(smartDevice.getSmartDeviceLogic().getSecretKey(), true));

                } else {
                    if(smartDevice != null) {
                        OutPutMessage.LogCatInfo("局域网标记", "LAN_NET" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!!!!");
                    }
                    OutPutMessage.LogCatInfo(TAG, "发送局域网数据密钥：" + ByteUtil.byteArrayToHexString(devicePW));
                    OutPutMessage.LogCatInfo(TAG, "无密钥数据：" + ByteUtil.byteArrayToHexString(SmartDeviceManage.defaultManager().noSecretKey(), true));
                    sendData = SmartHomeHead.addHead(smartHoneData, devicePW, SmartDeviceManage.defaultManager().noSecretKey());
                }
                break;
            case Constant.NetWork.WAN_NET:
                netWork = Constant.NetWork.WAN_NET;
                if(opcode == SmartHomeConstant.OPCode.WAN_PROXY){
                    smartHoneData.keyLevel = SmartHomeConstant.Key.LOGIN_SECRET_KEY;
                    smartHoneData.dstID = wsDstID;
                    sendData = SmartHomeHead.addHead(smartHoneData, SecurityUserManage.pwEncrypt, SecurityUserManage.tmpEncrypt);
                }else{
                    if (smartDevice != null && smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                        OutPutMessage.LogCatInfo(TAG, "发送广域网数据--->" + smartDevice.getSmartDeviceLogic().getDeviceMac());
                        OutPutMessage.LogCatInfo(TAG, "发送广域网数据密钥：" + ByteUtil.byteArrayToHexString(devicePW));
                        OutPutMessage.LogCatInfo(TAG, "发送广域网数据：" + ByteUtil.byteArrayToHexString(smartDevice.getSmartDeviceLogic().getSecretKey()));
                        OutPutMessage.LogCatInfo(TAG, "发送广域网数据：" + smartHoneData.toString());
                        sendData = SmartHomeHead.addHead(smartHoneData, devicePW, smartDevice.getSmartDeviceLogic().getSecretKey());
                    } else {

                        sendData = SmartHomeHead.addHead(smartHoneData, devicePW, SmartDeviceManage.defaultManager().noSecretKey());
                        OutPutMessage.LogCatInfo(TAG, "==发送广域网数据--->" + sendData.length);
                    }

                }

                break;
            case Constant.NetWork.ZIG_BEE_NET:
                netWork = Constant.NetWork.ZIG_BEE_NET;
                OutPutMessage.LogCatInfo(TAG, "发送ZigBee数据密钥：" + ByteUtil.byteArrayToHexString(devicePW));
                OutPutMessage.LogCatInfo("ZigBee发送数据", "sessionId:ZigBee--->" + ByteUtil.byteArrayToHexString(sessionId, true));
                OutPutMessage.LogCatInfo("ZigBee发送数据", "sessionId:ZigBee--->" + smartDevice.getSmartDeviceLogic().getSrcID());
                if (smartDevice != null && smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                    OutPutMessage.LogCatInfo("ZigBee发送数据", "数据长度" + "----------------------->" + ByteUtil.byteArrayToHexString(smartDevice.getSmartDeviceLogic().getSecretKey(), true));
                    sendData = SmartHomeHead.addZigbeeHead(smartHoneData, devicePW, smartDevice.getSmartDeviceLogic().getSecretKey());
                } else {
                    sendData = SmartHomeHead.addZigbeeHead(smartHoneData, devicePW, SmartDeviceManage.defaultManager().noSecretKey());
                }
                break;
        }

        return sendData;
    }


    /**
     * 设备信息
     *
     * @return 设备或者网关
     */
    private SmartDevice findDeviceInfo() {

        if (smartDevice == null) {
            return null;
        }
        SmartDevice device = smartDevice;


        OutPutMessage.LogCatInfo(" findDeviceInfo", "---->设备的网络标记 findDeviceInfo" + smartDevice);
//        OutPutMessage.LogCatInfo("心跳下线协议==","添加设备信息："+device.getSmartDeviceLogic().getDeviceMac());
        if (device != null) {
            OutPutMessage.LogCatInfo(" findDeviceInfo", "---->设备的网络标记 findDeviceInfo" + device.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET_PROXY));
            switch (msgID) {
                case SmartDeviceConstant.MsgID.DEVICE_UPDATE_INFO:
                case SmartDeviceConstant.MsgID.DEVICE_UPDATE_DATA:
                    if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {//网关子设备，获取网关状态发出
                        if (smartDevice.getSmartDeviceLogic().getGatewayMac() != null) {
                            SmartDevice gatewayDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(smartDevice.getSmartDeviceLogic().getGatewayMac());
                            device = gatewayDevice;
                        }
                    }
                    OutPutMessage.LogCatInfo("网络标记", "---->设备的网络标记" + device.getSmartDeviceLogic().getDeviceNetWork());
                    //TODO 此处修改网络连接顺序

                    if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                        this.netWork = Constant.NetWork.LAN_NET;

                        if (device.getSmartDeviceLogic().getDeviceVer().equals("1")) {
                            this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.WAN_NET);
                        }else if (device.getSmartDeviceLogic().getDeviceVer().equals("2")){

                            this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.LAN_NET);

//                            OutPutMessage.LogCatInfo("网络标记", "升级==" + device.getSmartDeviceLogic().getDeviceMac() + "@@@" + ByteUtil.byteArrayToHexString(th));
                        }

                        OutPutMessage.LogCatInfo("网络标记", "---->设备的网络标记" + device.getSmartDeviceLogic().getDeviceNetWork());
                    } else if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {//TODO zigBEE降低到最后一级别
                        this.netWork = Constant.NetWork.ZIG_BEE_NET;
                        this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.ZIG_BEE_NET);
                    }
                    break;

                case SmartDeviceConstant.MsgID.DEVICE_NOT_ONLINE:


                    this.netWork = Constant.NetWork.WAN_NET;
                    this.sessionId = new byte[8];
                    for (int i = 0; i<sessionId.length;i++){
                        sessionId[i] = 0;
                    }

                    OutPutMessage.LogCatInfo("心跳下线协议==",netWork+"   "+ByteUtil.byteArrayToHexString(sessionId));
                    break;
                default:
                    OutPutMessage.LogCatInfo(" findDeviceInfo", "---->设备的网络标记 findDeviceInfo" + device.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET_PROXY));
                    //TODO 此处修改网络连接顺序
                    if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET_PROXY) && msgID != 0x03) {
                        if (smartDevice.getSmartDeviceLogic().getDeviceId() != null) {
                            this.wsDstID = Long.valueOf(device.getSmartDeviceLogic().getDeviceId());

                            OutPutMessage.LogCatInfo("广域网ID", this.wsDstID + "!!!!" + smartDevice.getSmartDeviceLogic().getDeviceMac());
                        }
                        this.netWork = Constant.NetWork.WAN_NET;
                        this.sessionId = SecurityUserManage.pwEncrypt;
                    }else if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
                        if (smartDevice.getSmartDeviceLogic().getDeviceId() != null) {
                            this.wsDstID = Long.valueOf(device.getSmartDeviceLogic().getDeviceId());
                            OutPutMessage.LogCatInfo("广域网ID", this.wsDstID + "!!!!" + smartDevice.getSmartDeviceLogic().getDeviceMac());
                        }
                        this.netWork = Constant.NetWork.WAN_NET;
                        this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.WAN_NET);
                    } else if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                        this.netWork = Constant.NetWork.LAN_NET;
                        OutPutMessage.LogCatInfo("局域网标记", "LAN_NET" + device.getSmartDeviceLogic().getDeviceMac());
                        this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.LAN_NET);
                    } else if (device.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {//TODO zigBEE降低到最后一级别
                        this.netWork = Constant.NetWork.ZIG_BEE_NET;
                        this.sessionId = device.getSmartDeviceLogic().getNetWorkMap().get(Constant.NetWork.ZIG_BEE_NET);
                    }
                    break;
            }
            this.deviceIP = device.getSmartDeviceLogic().getDeviceIp();
        }
        return device;
    }

    /**
     * 获取源ID
     *
     * @return 源ID
     */
    private long getSrcID() {
        //手机mac
        if (!StringUtil.getUserID().equals("")) {
            String srcId = StringUtil.getUserID();
            long userID = Long.parseLong(srcId, 10);
            return userID;
        } else {
            String src = PhoneUtil.getMacAddress(Controller.getContext());
            long srcID = Long.parseLong(src, 16);
            return srcID;
        }
    }

    /**
     * 获取操作码
     *
     * @return 操作码
     */
    private byte getOpcode(SmartDevice smartDevice) {
        //设备本身记录一次，因为回复数据时会根据源ID判断数据是否有效
        switch (msgID) {
            case SmartDeviceConstant.MsgID.DEVICE_UPDATE_INFO:

            case SmartDeviceConstant.MsgID.DEVICE_UPDATE_DATA:
                OutPutMessage.LogCatInfo("网络标记", "---->设备的网络标记" + smartDevice.getSmartDeviceLogic().getDeviceNetWork());
                //TODO 此处修改网络连接顺序
                if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                    smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                    return 0;
                } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                    String src = NetworkManager.defaultNetworkManager().zigBeeSrc();
                    long srcID = ByteUtil.macToLong(src);
                    smartDevice.getSmartDeviceLogic().setSrcID(srcID);
                    return 4;
                }
                break;
            case SmartDeviceConstant.MsgID.DEVICE_NOT_ONLINE:

                OutPutMessage.LogCatInfo("心跳下线协议",smartDevice.getSmartDeviceLogic().getDeviceNetWork()+"是不是广域网"+smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET));

//                if (true) {
                    smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                    return 2;
//                }
//                break;
            default:
                //TODO 此处修改网络连接顺序

                OutPutMessage.LogCatInfo(TAG, "get  OPCODE：deviceVer   :  " + smartDevice.getSmartDeviceLogic().getDeviceVer());
                if (smartDevice.getSmartDeviceLogic().getDeviceVer().equals("2")) {
                    if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET_PROXY) && msgID != 0x03) {
                        smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                        return 5;
                    } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                        smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                        return 0;
                    } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                        String src = NetworkManager.defaultNetworkManager().zigBeeSrc();
                        long srcID = ByteUtil.macToLong(src);
                        smartDevice.getSmartDeviceLogic().setSrcID(srcID);
                        return 4;
                    }
                }else{
                    if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
                        smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                        return 3;
                    } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                        smartDevice.getSmartDeviceLogic().setSrcID(getSrcID());
                        return 0;
                    } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                        String src = NetworkManager.defaultNetworkManager().zigBeeSrc();
                        long srcID = ByteUtil.macToLong(src);
                        smartDevice.getSmartDeviceLogic().setSrcID(srcID);
                        return 4;
                    }
                }
                break;
        }

        return 0;
    }

    public int getNetWork() {
        return netWork;
    }

    public String getMac() {
        return mac;
    }

    public byte[] getHeadData() {
        return headData;
    }

    public String getDeviceIP() {
        return deviceIP;
    }

    public boolean isRead() {
        return isRead;
    }

    public short getMsgID() {
        return msgID;
    }

    private byte[] data;

    public byte[] getData() {
        return data;
    }

    private short dadaSeuq;//小包号

    public short getDadaSeuq() {
        return dadaSeuq;
    }


}
