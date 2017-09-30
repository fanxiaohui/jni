package com.vision.smarthomeapi.dal.data;


import android.support.annotation.NonNull;

import com.smarthome.head.SmartHomeData;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.DeviceUpgradeManage;
import com.vision.smarthomeapi.bll.manage.ResendQueueManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.DeviceParseHeadData;
import com.vision.smarthomeapi.dal.DeviceSendHeadData;
import com.vision.smarthomeapi.dal.function.UpdateData;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;


/**
 * 设备状态机管理
 */
public enum SmartDeviceState {

    /**
     * 初始化状态
     **/
    INIT_DEVICE {
        @Override
        public void deviceStateEntrance(SmartDevice smartDevice) {
            smartDevice.getSmartDeviceLogic().setIsReceive(false);//心跳状态初始化
            smartDevice.getSmartDeviceLogic().setSequence(0);//清空大包号
           // smartDevice.getSmartDeviceLogic().setOnLine(0);
            smartDevice.getSmartDeviceLogic().updateVisible();//设置不可建
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "INIT_DEVICE--->deviceStateEntrance-->");
        }

        @Override
        public boolean deviceSendData(@NonNull DeviceSendHeadData deviceSendHeadData, boolean isResend) {
            return false;
        }

        @Override
        public void deviceReceiveData(@NonNull DeviceParseHeadData deviceParseHeadData) {
            SmartDevice smartDevice = deviceParseHeadData.getSmartDevice();
            if (deviceParseHeadData.getNetWork() == Constant.NetWork.LAN_NET) {
                smartDevice.getSmartDeviceLogic().parseLanDeviceInfo(deviceParseHeadData.getData());//交给设备去处理
            } else if (deviceParseHeadData.getNetWork() == Constant.NetWork.WAN_NET) {
                smartDevice.getSmartDeviceLogic().parseWanDeviceInfo(deviceParseHeadData.getWanDevice());
            }
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "INIT_DEVICE--->deviceReceiveData-->");
        }


        @Override
        public boolean deviceEvent(SmartDevice smartDevice, @SmartDeviceConstant.EventStatus.Event int event) {

            return false;
        }

        @Override
        public void deviceStateExit(SmartDevice smartDevice) {

        }
    },
    /**
     * 登录状态
     **/
    LOGIN_DEVICE {
        @Override
        public void deviceStateEntrance(SmartDevice smartDevice) {
            smartDevice.getSmartDeviceLogic().updateVisible();
            smartDevice.getSmartDeviceLogic().setSequence(0);//清空发送大包号
            smartDevice.getSmartDeviceLogic().clearSendPacketNumber();//清理小包号
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "最终登录"+smartDevice.getSmartDeviceLogic().getDeviceNetWork());
            smartDevice.getSmartDeviceLogic().sendDeviceLoginData(true);//直接去登录
//            smartDevice.getSmartDeviceLogic().setOnLine(0);
            smartDevice.getSmartDeviceLogic().setReceiveSequence(0);//清空接收大包好
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "LOGIN_DEVICE--->deviceStateEntrance-->");
        }

        @Override
        public boolean deviceSendData(@NonNull DeviceSendHeadData smartDeviceHeadData, boolean isResend) {

            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "最终登录发送数据！！！！！！！" + smartDeviceHeadData.getMac());
            boolean isSend = false;
            sendMsgToNetWork(smartDeviceHeadData, isResend);
            return isSend;
        }

        @Override
        public void deviceReceiveData(@NonNull DeviceParseHeadData deviceParseHeadData) {
//            SmartHomeData smartHomeData = deviceParseHeadData.getSmartHomeData();
//            SmartDevice smartDevice = deviceParseHeadData.getSmartDevice();
//            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "LOGIN_DEVICE--->deviceReceiveData-->" + smartDevice.getDeviceMac() + "-->MSG:" + Integer.toHexString(smartHomeData.msgID) + "!ISACK-->" + smartHomeData.isACK);
//
//            //不是登陆返回,属于无效数据
//            if (isDeviceData(this, smartHomeData.msgID)) {
//                if (smartHomeData.isACK) {//主动发送控制指令，回复
//                    smartDevice.parseStateReturnCode(smartHomeData);
//                }
//            }

        }

        @Override
        public boolean deviceEvent(SmartDevice smartDevice, @SmartDeviceConstant.EventStatus.Event int event) {
            switch (event) {
                case SmartDeviceConstant.EventStatus.LOGIN_TIME_OUT://登录超时
                    if (smartDevice.getSmartDeviceLogic().deleteHighNetWork() == 0) {
                        OutPutMessage.showToast("当前" + smartDevice.getSmartDeviceLogic().getDeviceName() + ",登录超时,请检查设备是否在线!");
//                        smartDevice.setSmartDeviceStateManager(DISCONNECT_DEVICE);
                    }
                    break;
            }
            return false;
        }

        @Override
        public void deviceStateExit(SmartDevice smartDevice) {

        }
    },
    /**
     * 连接状态
     **/
    CONNECT_DEVICE {
        @Override
        public void deviceStateEntrance(SmartDevice smartDevice) {
            smartDevice.getSmartDeviceLogic().setSortState(SmartDeviceConstant.OrderStatus.CONNECT_DEVICE);//记录当前状态，排序使用
            smartDevice.getSmartDeviceLogic().updateVisible();
            sendDeviceInfo(smartDevice);
//            smartDevice.getSmartDeviceLogic().setOnLine(1);
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
        }

        @Override
        public boolean deviceSendData(@NonNull DeviceSendHeadData smartDeviceHeadData, boolean isResend) {
            return sendMsgToNetWork(smartDeviceHeadData, isResend);
        }

        @Override
        public void deviceReceiveData(@NonNull DeviceParseHeadData deviceParseHeadData) {
            SmartHomeData smartHomeData = deviceParseHeadData.getSmartHomeData();
            SmartDevice smartDevice = deviceParseHeadData.getSmartDevice();
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "CONNECT_DEVICE--->deviceReceiveData-->" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "-->MSG:" + Integer.toHexString(smartHomeData.msgID) + "!ISACK-->" + smartHomeData.isACK);

            if (isDeviceData(this, smartHomeData.msgID)) {
                if (smartHomeData.isACK) {//主动发送控制指令，回复
                    smartDevice.getSmartDeviceLogic().parseStateReturnCode(smartHomeData);
                } else {//不是应答，被动接收,消息类
                   // OutPutMessage.LogCatInfo("result接收","写入推送的包号：" + smartHomeData.sequence);
                    OutPutMessage.LogCatInfo("30指令接收","2通道");
                    smartDevice.getSmartDeviceLogic().parseResponseNoAckData(smartHomeData);
                }
            }
        }

        @Override
        public boolean deviceEvent(SmartDevice smartDevice, @SmartDeviceConstant.EventStatus.Event int event) {
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "CONNECT_DEVICE--->deviceEvent-->" + event);

            switch (event) {
                case SmartDeviceConstant.EventStatus.DEVICE_BINDING://设备绑定
                    if (!smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.BINDING)) {
                        smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.BINDING);
                    }
                    break;
                case SmartDeviceConstant.EventStatus.DEVICE_UN_BINDING://设备解绑
                    if (smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.BINDING)) {
                        smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.BINDING);
                    }
                    break;
                case SmartDeviceConstant.EventStatus.DEVICE_DISCONNECT://设备断线

                    break;
                case SmartDeviceConstant.EventStatus.WEB_SOCKET_DISCONNECT://WebSocket断开

                    break;

            }
            return false;
        }

        @Override
        public void deviceStateExit(SmartDevice smartDevice) {

        }
    },
    /**
     * 未连接状态
     **/
    DISCONNECT_DEVICE {
        @Override
        public void deviceStateEntrance(SmartDevice smartDevice) {
            smartDevice.getSmartDeviceLogic().stopHeartbeatThread();//退出心跳
//            smartDevice.getSmartDeviceLogic().setOnLine(0);
            if (StringUtil.getUserID().equals("")) {
                smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.BINDING);
            }
            OutPutMessage.LogCatInfo("设备状态刷新", "设备状态刷新DISCONNECT_DEVICE---------->" + smartDevice.toString());
            smartDevice.getSmartDeviceLogic().updateVisible();
            OutPutMessage.LogCatInfo("CPU升级","删除升级标记1");
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.UPGRADE_CPU);
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.UPGRADE_MCU);
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.TIMING);
            smartDevice.getSmartDeviceLogic().setSortState(SmartDeviceConstant.OrderStatus.DISCONNECT_DEVICE);//记录当前状态，排序使用
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "DISCONNECT_DEVICE--->deviceStateEntrance-->");
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
        }

        @Override
        public boolean deviceSendData(@NonNull DeviceSendHeadData smartDeviceHeadData, boolean isResend) {

            //下线通知去发送
            return sendMsgToNetWork(smartDeviceHeadData, isResend);
        }

        @Override
        public void deviceReceiveData(@NonNull DeviceParseHeadData deviceParseHeadData) {
            SmartHomeData smartHomeData = deviceParseHeadData.getSmartHomeData();
            SmartDevice smartDevice = deviceParseHeadData.getSmartDevice();
            if (isDeviceData(this, smartHomeData.msgID)) {
                if (smartHomeData.isACK) {//主动发送控制指令，回复
                    smartDevice.getSmartDeviceLogic().parseStateReturnCode(smartHomeData);
                } else {//不是应答，被动接收,消息类
                    // OutPutMessage.LogCatInfo("result接收","写入推送的包号：" + smartHomeData.sequence);
                    OutPutMessage.LogCatInfo("30指令接收","3通道");
                    smartDevice.getSmartDeviceLogic().parseResponseNoAckData(smartHomeData);
                }
            }
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "DISCONNECT_DEVICE--->deviceReceiveData-->");
        }

        @Override
        public boolean deviceEvent(SmartDevice smartDevice, @SmartDeviceConstant.EventStatus.Event int event) {
            return false;
        }

        @Override
        public void deviceStateExit(SmartDevice smartDevice) {

        }
    };

    /**
     * 设备状态机进入
     **/
    public abstract void deviceStateEntrance(SmartDevice smartDevice);

    /**
     * 验证发送消息
     **/
    public abstract boolean deviceSendData(@NonNull DeviceSendHeadData smartDeviceHeadData, boolean isResend);

    /**
     * 验证解析消息
     **/
    public abstract void deviceReceiveData(@NonNull DeviceParseHeadData deviceParseHeadData);

    /**
     * 状态机事件处理
     **/
    public abstract boolean deviceEvent(SmartDevice smartDevice, @SmartDeviceConstant.EventStatus.Event int event);

    /**
     * 设备状态机退出
     **/
    public abstract void deviceStateExit(SmartDevice smartDevice);


    public void sendDeviceInfo(SmartDevice smartDevice) {
        UpdateData updateData = smartDevice.getSmartDeviceLogic().getUpdateData();
        boolean isReadUpdate = smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)
                || smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)
                || smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET);
        OutPutMessage.LogCatInfo("升级管理", "设备Mac地址->" + smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!isReadUpdate->" + isReadUpdate + "!!!ChipType->" +
                updateData.getDeviceChipType());
        //请求升级信息，和 设备本身不是广域网
        if (isReadUpdate
                && updateData.getDeviceChipType() != 0) {
            //设备类型_芯片型号
            DeviceUpgradeManage.defaultDeviceUpgradeManager().isUpgradeDevice(smartDevice);//检测是否升级

        }

        //状态查询  73.74指令弃用。报警通过平台获取
        smartDevice.getSmartDeviceLogic().sendQueryDeviceInfo();
