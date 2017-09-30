package com.vision.smarthomeapi.dal;


import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceState;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 心跳管理
 *
 */
public class SmartDeviceHeartbeatThread extends Thread {

    public static final String TAG = "心跳";

    private SmartDevice smartDevice;

    private int resendTimes;

    private boolean pause;

    /**
     * 设置心跳次数
     *
     * @param resendTimes 心跳次数
     */
    public void setResendTimes(int resendTimes) {
        this.resendTimes = resendTimes;
    }

    /**
     * 构造心跳线程
     *
     * @param smartDevice 要开启心跳设备
     */
    public SmartDeviceHeartbeatThread(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
    }

    /**
     * 计算心跳时间
     *
     * @param index
     * @return
     */
    private int headTimeSeconds(int index) {
        int headTimeSECONDS = 2;
        if (index >= 2) {
            if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                headTimeSECONDS = 2;
            } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
                headTimeSECONDS = 40;
            } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                headTimeSECONDS = 5;
            }
        } else {
            if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                headTimeSECONDS = 40;
            } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
                headTimeSECONDS = 40;
            } else if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                headTimeSECONDS = 40;
            }
        }
        return headTimeSECONDS;
    }

    /**
     * 设置暂停状态
     *
     * @param pause
     */
    public void setPause(boolean pause) {
        this.pause = pause;
    }

    /**
     * 是否为暂停状态
     *
     * @return
     */
    public boolean isPause() {
        return pause;
    }

    @Override
    public void run() {
        super.run();
        while (!Thread.currentThread().isInterrupted()) {
            if (pause) {
                continue;
            } else {
                try {
                    TimeUnit.SECONDS.sleep(headTimeSeconds(resendTimes));
                } catch (InterruptedException e) {
                    return;
                }
                if (smartDevice != null) {
                    if (smartDevice.getSmartDeviceLogic().isReceive()) { // 连接异常
                        //如果心跳断开，++
                        resendTimes++;
                    }
                    smartDevice.getSmartDeviceLogic().sendHeartbeatData();
                    OutPutMessage.LogCatInfo(SmartDeviceHeartbeatThread.TAG, "设备名称：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!" + "发送次数：" + resendTimes);
                    //重置心跳状态
                    if (resendTimes > 4) { //如果连接次数大于5次，设备掉线
                        notLineEvent(smartDevice);
                        resendTimes = 0;
                    }
                } else {//如果设备为null，自己关闭线程
                    resendTimes = 0;
                    Thread.currentThread().interrupt();//关闭线程
                }
            }
        }
    }

    /**
     * 掉线处理
     *
     * @param smartDevice
     */
    private void notLineEvent(SmartDevice smartDevice) {
        //if (smartDevice.isExistChildDevice()) {//如果属于网关，则清除掉子设备标记
        List<SmartDevice> smartDeviceList = SmartDeviceManage.defaultManager().findGatewayChildDeviceList(smartDevice.getSmartDeviceLogic().getDeviceMac());//获取网关列表

        for (int i = 0; i < smartDeviceList.size(); i++) {
            SmartDevice sd = smartDeviceList.get(i);
            if (sd.getSmartDeviceLogic().getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                sd.getSmartDeviceLogic().deleteNetWork(Constant.NetWork.GATEWAY_NET, true);//去除掉子设备网关标记
            }
        }
        //}
        OutPutMessage.LogCatInfo(SmartDeviceHeartbeatThread.TAG, "设备名称：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!" + "发送事件：");
//        SmartDeviceState smartDeviceState = smartDevice.getSmartDeviceState();//向状态机发送事件
//        smartDeviceState.deviceEvent(smartDevice, SmartDeviceConstant.EventStatus.DEVICE_DISCONNECT);
        int netWork = smartDevice.getSmartDeviceLogic().deleteHighNetWork();
        OutPutMessage.LogCatInfo(SmartDeviceHeartbeatThread.TAG, "CONNECT_DEVICE--->事件总结-->" + netWork);


        if (netWork == 0) {
            OutPutMessage.LogCatInfo("心跳下线协议 心跳断开",smartDevice.getSmartDeviceLogic().getDeviceName()+"下线");
          //  OutPutMessage.showToast("设备名称:" + smartDevice.getSmartDeviceLogic().getDeviceName() + ",已经掉线,请重新检索...");
            smartDevice.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.DISCONNECT_DEVICE);
            //NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_NOT_LINE, null, smartDevice);
          //  OutPutMessage.LogCatInfo("心跳下线协议",smartDevice.getSmartDeviceLogic().getDeviceName()+"下线");
//        if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
          //  OutPutMessage.LogCatInfo("心跳下线协议",smartDevice.getSmartDeviceLogic().getDeviceName()+"下线通知平台");
//            smartDevice.getSmartDeviceLogic().sendDeviceNotOnline();
//        }
        }


    }
}
