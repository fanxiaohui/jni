package com.vision.smarthomeapi.bll.manage;

import android.content.ContentValues;
import android.text.TextUtils;

import com.smarthome.head.SmartHomeData;
import com.vision.smarthomeapi.bean.RSecurityDevice;
import com.vision.smarthomeapi.bean.RSecurityTypeInfo;
import com.vision.smarthomeapi.bean.SecurityTypeInfo;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.DeviceParseHeadData;
import com.vision.smarthomeapi.dal.DeviceSendHeadData;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.HeadData;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.data.SmartDeviceLogic;
import com.vision.smarthomeapi.dal.data.SmartDeviceState;
import com.vision.smarthomeapi.dal.function.AlarmData;
import com.vision.smarthomeapi.dal.function.UpdateData;
import com.vision.smarthomeapi.dal.impl.SmartInitDevice;
import com.vision.smarthomeapi.dal.sql.SmartDeviceSQL;
import com.vision.smarthomeapi.dal.user.SecurityUserInfo;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备管理类
 */
public class SmartDeviceManage {

    public final static String DEVICE_TAG = "设备解析";
    public final static String DEVICE_STATE_TAG = "设备状态功能";
    public static SmartDeviceManage control;
    /**
     * 传递设备MAC到下一界面
     */
    public final static String INTENT_MAC = "mac";

    public static SmartDeviceManage defaultManager() {
        return control == null ? control = new SmartDeviceManage() : control;
    }

    private ConcurrentHashMap<String, SmartDevice> mDeviceHashMap;//本地

    private ConcurrentHashMap<String, SmartDevice> mChildDevice;//记录子设备

    private ConcurrentHashMap<String, Boolean> logDevice;//是否显示上线

    //本地列表
    public List<SmartDevice> sqlDeviceList;


    private SmartInitDevice smartInitDevice;


    public static Map<String, SecurityTypeInfo> typeMap;
    private static Map<Integer, SecurityTypeInfo> typeMapForId;


    public static String getModel(int type, int version) {
        String model = "";

        SecurityTypeInfo info = typeMap.get(type + "-" + version);
        if (info != null) {
            model = info.getVersionCode();
        }
        return model;
    }

    public static String getInfo(int type, int version) {
        String info = "";

        SecurityTypeInfo securityTypeInfo = typeMap.get(type + "-" + version);
        if (securityTypeInfo != null) {
            info = securityTypeInfo.getInfo();
        }
        return info;
    }

    /**
     * 获取设备名称
     * 扫描设备二维码、显示设备名称使用
     *
     * @param type    设备类型
     * @param version 设备型号编码
     * @return 设备名称
     */
    public static String getDeviceName(int type, int version) {
        SecurityTypeInfo securityTypeInfo = typeMap.get(type + "-" + version);
        if (securityTypeInfo != null) {
            return securityTypeInfo.getDevName();
        }
        return "";
    }

    /**
     * 获取设备通讯类型
     *
     * @param type    设备类型
     * @param version 设备型号编码
     * @return 设备通讯类型
     */
    public static int getCommType(int type, int version) {
        SecurityTypeInfo securityTypeInfo = typeMap.get(type + "-" + version);
        if (securityTypeInfo != null) {
            return securityTypeInfo.getCommunicationType();
        }
        return 0;
    }

    public void setTypeInfo(RSecurityTypeInfo rSecurityTypeInfo) {

        if (rSecurityTypeInfo != null) {

            for (int i = 0; i < rSecurityTypeInfo.getList().length; i++) {

                typeMap.put(rSecurityTypeInfo.getList()[i].getDevTypeId() + "-" + rSecurityTypeInfo.getList()[i].getCode(), rSecurityTypeInfo.getList()[i]);
                typeMapForId.put(rSecurityTypeInfo.getList()[i].getId(), rSecurityTypeInfo.getList()[i]);

                OutPutMessage.LogCatInfo("类型汇总",rSecurityTypeInfo.getList()[i].getId() +"   "+rSecurityTypeInfo.getList()[i].getDevName());
            }

            smartInitDevice.setTypeInfo(rSecurityTypeInfo);
        }
    }

    private SmartDeviceManage() {
        mDeviceHashMap = new ConcurrentHashMap<>();
        mChildDevice = new ConcurrentHashMap<>();
        sqlDeviceList = new ArrayList<>();
        logDevice = new ConcurrentHashMap<>();
        typeMap = new HashMap<>();
        typeMapForId = new HashMap<>();
    }

    public static Map<Integer, SecurityTypeInfo> getTypeMapForId() {
        return typeMapForId;
    }

    public static void setTypeMapForId(Map<Integer, SecurityTypeInfo> typeMapForId) {
        SmartDeviceManage.typeMapForId = typeMapForId;
    }

    /**
     * 添加单个设备
     *
     * @param newSmartDevice 被添加设备
     * @param netLan         网络类型
     */
    public int addDevice(SmartDevice newSmartDevice, int netLan) {
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, "添加设备" + newSmartDevice.toString());
        //没有网络状态则返回错误
        OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, "添加设备" + newSmartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!!" + netLan);

        if (netLan == 0) {
            return SmartDeviceConstant.addError;
        }
        if (StringUtil.isEmpty(newSmartDevice.getSmartDeviceLogic().getDeviceMac())) {
            return SmartDeviceConstant.addError;
        }
        //添加设备没有mac返回错误
        newSmartDevice.getSmartDeviceLogic().setDeviceNetWork(netLan);//设置网络状态
        //设置设备名称
        SmartDevice oldSmartDevice = mDeviceHashMap.get(newSmartDevice.getSmartDeviceLogic().getDeviceMac());

        //如果以前存在
        if (oldSmartDevice != null) {
            //先查看本地是否存在zigBee
            boolean isZigBee = findSqlZigBee(newSmartDevice, oldSmartDevice);
            if (isZigBee) {
                return SmartDeviceConstant.addExist;
            }
            smartDeviceClone(oldSmartDevice, newSmartDevice,true);//复制参数

        } else {

            if (newSmartDevice.getSmartDeviceLogic().isSQLExist()) {
                addNewDevice(netLan, newSmartDevice);//设备添加
                newSmartDevice.getSmartDeviceLogic().loginDevice(true, netLan);
            }
            OutPutMessage.LogCatInfo(SmartDeviceManage.DEVICE_TAG, "设备 ：" + newSmartDevice.toString());
        }

//        NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);

