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
 * 漏电保护器
 * Created by zhaoqing on 2016/1/21.
 */
public class EarthLeakageProtective extends SmartDevice implements IDeviceBuzzer {

    /**
     * 开关
     */
    private boolean power;

    private boolean temppower;
    /**
     * 蜂鸣器开关
     */
    private boolean buzzer;

    private boolean tempbuzzer;

    /**
     * 电弧保护开关
     */
    private boolean crowbar;

    private boolean tempcrowbar;


    public EarthLeakageProtective(){

        initSmartDeviceData(this);

    }
    private void tempInit(){
        temppower = power;
        tempbuzzer = buzzer;
        tempcrowbar = crowbar;
    }
    /**
     * 发送->插座,控制状态改变,协议0x11
     * boolean power = socket.getPowerArray()[0];
     * 用法:openSocketPowerArray(new PowerArrayValue[]{new PowerArrayValue(0,!power)});
     */

    public boolean openSocketPower(boolean power) {

        tempInit();
        this.temppower = power;
        return sendDeviceStatus();
    }

    /**
     * 发送->插座,控制状态改变,协议0x11
     * boolean power = socket.getPowerArray()[0];
     * 用法:openSocketPowerArray(new PowerArrayValue[]{new PowerArrayValue(0,!power)});
     */
    @Override
    public boolean openSocketbuzzer(boolean buzzer) {
        tempInit();
        this.tempbuzzer = buzzer;
        return sendDeviceStatus();
    }

    /**
     * 发送->插座,控制状态改变,协议0x11
     * boolean power = socket.getPowerArray()[0];
     * 用法:openSocketPowerArray(new PowerArrayValue[]{new PowerArrayValue(0,!power)});
     */
    public boolean openCrowbar(boolean crowbar){
        tempInit();
        this.tempcrowbar = crowbar;
        return sendDeviceStatus();

    }

    @Override
    public boolean sendDeviceStatus() {
        byte powerByte = 0;
        powerByte |= temppower ? 1 : 0;

        byte buzzerByte = 0;
        buzzerByte |= tempbuzzer ? 1 : 0;

        byte crowbarByte = 0;
        crowbarByte |= tempcrowbar ? 1 : 0;

        ByteBuffer bb = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        bb.put(powerByte);
        bb.put(buzzerByte);
        bb.put(crowbarByte);
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
            OutPutMessage.LogCatInfo("校验方法", "数据体：" + ByteUtil.byteArrayToHexString(data, true) + "!!! 数据" + shd.opcode);
            if (data.length < 4) {
                return SmartDeviceConstant.PARSE_ERROR_LENGTH;
            }
            byte powerByte = (byte) (data[0] & 0xff);
            byte buzzerByte = (byte) (data[1] & 0xff);
            byte crowbarByte = (byte) (data[2] & 0xff);

            this.power = (powerByte == 1);
            this.buzzer = (buzzerByte == 1);
            this.crowbar = (crowbarByte == 1);


            return SmartDeviceConstant.PARSE_OK;
        }
        return SmartDeviceConstant.PARSE_ERROR_LENGTH;

    }

    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {

        OutPutMessage.LogCatInfo("电弧接收数据", "(设备MAC)--> :" + getSmartDeviceLogic().getDeviceMac() + ",(消息ID)-->" + Integer.toHexString(msgID) + ",(数据内容)-->" + ByteUtil.byteArrayToHexString(data, true));
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);//解析共有消息
        switch (msgID) {

            default:

                break;

        }

    }

    @Override
    public boolean getBuzzerSate() {
        return buzzer;
    }

    public boolean getCrowbarSate() {
        return crowbar;
    }


}
