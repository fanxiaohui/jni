package com.vision.smarthome.dal.device;

import com.vision.smarthome.dal.function.ParameterData;
import com.vision.smarthome.dal.impl.IDeviceParameter;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.SocketHead;
import com.vision.smarthomeapi.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * 电暖炉
 * Created by yangle on 2016/12/5.
 */
public class ElectricStove extends SmartDevice implements IDeviceParameter {

    /**
     * 开关
     * 0-待机，1-开机。
     */
    public boolean powerSupply = true;

    //用于控制==
    public boolean tempPowerSupply = true;
    /**
     * 室温控制方式
     * 0-回水温度控制方式，1-室内环境温度控制方式。
     */
    public byte roomTempControlMode;
    //用于控制==
    public byte temproomTempControlMode;
    /**
     * 加热棒是否在加热
     * 0-加热棒没有加热，1-加热棒正在加热。
     */
    public boolean isHeat;
    /**
     * 循环泵是否在工作
     * 0-循环泵没有工作，1-循环泵正在工作。
     */
    public boolean isWaterPumpWorking;
    /**
     * 工作模式
     * 0-自动模式，1-在家模式，2-外出模式。
     */
    public byte workingMode;
    //用于控制==
    public byte tempworkingMode;
    /**
     * 是否设置室温
     * 0-不设置室温，室温数值无效；1-设置室温，室温数值有效。
     */
    public boolean isSetRoomTemp;
    //用于控制==
    public boolean tempisSetRoomTemp;
    /**
     * 室内温度
     * 当前室内实际温度值。
     */
    public int roomTemp;
    /**
     * 室内设定温度
     * 室温控制温度数值，取值15～30摄氏度。
     */
    public int roomTempSet;
    //用于控制
    public int temproomTempSet;
    /**
     * 出水温度
     * 系统出水温度值。
     */
    public int effluentTemp;

//    //用于控制
//    public int tempeffluentTemp;
    /**
     * 出水设定温度
     * 系统出水温度限制数值。
     */
    public int effluentTempSet;

    public int tempeffluentTempSet;
    /**
     * 是否设置回水温度
     * 0-不设置回水温度，回水温度数值无效；1-设置回水温度，回水温度数值有效。
     */
    public boolean isSetBackwaterTemp;
    //用于控制
    public boolean tempisSetBackwaterTemp;
    /**
     * 回水温度
     * 系统回水温度值。
     */
    public int backwaterTemp;
    /**
     * 回水设定温度
     * 回水温度控制数值，取值35～65摄氏度。
     */
    public int backwaterTempSet;

    /**
     * 功率
     * 表示当前电热炉运行功率值，单位W。
     */
    public int power;
    /**
     * 电压
     * 表示当前电热炉工作的电压，单位V。
     */
    public int voltage;
    /**
     * 电流
     * 表示当前通过电热炉的回路电流，单位mA
     */
    public double electricity;
    /**
     * 参数设置
     */
    public List<ParameterData> parameterDatas;

    public ElectricStove() {
        initSmartDeviceData(this);
        parameterDatas = new ArrayList<>();
        for (int i=0;i<4;i++){
            parameterDatas.add(new ParameterData());
        }
    }


    /**
     * 发送,控制状态改变,协议0x11
     */
    public boolean openPowerSupply(boolean powerSupply) {
        tempInit();
        this.tempPowerSupply = powerSupply;
        return sendDeviceStatus();
    }



    public boolean changeControlMode(byte roomTempControlMode){
        tempInit();
        this.temproomTempControlMode = roomTempControlMode;
        return sendDeviceStatus();
    }

