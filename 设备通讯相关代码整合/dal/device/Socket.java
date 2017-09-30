package com.vision.smarthome.dal.device;

import com.vision.smarthome.dal.function.TimeDataNew;
import com.vision.smarthome.dal.impl.IDeviceNetMode;
import com.vision.smarthome.dal.impl.IDeviceTime;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.SocketHead;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * 插座
 */
public final class Socket extends SmartDevice implements
        IDeviceTime, IDeviceNetMode {

    /**
     * 开关状态位(多路)
     */
    private boolean powerArray[] = new boolean[8];
    //控制用
    private boolean temppowerArray[] = new boolean[8];
    /**
     * 开关锁定位(多路)
     */
    private boolean lockArray[] = new boolean[8];
    /**
     * 开关掩码位(多路)
     */
    private boolean maskArray[] = new boolean[8];


    private TimeDataNew timeData;

    /**
     * 插座当前温度
     */
    private byte socketTemp;
    /**
     * 当前电量
     */
    private int socketCurrent;
    /**
     * 当前电压
     */
    private int socketVoltage;
    /**
     * 当前功率
     */
    private int socketPower;


    public Socket() {
        super();
        initSmartDeviceData(this);
//        this.setDeviceType(SmartDeviceConstant.DeviceType.SOCKET);

        timeData = new TimeDataNew(this);


    }

    /**
     * 发送->插座,控制状态改变,协议0x11
     * boolean power = socket.getPowerArray()[0];
     * 用法:openSocketPowerArray(new PowerArrayValue[]{new PowerArrayValue(0,!power)});
     */
    public boolean openSocketPowerArray(PowerArrayValue[] values) {
        if (values == null) {
            return false;
        }
        for (PowerArrayValue value : values) {
            if (value.loc > values.length || value.loc < 0) {
                continue;
            }
            temppowerArray[value.loc] = value.value;
            // maskArray[value.loc] = true;
        }
        return sendDeviceStatus();
    }


    /**
     * 构建插座状态数据体
     *
     * @return 构建好的插座数据
     */
    @Override
    public boolean sendDeviceStatus() {
        byte power = 0;
        for (int i = 0; i < temppowerArray.length; i++) {
            power |= (temppowerArray[i] ? 1 : 0) << i;
        }

        byte lock = 0;
        for (int i = 0; i < lockArray.length; i++) {
            lock |= (lockArray[i] ? 1 : 0) << i;
        }

        byte mask = 0;
        for (int i = 0; i < maskArray.length; i++) {
            mask |= (maskArray[i] ? 1 : 0) << i;
        }

        ByteBuffer bb = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        bb.put(power);
        bb.put(lock);
        bb.put(mask);
        bb.put((byte) 0);

        for (int i = 0; i < maskArray.length; i++) {
            maskArray[i] = false;
        }

        OutPutMessage.LogCatInfo("校验方法", "原数据体：" + ByteUtil.byteArrayToHexString(bb.array(), true));
        byte[] data = SocketHead.buildSocketHead(bb.array(), (byte) SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE);
        OutPutMessage.LogCatInfo("校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true));

        getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE, data);

        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 解析->运行状态同步,协议0x30
     *
     * @param _data 插座数据
     */
    @Override
    public boolean parseDeviceStatus(byte[] _data) {
        if (_data.length < 4) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SocketHead.SocketHeadData shd = SocketHead.parseSocketHead(_data);
        if (shd != null) {
            byte[] data = shd.data;
            OutPutMessage.LogCatInfo("校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true) + "!!! 数据" + shd.opcode);
            if (data.length < 12) {
                return SmartDeviceConstant.PARSE_ERROR_LENGTH;
            }
            byte powers = (byte) (data[0] & 0xff);
            byte locks = (byte) (data[1] & 0xff);
            for (int i = 0; i < powerArray.length; i++) {
                powerArray[i] = ((powers >> i) & 0x01) == 1;
            }
            for (int i = 0; i < lockArray.length; i++) {
                lockArray[i] = ((locks >> i) & 0x01) == 1;
            }
            socketTemp = data[5];
            socketCurrent = 0x00ffff & ((data[6] & 0xff) | ((data[7] << 8) & 0xff00));
            socketVoltage = 0x00ffff & ((data[8] & 0xff) | ((data[9] << 8) & 0xff00));
            socketPower = 0x00ffff & ((data[10] & 0xff) | ((data[11] << 8) & 0xff00));
            return SmartDeviceConstant.PARSE_OK;
        }
        return SmartDeviceConstant.PARSE_ERROR_LENGTH;
    }


    /**
     * 发送->定时设置,协议0x53
     *
     * @param isRead
     * @return
     */
    @Override
    public boolean sendDeviceTime(boolean isRead) {
        byte[] data = null;
        if (!isRead) {
            data = timeData.sendTimeData();
            if (data != null) {
                getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_TIMING, data, isRead);
            } else {
                return false;
            }
        } else {
            getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_TIMING, data, isRead);
        }
        return true;
    }

//    /**
//     * 发送->系统时间,0x51
//     *
//     * @param isRead 设备读写操作
//     * @return
//     */
//    @Override
//    public boolean sendDeviceCheckTime(boolean isRead) {
//        byte[] data = null;
//        if (!isRead) {
//            data = timeData.sendCheckTimeData();
//        }
//
//        getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_SYSTEM_TIME, data, isRead);
//        return true;
//    }


//    @Override
//    public boolean parseDeviceCheckTime(byte[] data) {
//        OutPutMessage.LogCatInfo("时间", "mac :" + getSmartDeviceLogic().getDeviceMac() + "!!!校时回复" + data.length);
//        if (data.length < 5) {
//            return false;
//        }
//
//        timeData.parseCheckTimeData(data);
//        if (timeData.getDeviceTime() != System.currentTimeMillis() / 1000 || timeData.getDeviceTimeZone() != 8) {
//            sendDeviceCheckTime(SmartDeviceConstant.OperateState.No_Read);//发送校时
//        }
//        OutPutMessage.LogCatInfo("时间", timeData.getDeviceTime() + "!!!" + System.currentTimeMillis() / 1000);
//        return false;
//    }

    @Override
    public List<TimeDataNew.SubInsTime> getSubInsTimeList() {
        return timeData.getSubInsTimes();
    }

    @Override
    public boolean addSubInsTime(TimeDataNew.SubInsTime subInsTime) {
        boolean isAdd = timeData.addSubInfTimes(subInsTime);
        if (isAdd) {
            sendDeviceTime(SmartDeviceConstant.OperateState.No_Read);
        }
        return isAdd;
    }

    @Override
    public boolean deleteSubInsTime(int subID) {
        boolean isDelete = timeData.removeSubInsTimes(subID);
        if (isDelete) {
            sendDeviceTime(SmartDeviceConstant.OperateState.No_Read);
        }
        return isDelete;
    }

    @Override
    public TimeDataNew.SubInsTime getNewSubInsTime() {
        return timeData.getNewSubInsTime();
    }

    @Override
    public void backupSubInfTime(TimeDataNew.SubInsTime subInsTime) {
        timeData.backupSubInfTime(subInsTime);
    }

    @Override
    public TimeDataNew.SubInsTime findSubInfTime(int id) {
        return timeData.findListSubInsTime(id);
    }

    @Override
    public void recoverBackupSubInfTime() {
        timeData.getBackupSubInfTime();
    }


    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        OutPutMessage.LogCatInfo("插座接收数据", "(设备MAC)--> :" + getSmartDeviceLogic().getDeviceMac() + ",(消息ID)-->" + Integer.toHexString(msgID) + ",(数据内容)-->" + ByteUtil.byteArrayToHexString(data, true));
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);//解析共有消息
        switch (msgID) {
//            case SmartDeviceConstant.MsgID.DEVICE_ALARM_SYNC://报警状态同步
//                if (parseAlarmInfo(data)) {
//                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_MESSAGE_ALARM_INFO, null, this);
//                }
//                break;
            case SmartDeviceConstant.MsgID.DEVICE_FAULT_SYNC://故障状态同步

                break;
//            case SmartDeviceConstant.MsgID.DEVICE_SYSTEM_TIME://系统时间
//                if (isRead) {
//                    parseDeviceCheckTime(data);
//                }
//                break;
            case SmartDeviceConstant.MsgID.DEVICE_TIMING://通用定时设置

                this.getSmartDeviceLogic().setTimeControl(true);
                if (!isRead) {//操作成功在读取一次
                    //sendDeviceTime(SmartDeviceConstant.OperateState.Read);
                } else if (parseDeviceTiming(data)) {
                }
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_TIMING_SYNC, null, this);
                break;
            case SmartDeviceConstant.MsgID.DEVICE_NETWORK_SET://Ap配置成功
                sendRestartDevice();
                //  OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "设备发送重启---->");
                NotificationManager.defaultManager().postNotification(Constant.NotificationType.ACTIVITY_AP_OK, null, this);
                break;


            default:

                break;

        }
    }


    public boolean[] getPowerArray() {
        return powerArray;
    }

    @Override
    public boolean sendSsIdPassDevice(String ssId, String passWord) {
        byte[] ssIDBytes = ssId.getBytes();
        byte ssIDLength = (byte) ssIDBytes.length;
        String pw = passWord == null ? "" : passWord;
        byte[] passBytes = pw.getBytes();
        byte passLength = (byte) passBytes.length;
        int mode = 0x00;
        byte[] mByte = ByteBuffer.allocate(1 + 1 + ssIDLength + 1 + passLength)
                .put((byte) mode)
                .put(ssIDLength)
                .put(ssIDBytes)
                .put(passLength)
                .put(passBytes)
                .array();
        // OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "发送Data长度" + mByte.length);
        return getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_NETWORK_SET, mByte, SmartDeviceConstant.OperateState.No_Read);
    }

    @Override
    public boolean sendRestartDevice() {
        return getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CONTROL_RESTART, null,
                SmartDeviceConstant.OperateState.No_Read,
                SmartDeviceConstant.OperateState.No_Resend,
                SmartDeviceConstant.OperateState.No_ACk);
    }

    /**
     * 多路插排每路开关状态包装类
     */
    public static class PowerArrayValue {
        public PowerArrayValue(int loc, boolean value) {
            this.loc = loc;
            this.value = value;
        }

        /**
         * 第几路（0起始）
         */
        public int loc;
        /**
         * 该路开关状态
         */
        public boolean value;
    }


    /**
     * 解析->定时设置,协议0x53
     *
     * @return
     */
    private boolean parseDeviceTiming(byte[] data) {
        if (data.length < 64) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        return timeData.parseTimeData(data);
    }


}
