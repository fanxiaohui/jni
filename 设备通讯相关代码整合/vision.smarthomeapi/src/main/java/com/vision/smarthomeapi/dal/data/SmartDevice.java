package com.vision.smarthomeapi.dal.data;


import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;

/**
 * 设备总类
 */
public abstract class SmartDevice{

    public int sqlID;

//    public static String PASS_WORD = "wabjtam123456789";

    private SmartDeviceLogic smartDeviceLogic;

    /**
     * 初始化数据
     *
     * @param smartDevice
     */
    public void initSmartDeviceData(SmartDevice smartDevice) {
        smartDeviceLogic = new SmartDeviceLogic(smartDevice);
        smartDeviceLogic.initSmartDeviceState(smartDevice);


    }


    /**
     * 获取设备状态
     *
     * @return
     */
    public SmartDeviceState getSmartDeviceState() {
        return smartDeviceLogic.getSmartDeviceState();
    }

    /**
     * 获取设备参数
     *
     * @return
     */
    public SmartDeviceLogic getSmartDeviceLogic() {
        return smartDeviceLogic;
    }

    /**
     * 发送->控制状态改变
     *
     * @return 返回字节码
     */
    public abstract boolean sendDeviceStatus();

    /**
     * 解析->运行状态同步
     *
     * @param data
     * @return
     */
    public abstract boolean parseDeviceStatus(byte[] data);

    public boolean parseDeviceParameter(byte[] data) {
        OutPutMessage.LogCatInfo("SmartDevice", "读取参数接收数据：" + ByteUtil.byteArrayToHexString(data));
        return false;
    }

    /**
     * 解析数据体
     */
    public abstract void parseNetworkData(boolean isRead, byte[] data, int msgID);


    @Override
    public String toString() {
        return smartDeviceLogic.toString();
    }
}
