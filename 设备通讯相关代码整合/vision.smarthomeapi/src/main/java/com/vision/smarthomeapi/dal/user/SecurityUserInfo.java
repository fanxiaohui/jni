package com.vision.smarthomeapi.dal.user;

import android.util.ArrayMap;

import com.vision.smarthomeapi.bean.*;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.DeviceUpgradeManage;
import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.net.SecurityHTTPChannel;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.LocalCacheUtils;
import com.vision.smarthomeapi.util.MD5ToText;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PrefUtils;
import com.vision.smarthomeapi.util.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class SecurityUserInfo extends DataSupport {


    /**
     * 主键id
     */
    private int id;
    private String userID;
    private String userName;
    private String userIniAccount;
    private String userAccount;
    private String districtID;
    private String districtName;
    private String districtPhone;


    public Long vipTime;

    //服务信息
    public SecurityServiceInfo[] service = new SecurityServiceInfo[]{};
    public RSecurityCustomer customer;

    public SecurityUserInfo() {

    }

    public SecurityUserInfo(String userID, String userIniAccount, String userAccount,
                            String districtID, String districtName, String districtPhone) {
        this.userID = userID;
        this.userIniAccount = userIniAccount;
        this.userAccount = userAccount;
        this.districtID = districtID;
        this.districtName = districtName;
        this.districtPhone = districtPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserID() {
        return userID == null ? "" : userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIniAccount() {
        return userIniAccount;
    }

    public void setUserIniAccount(String userIniAccount) {
        this.userIniAccount = userIniAccount;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getDistrictID() {
        return districtID;
    }

    public void setDistrictID(String districtID) {
        this.districtID = districtID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictPhone() {
        return districtPhone;
    }

    public void setDistrictPhone(String districtPhone) {
        this.districtPhone = districtPhone;
    }

    public SecurityServiceInfo[] getService() {
        return service;
    }

    public void setService(SecurityServiceInfo[] service) {
        this.service = service;
    }

    public RSecurityCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(RSecurityCustomer customer) {
        this.customer = customer;
    }

    public Long getVipTime() {
        return vipTime;
    }

    public void setVipTime(Long vipTime) {
        this.vipTime = vipTime;
    }

    /**
     * 获取代理服务器地址方法
     */
    public void getCometadr() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CGetWebSocketInfo cGetWebSocketInfo = new CGetWebSocketInfo(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cGetWebSocketInfo);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getCometadrResponse(RBean rBean, RWebSocketInfoF rWebSocketInfoF) {
        if (rWebSocketInfoF == null) {
            return;
        }
        if (rBean.mode() == Bean.OK) {
            Controller.defaultController().handler.obtainMessage(1, rWebSocketInfoF.getData()).sendToTarget();
        }
    }

    /**
     * 获取用户设备列表
     */
    public void getDeviceList() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecurityDeviceList cSecurityDeviceList = new CSecurityDeviceList(SecurityUserManage.token, userID);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceListResponse(RBean rBean, RSecurityDeviceList rSecurityDeviceList) {
        if (rBean == null || rSecurityDeviceList == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            //获取报警信息
            alarmInfo();
            // 初始化数据并添加到设备列表中
            SmartDeviceManage.defaultManager().parseWanNetDeviceList(rSecurityDeviceList.getDeviceList());
            Controller.defaultController().handler.obtainMessage(3, Constant.UrlOrigin.security_token).sendToTarget();
        }
    }


    /**
     * 获取用户设备列表带分享
     */
    public void getDeviceListAndShareList() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecurityDeviceListAndShareList cSecurityDeviceListAndShareList = new CSecurityDeviceListAndShareList(SecurityUserManage.token, userID);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceListAndShareList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceListAndShareListResponse(RBean rBean, RSecurityDeviceList rSecurityDeviceList) {
        if (rBean == null || rSecurityDeviceList == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            //获取报警信息
            alarmInfo();
            // 初始化数据并添加到设备列表中
            SmartDeviceManage.defaultManager().parseWanNetDeviceList(rSecurityDeviceList.getDeviceList());
            Controller.defaultController().handler.obtainMessage(3, Constant.UrlOrigin.security_token).sendToTarget();
        }
    }

    /**
     * 获取用户可查看状态的设备列表
     */
    public void getDeviceListWithState() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecurityDeviceListWithState cSecurityDeviceListWithState = new CSecurityDeviceListWithState(SecurityUserManage.token, userID);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceListWithState);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceListWithStateResponse(RBean rBean, RSecurityDeviceList rSecurityDeviceList) {
        if (rBean == null || rSecurityDeviceList == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_WITHSTATE, null, rSecurityDeviceList);
        }
    }


    /**
     * 根据MAC查询设备
     *
     * @param mac 设备mac
     */
    public void getDeviceByMac(String mac) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecurityDeviceByMac cSecurityDeviceByMac = new CSecurityDeviceByMac(SecurityUserManage.token, mac);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceByMac);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceByMacResponse(RBean rBean, RSecurityDeviceByMac rSecurityDeviceByMac) {
        if (rBean == null || rSecurityDeviceByMac == null) {
            return;
        }

        OutPutMessage.LogCatInfo("设备类型修改", rSecurityDeviceByMac.getDevice().getMac() +
                "___" + rSecurityDeviceByMac.getDevice().getDevType());

        if (rBean.mode() == RBean.OK) {
            SmartDevice device = SmartDeviceManage.defaultManager().getDeviceHashMap().get(
                    rSecurityDeviceByMac.getDevice().getMac());
            if (device != null) {
                if (rSecurityDeviceByMac.getDevice().getDevType() != device.getSmartDeviceLogic().getDeviceTypeId()) {
                    OutPutMessage.LogCatInfo("设备类型修改", "发送通知");
                    NotificationManager.defaultManager().postNotification(
                            Constant.NotificationType.DEVICE_TYPE_CHANGE, null, rSecurityDeviceByMac, rBean);

                } if (rSecurityDeviceByMac.getDevice().getIpPort() != device.getSmartDeviceLogic().getIpPort()) {
                    OutPutMessage.LogCatInfo("设备上线", "发送通知");
                    device.getSmartDeviceLogic().setIpPort(rSecurityDeviceByMac.getDevice().getIpPort());
                    NotificationManager.defaultManager().postNotification(
                            Constant.NotificationType.DEVICE_LIST_CHANGE);
                }
            }
        }
    }

    /**
     * 会员信息
     */
    public void getLetvVipMe(){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipMe cLetvVipMe = new CLetvVipMe(SecurityUserManage.token);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipMe);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void getLetvVipMeResponse(RBean rBean, RLetvVipMe rLetvVipMe){
        if (rBean == null && rLetvVipMe == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            this.vipTime = rLetvVipMe.getViptime();
        }

    }


    /**
     * 开通会员列表
     */
    public void getLetvVipHis(){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipHis cLetvVipHis = new CLetvVipHis(SecurityUserManage.token);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipHis);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void getLetvVipHisResponse(RBean rBean, RLetvVipHis rLetvVipHis){
        if (rBean == null && rLetvVipHis == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.LETV_GET_VIP_HIS, null, rLetvVipHis, rBean);

        }

    }

    /**
     * 开通会员列表
     */
    public void getLetvVipMem(){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipMem cLetvVipMem = new CLetvVipMem(SecurityUserManage.token);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipMem);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void getLetvVipMemResponse(RBean rBean, RLetvVipMem rLetvVipMem){
        if (rBean == null && rLetvVipMem == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.LETV_GET_VIP_MEM, null, rLetvVipMem, rBean);

        }

    }

    /**
     * 开通会员开通按钮
     * @param pid
     */
    public void getLetvVipBm(String pid){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipBm cLetvVipBm = new CLetvVipBm(SecurityUserManage.token,pid);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipBm);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void getLetvVipBmResponse(RBean rBean, RLetvVipBm rLetvVipBm){
        if (rBean == null && rLetvVipBm == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.LETV_GET_VIP_BM, null, rLetvVipBm, rBean);

        }

    }

    /**
     * 选择支付方式后去支付
     * @param no 订单编号
     * @param pt 支付方式（1：支付宝，2：微信）
     */
    public void getLetvVipGo(String no,String pt){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipGo cLetvVipGo = new CLetvVipGo(SecurityUserManage.token,no,pt);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipGo);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void getLetvVipGoResponse(RBean rBean, RLetvVipGo rLetvVipGo){
        if (rBean == null && rLetvVipGo == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.LETV_GET_VIP_GO, null, rLetvVipGo, rBean);

        }

    }
    /**
     * 支付宝，app验签支付结果
     */
    public void setLetvVipFinal(LetvAlipayResult letvAlipayResult,String alipay_trade_app_pay_response ){
        if (Controller.defaultController().APPSendCode()) {
            if (SecurityUserManage.token != null) {
                CLetvVipFinal cLetvVipFinal = new CLetvVipFinal(letvAlipayResult,alipay_trade_app_pay_response);
                NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvVipFinal);
                message.setHttpGet(false);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }


    public void setLetvVipFinalResponse(RBean rBean, RLetvVipFinal rLetvVipFinal){
        if (rBean == null && rLetvVipFinal == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.LETV_GET_VIP_FINAL, null, rLetvVipFinal, rBean);

        }

    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        if (Controller.defaultController().APPSendCode()) {
            if (userID != null && SecurityUserManage.token != null) {
                CSecurityUserInfo securityUserInfo = new CSecurityUserInfo(SecurityUserManage.token, userID + "");
                NetworkMessage message = SecurityUserManage.getShare().buildBean(securityUserInfo);
                message.setHttpGet(true);
                message.setToken(SecurityUserManage.token);
                NetworkManager.defaultNetworkManager().sendMessage(message);
            }
        }
    }

    public void getUserInfoResponse(RBean rBean, RSecurityUserInfo rSecurityUserInfo) {
        if (rBean == null && rSecurityUserInfo == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            this.setService(rSecurityUserInfo.getServiceList());
            this.setCustomer(rSecurityUserInfo.getCustomer());
            this.userName = rSecurityUserInfo.getCustomer().getName();

            // 存储用户名称
            SecurityUserInfo securityUserInfo = new SecurityUserInfo();
            securityUserInfo.setUserID(userID);
            securityUserInfo.setUserName(userName);
            SecurityUserManage.getShare().updateUserFromDatabase(securityUserInfo);

            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.ACTIVITY_GET_USERINFO, null, null,rBean);
        }
    }

    /**
     * 保存个性账号保存
     *
     * @param account
     * @param password
     */
    public void userSaveAccount(String account, String password) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecuritySaveAccount cSecuritySaveAccount = new CSecuritySaveAccount(
                    SecurityUserManage.token, userID, account, password);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySaveAccount);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 保存个性账号返回
     *
     * @param rBean
     */
    public void userSaveAccountResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SAVE_ACCOUNT, null, null, rBean);
    }

    /**
     * 更新设备地址
     *
     * @param devID
     * @param address
     */
    public void updateAddName(String devID, String address) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateName cSecurityUpdateName = new CSecurityUpdateName(
                    SecurityUserManage.token, devID, address);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateName);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateAddNameResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATE_ADDNAME, null, null, rBean);
    }

    /**
     * 修改通用盒子可修改的设备类型
     */
    public void getDeviceCanModify() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {

            CSecurityDeviceCanModify cSecurityDeviceCanModify = new CSecurityDeviceCanModify(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceCanModify);
            message.setHttpGet(true);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceCanModifyResponse(RBean rBean, RSecurityDeviceCanModify rSecurityDeviceCanModify) {

        if (rBean == null) {
            return;
        }

        if (rBean.mode() == RBean.OK && rSecurityDeviceCanModify != null) {
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.GET_DEVICE_CAN_MODIFY, null, rSecurityDeviceCanModify, rBean);

        }

    }

    /**
     * 修改通用盒子的设备类型
     *
     * @param devID
     * @param type
     */
    public void updateType(int devID, int type) {
        OutPutMessage.LogCatInfo("修改通用盒子设备类型", devID + "  " + type);
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateType csecurityUpdateType = new CSecurityUpdateType(
                    SecurityUserManage.token, devID, type);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(csecurityUpdateType);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateTypeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATE_TYPE, null, null, rBean);
    }

    /**
     * 根据所选择的探测设备获取匹配的动作设备
     */
    public void getTwoLinkage(int devID) {
        OutPutMessage.LogCatInfo("获取二级联动", devID + " ");
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetTwoLinkage cSecurityGetTwoLinkage = new CSecurityGetTwoLinkage(
                    SecurityUserManage.token, devID);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetTwoLinkage);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getTwoLinkageResponse(RBean rBean, RSecurityDeviceList rSecurityDeviceList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETTWOLINKAGE, null, rSecurityDeviceList, rBean);
    }

    /**
     * 保存用户设置的二级联动方案
     */
    public void saveTwoLinkage(int deviceId, String ids) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecuritySaveTwoLinkage cSecuritySaveTwoLinkage = new CSecuritySaveTwoLinkage(
                    SecurityUserManage.token, deviceId, ids);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySaveTwoLinkage);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void saveTwoLinkageResponse(RBean rBean, RSecuritySaveLinkage rSecuritySaveLinkage) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SAVE_TWO_LINKAGE, null, rSecuritySaveLinkage, rBean);
    }

    /**
     * 获取三级联动方案
     */
    public void getThreeLinkage() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetThreeLinkage cSecurityGetThreeLinkage = new CSecurityGetThreeLinkage(
                    SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetThreeLinkage);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getThreeLinkageResponse(RBean rBean, RSecurityGetThreeLinkage rSecurityGetThreeLinkage) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETTHREELINKAGE, null, rSecurityGetThreeLinkage, rBean);
    }

    /**
     * 保存用户设置的二级联动方案
     */
    public void saveThreeLinkage(int scheme) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecuritySaveThreeLinkage cSecuritySaveThreeLinkage = new CSecuritySaveThreeLinkage(
                    SecurityUserManage.token, scheme);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySaveThreeLinkage);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void saveThreeLinkageResponse(RBean rBean, RSecuritySaveLinkage rSecuritySaveLinkage) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SAVE_THREE_LINKAGE, null, rSecuritySaveLinkage, rBean);
    }

    /**
     * 获取所有的历史报警记录
     */
    public void getAlarms(int start, int limit) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetAlarms cSecurityGetAlarms = new CSecurityGetAlarms(SecurityUserManage.token, getUserID(), start, limit);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetAlarms);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getAlarmsResponse(RBean rBean, RSecurityGetAlarms rSecurityGetAlarms) {
        if (rBean == null) {
            return;
        }
//        if (rSecurityGetAlarms.getAlarmList() != null) {
//            for (int i = 0; i < rSecurityGetAlarms.getAlarmList().length; i++) {
//                SecurityUserManage.alarmInfoMap.put(rSecurityGetAlarms.getAlarmList()[i].getId(),
//                        rSecurityGetAlarms.getAlarmList()[i]);
//            }
//        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETALARMS, null, rSecurityGetAlarms, rBean);
    }

    /**
     * 根据设备获取所有的历史报警记录
     */
    public void getDeviceAlarms(int start, int limit,int deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetDeviceAlarms cSecurityGetDeviceAlarms = new CSecurityGetDeviceAlarms(SecurityUserManage.token, deviceId, start, limit);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetDeviceAlarms);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getDeviceAlarmsResponse(RBean rBean, RSecurityGetAlarms rSecurityGetAlarms) {
        if (rBean == null) {
            return;
        }
//        if (rSecurityGetAlarms.getAlarmList() != null) {
//            for (int i = 0; i < rSecurityGetAlarms.getAlarmList().length; i++) {
//                SecurityUserManage.alarmInfoMap.put(rSecurityGetAlarms.getAlarmList()[i].getId(),
//                        rSecurityGetAlarms.getAlarmList()[i]);
//            }
//        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETALARMS, null, rSecurityGetAlarms, rBean);
    }

    /**
     * 根据设备清空告警记录
     */
    public void deleteDeviceAlarms(int deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityDeleteDeviceAlarms cSecurityDeleteDeviceAlarms = new CSecurityDeleteDeviceAlarms(SecurityUserManage.token, deviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeleteDeviceAlarms);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void deleteDeviceAlarmsResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_DELETEALARMS_BYID, null, null, rBean);
    }


    /**
     * 根据设备清空猫眼访客记录
     */
    public void deleteCatAlarms(int deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityDeleteCatAlarms cSecurityDeleteCatAlarms = new CSecurityDeleteCatAlarms(SecurityUserManage.token, deviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeleteCatAlarms);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void deleteCatAlarmsResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_DELETECAT_BYID, null, null, rBean);
    }


    /**
     * 获取设备各状态报告
     * deviceId	设备id
     * type	设备的状态类型 1:电压,2:电流,3:温度,4:报警
     * timeType	展示方式 1:去24小时，2:过去7天，3:过去30天
     */
    public void getStateReport(int deviceId, int type, int timeType) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetStateReport cSecurityGetStateReport = new CSecurityGetStateReport(
                    SecurityUserManage.token, deviceId, type, timeType);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetStateReport);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getStateReportResponse(RBean rBean, RSecurityStateReport rSecurityStateReport) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_STATEREPORT, null, rSecurityStateReport, rBean);
    }

    /**
     * 用户登出
     */
    public void userLoginOff() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null && userID != null) {
            CSecurityLoginOff cSecurityLoginOff = new CSecurityLoginOff(SecurityUserManage.token, userID);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityLoginOff);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void userLoginOffResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {

        }
    }

    /**
     * 更新布防撤防信息
     */
    public void updateDefense(int state) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateDefense cSecurityUpdateDefense = new CSecurityUpdateDefense(
                    SecurityUserManage.token, state);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateDefense);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateDefenseResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEDEFENSE, null, null, rBean);
    }

    /**
     * 获取验证原始手机号的验证码
     */
    public void updatePhoneValidateCode(String phoneNumber) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdatePhoneValidateCode cSecurityUpdatePhoneValidateCode =
                    new CSecurityUpdatePhoneValidateCode(SecurityUserManage.token, phoneNumber);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdatePhoneValidateCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updatePhoneValidateCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEDEPHONEValidateCODE, null, null, rBean);
    }

    /**
     * 验证原始手机号
     */
    public void updatePhoneValidate(String phoneNumber, String code) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdatePhoneValidate cSecurityUpdatePhoneValidate =
                    new CSecurityUpdatePhoneValidate(SecurityUserManage.token, phoneNumber, code);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdatePhoneValidate);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updatePhoneValidateResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEPHONEVALIDATE, null, null, rBean);
    }

    /**
     * 获取修改手机的验证码
     */
    public void updatePhoneCode(String phoneNumber) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdatePhoneCode cSecurityUpdatePhoneCode = new CSecurityUpdatePhoneCode(
                    SecurityUserManage.token, phoneNumber);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdatePhoneCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updatePhoneCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEDEPHONECODE, null, null, rBean);
    }

    /**
     * 修改手机号码
     */
    public void updatePhone(String phoneNumber, String code) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdatePhone cSecurityUpdatePhone = new CSecurityUpdatePhone(
                    SecurityUserManage.token, phoneNumber, code);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdatePhone);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updatePhoneResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEDEPHONE, null, null, rBean);
    }

    /**
     * 修改密码
     */
    public void updatePwd(String oldPwd, String newPwd) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdatePwd cSecurityUpdatePwd = new CSecurityUpdatePwd(
                    SecurityUserManage.token, MD5ToText.MD5Encode(oldPwd), MD5ToText.MD5Encode(newPwd));
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdatePwd);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updatePwdResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEDEPWD, null, null, rBean);
    }

    /**
     * 修改邮箱
     */
    public void updateEmail(String oldEmail, String newEmail) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateEmail cSecurityUpdateEmail = new CSecurityUpdateEmail(
                    SecurityUserManage.token, oldEmail, newEmail);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateEmail);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateEmaildResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATEEMAIL, null, null, rBean);
    }

    /**
     * 修改紧急联系人
     */
    public void updateContact(String name, String phone) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateContact cSecurityUpdateContact = new CSecurityUpdateContact(
                    SecurityUserManage.token, name, phone);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateContact);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateContactResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATECONTACT, null, null, rBean);
    }

    /**
     * 修改备用联系人
     */
    public void updateStandby(String name, String phone) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateStandby cSecurityUpdateStandby = new CSecurityUpdateStandby(
                    SecurityUserManage.token, name, phone);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateStandby);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateStandbyResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATESTANDBY, null, null, rBean);
    }

    /**
     * 获取设备历史报警记录
     */
    public void alarmList(String time) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmList cSecurityAlarmList = new CSecurityAlarmList(SecurityUserManage.token, time);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void alarmList(int recordId, String time) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmList cSecurityAlarmList = new CSecurityAlarmList(SecurityUserManage.token, recordId, time);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void alarmListResponse(RBean rBean, RSecurityDeviceAlarmList rSecurityDeviceAlarmList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_ALARMLIST, null, rSecurityDeviceAlarmList, rBean);
    }

    /**
     * 获取设备有报警记录的月份
     */
    public void alarmMonth() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmMonth cSecurityAlarmMonth = new CSecurityAlarmMonth(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmMonth);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void alarmMonthResponse(RBean rBean, RSecurityAlarmMonth rSecurityAlarmMonth) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_ALARMMONTH, null, rSecurityAlarmMonth, rBean);
    }

    /**
     * 根据服务ID获取报警信息
     *
     * @param serviceId
     */
    public void alarmInfo_byServiceId(int serviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmInfoByServiceId cSecurityAlarmInfo = new CSecurityAlarmInfoByServiceId(
                    SecurityUserManage.token, serviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmInfo);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void alarmInfo_byServiceIdResponse(RBean rBean,
                                              RSecurityDeviceAlarmInfoList rSecurityDeviceAlarmInfoList) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            NotificationManager.defaultManager().postNotification(
                    Constant.NotificationType.ALARM_HTTP_INFO, null, rSecurityDeviceAlarmInfoList);
        }
    }

    /**
     * 获取全部设备报警详情
     */
    public void alarmInfo() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmInfo cSecurityAlarmInfo = new CSecurityAlarmInfo(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmInfo);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void alarmInfoResponse(RBean rBean, RSecurityDeviceAlarmInfoList rSecurityDeviceAlarmInfoList) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK) {
            if (rSecurityDeviceAlarmInfoList != null && rSecurityDeviceAlarmInfoList.getDeviceList() != null) {
                for (int i = 0; i < rSecurityDeviceAlarmInfoList.getDeviceList().length; i++) {
                    RSecurityDevice rSecurityDevice = rSecurityDeviceAlarmInfoList.getDeviceList()[i];
                    OutPutMessage.LogCatInfo("告警HTTP", rSecurityDevice.getMac() +
                            "___" + rSecurityDevice.getAlarmType());

                    String mac = rSecurityDevice.getMac();

                    //====所有的报警信息目前很混乱====
                    SecurityUserManage.deviceAlarm.put(mac,rSecurityDevice);

                    SmartDevice smartDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(mac);
                    if (smartDevice != null) {
                        if (smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ALARM)) {

                        } else {
                            //原来不是报警的话，除离线报警外的报警认为设备报警
                            if (!rSecurityDevice.getAlarmType().equals("离线")) {
                                smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.ALARM);
                            } else {
                                smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.ALARM);
                            }
                        }
                    }
                }
                NotificationManager.defaultManager().postNotification(
                        Constant.NotificationType.ALARM_HTTP_INFO_ALL, null, rSecurityDeviceAlarmInfoList);
            } else {
                List<SmartDevice> smartDeviceList = SmartDeviceManage.defaultManager().findDeviceList();
                for (SmartDevice smartDevice : smartDeviceList) {
                    smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.ALARM);
                }
            }
            NotificationManager.defaultManager().postNotification(Constant.NotificationType.DEVICE_LIST_CHANGE, null);
        }
    }

    /**
     * 获取短问卷列表
     */
    public void getShortQuestion() {
        OutPutMessage.LogCatInfo("获取短问卷",
                "获取获取" + Controller.defaultController().APPSendCode() + "___" + SecurityUserManage.token);

        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetShortQuestionnaire cSecurityGetShortQuestionnaire =
                    new CSecurityGetShortQuestionnaire(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetShortQuestionnaire);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getShortQuestionResponse(RBean rBean,
                                         RSecurityShortQuestionnaireData rSecurityShortQuestionnaireData) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETSHORTQUESTION, null, rSecurityShortQuestionnaireData, rBean);
    }

    /**
     * 查看用户是否做过问卷调查
     */
    public void checkQuestion() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityCheckQuestion cSecurityCheckQuestion = new CSecurityCheckQuestion(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityCheckQuestion);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void checkQuestionResponse(RBean rBean, RSecurityCheckQuestion rSecurityCheckQuestion) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_CHECKQUESTION, null, rSecurityCheckQuestion, rBean);
    }


    /**
     * 获取长问卷连接
     */
    public void getLongQuestion() {
        OutPutMessage.LogCatInfo("获取短问卷", Controller.defaultController().APPSendCode()
                + "___" + SecurityUserManage.token);

        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetLongQuestion cSecurityGetLongQuestion = new CSecurityGetLongQuestion(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetLongQuestion);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getLongQuestionResponse(RBean rBean, RSecurityGetQuestion rSecurityGetQuestion) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETLONGQUESTION, null, rSecurityGetQuestion, rBean);
    }

    /**
     * 获取问卷列表
     */
    public void getQuestionList() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityQuestionnaireData cSecurityQuestionnaireData = new CSecurityQuestionnaireData();
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityQuestionnaireData);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getQuestionListResponse(RBean rBean, RSecurityQuestionnaireData rSecurityQuestionnaireData) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETQUESTION, null, rSecurityQuestionnaireData, rBean);
    }

    /**
     * 保存答案
     *
     * @param date 日期
     */
    public void updateAnswer(String date) {
        OutPutMessage.LogCatInfo("HTTP保存答案", date);

        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecuritySubmit cSecuritySubmit = new CSecuritySubmit(SecurityUserManage.token, date);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySubmit);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void updateAnswerResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SUBMITQUESTION, null, null, rBean);
    }

    /**
     * 设备更新请求地址
     *
     * @param type
     */
    public void checkDeviceVersion(String type, String mac, String mode) {
        OutPutMessage.LogCatInfo("升级传参", type + "___" + mac);

        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateDevice cSecurityUpdateDevice = new CSecurityUpdateDevice(
                    SecurityUserManage.token, type, mac, mode);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateDevice);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void checkDeviceVersionResponse(RBean rBean, RUpgradeInfo upgradeInfo) {
        if (rBean == null) {
            return;
        }
        OutPutMessage.LogCatInfo("升级版本HTTP返回2", upgradeInfo + "");
        DeviceUpgradeManage.defaultDeviceUpgradeManager().updateDeviceInfo(upgradeInfo);
    }

    /**
     * 更新昵称
     *
     * @param name 昵称
     */
    public void updateNick(String name) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateNick cSecurityUpdateNick = new CSecurityUpdateNick(SecurityUserManage.token, name);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateNick);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 更新昵称回复
     *
     * @param rBean 回复信息
     */
    public void updateNickResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATE_NICK, null, null, rBean);
    }

    /**
     * 验证安全码
     *
     * @param code 安全码
     */
    public void checkSecurityCode(String code) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            String codeEncrypt = MD5ToText.MD5Encode(MD5ToText.MD5Encode(code));
            CSecurityCheckCode cSecurityCheckCode = new CSecurityCheckCode(
                    SecurityUserManage.token, codeEncrypt);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityCheckCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 验证安全码回复
     *
     * @param rBean 回复信息
     */
    public void checkSecurityCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_CHECK_CODE, null, null, rBean);
    }

    /**
     * 获取门锁的授权解锁码
     *
     * @param id 设备ID
     */
    public void getLockKey(int id) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetLockKey cSecurityGetLockKey = new CSecurityGetLockKey(SecurityUserManage.token, id);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetLockKey);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取门锁的授权解锁码回复
     *
     * @param rBean               回复信息
     * @param rSecurityGetLockKey 回复信息
     */
    public void getLockKeyResponse(RBean rBean, RSecurityGetLockKey rSecurityGetLockKey) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_LOCK_KEY, null, rSecurityGetLockKey, rBean);
    }

    /**
     * 获取用户的布撤防状态
     */
    public void getDefenceStatus() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetDefenceStatus cSecurityGetDefenceStatus = new CSecurityGetDefenceStatus(
                    SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetDefenceStatus);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取用户的布撤防状态回复
     *
     * @param rBean                     回复信息
     * @param rSecurityGetDefenceStatus 回复信息
     */
    public void getDefenceStatusResponse(RBean rBean, RSecurityGetDefenceStatus rSecurityGetDefenceStatus) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_DEFENCE_STATUS, null, rSecurityGetDefenceStatus, rBean);
    }


    /**
     * 查首页告警信息
     */
    public void getCurrentAlarms() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetCurrentAlarms cSecurityGetCurrentAlarms = new CSecurityGetCurrentAlarms(
                    SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetCurrentAlarms);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 查首页告警信息回复
     *
     * @param rBean                     回复信息
     */
    public void getCurrentAlarmsResponse(RBean rBean,RGetCurrentAlarms rGetCurrentAlarms) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_CURRENTALARMS, null, rGetCurrentAlarms, rBean);
    }

    /**
     * 获取猫眼的告警详情
     *
     * @param id 告警ID
     */
    public void getCatEyeInfo(int id) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetCatEyeInfo cSecurityGetCatEyeInfo = new CSecurityGetCatEyeInfo(
                    SecurityUserManage.token, id,System.currentTimeMillis());
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetCatEyeInfo);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取猫眼的告警详情回复
     *
     * @param rBean                  回复信息
     * @param rSecurityGetCatEyeInfo 回复信息
     */
    public void getCatEyeInfoResponse(RBean rBean, RSecurityGetCatEyeInfo rSecurityGetCatEyeInfo) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_CATEYE_INFO, null, rSecurityGetCatEyeInfo, rBean);
    }

    /**
     * 更改门铃的红外推送策略
     *
     * @param devId    设备ID
     * @param strategy 推送策略
     */
    public void setPushStrategy(int devId, int strategy) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecuritySetPushStrategy cSecuritySetPushStrategy = new CSecuritySetPushStrategy(
                    SecurityUserManage.token, devId, strategy);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySetPushStrategy);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 更改门铃的红外推送策略回复
     *
     * @param rBean                    回复信息
     * @param rSecuritySetPushStrategy 回复信息
     */
    public void setPushStrategyResponse(RBean rBean, RSecuritySetPushStrategy rSecuritySetPushStrategy) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SET_PUSH_STRATEGY, null, rSecuritySetPushStrategy, rBean);
    }

    /**
     * 获取用户访客记录
     *
     * @param customerId 用户ID
     * @param start      开始位置
     * @param limit      记录条数
     */
    public void getVisitors(String customerId, int start, int limit) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetVisitors cSecurityGetVisitors = new CSecurityGetVisitors(
                    SecurityUserManage.token, customerId, start, limit);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetVisitors);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取用户访客记录回复
     *
     * @param rBean                回复信息
     * @param rSecurityGetVisitors 回复信息
     */
    public void getVisitorsResponse(RBean rBean, RSecurityGetVisitors rSecurityGetVisitors) {
        if (rBean == null) {
            return;
        }

        for (int i=0;i<rSecurityGetVisitors.getAlarmList().size();i++){
            OutPutMessage.LogCatInfo("访客记录",rSecurityGetVisitors.getAlarmList().get(i).getAlarmTime()+"   "+rSecurityGetVisitors.getAlarmList().get(i).getPicUrl());
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_VISITORS, null, rSecurityGetVisitors, rBean);
    }

    /**
     * 根据设备获取用户访客记录
     *
     * @param deviceId   设备ID
     * @param start      开始位置
     * @param limit      记录条数
     */
    public void getVisitorsForID(int deviceId, int start, int limit) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetVisitorsForID cSecurityGetVisitorsForID = new CSecurityGetVisitorsForID(
                    SecurityUserManage.token, deviceId, start, limit, System.currentTimeMillis());
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetVisitorsForID);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    /**
     * 获取用户告警信息
     *
     * @param id 告警ID
     */
    public void getAlarmInfo(int id) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetAlarmInfo cSecurityGetAlarmInfo = new CSecurityGetAlarmInfo(
                    SecurityUserManage.token, id);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetAlarmInfo);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取用户告警信息回复
     *
     * @param rBean                 回复信息
     * @param rSecurityGetAlarmInfo 回复信息
     */
    public void getAlarmInfoResponse(RBean rBean, RSecurityGetAlarmInfo rSecurityGetAlarmInfo) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_ALARM_INFO, null, rSecurityGetAlarmInfo, rBean);
    }

    /**
     * 获取告警类型的提示
     */
    public void getAlarmTips() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetAlarmTips cSecurityGetAlarmTips = new CSecurityGetAlarmTips(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetAlarmTips);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取告警类型的提示回复
     *
     * @param rBean     回复信息
     * @param alarmTips 回复信息
     */
    public void getAlarmTipsResponse(RBean rBean, String alarmTips) {
        if (rBean == null) {
            return;
        }
        PrefUtils.putString("alarmTips", alarmTips);
    }

    /**
     * 设备绑定
     *
     * @param qrcode 二维码
     */
    public void deviceBind(String qrcode) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityDeviceBind cSecurityDeviceBind = new CSecurityDeviceBind(
                    SecurityUserManage.token, qrcode);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceBind);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 设备绑定回复
     *
     * @param rBean               回复信息
     * @param rSecurityDeviceBind 回复信息
     */
    public void deviceBindResponse(RBean rBean, RSecurityDeviceBind rSecurityDeviceBind) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_DEVICE_BIND, null, rSecurityDeviceBind, rBean);
    }

    /**
     * 设备解绑
     *
     * @param deviceId 设备ID
     */
    public void deviceUnbind(String deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityDeviceUnbind cSecurityDeviceUnbind = new CSecurityDeviceUnbind(
                    SecurityUserManage.token, deviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeviceUnbind);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 设备解绑回复
     *
     * @param rBean                 回复信息
     * @param rSecurityDeviceUnbind 回复信息
     */
    public void deviceUnbindResponse(RBean rBean, RSecurityDeviceUnbind rSecurityDeviceUnbind) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_DEVICE_UNBIND, null, rSecurityDeviceUnbind, rBean);
    }

    /**
     * 设置安全码
     *
     * @param code 安全码
     * @param identifier 身份证
     */
    public void setSecurityCode(String code,String identifier) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecuritySetCode cSecuritySetCode = new CSecuritySetCode(SecurityUserManage.token,identifier,
                    MD5ToText.MD5Encode(code));
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecuritySetCode);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 设置安全码回复
     *
     * @param rBean 回复信息
     */
    public void setSecurityCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SET_CODE, null, null, rBean);
    }

    /**
     * 获取用户告警状态
     */
    public void getAlarmStatus() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAlarmStatus cSecurityAlarmStatus = new CSecurityAlarmStatus(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAlarmStatus);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取用户告警状态回复
     *
     * @param rBean                回复信息
     * @param rSecurityAlarmStatus 回复信息
     */
    public void getAlarmStatusResponse(RBean rBean, RSecurityAlarmStatus rSecurityAlarmStatus) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_ALARM_STATUS, null, rSecurityAlarmStatus, rBean);
    }

    /**
     * 获取门铃的红外推送策略
     *
     * @param devId 设备ID
     */
    public void getPushStrategy(int devId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetPushStrategy cSecurityGetPushStrategy = new CSecurityGetPushStrategy(
                    SecurityUserManage.token, devId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetPushStrategy);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 获取门铃的红外推送策略回复
     *
     * @param rBean                    回复信息
     * @param rSecurityGetPushStrategy 回复信息
     */
    public void getPushStrategyResponse(RBean rBean, RSecurityGetPushStrategy rSecurityGetPushStrategy) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GET_PUSH_STRATEGY, null, rSecurityGetPushStrategy, rBean);
    }

    /**
     * 重置安全码（上传身份证照片）
     *
     * @param fileName 照片名称
     */
    public void updateSecurityCode(String fileName) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            File file = LocalCacheUtils.getServiceImageFileFromLocal(fileName);
            SecurityHTTPChannel.fileUpload(Constant.UrlOrigin.security_update_code,
                    SecurityUserManage.token, file);
        }
    }

    /**
     * 反馈
     */
    public void feedBack(ArrayList<String> fileName,String content,String contactWay){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            List<File> files = new ArrayList<>();

            for (int i=0;i<fileName.size();i++) {
                File file = LocalCacheUtils.getSystemImageFileFromLocal(fileName.get(i));
                files.add(file);
            }


            Map<String ,String > parms = new HashMap<>();

            //添加其它信息
            parms.put("token",SecurityUserManage.token);
            parms.put("content", content);
            parms.put("contactWay",contactWay);


            SecurityHTTPChannel.sendMultipart(Constant.UrlOrigin.letv_feed_back,parms,"uploadFiles",files,Constant.NotificationType.LETV_FEED_BACK);

        }
    }


    /**
     * 获取消息列表
     */
    public void getNews(int start, int limit) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetNews cLetvGetNews = new CLetvGetNews(SecurityUserManage.token,  start, limit);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetNews);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getNewsResponse(RBean rBean, RLetvNewsList rLetvNewsList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GET_NEWS, null, rLetvNewsList, rBean);
    }

    /**
     * 获取开锁记录
     */
    public void getNotes(int start, int limit,int deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetNotes cLetvGetNotes = new CLetvGetNotes(SecurityUserManage.token,  start, limit,deviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetNotes);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getNotesResponse(RBean rBean, RLetvNotesList rLetvNotesList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GET_NOTES, null, rLetvNotesList, rBean);
    }
    /**
     * 指纹设置
     */
    public void getUpdateFingerprint(String headId,String name,String fingerprint,int deviceId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvUpdateFingerprint cLetvUpdateFingerprint = new CLetvUpdateFingerprint(SecurityUserManage.token,  headId, name,fingerprint,deviceId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvUpdateFingerprint);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getUpdateFingerprintResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_UPDATE_Fingerprint, null, null, rBean);
    }



    /**
     * 获取工单列表
     */
    public void getOrderList(int start, int limit) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetOrderList cLetvGetOrderList = new CLetvGetOrderList(SecurityUserManage.token,  start, limit);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetOrderList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getOrderListResponse(RBean rBean, RLetvOrderList rLetvOrderList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GET_ORDERLIST, null, rLetvOrderList, rBean);
    }



    /**
     * 同步京东订单
      * @param jdCode
     */
    public void synJDOrder(String jdCode){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvSynJDOrder cLetvSynJDOrder = new CLetvSynJDOrder(SecurityUserManage.token,  jdCode);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvSynJDOrder);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }

    }



    public void synJDOrderResponse(RBean rBean, RLetvSynJDOrder rLetvSynJDOrder) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_SYN_JDORDER, null, rLetvSynJDOrder, rBean);
    }


    /**
     * 确认收货扫二维码
     * @param
     */
    public void checkReciverLock(String tdCode){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvCheckReciverLock cLetvCheckReciverLock = new CLetvCheckReciverLock(SecurityUserManage.token,  tdCode);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvCheckReciverLock);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }

    }



    public void checkReciverLockResponse(RBean rBean, RLetvSynJDOrder rLetvSynJDOrder) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_SYN_JDORDER, null, rLetvSynJDOrder, rBean);
    }




    /**
     * 获取订单详情
     * @param orderId
     */
    public void getOrderDetail(int orderId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetOrderDetail cLetvGetOrderDetail = new CLetvGetOrderDetail(SecurityUserManage.token,  orderId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetOrderDetail);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void getOrderDetailResponse(RBean rBean, RLetvGetOrderDetail rLetvGetOrderDetail) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GETORDERDETAIL, null, rLetvGetOrderDetail, rBean);
    }

    /**
     * 获取用户绑定申请
     */
    public void getDistrictApply(){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetDistrictApply cLetvGetDistrictApply = new CLetvGetDistrictApply(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetDistrictApply);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void getDistrictApplyResponse(RBean rBean, RLetvGetDistrictApply rLetvGetDistrictApply) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GETDISTRICTAPPLY, null, rLetvGetDistrictApply, rBean);
    }


    /**
     * 获取所有小区信息
     */
    public void getAllDistrict(){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetAllDistrict cLetvGetAllDistrict = new CLetvGetAllDistrict(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetAllDistrict);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void getAllDistrictResponse(RBean rBean, RLetvGetAllDistrict rLetvGetAllDistrict) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GETALLDISTRICT, null, rLetvGetAllDistrict, rBean);
    }

    /**
     * 猫眼获取所有可绑定的设备
     */
    public void getUnLinkbindDevices(int realType){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityGetUnLinkbindDevices cSecurityGetUnLinkbindDevices = new CSecurityGetUnLinkbindDevices(SecurityUserManage.token,realType);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityGetUnLinkbindDevices);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void getUnLinkbindDevicesResponse(RBean rBean, RSecurityBindDeviceList rSecurityDeviceList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_GETUNLINKBINDDEVICE, null, rSecurityDeviceList, rBean);
    }

    /**
     * 绑定猫眼门锁关系
     */
    public void bindLinkDevice(int id,int bindId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityBindLinkDevice cSecurityBindLinkDevice = new CSecurityBindLinkDevice(SecurityUserManage.token,id,bindId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityBindLinkDevice);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void bindLinkDeviceResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_BINDLINKDEVICE, null, null, rBean);
    }


    /**
     * 解除猫眼门锁关系
     */
    public void deletebindLinkDevice(int id){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityDeleteBindLinkDevice cSecurityDeleteBindLinkDevice = new CSecurityDeleteBindLinkDevice(SecurityUserManage.token,id);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityDeleteBindLinkDevice);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void deletebindLinkDeviceResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_DELETEBINDLINKDEVICE, null, null, rBean);
    }

    /**
     * 申请绑定物业
     */
    public void applyDistrict(int id){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvApplyDistrict cLetvApplyDistrict = new CLetvApplyDistrict(id,SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvApplyDistrict);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void applyDistrictResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_APPLYPROPERTY, null, null, rBean);
    }



    /**
     * 服务地址验证
     */
    public void checkOrderAdd(String address){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvCheckOrderAdd cLetvCheckOrderAdd = new CLetvCheckOrderAdd(SecurityUserManage.token,address);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvCheckOrderAdd);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void checkOrderAddResponse(RBean rBean,RLetvCheckAdd rLetvCheckAdd) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_CHECKORDERADD, null, rLetvCheckAdd, rBean);
    }
    /**
     * 锁猫眼确认升级
     */
    public void sureUpgrade(String mac){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvSureUpgrade cLetvSureUpgrade = new CLetvSureUpgrade(SecurityUserManage.token,mac);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvSureUpgrade);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void sureUpgradeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_SURE_UPGRADE, null, null, rBean);
    }

    /**
     * 获取设备分享列表
     */
    public void shareRevicerList(){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityRevicerList cSecurityRevicerList = new CSecurityRevicerList(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityRevicerList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareRevicerListResponse(RBean rBean,RShareList rShareList) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_SHARELIST, null, rShareList, rBean);


    }
    /**
     * 获取分享设备接收列表
     */
    public void shareAcceptList(){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityAcceptList cSecurityAcceptList = new CSecurityAcceptList(SecurityUserManage.token);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityAcceptList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareAcceptListResponse(RBean rBean,RShareAcceptList rShareAcceptList) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_ACCEPTLIST, null, rShareAcceptList, rBean);


    }

    /**
     * 分享账号检测
     */
    public void shareCheckAccount(String account){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareCheckAccount cShareCheckAccount = new CShareCheckAccount(SecurityUserManage.token,account);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareCheckAccount);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareCheckAccountResponse(RBean rBean,RShareCheckAccount rShareCheckAccount) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_CHECKACCPINT, null, rShareCheckAccount, rBean);

    }


    /**
     * 分享设备
     */
    public void shareAddShare(String account,String deviceIds){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareAddShare cShareAddShare = new CShareAddShare(SecurityUserManage.token,account,deviceIds);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareAddShare);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareAddShareResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_ADDSHARE, null, null, rBean);

    }


    /**
     * 分享并接收设备
     */
    public void shareAddSureShare(int revicerId,String deviceIds){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareAddSureShare cShareAddShareSure = new CShareAddSureShare(SecurityUserManage.token,revicerId,deviceIds);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareAddShareSure);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareAddSureShareResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_ADDSHARE_SURE, null, null, rBean);

    }

    /**
     * 取消分享设备
     */
    public void shareCancelShare(int revicerId,String deviceIds){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareCancelShare cShareCancelShare = new CShareCancelShare(SecurityUserManage.token,revicerId,deviceIds);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareCancelShare);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void shareCancelShareResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_CANCELSHARE, null, null, rBean);

    }

    /**
     * 接受分享设备
     */
    public void sureShare(int shareId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareSureShare cShareSureShare = new CShareSureShare(SecurityUserManage.token,shareId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareSureShare);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void sureShareResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SURE_SHARE, null, null, rBean);

    }


    /**
     * 拒绝分享设备
     */
    public void refusedShare(int shareId,String deviceIds){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CRefusedSureShare cRefusedSureShare = new CRefusedSureShare(SecurityUserManage.token,shareId,deviceIds);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cRefusedSureShare);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void refusedShareResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_REFUSER_SHARE, null, null, rBean);

    }
    /**
     * 管理分享设备列表
     */
    public void shareManageList(int revicerId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CShareManageList cShareManageList = new CShareManageList(SecurityUserManage.token,revicerId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cShareManageList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void shareManageListResponse(RBean rBean,RShareManageList rShareManageList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_SHARE_LISTINFO, null, rShareManageList, rBean);

    }

    /**
     * 更新分享昵称
     *
     * @param name 昵称
     * @param revicerId 接受者ID
     */
    public void updateShareNick(String name,int revicerId) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityUpdateShareNick cSecurityUpdateShareNick = new CSecurityUpdateShareNick(SecurityUserManage.token, name,revicerId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityUpdateShareNick);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 更新分享昵称回复
     *
     * @param rBean 回复信息
     */
    public void updateShareNickResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_UPDATE_SHARE_NICK, null, null, rBean);
    }

    List<File> files;
    /**
     * 创建订单
     */
    public void createOrder(ArrayList<String> fileName,int type,String devCode,int area1,int area2,int area3,
                            String serviceAddress,String customerName,String contactWay
            ,String questionDesc,RLetvCheckAdd rLetvCheckAdd,Long hopeTime
    ){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            files = new ArrayList<>();

            for (int i=0;i<fileName.size();i++) {
                File file = LocalCacheUtils.getSystemImageFileFromLocal(fileName.get(i));
                files.add(file);
            }


            Map<String ,String > parms = new HashMap<>();
            parms.put("token", SecurityUserManage.token);
            parms.put("type",type+"");
            parms.put("devCode",devCode);
            parms.put("area1",area1+"");
            parms.put("area2",area2+"");
            parms.put("area3",area3+"");

            parms.put("customerName",customerName);
            parms.put("contactWay",contactWay);
            parms.put("questionDesc",questionDesc);
            parms.put("hopeTime",hopeTime+"");
            if (rLetvCheckAdd!= null && rLetvCheckAdd.getRemap() != null){
                parms.put("location",rLetvCheckAdd.getRemap().getLocation());
                parms.put("street",rLetvCheckAdd.getRemap().getStreet());
                parms.put("province",rLetvCheckAdd.getRemap().getProvince());
                parms.put("city",rLetvCheckAdd.getRemap().getCity());
                parms.put("district",rLetvCheckAdd.getRemap().getDistrict());
                parms.put("serviceAddress",rLetvCheckAdd.getRemap().getServiceAddress());

            }


            SecurityHTTPChannel.sendMultipart(Constant.UrlOrigin.letv_create_order,parms,"uploadFiles",files,Constant.NotificationType.LETV_CREATE_ORDER);

        }
    }


    /**
     * 用户评价
     */
    public void addAssess(ArrayList<String> fileName,int qualityScore,int serviceScore,int trainScore,String content,int orderId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            List<File> files = new ArrayList<>();

            for (int i=0;i<fileName.size();i++) {
                File file = LocalCacheUtils.getSystemImageFileFromLocal(fileName.get(i));
                files.add(file);
            }


            Map<String ,String > parms = new HashMap<>();
            //添加其它信息
            parms.put("token",SecurityUserManage.token);
            parms.put("qualityScore",qualityScore+"");
            parms.put("serviceScore", serviceScore+"");
            parms.put("trainScore",trainScore+"");
            parms.put("content",content);
            parms.put("orderId",orderId+"");


            SecurityHTTPChannel.sendMultipart(Constant.UrlOrigin.letv_add_assess,parms,"uploadFiles",files,Constant.NotificationType.LETV_ORDERASSESS);

        }
    }


    /**
     * 获取订单进度
     * @param orderId
     */
    public void getCommunicationList(int orderId){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetCommunicationList cLetvGetCommunicationList = new CLetvGetCommunicationList(SecurityUserManage.token,  orderId);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetCommunicationList);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }



    public void getCommunicationListResponse(RBean rBean, RLetvCommunicationList rLetvCommunicationList) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_COMMUMICATION, null, rLetvCommunicationList, rBean);
    }



}