        return SmartDeviceConstant.addOk;
    }


    /**
     * 删除当前用户下设备
     *
     * @param userID
     * @param type
     */
    public void deleteUserIDDeviceList(String userID, int type) {
        if (mDeviceHashMap.size() == 0 && userID.equals("")) {
            return;
        }
        switch (type) {
            case 1://删除单个用户设备列表
                DataSupport.deleteAll(SmartDeviceSQL.class, "ownerUserID = ?", userID);
                break;
            case 2://解散家庭全部删除
                DataSupport.deleteAll(SmartDeviceSQL.class, "ownerUserID != ?", userID);
                break;
        }
    }

    /**
     * 清理所有网络状态
     */
    public void clearNetWork() {
        OutPutMessage.LogCatInfo("设备状态刷新", mDeviceHashMap.size() + "的长度");
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            smartDevice.getSmartDeviceLogic().setSendInfo(false);
            smartDevice.getSmartDeviceLogic().deleteNetWork(Constant.NetWork.LAN_NET | Constant.NetWork.WAN_NET | Constant.NetWork.GATEWAY_NET, true);
        }
    }

    /**
     * 清理所有网络状态
     */
    public void sendZigBeeLogIn() {
        OutPutMessage.LogCatInfo("设备状态刷新", mDeviceHashMap.size() + "的长度");
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            smartDevice.getSmartDeviceLogic().setSendInfo(false);
            if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
                smartDevice.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);
            }
        }
    }

    /**
     * 清理是否已经读取设备状态
     */
    public void clearSendInfo() {
        OutPutMessage.LogCatInfo("设备状态刷新", mDeviceHashMap.size() + "的长度");
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            smartDevice.getSmartDeviceLogic().setSendInfo(false);
        }
    }

    /**
     * 初始化设备相关列表
     */
    public void initDeviceData() {
        String userID = StringUtil.getUserID();
        if (sqlDeviceList.size() == 0) {
            mDeviceHashMap.clear();
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
            return;
        }
        for (SmartDevice smartDeviceSql : sqlDeviceList) {
            SmartDevice device = smartDeviceSql;
            //多用户切换后，当前userID与本地userID是否相同，如果不包含直接添加
            if (!mDeviceHashMap.containsKey(device.getSmartDeviceLogic().getDeviceMac())) {
                if (device.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ZIG_BEE_DEVICES)) {
                    device.getSmartDeviceLogic().setDeviceNetWork(Constant.NetWork.ZIG_BEE_NET);
//                    device.getSmartDeviceLogic().setDevicePassword(SmartDevice.PASS_WORD);
                    mDeviceHashMap.put(device.getSmartDeviceLogic().getDeviceMac(), device);
                } else if (!userID.equals("") && device.getSmartDeviceLogic().getUserID().equals(userID)) {
                    mDeviceHashMap.put(device.getSmartDeviceLogic().getDeviceMac(), device);
                }
            } else {
                SmartDevice smartDevice = mDeviceHashMap.get(device.getSmartDeviceLogic().getDeviceMac());
                //如果包含并且是zigBee设备则修改状态
                if (device.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ZIG_BEE_DEVICES)) {
                    smartDevice.getSmartDeviceLogic().setDeviceNetWork(Constant.NetWork.ZIG_BEE_NET);
                    smartDevice.getSmartDeviceLogic().setDeviceName(device.getSmartDeviceLogic().getDeviceName());
                    smartDevice.getSmartDeviceLogic().setDeviceMac(device.getSmartDeviceLogic().getDeviceMac());
//                    smartDevice.getSmartDeviceLogic().setDevicePassword(SmartDevice.PASS_WORD);
                } else if (userID.equals("") || !device.getSmartDeviceLogic().getUserID().equals(userID)) {
                    mDeviceHashMap.remove(device.getSmartDeviceLogic().getDeviceMac());
                }
            }
        }

        OutPutMessage.LogCatInfo(DEVICE_TAG, "初始化设备--->" + mDeviceHashMap);
    }

    public List<SmartDeviceSQL> queryDeviceList() {
        return DataSupport.where("userId = ?", StringUtil.getUserID()).find(SmartDeviceSQL.class);
    }

    /**
     * 查询设备列表
     *
     * @return 本地设备列表
     */
    public void queryDeviceTable() {
        sqlDeviceList.clear();
        List<SmartDeviceSQL> smartDeviceList = DataSupport.where("userId = ?", StringUtil.getUserID()).find(SmartDeviceSQL.class);
        for (int i = 0; i < smartDeviceList.size(); i++) {
            SmartDeviceSQL mSQLSmartDevice = smartDeviceList.get(i);
            if (smartInitDevice != null) {
                HeadData headData = new HeadData();
                headData.devVersion = mSQLSmartDevice.getDevVersion();
                headData.devType = mSQLSmartDevice.getDeviceType();
                headData.mSQLSmartDevice = mSQLSmartDevice;
                headData.netType = SQL;
                headData.isOver = false;
                if (i == (smartDeviceList.size() - 1)) {
                    headData.isOver = true;
                }
                smartInitDevice.smartDeviceDataInit(headData);
            }
        }

    }


    /**
     * 注册设备初始化事件
     *
     * @param smartInitDevice
     */
    public void setSmartInitDevice(SmartInitDevice smartInitDevice) {
        this.smartInitDevice = smartInitDevice;
    }

    private void addSqlDevice(SmartDevice smartDevice, HeadData headData) {
        SmartDeviceSQL mSQLSmartDevice = headData.mSQLSmartDevice;
        smartDevice.sqlID = mSQLSmartDevice.getId();
        smartDevice.getSmartDeviceLogic().setDeviceType(mSQLSmartDevice.getDeviceType());
        smartDevice.getSmartDeviceLogic().setDevVersion(mSQLSmartDevice.getDevVersion());
        smartDevice.getSmartDeviceLogic().setName(mSQLSmartDevice.getDeviceName());
        smartDevice.getSmartDeviceLogic().setDeviceMac(mSQLSmartDevice.getMac());
        smartDevice.getSmartDeviceLogic().addDeviceState(mSQLSmartDevice.getZigBee());
        smartDevice.getSmartDeviceLogic().setUserID(mSQLSmartDevice.getUserId());
        smartDevice.getSmartDeviceLogic().setOwnerUserID(mSQLSmartDevice.getOwnerUserID());
        smartDevice.getSmartDeviceLogic().setSQLExist(true);
        smartDevice.getSmartDeviceLogic().setIsfirstLogin(1);
        sqlDeviceList.add(smartDevice);

        if (headData.isOver) {
            initDeviceData();
        }
    }


    //广播次数
    private int index = 0;


    /**
     * 功能描述：发送局域网检索
     */
    public void sendLanRetrieve() {
        logDevice.clear();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                index++;
                DeviceSendHeadData smartDeviceHead = new DeviceSendHeadData("255.255.255.255");
                byte[] data = smartDeviceHead.sendLanRetrieveHeadData();
                NetworkMessage networkMessage = new NetworkMessage(true, data, "", Constant.NetWork.LAN_NET, smartDeviceHead.getDeviceIP());
                Controller.defaultController().sendCode(networkMessage);
                OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "发送广播");
                if (index == 5) {//广播次数
                    timer.cancel();
                    this.cancel();
                    index = 0;
                    Controller.isRetrieve = false;//重置广播标记
                }
            }
        }, 0, 1000);
    }

    /**
     * 解析数据回调接口
     *
     * @param networkMessage NetWork任务对象
     */
    public void parseNetWorkData(NetworkMessage networkMessage) {
        if (networkMessage == null) {
            throw new NullPointerException("this NetworkMessage is NullPointer");
        }
        switch (networkMessage.getNetWorkType()) {
            case Constant.NetWork.LAN_NET:
                parseSmartHeadInfo(networkMessage);
                break;
            case Constant.NetWork.WAN_NET:
                parseSmartHeadInfo(networkMessage);
                break;
            case Constant.NetWork.ZIG_BEE_NET:
                parseSmartHeadZigBeeInfo(networkMessage);
                break;
        }
    }

    /**
     * 无密钥数据
     *
     * @return
     */
    public byte[] noSecretKey() {
        byte[] bytes = new byte[16];
        return bytes;
    }

    /**
     * 验证头信息,并对头信息做业务处理，最后把正确数据交给设备类
     *
     * @param networkMessage net层返回数据
     */
    private void parseSmartHeadInfo(NetworkMessage networkMessage) {
        DeviceParseHeadData deviceParseHeadData = new DeviceParseHeadData(networkMessage.getNetWorkType());
        boolean isCheck = deviceParseHeadData.parseSmartHeadCheckInfo(networkMessage.getBinaryMsg());
        OutPutMessage.LogCatInfo("流程测试", "返回校验-->" + isCheck + "!!设备返回-->" + deviceParseHeadData.getMacStr() + "!!消息ID-->" + Integer.toHexString(deviceParseHeadData.getMsgID()));


        if (isCheck) {
            switch (deviceParseHeadData.getMsgID()) {
                case SmartDeviceConstant.MsgID.LAN_RETRIEVE:
                    int isOk = deviceParseHeadData.parseSmartHeadLanInfo(networkMessage.getBinaryMsg(), networkMessage.getIp());
                    switch (isOk) {
                        case 0:
                            if (smartInitDevice != null) {

                                HeadData headData = deviceParseHeadData.getHeadData();
                                headData.netType = LAN;

                                smartInitDevice.smartDeviceDataInit(headData);
                            }
                            break;
                        case 2004:
                            if (!logDevice.containsKey(deviceParseHeadData.getMacStr())) {
                                OutPutMessage.LogCatInfo("连接数", StringUtil.getMacAfterFour(deviceParseHeadData.getMacStr()) + ",当前设备连接数,已达到上限...");
                                logDevice.put(deviceParseHeadData.getMacStr(), true);
                            }
                            break;
                    }
                    break;
                case SmartDeviceConstant.MsgID.DEVICE_LOGIN:
                    OutPutMessage.LogCatInfo("局域网标记", "LAN_NET" + deviceParseHeadData.getMacStr() + "!!hui!!" +
                            deviceParseHeadData.getMsgID() +"!!!" + ByteUtil.byteArrayToHexString(networkMessage.getBinaryMsg()));

                    deviceParseHeadData.parseSmartHeadLoginInfo(networkMessage.getBinaryMsg());
                    break;
                default:
                    deviceParseHeadData.parseSmartHeadInfo(networkMessage.getBinaryMsg());
                    break;
            }
        }
    }


    /**
     * zigBee验证
     *
     * @param networkMessage net层返回数据
     */
    private void parseSmartHeadZigBeeInfo(NetworkMessage networkMessage) {
        DeviceParseHeadData deviceParseHeadData = new DeviceParseHeadData(networkMessage.getNetWorkType());
        boolean isCheck = deviceParseHeadData.parseSmartHeadCheckZigBeeInfo(networkMessage.getBinaryMsg(), networkMessage.getMac());
        if (isCheck) {
            switch (deviceParseHeadData.getMsgID()) {
                case SmartDeviceConstant.MsgID.DEVICE_LOGIN:
                    deviceParseHeadData.parseSmartHeadLoginInfo(networkMessage.getBinaryMsg());
                    break;
                default:
                    deviceParseHeadData.parseSmartHeadInfo(networkMessage.getBinaryMsg());
                    break;
            }
        }
    }

    /**
     * 局域网
     */
    public final static int LAN = 1;
    /**
     * 广域网
     */
    public final static int WAN = 2;
    /**
     * 本地
     */
    public final static int SQL = 3;

    /**
     * 解析广域网列表
     *
     * @param rDeviceList 广域网列表
     */
    public void parseWanNetDeviceList(RSecurityDevice[] rDeviceList) {
        if (rDeviceList != null) {
            List<SmartDevice> deviceWanNewList = new ArrayList<>();
            for (int i = 0; i < rDeviceList.length; i++) {
                RSecurityDevice wanDevice = rDeviceList[i];
                if (wanDevice != null) {
                    if (smartInitDevice != null) {
                        HeadData headData = new HeadData();
                        headData.wanDevice = wanDevice;

                        SecurityTypeInfo securityTypeInfo = typeMapForId.get(wanDevice.getDevType());
                        if (securityTypeInfo != null) {
                            headData.devVersion = securityTypeInfo.getCode();
                            headData.devType = securityTypeInfo.getDevTypeId();
                            headData.wanDevice.setControlType(securityTypeInfo.getControlType());
                        }
                        headData.communicationType = wanDevice.getCommunicationType();
                        headData.netType = WAN;
                        headData.deviceWanNewList = deviceWanNewList;
                        headData.isOver = false;
                        if (i == (rDeviceList.length - 1)) {//最后一个设备，用来处理本地是否删除的逻辑
                            headData.isOver = true;
                        }
                        smartInitDevice.smartDeviceDataInit(headData);


                    }
                }
            }

            //本地存储的设备检查
//            OutPutMessage.LogCatInfo("删除设备", "-------------------list>" + headData.deviceWanNewList);
//            if (headData.isOver) {
                List<SmartDeviceSQL> smartDeviceList = DataSupport.where("userId = ?", StringUtil.getUserID()).find(SmartDeviceSQL.class);
                OutPutMessage.LogCatInfo("删除设备", "-------------------0000>" + smartDeviceList);
                if (smartDeviceList.size() == 0) {
                    return;
                }
                for (int i = 0; i < smartDeviceList.size(); i++) {
                    smartDeviceList.get(i).setExist(false);
                }
                if (rDeviceList.length == 0) {//如果广域网不存在，
                    OutPutMessage.LogCatInfo("删除设备", "------------------->1111111111111111");
                    removeSQLDevice(smartDeviceList);
                } else {
                    for (int i = 0; i < rDeviceList.length; i++) {
                        addSQLExist(rDeviceList[i].getMac(), smartDeviceList);
                    }
                    removeSQLDevice(smartDeviceList);
                }
//            }

            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
        }
    }


    /**
     * 添加初始化设备
     *
     * @param smartDevice 设备实例
     * @param headData    网络返回数据
     */
    public void addInitDevice(SmartDevice smartDevice, HeadData headData) {
        if (smartDevice != null && headData != null) {
            switch (headData.netType) {
                case LAN:
                    addLanDevice(smartDevice, headData);
                    break;
                case WAN:
                    addWanDevice(smartDevice, headData);
                    break;
                case SQL:
                    addSqlDevice(smartDevice, headData);
                    break;
            }
        }
    }

    private void addWanDevice(SmartDevice smartDevice, HeadData headData) {
        if (smartDevice != null) {
            DeviceParseHeadData deviceParseHeadData = new DeviceParseHeadData(smartDevice, Constant.NetWork.WAN_NET);
            deviceParseHeadData.setWanDevice(headData.wanDevice);
            SmartDeviceState smartDeviceState = smartDevice.getSmartDeviceLogic().getSmartDeviceState();
            smartDeviceState.deviceReceiveData(deviceParseHeadData);
            if (smartDevice.getSmartDeviceLogic().getCommunicationType() == SmartDeviceConstant.CommunicationType.PASSIVE_DEVICE) {
                //如果本地没用保存过
                addDevice(smartDevice, Constant.NetWork.GATEWAY_NET);
            } else {
                addDevice(smartDevice, Constant.NetWork.WAN_NET);
            }


            headData.deviceWanNewList.add(smartDevice);


        }
    }

    /**
     * 去除当前内存中所有绑定标记
     *
     * @return 网关列表
     */
    private void clearDeviceList() {
        if (mDeviceHashMap.size() == 0) {
            return;
        }
        mDeviceHashMap.clear();
    }


    /**
     * 删除本地设备
     */
    private void removeSQLDevice(List<SmartDeviceSQL> smartDeviceList) {
        OutPutMessage.LogCatInfo("删除设备", "------------------->smartDeviceList" + smartDeviceList.size());
        if (smartDeviceList.size() == 0) {
            clearDeviceList();
        } else {
            for (int i = 0; i < smartDeviceList.size(); i++) {
                SmartDeviceSQL sd = smartDeviceList.get(i);
                OutPutMessage.LogCatInfo("删除设备", "------------------->151515151");
                if (!sd.isExist()) {//如果广域网列表没出现过则删除本地
                    DataSupport.delete(SmartDeviceSQL.class, sd.getId());
                    SmartDevice smartDevice = mDeviceHashMap.get(sd.getMac());
                    OutPutMessage.LogCatInfo("删除设备", "------------------->22222222222");
                    if (smartDevice != null) {
                        remove(smartDevice.getSmartDeviceLogic().getDeviceMac());
                    }
                }
            }
        }
    }

    private void addLanDevice(SmartDevice smartDevice, HeadData headData) {
        if (smartDevice != null) {
            // OutPutMessage.LogCatInfo("广播回复222222222222222", ByteUtil.byteArrayToHexString(smartHomeData.data, true) + "");
            int length = 4 + 4;

            SmartHomeData smartHomeData = headData.deviceParseHeadData.getSmartHomeData();
            SmartDeviceLogic smartDeviceLogic = smartDevice.getSmartDeviceLogic();
            byte[] data = new byte[smartHomeData.data.length - length];//数据长度，去掉头4消息值
            System.arraycopy(smartHomeData.data, length, data, 0, data.length);//复制到新数组中，交给设备去处理
            smartDeviceLogic.setDeviceIp(headData.ip);
            smartDeviceLogic.setDeviceType(headData.devType);
            smartDeviceLogic.setDevVersion(headData.devVersion);
            smartDeviceLogic.setDeviceUpdateType(headData.deviceeUpdateType);
            smartDeviceLogic.setCommunicationType(headData.communicationType);
            SmartDeviceState smartDeviceState = smartDeviceLogic.getSmartDeviceState();
            headData.deviceParseHeadData.setData(data);
            headData.deviceParseHeadData.setSmartDevice(smartDevice);
            smartDeviceState.deviceReceiveData(headData.deviceParseHeadData);//传递给状态机处理
            OutPutMessage.LogCatInfo("广播搜索局域网设备返回", smartDevice.getSmartDeviceLogic().getDeviceMac() + "!!!!" + headData.communicationType);
            addDevice(smartDevice, Constant.NetWork.LAN_NET);//添加到设备列表里
        }
    }

    /**
     * 获取最新的设备列表,带有排序功能
     *
     * @param sort true 设备类型排序 false 在线状态排序
     * @return 排序后设备列表
     */
    public List<SmartDevice> findDeviceListSort(int sort) {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = findDeviceList();
        if (smartDeviceList.size() > 0) {
            switch (sort) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
            }
        }
        OutPutMessage.LogCatInfo("使用率测试", smartDeviceList + "");
        return smartDeviceList;
    }


    /**
     * 查看本地是否包含
     *
     * @param mac
     * @return
     */
    private boolean addSQLExist(String mac, List<SmartDeviceSQL> smartDeviceList) {
        for (int i = 0; i < smartDeviceList.size(); i++) {
            SmartDeviceSQL sd = smartDeviceList.get(i);
            if (sd.getMac().equals(mac)) {
                sd.setExist(true);
                return true;
            }
        }
        return false;
    }

    /**
     * 解析消息推送
     *
     * @param data 消息体
     */
    public void parseDeviceMessageInfo(byte[] data) {
        OutPutMessage.LogCatInfo("消息推送接收", ByteUtil.byteArrayToHexString(data, true));
        ByteBuffer md = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        md.put(data, 0, data.length);
        //消息类型码 1
        byte messType = md.get(0);
        //ID值
        long deviceId = md.getLong(1);
        //deviceId = Long.reverseBytes(deviceId);
        //消息长度
        byte length = md.get(9);
        //消息解析
        byte[] strByte = new byte[length];
        System.arraycopy(data, 10, strByte, 0, strByte.length);
        parseDeviceData(messType, deviceId);
    }

    private void parseDeviceData(byte messType, long deviceId) {
        SmartDevice smartDevice = findWanDeviceID(deviceId + "");
        OutPutMessage.LogCatInfo("消息推送", "设备ID：" + deviceId+"   "+messType);
        SecurityUserInfo user = SecurityUserManage.getShare().getUser();
        String msg = "";
        switch (messType) {
            case 7://设备绑定成功
//                if (user != null) {
////                    user.wanDeviceInfo(deviceId + "");
//                }
                break;
            case 8://设备解绑成功
//                if (smartDevice != null) {
//                    msg = smartDevice.getSmartDeviceLogic().getDeviceName() + "，设备解绑成功。";
//                    deleteSmartDeviceInfo(smartDevice);
//                }
//                if (user != null) {
//                    user.getDeviceList();
//                }
                break;
            case 9://设备上线
                if (smartDevice != null) {
                    msg = smartDevice.getSmartDeviceLogic().getDeviceName() + "，设备上线。" + smartDevice.getSmartDeviceLogic().getDeviceMac();

//                    OutPutMessage.LogCatInfo("消息推送", msg + "   " + smartDevice.getSmartDeviceLogic().getDeviceNetWork());

                    OutPutMessage.LogCatInfo("消息推送", msg+"  设备状态"+smartDevice.getSmartDeviceLogic().getSmartDeviceState());
                    SecurityUserManage.getShare().getUser().getDeviceByMac(smartDevice.getSmartDeviceLogic().getDeviceMac());

                    if (smartDevice.getSmartDeviceState() != SmartDeviceState.CONNECT_DEVICE) {

                        if (smartDevice.getSmartDeviceLogic().getDeviceNetWork() == Constant.NetWork.GATEWAY_NET) {
                            OutPutMessage.LogCatInfo("消息推送", msg + "   网关子设备上线");

                            smartDevice.getSmartDeviceLogic().addNetWork(Constant.NetWork.GATEWAY_NET, true);
                        } else {
                            smartDevice.getSmartDeviceLogic().addNetWork(Constant.NetWork.WAN_NET, true);
                        }

//                        smartDevice.getSmartDeviceLogic().setDeviceNetWork(Constant.NetWork.WAN_NET);
//                        smartDevice.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.LOGIN_DEVICE);
                    }
                }
                break;
            case 10://设备下线
                if (smartDevice != null) {
                    msg = smartDevice.getSmartDeviceLogic().getDeviceName() + "，设备下线。";

                    //设备离线消除报警信息
                    smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.ALARM);
                    smartDevice.getSmartDeviceLogic().setIpPort(null);
                    smartDevice.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.DISCONNECT_DEVICE);
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_NOT_LINE, null, smartDevice);

                    OutPutMessage.LogCatInfo("消息推送", msg+"  设备状态"+smartDevice.getSmartDeviceLogic().getSmartDeviceState());
                }
                break;

            case 13:
                if (smartDevice != null) {
                    msg = smartDevice.getSmartDeviceLogic().getDeviceName() + "，设备第一次登陆平台。";


                    SecurityUserManage.getShare().getUser().getDeviceByMac(smartDevice.getSmartDeviceLogic().getDeviceMac());
                    //设备第一次登陆平台
                    NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_FIRST_LOGIN, null);

                    OutPutMessage.LogCatInfo("消息推送", msg+"  设备状态"+smartDevice.getSmartDeviceLogic().getSmartDeviceState());


                }
                break;
        }
