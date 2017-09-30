package com.vision.smarthomeapi.bll.manage;

import android.content.ContentValues;
import android.content.Context;
import android.text.TextUtils;

import com.vision.smarthomeapi.bean.Bean;
import com.vision.smarthomeapi.bean.CLetvGetAreaAll;
import com.vision.smarthomeapi.bean.CLetvUserBindShowNo;
import com.vision.smarthomeapi.bean.CSecurityCheckversion;
import com.vision.smarthomeapi.bean.CSecurityForgetPW;
import com.vision.smarthomeapi.bean.CSecurityForgetPWCode;
import com.vision.smarthomeapi.bean.CSecurityForgetPwCheckCode;
import com.vision.smarthomeapi.bean.CSecurityLogin;
import com.vision.smarthomeapi.bean.CSecurityRefreshToken;
import com.vision.smarthomeapi.bean.CSecurityToken;
import com.vision.smarthomeapi.bean.CSecurityTypeInfo;
import com.vision.smarthomeapi.bean.CSecurityWeatherInfo;
import com.vision.smarthomeapi.bean.CUserRegister;
import com.vision.smarthomeapi.bean.CUserRegisterCheckCode;
import com.vision.smarthomeapi.bean.CUserRegisterGetCode;
import com.vision.smarthomeapi.bean.RAlarmInfo;
import com.vision.smarthomeapi.bean.RBean;
import com.vision.smarthomeapi.bean.RCheckDeviceVersion;
import com.vision.smarthomeapi.bean.RCurrentAlarm;
import com.vision.smarthomeapi.bean.RGetCurrentAlarms;
import com.vision.smarthomeapi.bean.RLetvCheckAdd;
import com.vision.smarthomeapi.bean.RLetvCommunication;
import com.vision.smarthomeapi.bean.RLetvCommunicationList;
import com.vision.smarthomeapi.bean.RLetvGetAllDistrict;
import com.vision.smarthomeapi.bean.RLetvGetAreaAll;
import com.vision.smarthomeapi.bean.RLetvGetDistrictApply;
import com.vision.smarthomeapi.bean.RLetvGetOrderDetail;
import com.vision.smarthomeapi.bean.RLetvNewsList;
import com.vision.smarthomeapi.bean.RLetvNotes;
import com.vision.smarthomeapi.bean.RLetvNotesList;
import com.vision.smarthomeapi.bean.RLetvOrderList;
import com.vision.smarthomeapi.bean.RLetvSynJDOrder;
import com.vision.smarthomeapi.bean.RLetvVipBm;
import com.vision.smarthomeapi.bean.RLetvVipFinal;
import com.vision.smarthomeapi.bean.RLetvVipGo;
import com.vision.smarthomeapi.bean.RLetvVipHis;
import com.vision.smarthomeapi.bean.RLetvVipMe;
import com.vision.smarthomeapi.bean.RLetvVipMem;
import com.vision.smarthomeapi.bean.RSecurityAlarmMonth;
import com.vision.smarthomeapi.bean.RSecurityAlarmStatus;
import com.vision.smarthomeapi.bean.RSecurityBindDeviceList;
import com.vision.smarthomeapi.bean.RSecurityCheckQuestion;
import com.vision.smarthomeapi.bean.RSecurityCheckversion;
import com.vision.smarthomeapi.bean.RSecurityDevice;
import com.vision.smarthomeapi.bean.RSecurityDeviceAlarmInfoList;
import com.vision.smarthomeapi.bean.RSecurityDeviceAlarmList;
import com.vision.smarthomeapi.bean.RSecurityDeviceBind;
import com.vision.smarthomeapi.bean.RSecurityDeviceByMac;
import com.vision.smarthomeapi.bean.RSecurityDeviceCanModify;
import com.vision.smarthomeapi.bean.RSecurityDeviceList;
import com.vision.smarthomeapi.bean.RSecurityDeviceUnbind;
import com.vision.smarthomeapi.bean.RSecurityGetAlarmInfo;
import com.vision.smarthomeapi.bean.RSecurityGetAlarms;
import com.vision.smarthomeapi.bean.RSecurityGetCatEyeInfo;
import com.vision.smarthomeapi.bean.RSecurityGetDefenceStatus;
import com.vision.smarthomeapi.bean.RSecurityGetLockKey;
import com.vision.smarthomeapi.bean.RSecurityGetPushStrategy;
import com.vision.smarthomeapi.bean.RSecurityGetQuestion;
import com.vision.smarthomeapi.bean.RSecurityGetThreeLinkage;
import com.vision.smarthomeapi.bean.RSecurityGetVisitors;
import com.vision.smarthomeapi.bean.RSecurityLogin;
import com.vision.smarthomeapi.bean.RSecurityQuestionnaireData;
import com.vision.smarthomeapi.bean.RSecuritySaveLinkage;
import com.vision.smarthomeapi.bean.RSecuritySetPushStrategy;
import com.vision.smarthomeapi.bean.RSecurityShortQuestionnaireData;
import com.vision.smarthomeapi.bean.RSecurityStateReport;
import com.vision.smarthomeapi.bean.RSecurityToken;
import com.vision.smarthomeapi.bean.RSecurityTokenF;
import com.vision.smarthomeapi.bean.RSecurityTypeInfo;
import com.vision.smarthomeapi.bean.RSecurityUserInfo;
import com.vision.smarthomeapi.bean.RSecurityWeatherInfo;
import com.vision.smarthomeapi.bean.RShareAcceptList;
import com.vision.smarthomeapi.bean.RShareCheckAccount;
import com.vision.smarthomeapi.bean.RShareList;
import com.vision.smarthomeapi.bean.RShareManageList;
import com.vision.smarthomeapi.bean.RWebSocketInfoF;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDeviceLogic;
import com.vision.smarthomeapi.dal.user.SecurityUserInfo;
import com.vision.smarthomeapi.net.NetworkManager;
import com.vision.smarthomeapi.net.NetworkMessage;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.FileIO;
import com.vision.smarthomeapi.util.JsonUtil;
import com.vision.smarthomeapi.util.MD5ToText;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PrefUtils;
import com.vision.smarthomeapi.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoqing on 2016/1/6.
 */