    public boolean changeTemp(byte roomTempControlMode,int temp){
        tempInit();
        this.temproomTempControlMode = roomTempControlMode;

        if (roomTempControlMode == 0){
            tempisSetBackwaterTemp = true;
            tempisSetRoomTemp = false;
            tempeffluentTempSet = temp;
            temproomTempSet = roomTempSet;

        }else{
            tempisSetRoomTemp = true;
            tempisSetBackwaterTemp = false;
            temproomTempSet = temp;
            tempeffluentTempSet = effluentTemp;

        }
        return sendDeviceStatus();
    }
    /**
     * 发送,控制状态改变,协议0x11  更改工作模式  0定时 1正常 2节能
     */
    public boolean changeWorkingMode(byte workingMode) {
        tempInit();
        this.tempworkingMode = workingMode;
        return sendDeviceStatus();
    }
    private void tempInit(){
        tempPowerSupply = powerSupply;
        temproomTempControlMode = roomTempControlMode;
        tempworkingMode = workingMode;
        tempeffluentTempSet = effluentTempSet;
        OutPutMessage.LogCatInfo("电暖炉发送赋值","出水温度："+tempeffluentTempSet);
        temproomTempSet = roomTempSet;
        OutPutMessage.LogCatInfo("电暖炉发送赋值","室内温度："+temproomTempSet);
        tempisSetRoomTemp = isSetRoomTemp;
        tempisSetBackwaterTemp = isSetBackwaterTemp;
    }

    @Override
    public boolean sendDeviceStatus() {

        byte modeByte = 0;
        modeByte |= (tempPowerSupply ? 1 : 0) << 7;
        modeByte |= temproomTempControlMode << 5;
        modeByte |= tempworkingMode;

        byte roomTempByte = 0;
        roomTempByte |= (tempisSetRoomTemp ? 1 : 0) << 7;
        roomTempByte |= temproomTempSet;

        OutPutMessage.LogCatInfo("电暖炉发送","室内温度："+temproomTempSet);

        byte backwaterTempByte = 0;
        backwaterTempByte |= (tempisSetBackwaterTemp ? 1 : 0) << 7;
        backwaterTempByte |= tempeffluentTempSet;


        OutPutMessage.LogCatInfo("电暖炉发送","水温度："+tempeffluentTempSet);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(modeByte);
        byteBuffer.put(roomTempByte);
        byteBuffer.put(backwaterTempByte);

        byte[] data = SocketHead.buildSocketHead(byteBuffer.array(),
                (byte) SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE);
        getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE, data);