//        if (!msg.equals("")) {
//            UserLogManage.defaultManager().setHomePageLog(msg, UserLogManage.LogCategory.LOG_NORMAL);
//        }
        NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
    }

    /**
     * 通过广域网设备ID查找设备
     *
     * @param deviceId 广域网设备ID
     * @return 当前ID设备
     */
    private SmartDevice findWanDeviceID(String deviceId) {
        if (deviceId == null) {
            return null;
        }
        SmartDevice smartDevices = null;
        //判断是否是子设备
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (smartDevice.getSmartDeviceLogic().getDeviceId() != null) {
                //本地存儲，而且沒有在广域网和局域网出现过
                if (smartDevice.getSmartDeviceLogic().getDeviceId().equals(deviceId)) {
                    return smartDevice;
                }
            }
        }
        return smartDevices;
    }


    /**
     * 找到存储设备，并修改内容
     */
    private boolean deleteSmartDeviceSql(SmartDevice smartDevice) {
        SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(smartDevice);
        if (smartDeviceSQL == null) {
            return false;
        }
        DataSupport.delete(SmartDeviceSQL.class, smartDeviceSQL.getId());
        return true;
    }

    /**
     * 查找网关设备列表
     *
     * @return 网关列表
     */
    public List<SmartDevice> findGatewayDeviceList() {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<SmartDevice>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            OutPutMessage.LogCatInfo("添加子设备", smartDevice.toString() + "");
            if (smartDevice.getSmartDeviceLogic().isSubDeviceList() &&
                    smartDevice.getSmartDeviceLogic().getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE) {
                putDeviceToList(smartDevice, smartDeviceList);
            }
        }
        return smartDeviceList;
    }


    /**
     * 查找网关子设备列表
     *
     * @return
     */
    public List<SmartDevice> findGatewayChildDeviceList(String mac) {
        if (mDeviceHashMap.size() == 0 || TextUtils.isEmpty(mac)) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {
                if (!TextUtils.isEmpty(smartDevice.getSmartDeviceLogic().getGatewayMac()) && mac.equals(smartDevice.getSmartDeviceLogic().getGatewayMac())) {
                    putDeviceToList(smartDevice, smartDeviceList);
                }
            }
        }
        return smartDeviceList;
    }

    /**
     * 查找设备列表合并
     *
     * @return
     */
    public List<SmartDevice> findDeviceList() {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            // 设备状态 1.报警 2.故障 3.离线 4.在线
            int faultState = smartDevice.getSmartDeviceLogic().getFaultState();
            //  离线优先 其次故障再次报警
            if (!StringUtil.isEmpty(smartDevice.getSmartDeviceLogic().getIpPort())) {
                // 0 正常 1离线  其他为故障
                if (faultState == 0 || faultState == 1){
                    if (smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ALARM)) {
                        smartDevice.getSmartDeviceLogic().setDeviceState(1);
                    } else {
                        smartDevice.getSmartDeviceLogic().setDeviceState(4);
                    }
                }else{
                    smartDevice.getSmartDeviceLogic().setDeviceState(2);
                }
            } else {
                smartDevice.getSmartDeviceLogic().setDeviceState(3);
            }
            putDeviceToList(smartDevice, smartDeviceList);
        }
        Collections.sort(smartDeviceList, new Comparator<SmartDevice>() {
            @Override
            public int compare(SmartDevice lhs, SmartDevice rhs) {
                Integer ls = lhs.getSmartDeviceLogic().getDeviceState();
                Integer rs = rhs.getSmartDeviceLogic().getDeviceState();
                if (rs == null && ls == null) {
                    return 0;
                }
                return ls.compareTo(rs);
            }
        });
        return smartDeviceList;
    }

    /**
     * 查找设备列表合并
     *
     * @return
     */
    public List<SmartDevice> findUpdateDeviceList(int state) {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();

            OutPutMessage.LogCatInfo("升级List",smartDevice.getSmartDeviceLogic().getDeviceMac()+"   "+smartDevice.getSmartDeviceState()+"    "+
                    smartDevice.getSmartDeviceLogic().isVisible()+"    "+smartDevice.getSmartDeviceLogic().getDeviceNetWork()+"    ");


            OutPutMessage.LogCatInfo("升级List",(smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE)+"    "+(smartDevice.getSmartDeviceLogic().isVisible())+"   "+(

                    (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET) )+"   "+(smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)+"   "+
                            (smartDevice.getSmartDeviceLogic().isDeviceState(state)))));


            if (smartDevice.getSmartDeviceState() == SmartDeviceState.CONNECT_DEVICE
                    && smartDevice.getSmartDeviceLogic().isVisible()
                    && (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET) || smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET))
                    && smartDevice.getSmartDeviceLogic().isDeviceState(state)) {

                if (smartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {

                    OutPutMessage.LogCatInfo("null测试", getDeviceHashMap() + "");
                    OutPutMessage.LogCatInfo("null测试", smartDevice + "");
                    OutPutMessage.LogCatInfo("null测试", smartDevice.getSmartDeviceLogic() + "");
                    OutPutMessage.LogCatInfo("null测试", smartDevice.getSmartDeviceLogic().getGatewayMac() + "");

                    if (smartDevice.getSmartDeviceLogic().getGatewayMac() != null) {
                        SmartDevice sd = getDeviceHashMap().get(smartDevice.getSmartDeviceLogic().getGatewayMac());
                        if (sd.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {
                            putDeviceToList(smartDevice, smartDeviceList);
                        }
                    }
                } else {
                    putDeviceToList(smartDevice, smartDeviceList);
                }
            }
        }
        return smartDeviceList;
    }


    /**
     * 查找设备列表合并
     *
     * @return
     */
    public List<SmartDevice> findDeviceListType(int deviceType) {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (smartDevice.getSmartDeviceLogic().isVisible() &&
                    deviceType == smartDevice.getSmartDeviceLogic().getDeviceType()) {
                // 设备状态 1.报警 2.故障 3.离线 4.在线
                int faultState = smartDevice.getSmartDeviceLogic().getFaultState();
                //  离线优先 其次故障再次报警
                if (!StringUtil.isEmpty(smartDevice.getSmartDeviceLogic().getIpPort())) {
                    // 0 正常 1离线  其他为故障
                    if (faultState == 0 || faultState == 1){

                        if (smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ALARM)) {
                            smartDevice.getSmartDeviceLogic().setDeviceState(1);
                        } else {
                            smartDevice.getSmartDeviceLogic().setDeviceState(4);
                        }
                    }else{
                        smartDevice.getSmartDeviceLogic().setDeviceState(2);
                    }
                } else {
                    smartDevice.getSmartDeviceLogic().setDeviceState(3);
                }


                putDeviceToList(smartDevice, smartDeviceList);
            }
        }

        Collections.sort(smartDeviceList, new Comparator<SmartDevice>() {
            @Override
            public int compare(SmartDevice lhs, SmartDevice rhs) {
                Integer ls = lhs.getSmartDeviceLogic().getDeviceState();
                Integer rs = rhs.getSmartDeviceLogic().getDeviceState();
                if (rs == null && ls == null) {
                    return 0;
                }
                return ls.compareTo(rs);
            }
        });
        return smartDeviceList;
    }


    /**
     * 查看本地数据库
     *
     * @param oldSmartDevice 要查找设备
     * @return 本地数据设备
     */
    public SmartDeviceSQL findSqlSmartDevice(SmartDevice oldSmartDevice) {
        //数据库查找一次
        List<SmartDeviceSQL> sqlDeviceInfoList = DataSupport.where("mac = ? and userId = ?", oldSmartDevice.getSmartDeviceLogic().getDeviceMac(), StringUtil.getUserID()).find(SmartDeviceSQL.class);
        if (sqlDeviceInfoList.size() > 0) {
            SmartDeviceSQL sqlDeviceInfo = sqlDeviceInfoList.get(0);
            return sqlDeviceInfo;
        }
        return null;
    }

    /**
     * 查看本地是否存在ZigBee设备
     *
     * @param newSmartDevice 要被添加的设备
     * @param oldSmartDevice 已经存在设备
     * @return
     */
    private boolean findSqlZigBee(SmartDevice newSmartDevice, SmartDevice oldSmartDevice) {
        //如果新设备是zigBee
        if (newSmartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {
            if (!oldSmartDevice.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET)) {//并且老设备不包含zigBee
                //找一次本地以前设备是否存在
                boolean isOk = updateValues(oldSmartDevice, "zigBee", SmartDeviceConstant.State.ZIG_BEE_DEVICES);
                if (isOk) {
                    return isOk;
                } else {//设备不存在则重新保存
                    saveDeviceTable(oldSmartDevice, SmartDeviceConstant.State.ZIG_BEE_DEVICES);
                }
            } else {
                return true;//返回已存在
            }
        }

        return false;
    }

    /**
     * 新增设备
     *
     * @param netLan         网络类型
     * @param newSmartDevice
     */
    private void addNewDevice(int netLan, SmartDevice newSmartDevice) {
        if (netLan == Constant.NetWork.LAN_NET) {//局域网
//            newSmartDevice.getSmartDeviceLogic().setDeviceName(getName(newSmartDevice));
        } else if (netLan == Constant.NetWork.GATEWAY_NET) {//网关子设备
            saveDeviceTable(newSmartDevice, 0);
        } else if (netLan == Constant.NetWork.ZIG_BEE_NET) {//只保存zigBee设备
            saveDeviceTable(newSmartDevice, SmartDeviceConstant.State.ZIG_BEE_DEVICES);
        } else if (netLan == Constant.NetWork.WAN_NET) {//广域网
            saveDeviceTable(newSmartDevice, 0);
        }
        OutPutMessage.LogCatInfo("添加新设备", newSmartDevice.toString());
        mDeviceHashMap.put(newSmartDevice.getSmartDeviceLogic().getDeviceMac(), newSmartDevice);
    }

    /**
     * 功能描述：发送局域网检索
     * 配置网络sum=1
     * 广播包sum=5
     */
    public void sendLanRetrieve(final byte[] dstID, final int sum) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Controller.canSearch == false) {
                    timer.cancel();
                    this.cancel();
                    index = 0;
                    Controller.isRetrieve = false;//重置广播标记
                }
                index++;
                DeviceSendHeadData smartDeviceHead = new DeviceSendHeadData("255.255.255.255");
                byte[] data = smartDeviceHead.sendLanRetrieveHeadData(dstID);
                NetworkMessage networkMessage = new NetworkMessage(true, data, "", Constant.NetWork.LAN_NET, smartDeviceHead.getDeviceIP());
                Controller.defaultController().sendCode(networkMessage);
                OutPutMessage.LogCatInfo(DeviceNetModeManage.WIFI_TAG, "发送广播1");
                if (index == sum) {//广播次数
                    timer.cancel();
                    this.cancel();
                    index = 0;
                    Controller.isRetrieve = false;//重置广播标记
                }
            }
        }, 0, 1000);
    }


    /**
     * 设备对象赋值
     *
     * @param oldSmart
     * @param newSmart
     */
    public void smartDeviceClone(SmartDevice oldSmart, SmartDevice newSmart,boolean login) {
        oldSmart.getSmartDeviceLogic().setUserID(StringUtil.getUserID());

        if (newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)) {

            updateDeviceInfo(oldSmart, newSmart);

            oldSmart.getSmartDeviceLogic().setSQLExist(true);

            oldSmart.getSmartDeviceLogic().updateVisible();
            //网关Mac(网关通讯使用)
            oldSmart.getSmartDeviceLogic().setGatewayMac(newSmart.getSmartDeviceLogic().getGatewayMac());

//            if (TextUtils.isEmpty(oldSmart.getSmartDeviceLogic().getDeviceName())) {
//                oldSmart.getSmartDeviceLogic().setDeviceName(getName(oldSmart));
//            }

            oldSmart.getSmartDeviceLogic().setDeviceIp(newSmart.getSmartDeviceLogic().getDeviceIp());

            oldSmart.getSmartDeviceLogic().setSecretKey(newSmart.getSmartDeviceLogic().getSecretKey());

//            oldSmart.getSmartDeviceLogic().setDevicePassword(newSmart.getSmartDeviceLogic().getDevicePassword());

            oldSmart.getSmartDeviceLogic().setSessionId(newSmart.getSmartDeviceLogic().getSessionId());

            oldSmart.getSmartDeviceLogic().setDeviceId(newSmart.getSmartDeviceLogic().getDeviceId());
            oldSmart.getSmartDeviceLogic().setIpPort(newSmart.getSmartDeviceLogic().getIpPort());
            oldSmart.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.BINDING);
            oldSmart.getSmartDeviceLogic().setDefense(newSmart.getSmartDeviceLogic().getDefense());
            oldSmart.getSmartDeviceLogic().setGetDefenseSupport(newSmart.getSmartDeviceLogic().getGetDefenseSupport());
            oldSmart.getSmartDeviceLogic().setState(newSmart.getSmartDeviceLogic().getState());
            oldSmart.getSmartDeviceLogic().setDeviceType(newSmart.getSmartDeviceLogic().getDeviceType());
            oldSmart.getSmartDeviceLogic().setDevVersion(newSmart.getSmartDeviceLogic().getDevVersion());
            oldSmart.getSmartDeviceLogic().setDeviceTypeId(newSmart.getSmartDeviceLogic().getDeviceTypeId());
            oldSmart.getSmartDeviceLogic().setDeviceName(newSmart.getSmartDeviceLogic().getDeviceName());
            oldSmart.getSmartDeviceLogic().setCommunicationType(newSmart.getSmartDeviceLogic().getCommunicationType());
            oldSmart.getSmartDeviceLogic().setManufacturerEncoding(newSmart.getSmartDeviceLogic().getManufacturerEncoding());
            oldSmart.getSmartDeviceLogic().setUseType(newSmart.getSmartDeviceLogic().getUseType());
            oldSmart.getSmartDeviceLogic().setStartTime(newSmart.getSmartDeviceLogic().getStartTime());
            oldSmart.getSmartDeviceLogic().setDeadline(newSmart.getSmartDeviceLogic().getDeadline());
            oldSmart.getSmartDeviceLogic().setName(newSmart.getSmartDeviceLogic().getName());
            oldSmart.getSmartDeviceLogic().setMCUupdate_state(newSmart.getSmartDeviceLogic().getMCUupdate_state());
            oldSmart.getSmartDeviceLogic().setCPUupdate_state(newSmart.getSmartDeviceLogic().getCPUupdate_state());
            oldSmart.getSmartDeviceLogic().setIsfirstLogin(newSmart.getSmartDeviceLogic().getIsfirstLogin());
            oldSmart.getSmartDeviceLogic().setIsTwoLinkage(newSmart.getSmartDeviceLogic().getIsTwoLinkage());
            oldSmart.getSmartDeviceLogic().setDevName(newSmart.getSmartDeviceLogic().getDevName());
            oldSmart.getSmartDeviceLogic().setOrginVersion(newSmart.getSmartDeviceLogic().getOrginVersion());
            oldSmart.getSmartDeviceLogic().setDevImgUrl(newSmart.getSmartDeviceLogic().getDevImgUrl());
            oldSmart.getSmartDeviceLogic().setDevImgUrlFault(newSmart.getSmartDeviceLogic().getDevImgUrlFault());
            oldSmart.getSmartDeviceLogic().setElectricReport(newSmart.getSmartDeviceLogic().getElectricReport());
            oldSmart.getSmartDeviceLogic().setShareAccount(newSmart.getSmartDeviceLogic().getShareAccount());
            oldSmart.getSmartDeviceLogic().setShareName(newSmart.getSmartDeviceLogic().getShareName());
            oldSmart.getSmartDeviceLogic().setShareType(newSmart.getSmartDeviceLogic().getShareType());
            oldSmart.getSmartDeviceLogic().setDoorState(newSmart.getSmartDeviceLogic().getDoorState());
            oldSmart.getSmartDeviceLogic().setIsSource(newSmart.getSmartDeviceLogic().getIsSource());
            oldSmart.getSmartDeviceLogic().setFaultState(newSmart.getSmartDeviceLogic().getFaultState());
            oldSmart.getSmartDeviceLogic().setBindStatus(newSmart.getSmartDeviceLogic().getBindStatus());
            oldSmart.getSmartDeviceLogic().setControlType(newSmart.getSmartDeviceLogic().getControlType());
            oldSmart.getSmartDeviceLogic().setBindDeviceId(newSmart.getSmartDeviceLogic().getBindDeviceId());
            oldSmart.getSmartDeviceLogic().setCtlpwd(newSmart.getSmartDeviceLogic().getCtlpwd());
            oldSmart.getSmartDeviceLogic().updateVisible();

            OutPutMessage.LogCatInfo("添加的子设备", "mac : " + oldSmart.getSmartDeviceLogic().getDeviceMac() + "!!! id : " + oldSmart.getSmartDeviceLogic().getDeviceId());
        }

        if (newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)) {

            updateDeviceInfo(oldSmart, newSmart);

            oldSmart.getSmartDeviceLogic().setDeviceIp(newSmart.getSmartDeviceLogic().getDeviceIp());
            //是否为ap模式
            oldSmart.getSmartDeviceLogic().setNetMode(newSmart.getSmartDeviceLogic().getNetMode());

//            oldSmart.getSmartDeviceLogic().setDevicePassword(newSmart.getSmartDeviceLogic().getDevicePassword());

//            if (TextUtils.isEmpty(oldSmart.getSmartDeviceLogic().getDeviceName())) {
//                oldSmart.getSmartDeviceLogic().setDeviceName(getName(oldSmart));
//            }

            oldSmart.getSmartDeviceLogic().setCommunicationType(newSmart.getSmartDeviceLogic().getCommunicationType());

            //添加设备网络连接状态
            oldSmart.getSmartDeviceLogic().addNetWork(newSmart.getSmartDeviceLogic().getDeviceNetWork(), true);
        }

        if (newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
            OutPutMessage.LogCatInfo("广域网列表", "名称---》" + newSmart.getSmartDeviceLogic().getDeviceName());
//            if (TextUtils.isEmpty(newSmart.getSmartDeviceLogic().getDeviceName())) {
//                oldSmart.getSmartDeviceLogic().setDeviceName(getName(oldSmart));
//            }

            oldSmart.getSmartDeviceLogic().setSQLExist(true);

            oldSmart.getSmartDeviceLogic().setDeviceId(newSmart.getSmartDeviceLogic().getDeviceId());
            oldSmart.getSmartDeviceLogic().setIpPort(newSmart.getSmartDeviceLogic().getIpPort());
            oldSmart.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.BINDING);
            oldSmart.getSmartDeviceLogic().setDefense(newSmart.getSmartDeviceLogic().getDefense());
            oldSmart.getSmartDeviceLogic().setGetDefenseSupport(newSmart.getSmartDeviceLogic().getGetDefenseSupport());
            oldSmart.getSmartDeviceLogic().setState(newSmart.getSmartDeviceLogic().getState());
            oldSmart.getSmartDeviceLogic().setDeviceType(newSmart.getSmartDeviceLogic().getDeviceType());
            oldSmart.getSmartDeviceLogic().setDevVersion(newSmart.getSmartDeviceLogic().getDevVersion());
            oldSmart.getSmartDeviceLogic().setDeviceTypeId(newSmart.getSmartDeviceLogic().getDeviceTypeId());
            oldSmart.getSmartDeviceLogic().setCommunicationType(newSmart.getSmartDeviceLogic().getCommunicationType());
            oldSmart.getSmartDeviceLogic().setManufacturerEncoding(newSmart.getSmartDeviceLogic().getManufacturerEncoding());