public class SecurityUserManage {

    public static String token = null;

    //5次MD5的token
    public static byte[] pwEncrypt = null;
    //平台临时秘钥
    public static byte[] tmpEncrypt = null;

    //5次MD5
    String pw = "";
    /**
     * 回调管理
     */
    private static NotificationManager notification;
    /**
     * UserManage对象
     */
    private static SecurityUserManage userManage;
    /**
     * 申请认证的key和secret
     */
    private String key = "aaa";
    private String secret = "aaa";
    /**
     * 本地用户列表
     */
    private List<SecurityUserInfo> securityUserInfoList;
    /**
     * 当前登录用户
     */
    private SecurityUserInfo user;
    /**
     * 当前天气
     */
    private RSecurityWeatherInfo rSecurityWeatherInfo;
//    /**
//     * 当前报警
//     */
//    public static Map<Integer, RAlarmInfo> alarmInfoMap = new HashMap<>();

    /**
     * 设备报警详情
     */
    public static Map<String,RSecurityDevice> deviceAlarm = new HashMap<>();

    /**
     * 当前所有地区列表
     */
    public RLetvGetAreaAll rLetvGetAreaAll;
    /**
     * 当前app升级http返回
     */
    private RSecurityCheckversion rSecurityCheckversion;

    /**
     * 用户管理类初始化方法
     *
     * @return 用户管理实例
     */
    public SecurityUserManage() {
        securityUserInfoList = new ArrayList<>();
        notification = NotificationManager.defaultManager();
        initUserData();
    }

    /**
     * 获取用户管理类单例对象方法
     *
     * @return UserManager单例对象
     */
    public static SecurityUserManage getShare() {
        if (userManage == null) {
            userManage = new SecurityUserManage();
        }
        return userManage;
    }

    public RSecurityCheckversion getrSecurityCheckversion() {
        return rSecurityCheckversion;
    }

    public void setrSecurityCheckversion(RSecurityCheckversion rSecurityCheckversion) {
        this.rSecurityCheckversion = rSecurityCheckversion;
    }

    public RSecurityWeatherInfo getrSecurityWeatherInfo() {
        return rSecurityWeatherInfo;
    }

    public void setrSecurityWeatherInfo(RSecurityWeatherInfo rSecurityWeatherInfo) {
        this.rSecurityWeatherInfo = rSecurityWeatherInfo;
    }

    /**
     * 获取当前登录用户方法。
     *
     * @return 用户信息
     */
    public SecurityUserInfo getUser() {
        return  user == null ? new SecurityUserInfo() : user;
    }

    /**
     * 初始化用户相关列表
     */
    public void initUserData() {
        //从数据库读取用户列表
        securityUserInfoList.clear();
        securityUserInfoList = findUserListFromDatabase();
        if (securityUserInfoList.isEmpty()) {
            return;
        }

        //判断当前用户是否登录，如果登录将此用户的信息加载到内存
        String userId = fileFindUserId();
        if (!userId.equals("")) {
            this.user = findUserFromDatabase(userId);
        }
        OutPutMessage.LogCatInfo("本地用户列表:", securityUserInfoList.toString());
    }