//        smartDevice.getSmartDeviceLogic().sendQueryDeviceAlarm();
//        smartDevice.getSmartDeviceLogic().sendQueryDeviceFaulty();

        if (!smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {
            smartDevice.getSmartDeviceLogic().startHeartbeatThread();//不是子设备,开启心跳
        }
        //TODO 已还原为谁发谁记
//        else{//子设备清空包号
//            smartDevice.getSmartDeviceLogic().setSequence(0);
//            smartDevice.getSmartDeviceLogic().setReceiveSequence(0);
//        }

//        if (smartDevice instanceof IDeviceTime) {//查询校时
//            IDeviceTime iDeviceTime = ((IDeviceTime) smartDevice);
//            iDeviceTime.sendDeviceCheckTime(SmartDeviceConstant.OperateState.Read);
//            iDeviceTime.sendDeviceTime(SmartDeviceConstant.OperateState.Read);
//        }

        if (smartDevice.getSmartDeviceLogic().isSubDeviceList()) {
            smartDevice.getSmartDeviceLogic().sendSubDeviceListInfo((byte) 0);
        }

        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_STATE_TAG, "CONNECT_DEVICE--->deviceStateEntrance-->");
    }

    /**
     * 发送数据
     *
     * @param deviceSendHeadData 组装数据头与数据体
     * @param isResend           是否重发
     */
    protected boolean sendMsgToNetWork(DeviceSendHeadData deviceSendHeadData, boolean isResend) {

        OutPutMessage.LogCatInfo("websocket发送消息", deviceSendHeadData.getMac() + "   " + deviceSendHeadData.getMsgID() + "  " + isResend);
        if (!Controller.defaultController().APPSendCode()) {
            return false;
        }
        if (deviceSendHeadData != null) {//如果查询无此设备，则data应该为null
            NetworkMessage networkMessage = new NetworkMessage(true, deviceSendHeadData.getHeadData(), deviceSendHeadData.getMac(),
                    deviceSendHeadData.getNetWork(), deviceSendHeadData.getDeviceIP());
            Controller.defaultController().sendCode(networkMessage);//发送消息

            //是否重发
            if (isResend) {
                ResendQueueManage.defaultQueue().add(deviceSendHeadData, 3);
            }

        } else {
            return false;
        }
        return true;
    }


    /**
     * 验证数据是否能发出或者是否能解析
     *
     * @param smartDeviceState 状态机
     * @param msgID            消息类型
     * @return
     */
    protected boolean isDeviceData(SmartDeviceState smartDeviceState, int msgID) {
        boolean isSendData = false;
        switch (smartDeviceState) {
            case INIT_DEVICE:
                isSendData = true;
                return isSendData;
            case LOGIN_DEVICE:
                switch (msgID) {
                    case SmartDeviceConstant.MsgID.DEVICE_LOGIN:
                        isSendData = true;
                        break;
                }
                return isSendData;
            case CONNECT_DEVICE:
                switch (msgID) {
                    case SmartDeviceConstant.MsgID.DEVICE_LOGIN:
                        isSendData = false;
                        break;
                    case SmartDeviceConstant.MsgID.LAN_RETRIEVE:
                        isSendData = false;
                        break;
                    default:
                        isSendData = true;
                        break;
                }
                return isSendData;
            case DISCONNECT_DEVICE:
                isSendData = true;
                return isSendData;
        }
        return isSendData;
    }

}