//            oldSmart.getSmartDeviceLogic().setDevicePassword(newSmart.getSmartDeviceLogic().getDevicePassword());
            oldSmart.getSmartDeviceLogic().setUseType(newSmart.getSmartDeviceLogic().getUseType());
            oldSmart.getSmartDeviceLogic().setStartTime(newSmart.getSmartDeviceLogic().getStartTime());
            oldSmart.getSmartDeviceLogic().setDeadline(newSmart.getSmartDeviceLogic().getDeadline());
            oldSmart.getSmartDeviceLogic().setName(newSmart.getSmartDeviceLogic().getName());
            oldSmart.getSmartDeviceLogic().setDeviceName(newSmart.getSmartDeviceLogic().getDeviceName());
            oldSmart.getSmartDeviceLogic().setIsTwoLinkage(newSmart.getSmartDeviceLogic().getIsTwoLinkage());
            oldSmart.getSmartDeviceLogic().setDevName(newSmart.getSmartDeviceLogic().getDevName());
            oldSmart.getSmartDeviceLogic().setOrginVersion(newSmart.getSmartDeviceLogic().getOrginVersion());
            oldSmart.getSmartDeviceLogic().setDevImgUrl(newSmart.getSmartDeviceLogic().getDevImgUrl());
            oldSmart.getSmartDeviceLogic().setDevImgUrlFault(newSmart.getSmartDeviceLogic().getDevImgUrlFault());
            oldSmart.getSmartDeviceLogic().setElectricReport(newSmart.getSmartDeviceLogic().getElectricReport());
            oldSmart.getSmartDeviceLogic().setShareAccount(newSmart.getSmartDeviceLogic().getShareAccount());
            oldSmart.getSmartDeviceLogic().setShareName(newSmart.getSmartDeviceLogic().getShareName());
            oldSmart.getSmartDeviceLogic().setShareType(newSmart.getSmartDeviceLogic().getShareType());
            oldSmart.getSmartDeviceLogic().setDoorState(newSmart.getSmartDeviceLogic().getDoorState());
            oldSmart.getSmartDeviceLogic().setIsSource(newSmart.getSmartDeviceLogic().getIsSource());
            oldSmart.getSmartDeviceLogic().setFaultState(newSmart.getSmartDeviceLogic().getFaultState());
            oldSmart.getSmartDeviceLogic().setBindStatus(newSmart.getSmartDeviceLogic().getBindStatus());
            oldSmart.getSmartDeviceLogic().setAlarmStatus(newSmart.getSmartDeviceLogic().getAlarmStatus());
            oldSmart.getSmartDeviceLogic().setPid(newSmart.getSmartDeviceLogic().getPid());
            oldSmart.getSmartDeviceLogic().setUpgrade(newSmart.getSmartDeviceLogic().getUpgrade());
            oldSmart.getSmartDeviceLogic().setQrcode(newSmart.getSmartDeviceLogic().getQrcode());
            oldSmart.getSmartDeviceLogic().setControlType(newSmart.getSmartDeviceLogic().getControlType());
            oldSmart.getSmartDeviceLogic().setBindDeviceId(newSmart.getSmartDeviceLogic().getBindDeviceId());
            oldSmart.getSmartDeviceLogic().setCtlpwd(newSmart.getSmartDeviceLogic().getCtlpwd());
            oldSmart.getSmartDeviceLogic().updateVisible();
            //更新本地绑定状态
            boolean isUpdate = updateWanValues(oldSmart, newSmart);
            if (!isUpdate) {
                saveDeviceTable(oldSmart, 0);
            }

            OutPutMessage.LogCatInfo("广域网列表", "赋值设备---》" + oldSmart.toString());
        }

        if (login) {

            if (oldSmart.getSmartDeviceLogic().getIpPort() != null &&
                    !oldSmart.getSmartDeviceLogic().getIpPort().equals("")) {
                if (oldSmart.getSmartDeviceLogic().getDeviceVer().equals("1")) {
                    oldSmart.getSmartDeviceLogic().addNetWork(Constant.NetWork.WAN_NET, true);
                } else {
                    oldSmart.getSmartDeviceLogic().addNetWork(Constant.NetWork.WAN_NET_PROXY, false);
                }
            }

        }

    }

    private void updateDeviceInfo(SmartDevice oldSmart, SmartDevice newSmart) {
        UpdateData oldUpdateData = oldSmart.getSmartDeviceLogic().getUpdateData();
        UpdateData newUpdateData = newSmart.getSmartDeviceLogic().getUpdateData();

        oldUpdateData.setDeviceChipType(newUpdateData.getDeviceChipType());
        oldUpdateData.setAppVersionCPU(newUpdateData.getAppVersionCPU());
        oldUpdateData.setAppVersionMCU(newUpdateData.getAppVersionMCU());
        oldUpdateData.setDeviceUpdateType(newUpdateData.getDeviceUpdateType());
        oldUpdateData.setDeviceUpdateMode(newUpdateData.isDeviceUpdateMode());


        boolean isReadUpdate = newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)
                || newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.GATEWAY_NET)
                || newSmart.getSmartDeviceLogic().isNetWork(Constant.NetWork.ZIG_BEE_NET);
        OutPutMessage.LogCatInfo("升级管理", "设备Mac地址->" + oldSmart.getSmartDeviceLogic().getDeviceMac() + "!!!isReadUpdate->" + isReadUpdate + "!!!ChipType->" + oldUpdateData.getDeviceChipType());
        //请求升级信息，和 设备本身不是广域网
        if (isReadUpdate
                && oldUpdateData.getDeviceChipType() != 0) {
            //设备类型_芯片型号
            DeviceUpgradeManage.defaultDeviceUpgradeManager().isUpgradeDevice(oldSmart);//检测是否升级
        }

    }

    /**
     * 删除此设备，并更新列表
     *
     * @param smartDevice 要删除设备
     * @return 删除后设备列表
     */
    public void deleteSmartDeviceInfo(SmartDevice smartDevice) {
        //如果有绑定关系删除绑定关系
        deleteBind(smartDevice);

        SmartDevice sd = mDeviceHashMap.get(smartDevice.getSmartDeviceLogic().getDeviceMac());
        if (sd != null) {
//            sd.getSmartDeviceLogic().setDeviceName(getName(smartDevice));
            sd.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.BINDING);

            OutPutMessage.LogCatInfo("解绑设备",sd.getSmartDeviceLogic().getDeviceNetWork()+"  "+(sd.getSmartDeviceLogic().isNetWork(Constant.NetWork.LAN_NET)));
//            if (sd.getSmartDeviceLogic().isNetWork(Constant.NetWork.WAN_NET)) {
                remove(sd.getSmartDeviceLogic().getDeviceMac());
//            } else {
                sd.getSmartDeviceLogic().setOwnerUserID("");
                sd.getSmartDeviceLogic().updateVisible();
//            }
        }
        OutPutMessage.LogCatInfo("updateData长度-->", "----------------------------------------------->" + "删除的地方");
        SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(smartDevice);
        if (smartDeviceSQL == null) {
            return;
        }
        DataSupport.delete(SmartDeviceSQL.class, smartDeviceSQL.getId());
    }

    private void deleteBind(SmartDevice smartDevice){

        int bindId = smartDevice.getSmartDeviceLogic().getBindDeviceId();
        smartDevice.getSmartDeviceLogic().setBindDeviceId(0);


        String bindMac = "";
        SmartDevice bindSmartDevice;
        if (bindId == 0){
            return;
        }
        List<SmartDevice> smartDevices = SmartDeviceManage.defaultManager().findDeviceList();

        for (int i = 0; i < smartDevices.size(); i++) {
            if (smartDevices.get(i).getSmartDeviceLogic().getDeviceId().equals(bindId + "")) {
                bindMac = smartDevices.get(i).getSmartDeviceLogic().getDeviceMac();

            }
        }

        if (StringUtil.isEmpty(bindMac)) {
            return;
        } else {

            bindSmartDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(bindMac);
            if (bindSmartDevice == null) {
                return;
            } else {
                SmartDeviceLogic  bindSmartDeviceLogic = bindSmartDevice.getSmartDeviceLogic();
                bindSmartDeviceLogic.setBindDeviceId(0);

            }
        }

    }

    /**
     * 删除某个设备
     *
     * @param mac
     */
    public void remove(String mac) {
        if (mac != null && !mac.equals("")) {//删除AP设备
            SmartDevice smartDevice = mDeviceHashMap.get(mac);
            if (smartDevice != null) {
                smartDevice.getSmartDeviceLogic().stopHeartbeatThread();
                mDeviceHashMap.remove(smartDevice.getSmartDeviceLogic().getDeviceMac());
            }
        }
    }

    /**
     * 设备集合
     *
     * @return 设备集合
     */
    public ConcurrentHashMap<String, SmartDevice> getDeviceHashMap() {
        return mDeviceHashMap;
    }

    /**
     * 通过设备类型找到设备
     *
     * @param deviceID 设备类型
     * @return 当前类型设备列表
     */
    public SmartDevice findDeviceID(final String deviceID) {
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (deviceID.equals(smartDevice.getSmartDeviceLogic().getDeviceId())) {
                return smartDevice;
            }
        }
        return null;
    }


    /**
     * 通过设备类型找到设备
     *
     * @param type 设备类型
     * @return 当前类型设备列表
     */
    public List<SmartDevice> findDeviceTypeList(final int type) {
        List<SmartDevice> smartDevices = new ArrayList<SmartDevice>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (smartDevice.getSmartDeviceLogic().getDeviceType() == type &&
                    smartDevice.getSmartDeviceLogic().getSmartDeviceState() != SmartDeviceState.CONNECT_DEVICE) {
                putDeviceToList(smartDevice, smartDevices);
            }
        }
        return smartDevices;
    }