    /**
     * 获取token
     */
    public void getToken() {
        if (Controller.defaultController().APPSendCode()) {
            CSecurityToken cSecurityToken = new CSecurityToken(key, secret);
            NetworkMessage message = buildBean(cSecurityToken);
            message.setHttpGet(true);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getTokenResponse(RBean rBean, RSecurityToken rSecurityToken) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK && rSecurityToken != null) {
            OutPutMessage.LogCatInfo("HTTP", rSecurityToken.toString());
            this.token = rSecurityToken.getToken();

            //app升级
            if (Controller.APPUpade == false){
                appCheckversion();
            }

            if (!(SmartDeviceManage.getTypeMapForId().size() > 0)) {
                getTypeInfo();

            }

            if (rLetvGetAreaAll == null) {
                getAreaList();
            }

            String alarmTips = PrefUtils.getString("alarmTips");

            if (TextUtils.isEmpty(alarmTips)) {
                // 获取告警类型的提示
                getUser().getAlarmTips();
            }
            String userID = StringUtil.getUserID();
            if (!userID.equals("")) {

                //获取完token要搜索获取用户信息  主要是刷新token后
                SecurityUserManage.getShare().getUser().getUserInfo();
                SecurityUserManage.getShare().getUser().getLetvVipMe();
                Controller.defaultController().sendWebSocketInfo();

                OutPutMessage.LogCatInfo("获取短问卷",
                        "onStart" + (!StringUtil.getUserID().equals("")) + "___" + Controller.getShortQ);
                if (Controller.getShortQ) {
                    Controller.getShortQ = false;
                    getUser().getShortQuestion();
                }


                try {
                    pw = FileIO.getShareFielIo(Controller.getContext()).readFile("user_pw");

                    pw += token;

                    pwEncrypt = MD5ToText.MD5Encodebyte(pw);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            //将当前登录的用户id存入本地
            FileIO.getShareFielIo(Controller.getContext()).writeFile(rSecurityToken.getToken(), "token", Context.MODE_PRIVATE);
            FileIO.getShareFielIo(Controller.getContext()).writeFile(rSecurityToken.getRefresh_token(), "refresh_token", Context.MODE_PRIVATE);


        }
        notification.postNotification(Constant.NotificationType.SECURITY_GET_TOKEN, null, rSecurityToken, rBean);
    }

    /**
     * 刷新token
     */
    public void getRefreshToken(String token, String refresh_token) {
        if (Controller.defaultController().APPSendCode()) {
            String regId = PrefUtils.getString("regId");
            OutPutMessage.LogCatInfo("刷新token", "regId：" + regId);
            CSecurityRefreshToken cSecurityRefreshToken = new CSecurityRefreshToken(
                    refresh_token, key, token, MD5ToText.MD5Encode(key + refresh_token + secret), regId);
            NetworkMessage message = buildBean(cSecurityRefreshToken);
            message.setHttpGet(false);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 登陆绑定小区不再提示
     */
    public void userShowBindNo(){
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvUserBindShowNo cLetvUserBindShowNo = new CLetvUserBindShowNo(token);
            NetworkMessage message = buildBean(cLetvUserBindShowNo);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void userShowBindNoResponse(RBean rBean){
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK){

            OutPutMessage.LogCatInfo("登陆绑定小区不再提示","提交成功");
        }
    }
    /**
     * 用户登录方法
     *
     * @param phone  用户手机号
     * @param passwd 用户密码
     */
    public void userLogin(String phone, String passwd) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            pw = MD5ToText.MD5Encode(MD5ToText.MD5Encode(passwd));
            CSecurityLogin cSecurityLogin = new CSecurityLogin(token, phone, pw);
            NetworkMessage message = buildBean(cSecurityLogin);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void userLoginResponse(RBean rBean, RSecurityLogin rSecurityLogin) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK && rSecurityLogin != null) {
            OutPutMessage.LogCatInfo("密钥", "HTTP接收密钥：" + rSecurityLogin.getUser().getCtlpwd());
            byte[] mb = ByteUtil.hexStr2Byte(rSecurityLogin.getUser().getCtlpwd());
            SmartDeviceLogic.PASS_WORD = mb;
            OutPutMessage.LogCatInfo("密钥", "16进制密钥" + ByteUtil.byteArrayToHexString(SmartDeviceLogic.PASS_WORD));
            FileIO.getShareFielIo(Controller.getContext()).writeFile(rSecurityLogin.getUser().getCtlpwd(), "pw", Context.MODE_PRIVATE);

            pw = MD5ToText.MD5Encode(MD5ToText.MD5Encode(MD5ToText.MD5Encode(pw)));//做5次MD保存本地

            FileIO.getShareFielIo(Controller.getContext()).writeFile(pw, "user_pw", Context.MODE_PRIVATE);

            pw += token;

            pwEncrypt = MD5ToText.MD5Encodebyte(pw);

            // 用户id
            String userID = rSecurityLogin.getUser().getId() + "";
            // 初始账号
            String userIniAccount = rSecurityLogin.getUser().getInitAccount();
            // 手机号
            String userAccount = rSecurityLogin.getUser().getAccount();
            // 小区id
            String districtID = rSecurityLogin.getDistrict() != null ? rSecurityLogin.getDistrict().getId() + "" : "";
            // 小区名称
            String districtName = rSecurityLogin.getDistrict() != null ? rSecurityLogin.getDistrict().getDname() : "";
            // 小区紧急电话
            String districtPhone = rSecurityLogin.getDistrict() != null ? rSecurityLogin.getDistrict().getPhone() : "";


            SecurityUserInfo user = new SecurityUserInfo(userID, userIniAccount,
                    userAccount, districtID, districtName, districtPhone);

            addUserToDatabase(user);
            SecurityUserManage.getShare().getUser().getUserInfo();
            SecurityUserManage.getShare().getUser().getLetvVipMe();
        }

        OutPutMessage.LogCatInfo("HTTP", rSecurityLogin.toString());
        notification.postNotification(Constant.NotificationType.SECURITY_LOGIN, null, rSecurityLogin, rBean);
    }

    /**
     * 用户注册获取验证码
     *
     * @param phone 手机号
     */
    public void userRegisterGetCode(String phone) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CUserRegisterGetCode cUserRegisterGetCode = new CUserRegisterGetCode(
                    SecurityUserManage.token, phone);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cUserRegisterGetCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 用户注册获取验证码回复
     *
     * @param rBean 回复信息
     */
    public void userRegisterGetCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        notification.postNotification(
                Constant.NotificationType.SECURITY_REGISTER_GET_CODE, null, null, rBean);
    }

    /**
     * 用户注册验证验证码
     *
     * @param code  验证码
     * @param phone 手机号
     */
    public void userRegisterCheckCode(String code, String phone) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CUserRegisterCheckCode cUserRegisterCheckCode = new CUserRegisterCheckCode(
                    SecurityUserManage.token, code, phone);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cUserRegisterCheckCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 用户注册验证验证码回复
     *
     * @param rBean 回复信息
     */
    public void userRegisterCheckCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        notification.postNotification
                (Constant.NotificationType.SECURITY_REGISTER_CHECK_CODE, null, null, rBean);
    }

