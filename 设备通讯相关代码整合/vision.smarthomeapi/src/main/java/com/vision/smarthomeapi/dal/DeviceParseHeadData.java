package com.vision.smarthomeapi.dal;

import com.smarthome.head.SmartHomeConstant;
import com.smarthome.head.SmartHomeData;
import com.smarthome.head.SmartHomeHead;
import com.vision.smarthomeapi.bean.RSecurityDevice;
import com.vision.smarthomeapi.bll.manage.ResendQueueManage;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.HeadData;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.data.SmartDeviceLogic;
import com.vision.smarthomeapi.dal.data.SmartDeviceState;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;

/**
 * Created by zhanglong on 2015/9/24.
 */
public class DeviceParseHeadData {

    private SmartDevice smartDevice;

    private SmartHomeData smartHomeData;

    private int netWork;

    private int opcode;

    private RSecurityDevice wanDevice;

    private byte[] data;
    private byte[] key;

    public DeviceParseHeadData(SmartDevice smartDevice, int netWork) {
        this.smartDevice = smartDevice;
        this.netWork = netWork;
    }

    public void setSmartDevice(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
    }


    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }


    private SmartDeviceManage smartDeviceManage;


    public DeviceParseHeadData(int netWork) {
        this.netWork = netWork;
        this.smartDeviceManage = SmartDeviceManage.defaultManager();
        this.key = SmartDeviceManage.defaultManager().noSecretKey();
    }


    public SmartHomeData getSmartHomeData() {
        return smartHomeData;
    }

    public void setWanDevice(RSecurityDevice wanDevice) {
        this.wanDevice = wanDevice;
    }

    public RSecurityDevice getWanDevice() {
        return wanDevice;
    }

    public SmartDevice getSmartDevice() {
        return smartDevice;
    }


    public int getNetWork() {
        return netWork;
    }

    private int msgID;
    private String macStr;

    public int getMsgID() {
        return msgID;
    }

    public String getMacStr() {
        return macStr;
    }

    public boolean parseSmartHeadCheckInfo(byte[] headData) {
        OutPutMessage.LogCatInfo("接收数据", "headData  parseSmartHeadInfo--->" + ByteUtil.byteArrayToHexString(headData) + "   ");
        smartHomeData = SmartHomeHead.parseData(headData, (short) 0, (short) 0, new byte[]{}, key, false);//false data为null
        OutPutMessage.LogCatInfo("接收数据", "parseSmartHeadInfo--->" + smartHomeData.toString());
        if (smartHomeData == null) {
            return false;
        }
        if (smartHomeData.msgID == 0x02) {
            return false;
        }
        this.msgID = smartHomeData.msgID;
        this.opcode = smartHomeData.opcode;

        OutPutMessage.LogCatInfo("接收数据", "MAC的数据--->" + smartHomeData.srcID);
        String macStr = ByteUtil.longToMacStr(smartHomeData.srcID);
        OutPutMessage.LogCatInfo("接收数据", "MAC的数据--->" + macStr);
        //根据源ID转换成的MAC地址取设备，如果源ID为空则丢弃数据
        if (macStr != null) {
            this.macStr = macStr;
            this.smartDevice = smartDeviceManage.getDeviceHashMap().get(macStr);
            if (this.smartDevice == null) {
                this.smartDevice = smartDeviceManage.findDeviceID(macStr);
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isCheckData() {
        if (smartHomeData != null && smartHomeData.code == 0) {
            if (smartHomeData.data != null && smartHomeData.data.length >= 4) {
                return true;
            }
        }
        return false;
    }

    public boolean parseHeadDataInfo(byte[] data) {
        if (smartDevice == null) {
            return false;
        }
        this.data = data;
        short dataSequence = 0;
        int number = 0;
//        if (smartHomeData.isACK) {//控制包号
//            dataSequence = smartDevice.getSmartDeviceLogic().getSendPacketNumber(smartHomeData.msgID);//小包序号
//            number = smartDevice.getSmartDeviceLogic().getSendPacketNumber();//大包序号
//            OutPutMessage.LogCatInfo("包序号", "设备MAC : " + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!! 消息ID" + Integer.toHexString(smartHomeData.msgID) + "!!! 回复包号" + smartHomeData.sequence);
//            OutPutMessage.LogCatInfo("包序号", "设备MAC : " + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!! 消息ID" + Integer.toHexString(smartHomeData.msgID) + "!!! 设备包号" + smartHomeData.sequence);
//            OutPutMessage.LogCatInfo("result", "result接收：" + smartHomeData.sequence + "!!! mac" + smartDevice.getSmartDeviceLogic().getDeviceMac());
//        } else {//消息包号
//
//        }
        if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {//网关子设备，获取网关状态发出
            if (smartDevice.getSmartDeviceLogic().getGatewayMac() != null) {
                SmartDevice gatewayDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(smartDevice.getSmartDeviceLogic().getGatewayMac());
                if (smartHomeData.isACK) {//控制包号
                    dataSequence = gatewayDevice.getSmartDeviceLogic().getSendPacketNumber(smartHomeData.msgID);//小包序号
                    number = gatewayDevice.getSmartDeviceLogic().getSendPacketNumber();//大包序号
                    if (number < smartHomeData.sequence) {
                        OutPutMessage.LogCatInfo("包序号打印", number+"result接收错误包号：" + smartHomeData.sequence + "消息ID" + Integer.toHexString(msgID) + "!!! 包号mac" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "@@" + number);
                        return false;
                    }
                } else {
                    dataSequence = gatewayDevice.getSmartDeviceLogic().getReceivePacketNumber(smartHomeData.msgID);//小包序号
                    number = gatewayDevice.getSmartDeviceLogic().getReceiveSequence();//大包序号
                    if (number > smartHomeData.sequence) {
                        OutPutMessage.LogCatInfo("包序号打印", number+"result接收错误包号：" + smartHomeData.sequence + "消息ID" + Integer.toHexString(msgID) + "!!! 包号mac" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "@@" + number);
                        return false;
                    }
                    //大包号
                    gatewayDevice.getSmartDeviceLogic().setReceiveSequence(smartHomeData.sequence);

                    //小包号
                    OutPutMessage.LogCatInfo("添加小包号", gatewayDevice.getSmartDeviceLogic().getDeviceMac() + "  " + smartHomeData.msgID + "  " + smartHomeData.dataSequ);
                    gatewayDevice.getSmartDeviceLogic().setReceivePacketNumber(smartHomeData.msgID, smartHomeData.dataSequ);
                }

            }
        } else {
            if (smartHomeData.isACK) {//控制包号
                dataSequence = smartDevice.getSmartDeviceLogic().getSendPacketNumber(smartHomeData.msgID);//小包序号
                number = smartDevice.getSmartDeviceLogic().getSendPacketNumber();//大包序号
                if (number < smartHomeData.sequence) {
                    OutPutMessage.LogCatInfo("1包序号打印",number+ "result接收错误包号：" + smartHomeData.sequence + "消息ID" + Integer.toHexString(msgID) + "!!! 包号mac" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "@@" + number);
                    return false;
                }
            } else {
                dataSequence = smartDevice.getSmartDeviceLogic().getReceivePacketNumber(smartHomeData.msgID);//小包序号
                number = smartDevice.getSmartDeviceLogic().getReceiveSequence();//大包序号
                if (number > smartHomeData.sequence) {
                    OutPutMessage.LogCatInfo("2包序号打印", number+"result接收错误包号：" + smartHomeData.sequence + "消息ID" + Integer.toHexString(msgID) + "!!! 包号mac" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "@@" + number);
                    return false;
                }
                smartDevice.getSmartDeviceLogic().setReceiveSequence(smartHomeData.sequence);

                //小包号
                OutPutMessage.LogCatInfo("添加小包号", smartDevice.getSmartDeviceLogic().getDeviceMac() + "  " + smartHomeData.msgID + "  " + smartHomeData.dataSequ);
                smartDevice.getSmartDeviceLogic().setReceivePacketNumber(smartHomeData.msgID, smartHomeData.dataSequ);

            }

        }


        //OutPutMessage.LogCatInfo("result", "result接收：" + smartDevice.getSmartDeviceLogic().getReceiveSequence() + "消息ID"  + Integer.toHexString(msgID) + "!!! 包号mac" + smartDevice.getSmartDeviceLogic().getDeviceMac());

        byte[] bytes = null;
        SmartDevice sd = null;
        if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
            sd = smartDevice;
        } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {
            sd = smartDevice;
        } else {
            sd = smartDevice;
        }
//        if (sd.getSmartDeviceLogic().getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
//            bytes = sd.getSmartDeviceLogic().getSecretKey().clone();
//        } else {
        bytes = key;
//        }

        byte[] devicePW = SmartDeviceLogic.PASS_WORD;
        if (smartDevice != null){
            if (smartDevice.getSmartDeviceLogic().getShareType() == 1){
                devicePW = ByteUtil.hexStr2Byte(smartDevice.getSmartDeviceLogic().getCtlpwd());
            }
        }
        switch (netWork) {
            case Constant.NetWork.LAN_NET:
            case Constant.NetWork.WAN_NET:
                if (this.opcode == SmartHomeConstant.OPCode.WAN_PROXY) {
                    bytes = SecurityUserManage.tmpEncrypt;
                    smartHomeData = SmartHomeHead.parseData(data, number, dataSequence, SecurityUserManage.pwEncrypt, bytes, true);
                } else {
                    if (sd.getSmartDeviceLogic().getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                        bytes = sd.getSmartDeviceLogic().getSecretKey().clone();
                    }
//                    OutPutMessage.LogCatInfo("流程测试",);
                    smartHomeData = SmartHomeHead.parseData(data, number, dataSequence, devicePW, bytes, true);
                }

                return true;
            case Constant.NetWork.ZIG_BEE_NET:
                smartHomeData = SmartHomeHead.parseZigbeeData(data, number, dataSequence, devicePW, zigBeeMac, bytes, true);

                OutPutMessage.LogCatInfo("ZIGBee返回", smartHomeData.toString() + "");
                return true;
        }
        return true;
    }

    /**
     * 解析登录返回数据
     *
     * @param data
     * @return
     */
    public boolean parseSmartHeadLoginInfo(byte[] data) {
        if (parseHeadDataInfo(data)) {
            int i = 0;
            OutPutMessage.LogCatInfo("登录接收数据", smartHomeData.toString() + "");
            OutPutMessage.LogCatInfo("登录接收数据", ByteUtil.byteArrayToHexString(smartHomeData.data, true) + "");
            ResendQueueManage.defaultQueue().remove(smartHomeData.dataSequ, smartDevice.getSmartDeviceLogic().getDeviceMac(), smartHomeData.msgID);
            if (smartHomeData != null && smartHomeData.code == 0) {
                i = ByteUtil.deviceByteFourValidation(smartHomeData.data);
                if (i == 0) {
                    int length = 4;
                    byte[] _data = new byte[smartHomeData.data.length - length];//数据长度，去掉头4消息值
                    System.arraycopy(smartHomeData.data, 4, _data, 0, _data.length);
                    smartDevice.getSmartDeviceLogic().parseDeviceLogin(_data, netWork);
                    OutPutMessage.LogCatInfo("最终登录返回", "net:" + netWork+"   "+smartDevice.getSmartDeviceLogic().getDeviceMac());
                }
            }

            return true;
        }
        return false;
    }

    /**
     * 解析返回数据
     *
     * @return
     */
    public boolean parseSmartHeadInfo(byte[] data) {
        if (parseHeadDataInfo(data)) {
            OutPutMessage.LogCatInfo("流程测试数据", "code:" + smartHomeData.code + "消息ID:" + Integer.toHexString(smartHomeData.msgID) + "数据接收--->111111111111111111" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!!!!!!!!!!!" + ByteUtil.byteArrayToHexString(smartHomeData.data, true));


            if (smartHomeData != null) {
                if (smartHomeData.opcode == SmartHomeConstant.OPCode.WAN_PROXY) {
                    ResendQueueManage.defaultQueue().remove(smartDevice.getSmartDeviceLogic().getDeviceMac(), smartHomeData.msgID);
                } else {
                    ResendQueueManage.defaultQueue().remove(smartHomeData.dataSequ, smartDevice.getSmartDeviceLogic().getDeviceMac(), smartHomeData.msgID);
                }
                switch (netWork) {
                    case Constant.NetWork.LAN_NET:
                    case Constant.NetWork.WAN_NET:
                        if (smartHomeData != null) {
                            if (parseResponseHeadData(smartHomeData, smartDevice)) {
                                OutPutMessage.LogCatInfo("数据", "parseSmartHeadInfo33333--->" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!!!!!!!!!!!" + ByteUtil.byteArrayToHexString(smartHomeData.data, true));
                                //如果验证成功，则移除本地重发对了
                                SmartDeviceState smartDeviceState = smartDevice.getSmartDeviceLogic().getSmartDeviceState();//状态机
                                this.data = smartHomeData.data;
                                smartDeviceState.deviceReceiveData(this);

                                //门锁和猫眼只能通过这里同步状态
                                OutPutMessage.LogCatInfo("30指令接收","1通道"+smartDevice.getSmartDeviceLogic().getDeviceType());
                                if (smartDevice.getSmartDeviceLogic().getDeviceType() == 8 && smartHomeData.msgID == SmartDeviceConstant.MsgID.DEVICE_STATE_SYNC) {
                                    OutPutMessage.LogCatInfo("30指令接收","1通道");
                                    smartDevice.getSmartDeviceLogic().parseResponseNoAckData(smartHomeData);
                                }
                            }
                        }
                        return true;
                    case Constant.NetWork.ZIG_BEE_NET:
                        if (smartHomeData.code == 0) {
                            //如果验证成功，则移除本地重发对了
                            OutPutMessage.LogCatInfo("数据", "ZiB数据接收--->" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!!!!!!!!!!!" + ByteUtil.byteArrayToHexString(smartHomeData.data, true));
                            SmartDeviceState smartDeviceState = smartDevice.getSmartDeviceLogic().getSmartDeviceState();//状态机
                            this.data = smartHomeData.data;
                            smartDeviceState.deviceReceiveData(this);
                        }
                        break;
                }
            }

            return true;
        }
        return false;
    }

    /**
     * 解析设备推送数据头
     *
     * @param smartHomeData 数据头
     * @param smartDevice   推送设备
     * @return
     */
    private boolean parseResponseHeadData(SmartHomeData smartHomeData, SmartDevice smartDevice) {
        if (smartHomeData.code != 0) {
            return false;
        }
        return true;
    }

    /**
     * 解析广播
     *
     * @param lanData
     * @param lanIp
     * @return
     */
    public int parseSmartHeadLanInfo(byte[] lanData, String lanIp) {
        int i = -1;
//        String macStr = ByteUtil.longToMacStr(smartHomeData.srcID);
//        OutPutMessage.LogCatInfo("接收数据", "MAC的数据--->" + macStr);
//        //根据源ID转换成的MAC地址取设备，如果源ID为空则丢弃数据
//        String src = "";
//        SmartDevice device = null;
//        if (macStr != null) {
////            device = smartDeviceManage.getChildDevice().get(macStr);//查找是不是子设备
////            if (device != null) {
////                this.key = device.getSecretKey();
////                src = device.getDevicePassword();
////                OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据1--->" + macStr);
////                OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据2--->" + ByteUtil.byteArrayToHexString(this.key, true));
////            }
//        }
//        smartHomeData = SmartHomeHead.parseData(lanData, (short) 0, (short) 0, src, key, true);
//        OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据3--->" + ByteUtil.byteArrayToHexString(this.key, true));
//        OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据4--->" + smartHomeData.toString() + "!!!!!!!111111111111!!!");
        smartHomeData = SmartHomeHead.parseData(lanData, (short) 0, (short) 0, SmartDeviceLogic.PASS_WORD, SmartDeviceManage.defaultManager().noSecretKey(), true);
        OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据3--->" + ByteUtil.byteArrayToHexString(this.key, true));
        OutPutMessage.LogCatInfo("解析子设备功能", "MAC的数据4--->" + smartHomeData.toString() + "!!!!!!!111111111111!!!");
        if (isCheckData()) {
            OutPutMessage.LogCatInfo("广播回复555555555", smartHomeData.toString() + "!!!!!!!2222222222!!!");
            i = ByteUtil.deviceByteFourValidation(smartHomeData.data);
            OutPutMessage.LogCatInfo("广播回复77777777777777", smartHomeData.toString() + "!!!!!!!2222222222!!!" + i);
            if (i == 0) {//广播回复
                OutPutMessage.LogCatInfo("广播回复", macStr + "   LANOK");
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.LAN_OK, null, macStr);


            } else {
                return i;
            }
            if (smartDevice != null && smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                return -1;
            }

            OutPutMessage.LogCatInfo("广播回复8888888", smartHomeData.toString() + "!!!!!!!2222222222!!!" + i);
            if (i == 0) {
                byte[] typeByte = new byte[4];
                System.arraycopy(smartHomeData.data, 4, typeByte, 0, 4);
                int deviceType = typeByte[1];
                int deviceVesion = typeByte[0];
                int communicationType = typeByte[2];
                headData = new HeadData();
                headData.devType = deviceType;
                headData.communicationType = communicationType;
                headData.devVersion = deviceVesion;
                headData.deviceParseHeadData = this;
                headData.ip = lanIp;
                headData.deviceeUpdateType = ByteUtil.bytesToInt(typeByte);
            }
        }
        return i;
    }


    private HeadData headData;

    public HeadData getHeadData() {
        return headData;
    }

    private long zigBeeMac;

    public boolean parseSmartHeadCheckZigBeeInfo(byte[] headData, String mac) {
        //Log.i("测试接收", "协议ID::" + _headData.msgID);
        OutPutMessage.LogCatInfo("ZIGBEE接收数据", "数据Mac地址 ： " + mac);
        if (mac != null) {
            this.macStr = mac;
            this.smartDevice = smartDeviceManage.getDeviceHashMap().get(macStr);
        } else {
            return false;
        }

        long bigMac = ByteUtil.macToLong(mac);
        this.zigBeeMac = Long.reverseBytes(bigMac);
        //解析头信息用来获取设备mac 和协议id
        smartHomeData = SmartHomeHead.parseZigbeeData(headData, (short) 0, (short) 0, new byte[]{}, zigBeeMac, key, false);//false data为null
        this.msgID = smartHomeData.msgID;
        if (smartHomeData == null) {
            return false;
        }
        if (smartHomeData.msgID == 0x02) {
            return false;
        }
        OutPutMessage.LogCatInfo("ZIGBEE接收数据", "数据内容1111 ： " + smartHomeData.toString());
        return true;
    }
}