//    private String getName(SmartDevice smartDevice) {
//        return "设备Mac(" + StringUtil.getMacAfterFour(smartDevice.getSmartDeviceLogic().getDeviceMac()) + ")";
//    }

    /**
     * 切换状态机
     */
    public void cleaningDeviceHead() {
        //判断是否存在广域网设备
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            smartDevice.getSmartDeviceLogic().getNetWorkMap().clear();
            smartDevice.getSmartDeviceLogic().setSmartDeviceStateManager(SmartDeviceState.DISCONNECT_DEVICE);
//            smartDevice.getSmartDeviceLogic().setIpPort("");
        }
    }

    /**
     * 查找子设备
     *
     * @return
     */
    public ConcurrentHashMap<String, SmartDevice> getChildDevice() {
        return mChildDevice;
    }


    /**
     * 存储设备表
     *
     * @param smartDevice 要保存的设备
     * @param state       网络类型
     */
    public void saveDeviceTable(SmartDevice smartDevice, int state) {
        SmartDeviceSQL sqlSmartDevice = new SmartDeviceSQL();
        sqlSmartDevice.setId(smartDevice.getSmartDeviceLogic().getSqlID());
        sqlSmartDevice.setDeviceName(smartDevice.getSmartDeviceLogic().getDeviceName());
        sqlSmartDevice.setDeviceType(smartDevice.getSmartDeviceLogic().getDeviceType());
        sqlSmartDevice.setDevVersion(smartDevice.getSmartDeviceLogic().getDevVersion());
        sqlSmartDevice.setMac(smartDevice.getSmartDeviceLogic().getDeviceMac());
        if (state == SmartDeviceConstant.State.ZIG_BEE_DEVICES) {
            sqlSmartDevice.setZigBee(state);
        }
        String userID = StringUtil.getUserID();
        if (!userID.equals("")) {
            sqlSmartDevice.setUserId(userID);
        }
        sqlSmartDevice.setSQLExist(true);

        sqlSmartDevice.save();
    }

    /**
     * 找到存储设备，并修改内容
     *
     * @param smartDevice 设备
     * @param key         服务器key
     * @param value       要修改值
     */
    public boolean updateValues(SmartDevice smartDevice, String key, int value) {
        SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(smartDevice);
        if (smartDeviceSQL == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(key, value);
        DataSupport.update(SmartDeviceSQL.class, values, smartDeviceSQL.getId());
        return true;
    }


    /**
     * 找到存储设备，并修改内容
     */
    private boolean updateWanValues(SmartDevice oldSmart, SmartDevice newDevice) {
        SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(oldSmart);
        if (smartDeviceSQL == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        if (!oldSmart.getSmartDeviceLogic().getOwnerUserID().equals(newDevice.getSmartDeviceLogic().getOwnerUserID())) {
            values.put("ownerUserID", newDevice.getSmartDeviceLogic().getOwnerUserID());
        }
        if (!TextUtils.isEmpty(newDevice.getSmartDeviceLogic().getDeviceName()) &&
                !oldSmart.getSmartDeviceLogic().getDeviceName().equals(newDevice.getSmartDeviceLogic().getDeviceName())) {
            values.put("deviceName", newDevice.getSmartDeviceLogic().getDeviceName());
        }
        DataSupport.update(SmartDeviceSQL.class, values, smartDeviceSQL.getId());
        return true;
    }

    /**
     * 找到存储设备，并修改内容
     *
     * @param smartDevice 设备
     * @param key         服务器key
     * @param value       要修改值
     */
    public boolean updateValues(SmartDevice smartDevice, String key, String value) {
        SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(smartDevice);
        if (smartDeviceSQL == null) {
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(key, value);
        DataSupport.update(SmartDeviceSQL.class, values, smartDeviceSQL.getId());
        return true;
    }

    /**
     * 退出程序更新状态状态
     */
    public void exitSaveSmartDevice() {
        //判断是否存在广域网设备
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            SmartDeviceSQL smartDeviceSQL = findSqlSmartDevice(smartDevice);
            if (smartDeviceSQL != null) {
                ContentValues values = new ContentValues();
                values.put("deviceName", smartDevice.getSmartDeviceLogic().getDeviceName());
                DataSupport.update(SmartDeviceSQL.class, values, smartDeviceSQL.getId());
            }
        }
    }


    /**
     * 去除当前内存中所有绑定标记
     *
     * @return 网关列表
     */
    private List<SmartDevice> clearBinding() {
        if (mDeviceHashMap.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<SmartDevice> smartDeviceList = new ArrayList<SmartDevice>();
        for (Map.Entry<String, SmartDevice> e : mDeviceHashMap.entrySet()) {
            SmartDevice smartDevice = e.getValue();
            if (smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.BINDING)) {
                smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.BINDING);
                smartDevice.getSmartDeviceLogic().deleteNetWork(Constant.NetWork.WAN_NET, false);
                smartDevice.getSmartDeviceLogic().setOwnerUserID("");
//                smartDevice.getSmartDeviceLogic().setDeviceName(getName(smartDevice));
            }
        }
        return smartDeviceList;
    }


    /**
     * 设置需要显示设备
     *
     * @param smartDevice     添加设备
     * @param smartDeviceList 添加集合
     */
    private void putDeviceToList(SmartDevice smartDevice, List<SmartDevice> smartDeviceList) {
            smartDeviceList.add(smartDevice);
    }


}