    /**
     * 用户注册
     *
     * @param code         验证码
     * @param name         用户名称
     * @param password     密码
     * @param phone        用户手机号码
     * @param securityCode 安全码
     */
    public void userRegister(String code, String name, String password,
                             String phone, String securityCode) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CUserRegister cUserRegister = new CUserRegister(SecurityUserManage.token, code, name,
                    MD5ToText.MD5Encode(password), phone, MD5ToText.MD5Encode(securityCode));
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cUserRegister);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 用户注册回复
     *
     * @param rBean 回复信息
     */
    public void userRegisterResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        notification.postNotification(Constant.NotificationType.SECURITY_REGISTER, null, null, rBean);
    }

    /**
     * 找回密码验证安全码
     *
     * @param code  安全码
     * @param phone 手机号
     */
    public void forgetPwCheckSecurityCode(String code, String phone) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            String codeEncrypt = MD5ToText.MD5Encode(MD5ToText.MD5Encode(code));
            CSecurityForgetPwCheckCode cSecurityForgetPwCheckCode = new CSecurityForgetPwCheckCode(
                    SecurityUserManage.token, codeEncrypt, phone);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityForgetPwCheckCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    /**
     * 找回密码验证安全码回复
     *
     * @param rBean 回复信息
     */
    public void forgetPwCheckSecurityCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_FORGET_PW_CHECK_CODE, null, null, rBean);
    }

    /**
     * 获取忘记密码的验证码
     */
    public void forgetPwCode(String phoneNumber) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityForgetPWCode cSecurityForgetPWCode = new CSecurityForgetPWCode(
                    SecurityUserManage.token, phoneNumber);
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityForgetPWCode);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void forgetPwCodeResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_FORGETPWCODE, null, null, rBean);
    }

    /**
     * 忘记密码
     */
    public void forgetPw(String phoneNumber, String code, String newPassword) {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CSecurityForgetPW cSecurityForgetPW = new CSecurityForgetPW(
                    SecurityUserManage.token, phoneNumber, code, MD5ToText.MD5Encode(newPassword));
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cSecurityForgetPW);
            message.setHttpGet(false);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void forgetPwResponse(RBean rBean) {
        if (rBean == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.SECURITY_FORGETPW, null, null, rBean);
    }

    /**
     * 检测升级
     */
    public void appCheckversion() {
        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            String version = Controller.getAPPVersion();

            OutPutMessage.LogCatInfo("APP版本",version);
            CSecurityCheckversion cSecurityCheckversion = new CSecurityCheckversion(token, version);
            NetworkMessage message = buildBean(cSecurityCheckversion);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void appCheckversionResponse(RBean rBean, RSecurityCheckversion rSecurityCheckversion) {
        if (rBean == null) {
            return;
        }
        Controller.APPUpade = true;
        OutPutMessage.LogCatInfo("APP版本","http返回");
        if (rBean.mode() == RBean.OK && rSecurityCheckversion != null) {
            this.setrSecurityCheckversion(rSecurityCheckversion);
            notification.postNotification(
                    Constant.NotificationType.SECURITY_CHECKAPPVERSION, null, rSecurityCheckversion, rBean);
        }
    }

    /**
     * 获取所有设备类型
     */
    public void getTypeInfo() {
        CSecurityTypeInfo cSecurityTypeInfo = new CSecurityTypeInfo(token);
        NetworkMessage message = buildBean(cSecurityTypeInfo);
        message.setHttpGet(true);
        NetworkManager.defaultNetworkManager().sendMessage(message);
    }

    public void getTypeInfoResponse(RBean rBean, RSecurityTypeInfo rSecurityTypeInfo) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK && rSecurityTypeInfo != null) {
            SmartDeviceManage.defaultManager().setTypeInfo(rSecurityTypeInfo);
        }
    }

    /**
     * 获取天气信息
     *
     * @param location
     */
    public void getWeatherInfo(String location) {
        if (Controller.defaultController().APPSendCode()) {
            CSecurityWeatherInfo cSecurityWeatherInfo = new CSecurityWeatherInfo(location);
            NetworkMessage message = buildBean(cSecurityWeatherInfo);
            message.setHttpGet(true);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getWeatherInfoResponse(RBean rBean, RSecurityWeatherInfo rSecurityWeatherInfo) {
        if (rBean == null) {
            return;
        }
        if (rBean.mode() == RBean.OK && rSecurityWeatherInfo != null) {
            this.rSecurityWeatherInfo = rSecurityWeatherInfo;
            notification.postNotification(
                    Constant.NotificationType.SECURITY_GETWEATHER, null, rSecurityWeatherInfo, rBean);
        }
    }


    /**
     * 获取区域列表
     */
    public void getAreaList(){

        if (Controller.defaultController().APPSendCode() && SecurityUserManage.token != null) {
            CLetvGetAreaAll cLetvGetAreaAll = new CLetvGetAreaAll();
            NetworkMessage message = SecurityUserManage.getShare().buildBean(cLetvGetAreaAll);
            message.setHttpGet(true);
            message.setToken(SecurityUserManage.token);
            NetworkManager.defaultNetworkManager().sendMessage(message);
        }
    }

    public void getAreaListResponse(RBean rBean, RLetvGetAreaAll rLetvGetAreaAll) {
        if (rBean == null) {
            return;
        }

        this.rLetvGetAreaAll = rLetvGetAreaAll;

        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.LETV_GET_AREAALL, null, rLetvGetAreaAll, rBean);
    }

    /**
     * http接收数据接口实现方法
     *
     * @param netMessage NetworkMessage对象
     */
    public void httpResponse(NetworkMessage netMessage) {
        if (netMessage == null) {
            return;
        }

        String url = netMessage.getUri();
        String content = new String(netMessage.getBinaryMsg());
        OutPutMessage.LogCatInfo("http接收", "url:" + url + "__content:" + content);
        RBean bean = JsonUtil.fromJsonString(content, RBean.class);

        if (url.equals(Constant.UrlOrigin.security_token)) {
            RSecurityTokenF rSecurityTokenF = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityTokenF.class);
            getTokenResponse(bean, rSecurityTokenF.getAuthResponse());

        } else if (url.equals(Constant.UrlOrigin.security_refresh_token)) {
            RSecurityTokenF rSecurityTokenF = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityTokenF.class);
            getTokenResponse(bean, rSecurityTokenF.getAuthResponse());

        } else if (url.equals(Constant.UrlOrigin.user_login)) {
            RSecurityLogin rSecurityLogin = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()).toString(), RSecurityLogin.class);
            userLoginResponse(bean, rSecurityLogin);

        } else if (url.equals(Constant.UrlOrigin.security_register_get_code)) {
            userRegisterGetCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_register_check_code)) {
            userRegisterCheckCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_register)) {
            userRegisterResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_getType)) {
            RSecurityTypeInfo rSecurityTypeInfo = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityTypeInfo.class);
            getTypeInfoResponse(bean, rSecurityTypeInfo);

        } else if (url.equals(Constant.UrlOrigin.security_deviceList)) {
            RSecurityDeviceList rSecurityDeviceList = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityDeviceList.class);
            user.getDeviceListResponse(bean, rSecurityDeviceList);

        } else if (url.equals(Constant.UrlOrigin.security_devicelist_ContainShare)) {
            RSecurityDeviceList rSecurityDeviceList = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityDeviceList.class);
            user.getDeviceListAndShareListResponse(bean, rSecurityDeviceList);

        } else if (url.equals(Constant.UrlOrigin.security_deviceListWithState)) {
            RSecurityDeviceList rSecurityDeviceList = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityDeviceList.class);
            user.getDeviceListWithStateResponse(bean, rSecurityDeviceList);

        } else if (url.equals(Constant.UrlOrigin.security_deviceByMac)) {
            RSecurityDeviceByMac rSecurityDeviceByMac = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityDeviceByMac.class);
            user.getDeviceByMacResponse(bean, rSecurityDeviceByMac);

        } else if (url.equals(Constant.UrlOrigin.security_logout)) {
            user.userLoginOffResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_userinfo)) {
            RSecurityUserInfo rSecurityUserInfo = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityUserInfo.class);
            user.getUserInfoResponse(bean, rSecurityUserInfo);

        } else if (url.equals(Constant.UrlOrigin.security_saveAccount)) {
            user.userSaveAccountResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.user_getwebsocketinfo)) { // 获取webSocket信息
            RWebSocketInfoF rWebSocketInfoF = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RWebSocketInfoF.class);
            user.getCometadrResponse(bean, rWebSocketInfoF);

        } else if (url.equals(Constant.UrlOrigin.security_updateName)) {
            user.updateAddNameResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_deviceType)) {
            user.updateTypeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_checkversion)) {
            RSecurityCheckversion rSecurityCheckversion = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityCheckversion.class);
            appCheckversionResponse(bean, rSecurityCheckversion);

        } else if (url.equals(Constant.UrlOrigin.security_updatePhone_code)) {
            user.updatePhoneCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updatePhone_validate)) {
            user.updatePhoneValidateResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updatePhone)) {
            user.updatePhoneResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updatePhone_validate_code)) {
            user.updatePhoneValidateCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updatePwd)) {
            user.updatePwdResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updateEmail)) {
            user.updateEmaildResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updateContact)) {
            user.updateContactResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_updateStandby)) {
            user.updateStandbyResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_get_long_question)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetQuestion rSecurityGetQuestion = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetQuestion.class);
            user.getLongQuestionResponse(bean, rSecurityGetQuestion);

        } else if (url.equals(Constant.UrlOrigin.security_check_question)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityCheckQuestion rSecurityCheckQuestion = JsonUtil.fromJsonString(
                    jsonString, RSecurityCheckQuestion.class);
            user.checkQuestionResponse(bean, rSecurityCheckQuestion);

        } else if (url.equals(Constant.UrlOrigin.security_questionnaire_data)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityQuestionnaireData rSecurityQuestionnaireData = JsonUtil.fromJsonString(
                    jsonString, RSecurityQuestionnaireData.class);
            user.getQuestionListResponse(bean, rSecurityQuestionnaireData);

        } else if (url.equals(Constant.UrlOrigin.security_questionnaire_submit)) {
            user.updateAnswerResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_get_short_questionnaire)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityShortQuestionnaireData rSecurityShortQuestionnaireData = JsonUtil.fromJsonString(
                    jsonString, RSecurityShortQuestionnaireData.class);
            user.getShortQuestionResponse(bean, rSecurityShortQuestionnaireData);

        } else if (url.equals(Constant.UrlOrigin.security_weather_info)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityWeatherInfo rSecurityWeatherInfo = JsonUtil.fromJsonString(
                    jsonString, RSecurityWeatherInfo.class);
            getWeatherInfoResponse(bean, rSecurityWeatherInfo);

        } else if (url.equals(Constant.UrlOrigin.security_check_code_by_phone)) {
            forgetPwCheckSecurityCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_forgetPW_code)) {
            forgetPwCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_forgetPW)) {
            forgetPwResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_alarmMonth)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityAlarmMonth rSecurityAlarmMonth = JsonUtil.fromJsonString(
                    jsonString, RSecurityAlarmMonth.class);
            user.alarmMonthResponse(bean, rSecurityAlarmMonth);

        } else if (url.equals(Constant.UrlOrigin.security_alarmList)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceAlarmList rSecurityDeviceAlarmList = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceAlarmList.class);
            user.alarmListResponse(bean, rSecurityDeviceAlarmList);

        } else if (url.equals(Constant.UrlOrigin.security_alarmInfo)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceAlarmInfoList rSecurityDeviceAlarmInfoList = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceAlarmInfoList.class);
            user.alarmInfoResponse(bean, rSecurityDeviceAlarmInfoList);

        } else if (url.equals(Constant.UrlOrigin.security_alarmInfo_byServiceId)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceAlarmInfoList rSecurityDeviceAlarmInfoList = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceAlarmInfoList.class);
            user.alarmInfo_byServiceIdResponse(bean, rSecurityDeviceAlarmInfoList);

        } else if (url.equals(Constant.UrlOrigin.security_check_device_version)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RCheckDeviceVersion rCheckDeviceVersion = JsonUtil.fromJsonString(
                    jsonString, RCheckDeviceVersion.class);
            OutPutMessage.LogCatInfo("升级版本HTTP返回", rCheckDeviceVersion.getVersion() + "");
            user.checkDeviceVersionResponse(bean, rCheckDeviceVersion.getVersion());

        } else if (url.equals(Constant.UrlOrigin.security_gettwolinkage)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceList rSecurityDeviceList = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceList.class);
            user.getTwoLinkageResponse(bean, rSecurityDeviceList);

        } else if (url.equals(Constant.UrlOrigin.security_savetwolinkage)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecuritySaveLinkage rSecuritySaveLinkage = JsonUtil.fromJsonString(
                    jsonString, RSecuritySaveLinkage.class);
            user.saveTwoLinkageResponse(bean, rSecuritySaveLinkage);

        } else if (url.equals(Constant.UrlOrigin.security_getthreelinkage)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetThreeLinkage rSecurityGetThreeLinkage = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetThreeLinkage.class);
            user.getThreeLinkageResponse(bean, rSecurityGetThreeLinkage);

        } else if (url.equals(Constant.UrlOrigin.security_savethreelinkage)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecuritySaveLinkage rSecuritySaveLinkage = JsonUtil.fromJsonString(
                    jsonString, RSecuritySaveLinkage.class);
            user.saveThreeLinkageResponse(bean, rSecuritySaveLinkage);

        } else if (url.equals(Constant.UrlOrigin.security_get_statereport)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityStateReport rSecurityStateReport = JsonUtil.fromJsonString(
                    jsonString, RSecurityStateReport.class);
            user.getStateReportResponse(bean, rSecurityStateReport);
        } else if (url.equals(Constant.UrlOrigin.security_getalarms)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetAlarms rSecurityGetAlarms = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetAlarms.class);
            user.getAlarmsResponse(bean, rSecurityGetAlarms);

        } else if (url.equals(Constant.UrlOrigin.security_device_getalarms)) {
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetAlarms rSecurityGetAlarms = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetAlarms.class);
            user.getDeviceAlarmsResponse(bean, rSecurityGetAlarms);

        }else if (url.equals(Constant.UrlOrigin.security_delete_alarms_byid)) {

            user.deleteDeviceAlarmsResponse(bean);

        }else if (url.equals(Constant.UrlOrigin.security_delete_cat_alarms_byid)) {

            user.deleteCatAlarmsResponse(bean);

        }
        else if (url.equals(Constant.UrlOrigin.security_update_nick)) { // 更新昵称
            user.updateNickResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_check_code)) { // 验证安全码
            user.checkSecurityCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_get_lock_key)) { // 获取门锁的授权解锁码
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetLockKey rSecurityGetLockKey = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetLockKey.class);
            user.getLockKeyResponse(bean, rSecurityGetLockKey);

        } else if (url.equals(Constant.UrlOrigin.security_get_defence_status)) { // 获取用户的布撤防状态
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetDefenceStatus rSecurityGetDefenceStatus = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetDefenceStatus.class);
            user.getDefenceStatusResponse(bean, rSecurityGetDefenceStatus);

        } else if (url.equals(Constant.UrlOrigin.security_get_cateye_info)) { // 获取猫眼的告警详情
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetCatEyeInfo rSecurityGetCatEyeInfo = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetCatEyeInfo.class);
            user.getCatEyeInfoResponse(bean, rSecurityGetCatEyeInfo);

        } else if (url.equals(Constant.UrlOrigin.security_set_push_strategy)) { // 更改门铃的红外推送策略
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecuritySetPushStrategy rSecuritySetPushStrategy = JsonUtil.fromJsonString(
                    jsonString, RSecuritySetPushStrategy.class);
            user.setPushStrategyResponse(bean, rSecuritySetPushStrategy);

        } else if (url.equals(Constant.UrlOrigin.security_get_visitors)) { // 获取用户访客记录
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetVisitors rSecurityGetVisitors = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetVisitors.class);
            user.getVisitorsResponse(bean, rSecurityGetVisitors);

        }else if (url.equals(Constant.UrlOrigin.security_get_visitors_byid)) { // 获取设备访客记录
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetVisitors rSecurityGetVisitors = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetVisitors.class);
            user.getVisitorsResponse(bean, rSecurityGetVisitors);

        } else if (url.equals(Constant.UrlOrigin.security_get_alarm_info)) { // 获取用户告警信息
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetAlarmInfo rSecurityGetAlarmInfo = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetAlarmInfo.class);
            user.getAlarmInfoResponse(bean, rSecurityGetAlarmInfo);

        } else if (url.equals(Constant.UrlOrigin.security_get_alarm_tips)) { // 获取告警类型的提示
            String jsonString = JsonUtil.toJsonString(bean.getData());
            getUser().getAlarmTipsResponse(bean, jsonString);

        } else if (url.equals(Constant.UrlOrigin.security_device_bind)) { // 设备绑定
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceBind rSecurityDeviceBind = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceBind.class);
            user.deviceBindResponse(bean, rSecurityDeviceBind);

        } else if (url.equals(Constant.UrlOrigin.security_device_unbind)) { // 设备解绑
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityDeviceUnbind rSecurityDeviceUnbind = JsonUtil.fromJsonString(
                    jsonString, RSecurityDeviceUnbind.class);
            user.deviceUnbindResponse(bean, rSecurityDeviceUnbind);

        } else if (url.equals(Constant.UrlOrigin.security_set_code)) { // 设置安全码
            user.setSecurityCodeResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_device_can_modify)) { // 获取通用盒子可修改的设备类型
            RSecurityDeviceCanModify rSecurityDeviceCanModify = JsonUtil.fromJsonString(
                    JsonUtil.toJsonString(bean.getData()), RSecurityDeviceCanModify.class);
            user.getDeviceCanModifyResponse(bean, rSecurityDeviceCanModify);

        } else if (url.equals(Constant.UrlOrigin.security_updateDefense)) { // 更新布防撤防信息
            user.updateDefenseResponse(bean);

        } else if (url.equals(Constant.UrlOrigin.security_alarm_status)) { // 获取用户告警状态
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityAlarmStatus rSecurityAlarmStatus = JsonUtil.fromJsonString(
                    jsonString, RSecurityAlarmStatus.class);
            user.getAlarmStatusResponse(bean, rSecurityAlarmStatus);

        } else if (url.equals(Constant.UrlOrigin.security_get_push_strategy)) { // 获取门铃的红外推送策略
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityGetPushStrategy rSecurityGetPushStrategy = JsonUtil.fromJsonString(
                    jsonString, RSecurityGetPushStrategy.class);
            user.getPushStrategyResponse(bean, rSecurityGetPushStrategy);
        }else if (url.equals(Constant.UrlOrigin.letv_get_news)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvNewsList rLetvNewsList = JsonUtil.fromJsonString(
                    jsonString, RLetvNewsList.class);
            user.getNewsResponse(bean, rLetvNewsList);
        }else if (url.equals(Constant.UrlOrigin.letv_get_notes)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvNotesList rLetvNotesList = JsonUtil.fromJsonString(
                    jsonString, RLetvNotesList.class);
            user.getNotesResponse(bean, rLetvNotesList);
        }else if (url.equals(Constant.UrlOrigin.letv_update_fingerprint)){
            user.getUpdateFingerprintResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.letv_get_areaAll)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvGetAreaAll rLetvGetAreaAll = JsonUtil.fromJsonString(
                    jsonString, RLetvGetAreaAll.class);
            getAreaListResponse(bean,rLetvGetAreaAll);
        }else if (url.equals(Constant.UrlOrigin.letv_get_orderlist)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvOrderList rLetvOrderList = JsonUtil.fromJsonString(
                    jsonString, RLetvOrderList.class);
            user.getOrderListResponse(bean,rLetvOrderList);
        }else if (url.equals(Constant.UrlOrigin.letv_syn_jdorder)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvSynJDOrder rLetvSynJDOrder = JsonUtil.fromJsonString(
                    jsonString, RLetvSynJDOrder.class);
            user.synJDOrderResponse(bean,rLetvSynJDOrder);
        }else if (url.equals(Constant.UrlOrigin.letv_check_reciver_lock)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvSynJDOrder rLetvSynJDOrder = JsonUtil.fromJsonString(
                    jsonString, RLetvSynJDOrder.class);
            user.checkReciverLockResponse(bean,rLetvSynJDOrder);
        }else if (url.equals(Constant.UrlOrigin.letv_get_orderDetail)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvGetOrderDetail rLetvGetOrderDetail = JsonUtil.fromJsonString(
                    jsonString, RLetvGetOrderDetail.class);
            user.getOrderDetailResponse(bean,rLetvGetOrderDetail);
        }else if (url.equals(Constant.UrlOrigin.letv_get_communication_list)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvCommunicationList rLetvCommunicationList = JsonUtil.fromJsonString(
                    jsonString, RLetvCommunicationList.class);
            user.getCommunicationListResponse(bean,rLetvCommunicationList);
        }else if (url.equals(Constant.UrlOrigin.letv_get_district_apply)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvGetDistrictApply rLetvGetDistrictApply = JsonUtil.fromJsonString(
                    jsonString, RLetvGetDistrictApply.class);
            user.getDistrictApplyResponse(bean,rLetvGetDistrictApply);
        }else if (url.equals(Constant.UrlOrigin.letv_get_all_district)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvGetAllDistrict rLetvGetAllDistrict = JsonUtil.fromJsonString(
                    jsonString, RLetvGetAllDistrict.class);
            user.getAllDistrictResponse(bean,rLetvGetAllDistrict);
        }else if (url.equals(Constant.UrlOrigin.letv_add_district_apply)){
            user.applyDistrictResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.letv_vip_me)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipMe rLetvVipMe = JsonUtil.fromJsonString(
                    jsonString, RLetvVipMe.class);
            user.getLetvVipMeResponse(bean,rLetvVipMe);
        }else if (url.equals(Constant.UrlOrigin.letv_vip_mem)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipMem rLetvVipMem = JsonUtil.fromJsonString(
                    jsonString, RLetvVipMem.class);
            user.getLetvVipMemResponse(bean,rLetvVipMem);
        }else if (url.equals(Constant.UrlOrigin.letv_vip_bm)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipBm rLetvVipBm = JsonUtil.fromJsonString(
                    jsonString, RLetvVipBm.class);
            user.getLetvVipBmResponse(bean,rLetvVipBm);

        }else if (url.equals(Constant.UrlOrigin.letv_vip_go)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipGo rLetvVipGo = JsonUtil.fromJsonString(
                    jsonString, RLetvVipGo.class);
            user.getLetvVipGoResponse(bean,rLetvVipGo);

        }else if (url.equals(Constant.UrlOrigin.letv_vip_final)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipFinal rLetvVipFinal = JsonUtil.fromJsonString(
                    jsonString, RLetvVipFinal.class);
            user.setLetvVipFinalResponse(bean,rLetvVipFinal);

        }else if (url.equals(Constant.UrlOrigin.letv_vip_his)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvVipHis rLetvVipHis = JsonUtil.fromJsonString(
                    jsonString, RLetvVipHis.class);
            user.getLetvVipHisResponse(bean,rLetvVipHis);

        }else if (url.equals(Constant.UrlOrigin.letv_user_showbindno)){
            userShowBindNoResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.letv_check_orderAdd)){

            String jsonString = JsonUtil.toJsonString(bean.getData());
            RLetvCheckAdd rLetvCheckAdd = JsonUtil.fromJsonString(
                    jsonString, RLetvCheckAdd.class);
            user.checkOrderAddResponse(bean,rLetvCheckAdd);


        }else if (url.equals(Constant.UrlOrigin.letv_sure_upgrade)){
            user.sureUpgradeResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.security_getCurrentAlarms)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RGetCurrentAlarms rGetCurrentAlarms = JsonUtil.fromJsonString(
                    jsonString, RGetCurrentAlarms.class);
            user.getCurrentAlarmsResponse(bean,rGetCurrentAlarms);
        }else if (url.equals(Constant.UrlOrigin.security_getUnLinkBindDevices)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RSecurityBindDeviceList rSecurityDeviceList = JsonUtil.fromJsonString(
                    jsonString, RSecurityBindDeviceList.class);
            user.getUnLinkbindDevicesResponse(bean,rSecurityDeviceList);
        }else if (url.equals(Constant.UrlOrigin.security_bindlinkdevice)){
            user.bindLinkDeviceResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.security_deletebindlinkdevice)){
            user.deletebindLinkDeviceResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.share_revicer_list)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RShareList rShareList = JsonUtil.fromJsonString(
                    jsonString, RShareList.class);
            user.shareRevicerListResponse(bean,rShareList);
        }else if (url.equals(Constant.UrlOrigin.share_accept_list)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RShareAcceptList rShareAcceptList = JsonUtil.fromJsonString(
                    jsonString, RShareAcceptList.class);
            user.shareAcceptListResponse(bean,rShareAcceptList);
        }else if (url.equals(Constant.UrlOrigin.share_check_account)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RShareCheckAccount rShareCheckAccount = JsonUtil.fromJsonString(
                    jsonString, RShareCheckAccount.class);
            user.shareCheckAccountResponse(bean,rShareCheckAccount);
        }else if (url.equals(Constant.UrlOrigin.share_add_share)){
            user.shareAddShareResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.share_add_share_sure)){
            user.shareAddSureShareResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.share_cancel_share)){
            user.shareCancelShareResponse(bean);
        }else if (url.equals(Constant.UrlOrigin.share_sure_share)){
            user.sureShareResponse(bean);

        }else if (url.equals(Constant.UrlOrigin.cancel_share_revicer)){
            user.refusedShareResponse(bean);

        }else if (url.equals(Constant.UrlOrigin.revicer_share_info)){
            String jsonString = JsonUtil.toJsonString(bean.getData());
            RShareManageList rShareManageList = JsonUtil.fromJsonString(
                    jsonString, RShareManageList.class);

            user.shareManageListResponse(bean,rShareManageList);

        }else if (url.equals(Constant.UrlOrigin.security_update_share_nick)){
            user.updateShareNickResponse(bean);
        }
    }

    /**
     * 请求协议通过该方法封装成NetworkMessage并返回。
     *
     * @param bean
     * @return NetworkMessage对象
     */
    public NetworkMessage buildBean(Bean bean) {
        OutPutMessage.LogCatInfo("发送数据到远程", bean.toString());
        NetworkMessage networkMessage = new NetworkMessage(true, bean.toString().getBytes(),
                null, Constant.NetWork.HTTP, "");
        networkMessage.setHttpUrl(Controller.SERVER_URL);
        networkMessage.setUri(bean.getUrlOrigin());
        networkMessage.setParams(bean.setRequestParams());
        return networkMessage;
    }

    /**
     * webSocket连接状态管理
     *
     * @param state 连接状态
     */
    public void webSocketConnectState(int state) {
        OutPutMessage.LogCatInfo("消息推送:", state + "");
        if (Controller.defaultController().applicationState == Controller.ApplicationState.ApplicationActivate) {
            switch (state) {
                case Controller.OK:
                    user.getDeviceListAndShareList();//获取设备列表
                    break;

                case Controller.REPEAT_LOGIN://强制退出
                    if (!StringUtil.getUserID().equals("")) {
                        cancelLogin();
                        startActivity();
                    }
                    OutPutMessage.showToast("用户已在别处登录，请确认是否为本人操作");
                    break;

                case Controller.CLOSE:
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 用户强制退出,跳转至主界面
     */
    public void startActivity() {
        notification.postNotification(Constant.NotificationType.ACTIVITY_LOGOUT, null, null);
    }

    /**
     * 添加用户至本地数据库。
     *
     * @param user 用户类
     */
    private void addUserToDatabase(SecurityUserInfo user) {
        String userId = user.getUserID();
        if (findUserFromDatabase(userId) == null) {
            user.save();
            user.saveThrows();
        } else {
            updateUserFromDatabase(user);
        }
        this.user = user;
        //将当前登录的用户id存入本地
        FileIO.getShareFielIo(Controller.getContext()).writeFile(
                user.getUserID(), "user_id", Context.MODE_PRIVATE);
    }

    /**
     * 更新单个用户信息从数据库
     *
     * @param user 用户信息
     */
    public void updateUserFromDatabase(SecurityUserInfo user) {
        ContentValues contentValues = new ContentValues();
        if (!TextUtils.isEmpty(user.getUserName())) {
            contentValues.put("userName", user.getUserName());
        }
        if (!TextUtils.isEmpty(user.getUserAccount())) {
            contentValues.put("userAccount", user.getUserAccount());
        }
        if (!TextUtils.isEmpty(user.getUserIniAccount())) {
            contentValues.put("userIniAccount", user.getUserIniAccount());
        }
        if (!TextUtils.isEmpty(user.getDistrictID())) {
            contentValues.put("districtID", user.getDistrictID());
        }
        if (!TextUtils.isEmpty(user.getDistrictName())) {
            contentValues.put("districtName", user.getDistrictName());
        }
        if (!TextUtils.isEmpty(user.getDistrictPhone())) {
            contentValues.put("districtPhone", user.getDistrictPhone());
        }

        DataSupport.updateAll(SecurityUserInfo.class, contentValues, "userID = ?", user.getUserID());
    }

    /**
     * 查找用户信息列表从数据库
     *
     * @return 用户信息集合
     */
    private List<SecurityUserInfo> findUserListFromDatabase() {
        return DataSupport.findAll(SecurityUserInfo.class);
    }

    /**
     * 查找用户最后一次登录UserId
     *
     * @return 用户id
     */
    public String fileFindUserId() {
        String userId = "";
        try {
            userId = FileIO.getShareFielIo(Controller.getContext()).readFile("user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    /**
     * 查找单个用户信息从数据库
     *
     * @param userId 用户id
     * @return 用户信息
     */
    private SecurityUserInfo findUserFromDatabase(String userId) {
        List<SecurityUserInfo> userList = DataSupport.where("userID = ?", userId).find(SecurityUserInfo.class);
        if (!userList.isEmpty()) {
            return userList.get(0);
        }
        return null;
    }

    /**
     * 功能描述：取消登录
     */
    public void cancelLogin() {
        NetworkManager.defaultNetworkManager().closeWebSocket();
        FileIO.getShareFielIo(Controller.getContext()).delFile("user_id");
        FileIO.getShareFielIo(Controller.getContext()).delFile("token");
        FileIO.getShareFielIo(Controller.getContext()).delFile("pw");
        FileIO.getShareFielIo(Controller.getContext()).delFile("refresh_token");

        user = null;
        user = new SecurityUserInfo();
    }
}
