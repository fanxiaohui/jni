package com.vision.smarthome.dal.device;

import com.vision.smarthome.dal.impl.IDeviceBuzzer;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.SocketHead;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 传感器
 */
public final class SmokeSensor extends SmartDevice implements IDeviceBuzzer{

    /**
     * 蜂鸣器开关
     */
    private boolean buzzer;

    public SmokeSensor(){
        initSmartDeviceData(this);
    }
    /**
     * 发送,控制状态改变,协议0x11
     * boolean power = socket.getPowerArray()[0];
     * 用法:openSocketPowerArray(new PowerArrayValue[]{new PowerArrayValue(0,!power)});
     */
    @Override
    public boolean openSocketbuzzer(boolean buzzer) {

        this.buzzer = buzzer;
        return sendDeviceStatus();
    }
    @Override
    public boolean sendDeviceStatus() {

        byte buzzerByte = 0;
        buzzerByte |= buzzer ? 1 : 0;

        ByteBuffer bb = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        bb.put(buzzerByte);
        bb.put((byte) 0);
        bb.put((byte) 0);
        bb.put((byte) 0);


        OutPutMessage.LogCatInfo("校验方法", "原数据体：" + ByteUtil.byteArrayToHexString(bb.array(), true));
        byte[] data = SocketHead.buildSocketHead(bb.array(), (byte) SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE);
        OutPutMessage.LogCatInfo("校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true));

        getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE, data);

        return SmartDeviceConstant.PARSE_OK;
    }

    @Override
    public boolean parseDeviceStatus(byte[] _data) {
        if (_data.length < 4) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SocketHead.SocketHeadData shd = SocketHead.parseSocketHead(_data);
        if (shd != null) {
            byte[] data = shd.data;
            OutPutMessage.LogCatInfo("烟雾校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true) + "!!! 数据" + shd.opcode);
            if (data.length < 4) {
                return SmartDeviceConstant.PARSE_ERROR_LENGTH;
            }
            byte buzzerByte = (byte) (data[0] & 0xff);


            this.buzzer = (buzzerByte == 1);



            return SmartDeviceConstant.PARSE_OK;
        }
        return SmartDeviceConstant.PARSE_ERROR_LENGTH;

    }

    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        OutPutMessage.LogCatInfo("数据接收", "(设备MAC)--> :" + getSmartDeviceLogic().getDeviceMac() + ",(消息ID)-->" + Integer.toHexString(msgID) + ",(数据内容)-->" + ByteUtil.byteArrayToHexString(data, true));
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);
        switch (msgID) {

            default:

                break;
        }
    }

    @Override
    public boolean getBuzzerSate() {
        return buzzer;
    }


}