        OutPutMessage.LogCatInfo("电暖炉", "发送数据：" + ByteUtil.byteArrayToHexString(byteBuffer.array()));
        return SmartDeviceConstant.PARSE_OK;
    }



    @Override
    public boolean parseDeviceStatus(byte[] _data) {
        OutPutMessage.LogCatInfo("电暖炉", "同步状态接收数据：" + ByteUtil.byteArrayToHexString(_data));

        if (_data.length < 29) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SocketHead.SocketHeadData headData = SocketHead.parseSocketHead(_data);

        if (headData != null) {
            byte[] data = headData.data;
            if (data.length < 22) {
                return SmartDeviceConstant.PARSE_ERROR_LENGTH;
            }
            OutPutMessage.LogCatInfo("电暖炉", "同步状态接收数据：" + ByteUtil.byteArrayToHexString(data)+"   "+data.length);
            // 电源
            powerSupply = (data[0] & 0x80) != 0;
            // 室温控制方式
            roomTempControlMode = (byte) ((data[0] & 0x20) >> 5);
            // 加热棒是否在加热
            isHeat = (data[0] & 0x10) != 0;
            // 循环泵是否在工作
            isWaterPumpWorking = (data[0] & 0x08) != 0;
            // 工作模式
            workingMode = (byte) (data[0] & 0x07);

            byte[] temp = new byte[2];
            // 室内温度
            System.arraycopy(data, 2, temp, 0, temp.length);
            roomTemp = byteArrayToInt2(temp) / 10;

            // 室内设定温度
            System.arraycopy(data, 4, temp, 0, temp.length);
            roomTempSet = byteArrayToInt(temp) / 10;

            // 出水温度
            System.arraycopy(data, 6, temp, 0, temp.length);
            effluentTemp = byteArrayToInt2(temp)/ 10;

            // 水温设定温度
            System.arraycopy(data, 8, temp, 0, temp.length);
            effluentTempSet = byteArrayToInt(temp) / 10;

            // 回水温度
            System.arraycopy(data, 10, temp, 0, temp.length);
            backwaterTemp = byteArrayToInt2(temp)/ 10;

            // 回水设定温度
            System.arraycopy(data, 12, temp, 0, temp.length);
            backwaterTempSet = byteArrayToInt(temp) / 10;

            // 当前功率
            System.arraycopy(data, 14, temp, 0, temp.length);
            power = byteArrayToInt(temp);

            // 当前电压
            System.arraycopy(data, 16, temp, 0, temp.length);
            voltage = byteArrayToInt(temp);

            // 当前电流
            System.arraycopy(data, 18, temp, 0, temp.length);
            electricity = (double) byteArrayToInt(temp) / 1000;
        }

        OutPutMessage.LogCatInfo("电暖炉", "电源：" + powerSupply +
                "___室温控制方式：" + roomTempControlMode + "___加热棒是否在加热：" + isHeat +
                "___循环泵是否在工作：" + isWaterPumpWorking + "___工作模式：" + workingMode +
                "___室内温度：" + roomTemp + "___室内设定温度：" + roomTempSet +
                "___出水温度：" + effluentTemp + "___出水设定温度：" + effluentTempSet +
                "___回水温度：" + backwaterTemp + "___回水设定温度：" + backwaterTempSet +
                "___当前功率：" + power + "___当前电压：" + voltage + "___当前电流：" + electricity);
        return SmartDeviceConstant.PARSE_OK;
    }

    @Override
    public void parseNetworkData(boolean isRead, byte[] data, int msgID) {
        getSmartDeviceLogic().parsePublicNetWorkData(isRead, data, msgID);
        OutPutMessage.LogCatInfo("电暖炉接收数据:", msgID + "  "+data.length);
    }

    @Override
    public boolean sendDeviceParameter() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(new byte[4]);
        byteBuffer.put(parameterDatas.get(0).getParameter());
        byteBuffer.put(parameterDatas.get(1).getParameter());
        byteBuffer.put(parameterDatas.get(2).getParameter());
        byteBuffer.put(parameterDatas.get(3).getParameter());

        byte[] data = SocketHead.buildSocketHead(byteBuffer.array(),
                (byte) SmartDeviceConstant.MsgID.DEVICE_PARAMETER_SET);

        getSmartDeviceLogic().sendControllerByte(SmartDeviceConstant.MsgID.DEVICE_PARAMETER_SET, data);

        OutPutMessage.LogCatInfo("电暖炉", "发送数据：" + ByteUtil.byteArrayToHexString(data));
        return SmartDeviceConstant.PARSE_OK;
    }
    @Override
    public boolean parseDeviceParameter(byte[] _data) {
        OutPutMessage.LogCatInfo("电暖炉", "读取参数接收数据：" + ByteUtil.byteArrayToHexString(_data)+"   "+_data.length);

        if (_data.length < 27) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SocketHead.SocketHeadData headData = SocketHead.parseSocketHead(_data);
        if (headData != null) {
            byte[] data = headData.data;
            OutPutMessage.LogCatInfo("电暖炉", "读取参数接收数据：" + ByteUtil.byteArrayToHexString(data) + "   " + data.length);
            if (data.length < 20) {
                return SmartDeviceConstant.PARSE_ERROR_LENGTH;
            }
            parameterDatas = new ArrayList<>();

            for (int i=0;i<4;i++){
                byte[] newdata = new byte[4];
                System.arraycopy(data, i*4+4,newdata , 0, 4);

                ParameterData parameterData = new ParameterData();
                parameterData.setParameter(newdata);
                parameterDatas.add(parameterData);

            }



        }
        return SmartDeviceConstant.PARSE_OK;
    }



    /**
     * 将字节数组转换成int类型
     *
     * @param bytes 字节数据
     * @return int类型
     */
    private static int byteArrayToInt(byte[] bytes) {
        return ((bytes[1] & 0xff) << 8 | bytes[0] & 0xff);
    }
    /**
     * 将字节数组转换成int类型
     * 有符号
     * @param bytes 字节数据
     * @return int类型
     */
    private static int byteArrayToInt2(byte[] bytes) {
        return ((bytes[1] ) << 8 | bytes[0] );
    }

}
