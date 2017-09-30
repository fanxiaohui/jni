package com.vision.smarthome.dal.device;

import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.SocketHead;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 水电磁阀
 */
public class SolenoidValveWater  extends SmartDevice {

    /**
     * 开关状态位(多路)
     */
    private boolean powerArray[] = new boolean[8];

    private boolean temppowerArray[] = new boolean[8];
    /**
     * 开关锁定位(多路)
     */
    private boolean lockArray[] = new boolean[8];
    /**
     * 开关掩码位(多路)
     */
    private boolean maskArray[] = new boolean[8];



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


    public SolenoidValveWater() {
        super();
        initSmartDeviceData(this);
//        this.setDeviceType(SmartDeviceConstant.DeviceType.SOCKET);



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
            OutPutMessage.LogCatInfo("水电阀门接收数据校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true) + "!!! 数据" + shd.opcode);
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

            OutPutMessage.LogCatInfo("水电阀门接收数据", "(设备MAC)--> :" + getSmartDeviceLogic().getDeviceMac() + "  开关 "+powerArray[0]);
            return SmartDeviceConstant.PARSE_OK;
        }
        return SmartDeviceConstant.PARSE_ERROR_LENGTH;
    }






    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        OutPutMessage.LogCatInfo("水电阀门接收数据", "(设备MAC)--> :" + getSmartDeviceLogic().getDeviceMac() + ",(消息ID)-->" + Integer.toHexString(msgID) + ",(数据内容)-->" + ByteUtil.byteArrayToHexString(data, true));
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);//解析共有消息
        switch (msgID) {
//            case SmartDeviceConstant.MsgID.DEVICE_ALARM_SYNC://报警状态同步
//                if (parseAlarmInfo(data)) {
//                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_MESSAGE_ALARM_INFO, null, this);
//                }
//                break;
            case SmartDeviceConstant.MsgID.DEVICE_FAULT_SYNC://故障状态同步

                break;



            default:

                break;

        }
    }


    public boolean[] getPowerArray() {
        return powerArray;
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




}