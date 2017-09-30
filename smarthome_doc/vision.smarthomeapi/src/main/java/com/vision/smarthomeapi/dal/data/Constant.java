package com.vision.smarthomeapi.dal.data;

import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Zhanglong on 2015/1/27.
 */
public class Constant {

    /**
     * ZigBee路径
     */
    public static final String ZIGBEE_PATH = "/dev/zigbee";

    public static class NotificationType {

        public static final String LAN_FIND_DEVICE = "LAN_FIND_DEVICE";
        /**
         * 设备列表改变（增加或删除设备）
         */
        public static final String DEVICE_LIST_CHANGE = "DEVICE_LIST_CHANGE";

        /**
         * 获取可查看设备状态的设备列表
         */
        public static final String DEVICE_LIST_WITHSTATE = "DEVICE_LIST_WITHSTATE";

        /**
         * 设备类型修改
         */
        public static final String DEVICE_TYPE_CHANGE = "DEVICE_TYPE_CHANGE";

        public static final String DEVICE_FIRST_LOGIN = "DEVICE_FIRST_LOGIN";

        public static final String ALARM_INFO = "ALARM_INFO_CALLBACK";
        /**
         * 联动列表数据刷新
         */
        public static final String LINKAGE_LIST_CHANGE = "LINKAGE_LIST_CHANGE_CALLBACK";
        /**
         * 联动列表名称刷新
         */
        public static final String LINKAGE_LIST_NAME = "LINKAGE_LIST_NAME_CALLBACK";
        /**
         * WebSocket 连接状态
         */
        public static final String WEB_SOCKET_CONNECT_STATE = "WEB_SOCKET_CONNECT_STATE_CALLBACK";
        /**
         * 登录
         */
        public static final String ACTIVITY_LOGIN = "LOGIN_CAllBACk";
        /**
         * 登出
         */
        public static final String ACTIVITY_LOGOUT = "LOGOUT_CALLBACK";
        /**
         * 注册第一步
         */
        public static final String ACTIVITY_REGISTER1 = "REGISTER1_CAllBACk";
        /**
         * 注册第一步
         */
        public static final String ACTIVITY_REGISTER2 = "REGISTER2_CAllBACk";
        /**
         * 注册第一步
         */
        public static final String ACTIVITY_REGISTER3 = "REGISTER3_CAllBACk";
        /**
         * 用户获取绑定手机信息
         */
        public static final String ACTIVITY_GETUSERPHONE = "GETUSERPHONE_CAllBACk";
        /**
         * 忘记密码第一步
         */
        public static final String ACTIVITY_FORGET_PASSWORD1 = "FORGET_PASSWORD1_CALLBACK";
        /**
         * 忘记密码第二步
         */
        public static final String ACTIVITY_FORGET_PASSWORD2 = "FORGET_PASSWORD2_CALLBACK";
        /**
         * 忘记密码第三步
         */
        public static final String ACTIVITY_FORGET_PASSWORD3 = "FORGET_PASSWORD3_CALLBACK";
        /**
         * 用户修改密码第一步
         */
        public static final String ACTIVITY_CHANGEPASS1 = "CHANGEPASS1_CALLBACK";
        /**
         * 用户修改密码第二步
         */
        public static final String ACTIVITY_CHANGEPASS2 = "CHANGEPASS2_CALLBACK";
        /**
         * 修改绑定手机号第一步
         */
        public static final String ACTIVITY_CHANGEPHONE1 = "CHANGEPHONE1_CALLBACK";
        /**
         * 修改绑定手机号第二步
         */
        public static final String ACTIVITY_CHANGEPHONE2 = "CHANGEPHONE2_CALLBACK";
        /**
         * 修改绑定手机号第三步
         */
        public static final String ACTIVITY_CHANGEPHONE3 = "CHANGEPHONE3_CALLBACK";
        /**
         * 修改绑定手机号第四步
         */
        public static final String ACTIVITY_CHANGEPHONE4 = "CHANGEPHONE4_CALLBACK";
        /**
         * 用户绑定设备
         */
        public static final String ACTIVITY_DEVICE_BIND = "DEVICE_BIND_CALLBACK";
        /**
         * 用户解绑设备
         */
        public static final String ACTIVITY_DEVICE_UNBIND = "DEVICE_UNBIND_CALLBACK";
        /**
         * 推送绑定设备
         */
        public static final String PUSH_DEVICE_BIND = "PUSH_DEVICE_BIND_CALLBACK";
        /**
         * 推送解绑设备
         */
        public static final String PUSH_DEVICE_UNBIND = "PUSH_DEVICE_UNBIND_CALLBACK";
        /**
         * 远程操作状态
         */
        public static final String ACTIVITY_REMOTE_OPERATION = "REMOTE_OPERATION_CALLBACK";
        /**
         * 密码
         */
        public static final String ACTIVITY_PASSWORD = "PASSWORD_CAllBACk";
        /**
         * 修改设备密码
         */
        public static final String ACTIVITY_DEVICES_PASSWORD = "DEVICES_PASSWORD_CAllBACk";
        /**
         * 恢复出厂设置
         */
        public static final String ACTIVITY_RESTORE_DEFAULT = "RESTORE_DEFAULT_CAllBACk";

        public static final String ACTIVITY_UPLOAD_USER_GROUP = "UPLOAD_USER_GROUP_CAllBACk";

        public static final String ACTIVITY_USER_GROUP = "USER_GROUP_CAllBACk";
        /**
         * 设备状态同步
         */
        public static final String ACTIVITY_ELECTRIC_QUANTITY = "ELECTRIC_QUANTITY_CAllBACk";
        /**
         * 从Http下载完成回调
         */
        public static final String ACTIVITY_UPDATE_DATA = "UPDATE_DATA_CAllBACk";
        /**
         * 升级一块数据
         */
        public static final String ACTIVITY_UPDATE_DATA_BLOCk = "UPDATE_DATA_BLOCK_CAllBACk";
        /**
         * 升级成功回调
         */
        public static final String ACTIVITY_UPDATE_DATA_OK = "UPDATE_DATA_OK_CAllBACk";
        /**
         * 升级请求错误
         */
        public static final String ACTIVITY_UPDATE_ERROR = "UPDATE_DATA_ERROR_CAllBACk";
        /**
         * 请求升级
         */
        public static final String ACTIVITY_UPDATE_REQUEST = "UPDATE_REQUEST_CAllBACK";
        /**
         * 设备登录成功，回调
         */
        public static final String MAIN_DEVICE_LOGIN = "MAIN_DEVICE_LOGIN_CALLBACK";
        /**
         * 获取验证码
         */
        public static final String ACTIVITY_GET_CODE = "GET_CODE";
        /**
         * 验证验证码
         */
        public static final String ACTIVITY_CHECK_CODE = "CHECK_CODE";
        /**
         * 修改昵称
         */
        public static final String ACTIVITY_ALTER_NICK = "ALTER_NICK";
        /**
         * 用户头像上传
         */
        public static final String ACTIVITY_UPLOAD_PORTRAIT = "UPLOAD_PORTRAIT";
        /**
         * 用户头像下载
         */
        public static final String ACTIVITY_DOWNLOAD_PORTRAIT = "DOWNLOAD_PORTRAIT";
        /**
         * 添加家庭成员
         */
        public static final String ACTIVITY_FAMILY_ADD = "FAMILY_ADD";
        /**
         * 删除家庭成员
         */
        public static final String ACTIVITY_FAMILY_DELETE = "FAMILY_DELETE";
        /**
         * 重新发送激活邮件
         */
        public static final String ACTIVITY_SEND_EMAIL = "SEND_EMAIL";
        /**
         * 获取用户信息
         */
        public static final String ACTIVITY_GET_USERINFO = "GET_USERINFO";
        /**
         * 配置AP成功
         */
        public static final String ACTIVITY_AP_OK = "AP_OK_CALLBACK";
        /**
         * 配置AP过程
         */
        public static final String ACTIVITY_AP_ING = "AP_ING_CALLBACK";
        /**
         * 重新发送验证码邮件
         */
        public static final String ACTIVITY_SEND_CODE = "SEND_CODE";
        /**
         * 获取设备列表
         */
        public static final String ACTIVITY_GET_DEVICES = "GET_DEVICES";
        /**
         * 获取用户所属家庭
         */
        public static final String ACTIVITY_GET_FAMILIES = "GET_FAMILIES";
        /**
         * 用户主动退出家庭
         */
        public static final String ACTIVITY_FAMILY_QUIT = "FAMILY_QUIT";
        /**
         * 添加家庭成员，被动方确认是否加入
         */
        public static final String ACTIVITY_FAMILY_ADD_CONFIRM = "FAMILY_ADD_CONFIRM";
        /**
         * 解散家庭
         */
        public static final String ACTIVITY_FAMILY_DEMISSION = "FAMILY_DEMISSION";
        /**
         * 接收平台推送到手机的消息
         */
        public static final String WEBSOCKET_SEND_MESSAGEINFO = "SEND_MESSAGEINFO";
        /**
         * 子设备列表集合方式添加
         */
        public static final String CHILD_DEVICES_LIST = "CHILD_DEVICES_LIST_CAllBACk";
        /**
         * 子设备请求信息
         */
        public static final String CHILD_DEVICES_INFO = "CHILD_DEVICES_INFO_CAllBACk";
        /**
         * 设备名称操作
         */
        public static final String DEVICES_NAME_INFO = "DEVICES_NAME_CAllBACk";
        /**
         * 广域网列表
         */
        public static final String WAN_DEVICE_LIST = "WAN_DEVICE_LIST_CAllBACk";
        /**
         * 错误返回
         */
        public static final String DEVICES_ERROR = "DEVICES_ERROR_CAllBACk";
        /**
         * 设备信息查询
         */
        public static final String DEVICES_INFO_SYNCHRONOUS = "DEVICES_INFO_SYNCHRONOUS_CAllBACk";

        /**
         * 设备信息查询
         */
        public static final String DEVICES_PARAMETER_SYNCHRONOUS = "DEVICES_PARAMETER_SYNCHRONOUS_CAllBACk";
        /**
         * 设备告警
         */
        public static final String DEVICE_MESSAGE_ALARM_INFO = "DEVICE_MESSAGE_ALARM_CAllBACk";
        /**
         * 设备故障信息同步
         */
        public static final String DEVICE_FAULT_SYNC = "DEVICE_FAULT_SYNC";
        /**
         * 修改设备名称
         */
        public static final String DEVICE_MESSAGE_NAME_INFO = "DEVICE_MESSAGE_NAME_CAllBACk";
        /**
         * 子设备添加成功
         */
        public static final String ADD_DEVICE_GATEWAY = "ADD_DEVICE_GATEWAY_CAllBACk";
        /**
         * 设备登录超时
         */
        public static final String DEVICE_LOGIN_TIME_OUT = "DEVICE_LOGIN_TIME_OUT_CAllBACk";
        /**
         * 用户强制登出
         */
        public static final String CANCEL_LOGIN_ACTIVITY = "CANCEL_LOGIN_ACTIVITY_CAllBACk";
        /**
         * 设备掉线
         */
        public static final String DEVICE_NOT_LINE = "DEVICE_NOT_LINE_CAllBACk";
        /**
         * 子设备删除成功
         */
        public static final String DEL_DEVICE_GATEWAY = "DEL_DEVICE_GATEWAY_CAllBACk";
        /**
         * 定时同步
         */
        public static final String DEVICE_TIMING_SYNC = "DEVICE_TIMING_SYNC_CAllBACk";
        /**
         * Http请求完成回调
         */
        public static final String HTTP_DATA_OK = "HTTP_DATA_OK_CAllBACk";
        /**
         * 获取用户日志
         */
        public static final String GET_USER_LOGS = "GET_USER_LOGS_CALLBACK";
        /**
         * 首页显示用户日志
         */
        public static final String SET_USER_LOG = "SET_USER_LOG_CALLBACK";
        /**
         * 当前App状态改变回调
         */
        public static final String APP_STATE_CHANGE = "APP_STATE_CHANGE_CALLBACK";
        /**
         * 网络状态改变
         */
        public static final String NET_STATE_CHANGE = "NET_STATE_CHANGE";
        /**
         * 当前App状态改变回调
         */
        public static final String WIFI_NET_WORK_CONNECTION = "WIFI_NET_WORK_CONNECTION_CALLBACK";
        /**
         * 重启
         */
        public static final String DEVICE_RESTART_DEVICE = "DEVICE_RESTART_DEVICE_CALLBACK";
        /**
         * 刷新主界面
         */
        public static final String CHANGE_MAIN_UI = "CHANGE_MAIN_UI_CALLBACK";
        /**
         * 安防 获取token
         */
        public static final String SECURITY_GET_TOKEN = "TOKEN_CALLBACK";
        /**
         * 安防 用户登录
         */
        public static final String SECURITY_LOGIN = "LOGIN_CALLBACK";
        /**
         * 安防 用户注册获取验证码
         */
        public static final String SECURITY_REGISTER_GET_CODE = "SECURITY_REGISTER_GET_CODE";
        /**
         * 安防 用户注册验证验证码
         */
        public static final String SECURITY_REGISTER_CHECK_CODE = "SECURITY_REGISTER_CHECK_CODE";
        /**
         * 安防 用户注册
         */
        public static final String SECURITY_REGISTER = "SECURITY_REGISTER";
        /**
         * 安防 检测app是否需要升级
         */
        public static final String SECURITY_CHECKAPPVERSION = "SECURITY_CHECKAPPVERSION";
        /**
         * 安防 保存个性账号
         */
        public static final String SECURITY_SAVE_ACCOUNT = "SECURITY_SAVE_ACCOUNT";
        /**
         * 更新地址
         */
        public static final String SECURITY_UPDATE_ADDNAME = "SECURITY_UPDATE_ADDNAME";
        /**
         * 更新类型
         */
        public static final String SECURITY_UPDATE_TYPE = "SECURITY_UPDATE_TYPE";
        /**
         * 更新布防撤防状态
         */
        public static final String SECURITY_UPDATEDEFENSE = "SECURITY_UPDATEDEFENSE";
        /**
         * 找回密码验证安全码
         */
        public static final String SECURITY_FORGET_PW_CHECK_CODE = "SECURITY_FORGET_PW_CHECK_CODE";
        /**
         * 获取忘记密码验证码
         */
        public static final String SECURITY_FORGETPWCODE = "SECURITY_FORGETPWCODE";
        /**
         * 忘记密码
         */
        public static final String SECURITY_FORGETPW = "SECURITY_FORGETPW";
        /**
         * 获取修改手机号验证码
         */
        public static final String SECURITY_UPDATEDEPHONECODE = "SECURITY_UPDATEDEPHONECODE";
        /**
         * 获取修改手机号验证码
         */
        public static final String SECURITY_UPDATEDEPHONEValidateCODE = "SECURITY_UPDATEDEPHONEValidateCODE";
        /**
         * 验证原始手机号
         */
        public static final String SECURITY_UPDATEPHONEVALIDATE = "SECURITY_UPDATEPHONEVALIDATE";
        /**
         * 修改手机号码
         */
        public static final String SECURITY_UPDATEDEPHONE = "SECURITY_UPDATEDEPHONE";
        /**
         * 修改密码
         */
        public static final String SECURITY_UPDATEDEPWD = "SECURITY_UPDATEDEPWD";
        /**
         * 修改邮箱
         */
        public static final String SECURITY_UPDATEEMAIL = "SECURITY_UPDATEEMAIL";
        /**
         * 修改紧急联系人
         */
        public static final String SECURITY_UPDATECONTACT = "SECURITY_UPDATECONTACT";
        /**
         * 修改备用联系人
         */
        public static final String SECURITY_UPDATESTANDBY = "SECURITY_UPDATESTANDBY";
        /**
         * 获取问卷调研
         */
        public static final String SECURITY_GETQUESTION = "SECURITY_GETQUESTION";
        /**
         * 用户是否做过问卷
         */
        public static final String SECURITY_CHECKQUESTION = "SECURITY_CHECKQUESTION";
        /**
         * 获取长问卷连接
         */
        public static final String SECURITY_GETLONGQUESTION = "SECURITY_GETLONGQUESTION";
        /**
         * 获取短问卷调查
         */
        public static final String SECURITY_GETSHORTQUESTION = "SECURITY_GETSHORTQUESTION";
        /**
         * 提交问卷调研
         */
        public static final String SECURITY_SUBMITQUESTION = "SECURITY_SUBMITQUESTION";
        /**
         * 获取报警月份
         */
        public static final String SECURITY_ALARMMONTH = "SECURITY_ALARMMONTH";
        /**
         * 获取报警记录
         */
        public static final String SECURITY_ALARMLIST = "SECURITY_ALARMLIST";
        /**
         * 获取天气资讯
         */
        public static final String SECURITY_GETWEATHER = "SECURITY_WEATHER";
        /**
         * Http访问异常
         */
        public static final String SECURITY_HTTP_ERROR = "SECURITY_HTTP_ERROR";
        /**
         * 局域网搜索到
         */
        public static final String LAN_OK = "ADD_WIFI_OK";
        /**
         * 根据所选择的探测设备获取匹配的动作设备
         */
        public static final String SECURITY_GETTWOLINKAGE = "SECURITY_GETTWOLINKAGE";
        /**
         * 保存二级联动
         */
        public static final String SECURITY_SAVE_TWO_LINKAGE = "SECURITY_SAVE_TWO_LINKAGE";
        /**
         * 保存三级联动
         */
        public static final String SECURITY_SAVE_THREE_LINKAGE = "SECURITY_SAVE_THREE_LINKAGE";

        public static final String SECURITY_GETTHREELINKAGE = "SECURITY_GETTHREELINKAGE";
        /**
         * 获取状态
         */
        public static final String SECURITY_STATEREPORT = "SECURITY_STATEREPORT";

        public static final String ALARM_HTTP_INFO = "ALARM_HTTP_INFO";

        public static final String ALARM_HTTP_INFO_ALL = "ALARM_HTTP_INFO_ALL";
        /**
         * 更新昵称
         */
        public static final String SECURITY_UPDATE_NICK = "SECURITY_UPDATE_NICK";
        /**
         * 更新分享昵称
         */
        public static final String SECURITY_UPDATE_SHARE_NICK = "SECURITY_UPDATE_SHARE_NICK";
        /**
         * 验证安全码
         */
        public static final String SECURITY_CHECK_CODE = "SECURITY_CHECK_CODE";
        /**
         * 获取门锁的授权解锁码
         */
        public static final String SECURITY_GET_LOCK_KEY = "SECURITY_GET_LOCK_KEY";
        /**
         * 获取用户的布撤防状态
         */
        public static final String SECURITY_GET_DEFENCE_STATUS = "SECURITY_GET_DEFENCE_STATUS";
        /**
         * 获取猫眼的告警详情
         */
        public static final String SECURITY_GET_CATEYE_INFO = "SECURITY_GET_CATEYE_INFO";
        /**
         * 更改门铃的红外推送策略
         */
        public static final String SECURITY_SET_PUSH_STRATEGY = "SECURITY_SET_PUSH_STRATEGY";
        /**
         * 获取用户访客记录
         */
        public static final String SECURITY_GET_VISITORS = "SECURITY_GET_VISITORS";
        /**
         * 获取报警记录
         */
        public static final String SECURITY_GETALARMS = "SECURITY_GETALARMS";
        /**
         * 删除单个设备的报警记录
         */
        public static final String SECURITY_DELETEALARMS_BYID = "SECURITY_DELETEALARMS_BYID";
        /**
         * 删除猫眼的报警记录
         */
        public static final String SECURITY_DELETECAT_BYID = "SECURITY_DELETECAT_BYID";
        /**
         * 获取用户告警信息
         */
        public static final String SECURITY_GET_ALARM_INFO = "SECURITY_GET_ALARM_INFO";
        /**
         * 获取告警类型的提示
         */
        public static final String SECURITY_GET_ALARM_TIPS = "SECURITY_GET_ALARM_TIPS";
        /**
         * 设备绑定
         */
        public static final String SECURITY_DEVICE_BIND = "SECURITY_DEVICE_BIND";
        /**
         * 设备解绑
         */
        public static final String SECURITY_DEVICE_UNBIND = "SECURITY_DEVICE_UNBIND";
        /**
         * 设置安全码
         */
        public static final String SECURITY_SET_CODE = "SECURITY_SET_CODE";
        /**
         * 极光推送消息
         */
        public static final String JPUSH_MESSAGE = "JPUSH_MESSAGE";

        public static final String GET_DEVICE_CAN_MODIFY = "GET_DEVICE_CAN_MODIFY";
        /**
         * 获取用户告警状态
         */
        public static final String SECURITY_ALARM_STATUS = "SECURITY_ALARM_STATUS";
        /**
         * 获取门铃的红外推送策略
         */
        public static final String SECURITY_GET_PUSH_STRATEGY = "SECURITY_GET_PUSH_STRATEGY";
        /**
         * 重置安全码
         */
        public static final String SECURITY_UPDATE_CODE = "SECURITY_UPDATE_CODE";
        /**
         * 更新设备状态
         */
        public static final String SECURITY_UPDATE_DEVICE_STATE = "SECURITY_UPDATE_DEVICE_STATE";

        /**
         * 反馈记录
         */
        public static final String LETV_FEED_BACK = "LETV_FEED_BACK";

        /**
         * 获取消息列表
         */
        public static final String LETV_GET_NEWS = "LETV_GET_NEWS";

        /**
         * 获取开锁记录
         */
        public static final String LETV_GET_NOTES = "LETV_GET_NOTES";


        public static final String LETV_UPDATE_Fingerprint = "LETV_UPDATE_Fingerprint";

        /**
         * 获取区域列表
         */

        public static final String LETV_GET_AREAALL = "LETV_GET_AREAALL";
        /**
         * 获取工单列表
         */
        public static final String LETV_GET_ORDERLIST = "LETV_GET_ORDERLIST";
        /**
         * 创建订单
         */
        public static final String LETV_CREATE_ORDER = "LETV_CREATE_ORDER";

        /**
         * 同步京东订单
         */
        public static final String LETV_SYN_JDORDER = "LETV_SYN_JDORDER";
        /**
         * 获取订单详情
         */
        public static final String LETV_GETORDERDETAIL = "LETV_GETORDERDETAIL";

        public static final String LETV_ORDERASSESS = "LETV_ORDERASSESS";
        /**
         * 订单处理进度
         */
        public static final String LETV_COMMUMICATION = "LETV_COMMUMICATION";
        /**
         * 获取用户绑定申请
         */
        public static final String LETV_GETDISTRICTAPPLY = "LETV_GETDISTRICTAPPLY";

        /**
         * 获取所有小区信息
         */
        public static final String LETV_GETALLDISTRICT = "LETV_GETALLDISTRICT";

        /**
         * 获取所有可绑定的设备
         */
        public static final String SECURITY_GETUNLINKBINDDEVICE = "SECURITY_GETUNLINKBINDDEVICE";
        /**
         * 提交物业绑定
         */
        public static final String LETV_APPLYPROPERTY = "LETV_APPLYPROPERTY";
        /**
         * 验证订单地址
         */
        public static final String LETV_CHECKORDERADD = "LETV_CHECKORDERADD";
        /**
         * 开通会员列表
         */
        public static final String LETV_GET_VIP_MEM = "LETV_GET_VIP_MEM";

        public static final String LETV_GET_VIP_BM = "LETV_GET_VIP_BM";
        public static final String LETV_GET_VIP_GO = "LETV_GET_VIP_GO";

        public static final String LETV_GET_VIP_FINAL = "LETV_GET_VIP_FINAL";

        public static final String LETV_GET_VIP_HIS = "LETV_GET_VIP_HIS";

        public static final String LETV_SURE_UPGRADE = "LETV_SURE_UPGRADE";

        public static final String SECURITY_GET_CURRENTALARMS = "SECURITY_GET_CURRENTALARMS";

        public static final String SECURITY_HOMEMESSAGELIST_CHANGE = "SECURITY_HOMEMESSAGELIST_CHANGE";

        public static final String SECURITY_BINDLINKDEVICE = "SECURITY_BINDLINKDEVICE";

        public static final String SECURITY_DELETEBINDLINKDEVICE = "SECURITY_DELETEBINDLINKDEVICE";


        public static final String SECURITY_SHARE_CHECKACCPINT = "SECURITY_SHARE_CHECKACCPINT";

        public static final String SECURITY_SHARE_ADDSHARE = "SECURITY_SHARE_ADDSHARE";
        public static final String SECURITY_SHARE_ADDSHARE_SURE = "SECURITY_SHARE_ADDSHARE_SURE";

        public static final String SECURITY_SHARE_SHARELIST = "SECURITY_SHARE_SHARELIST";

        public static final String SECURITY_SHARE_CANCELSHARE = "SECURITY_SHARE_CANCELSHARE";

        public static  final String SECURITY_SHARE_ACCEPTLIST = "SECURITY_SHARE_ACCEPTLIST";

        public static final String SECURITY_SURE_SHARE = "SECURITY_SURE_SHARE";


        public static final String SECURITY_REFUSER_SHARE = "SECURITY_REFUSER_SHARE";

        public static final String SECURITY_SHARE_LISTINFO = "SECURITY_SHARE_LISTINFO";
        @StringDef({
                SECURITY_UPDATE_SHARE_NICK,
                SECURITY_SHARE_ADDSHARE_SURE,
                SECURITY_SHARE_LISTINFO,
                SECURITY_REFUSER_SHARE,
                SECURITY_SURE_SHARE,
                SECURITY_SHARE_ACCEPTLIST,
                SECURITY_SHARE_CANCELSHARE,
                SECURITY_SHARE_SHARELIST,
                SECURITY_SHARE_ADDSHARE,
                SECURITY_SHARE_CHECKACCPINT,
                SECURITY_DELETEBINDLINKDEVICE,
                SECURITY_BINDLINKDEVICE,
                SECURITY_GETUNLINKBINDDEVICE,
                SECURITY_HOMEMESSAGELIST_CHANGE,
                SECURITY_GET_CURRENTALARMS,
                SECURITY_DELETECAT_BYID,
                SECURITY_DELETEALARMS_BYID,
                LETV_SURE_UPGRADE,
                LETV_CHECKORDERADD,
                LETV_GET_VIP_HIS,
                LETV_GET_VIP_FINAL,
                LETV_GET_VIP_GO,
                LETV_GET_VIP_BM,
                LETV_GET_VIP_MEM,
                LETV_APPLYPROPERTY,
                LETV_GETALLDISTRICT,
                LETV_GETDISTRICTAPPLY,
                LETV_UPDATE_Fingerprint,
                LETV_GET_NOTES,
                LETV_COMMUMICATION,
                LETV_ORDERASSESS,
                LETV_GETORDERDETAIL,
                LETV_SYN_JDORDER,
                LETV_CREATE_ORDER,
                LETV_GET_ORDERLIST,
                LETV_GET_AREAALL,
                ALARM_HTTP_INFO,
                LAN_FIND_DEVICE,
                DEVICE_LIST_CHANGE,
                ALARM_INFO,
                LINKAGE_LIST_CHANGE,
                /**联动列表名称刷新**/
                LINKAGE_LIST_NAME,
                /** WebSocket 连接成功 **/
                WEB_SOCKET_CONNECT_STATE,
                PUSH_DEVICE_BIND,
                PUSH_DEVICE_UNBIND,
                ACTIVITY_LOGIN,
                ACTIVITY_LOGOUT,
                ACTIVITY_REGISTER1,
                ACTIVITY_REGISTER2,
                ACTIVITY_REGISTER3,
                ACTIVITY_GETUSERPHONE,
                ACTIVITY_CHANGEPASS1,
                ACTIVITY_CHANGEPASS2,
                ACTIVITY_CHANGEPHONE1,
                ACTIVITY_CHANGEPHONE2,
                ACTIVITY_CHANGEPHONE3,
                ACTIVITY_CHANGEPHONE4,
                ACTIVITY_DEVICE_BIND,
                ACTIVITY_DEVICE_UNBIND,
                ACTIVITY_REMOTE_OPERATION,
                ACTIVITY_PASSWORD,
                ACTIVITY_DEVICES_PASSWORD,
                ACTIVITY_RESTORE_DEFAULT,
                ACTIVITY_FORGET_PASSWORD1,
                ACTIVITY_FORGET_PASSWORD2,
                ACTIVITY_FORGET_PASSWORD3,
                ACTIVITY_UPLOAD_USER_GROUP,
                ACTIVITY_USER_GROUP,
                ACTIVITY_ELECTRIC_QUANTITY,
                ACTIVITY_UPDATE_DATA,
                ACTIVITY_UPDATE_DATA_BLOCk,
                ACTIVITY_UPDATE_DATA_OK,
                ACTIVITY_UPDATE_ERROR,
                ACTIVITY_UPDATE_REQUEST,
                MAIN_DEVICE_LOGIN,
                ACTIVITY_GET_CODE,
                ACTIVITY_CHECK_CODE,
                ACTIVITY_ALTER_NICK,
                ACTIVITY_UPLOAD_PORTRAIT,
                ACTIVITY_DOWNLOAD_PORTRAIT,
                ACTIVITY_FAMILY_ADD,
                ACTIVITY_FAMILY_DELETE,
                ACTIVITY_SEND_EMAIL,
                ACTIVITY_GET_USERINFO,
                ACTIVITY_AP_OK,
                ACTIVITY_SEND_CODE,
                ACTIVITY_GET_DEVICES,
                ACTIVITY_GET_FAMILIES,
                ACTIVITY_FAMILY_QUIT,
                ACTIVITY_FAMILY_ADD_CONFIRM,
                ACTIVITY_FAMILY_DEMISSION,
                WEBSOCKET_SEND_MESSAGEINFO,
                CHILD_DEVICES_LIST,
                CHILD_DEVICES_INFO,
                DEVICES_NAME_INFO,
                WAN_DEVICE_LIST,
                DEVICES_ERROR,
                DEVICES_INFO_SYNCHRONOUS,
                DEVICE_MESSAGE_ALARM_INFO,
                DEVICE_FAULT_SYNC,
                DEVICE_MESSAGE_NAME_INFO,
                ADD_DEVICE_GATEWAY,
                DEVICE_LOGIN_TIME_OUT,
                CANCEL_LOGIN_ACTIVITY,
                DEVICE_NOT_LINE,
                DEL_DEVICE_GATEWAY,
                DEVICE_TIMING_SYNC,
                GET_USER_LOGS,
                SET_USER_LOG,
                APP_STATE_CHANGE,
                WIFI_NET_WORK_CONNECTION,
                DEVICE_RESTART_DEVICE,
                ACTIVITY_AP_ING,
                CHANGE_MAIN_UI,
                SECURITY_GET_TOKEN,
                SECURITY_LOGIN,
                SECURITY_REGISTER_GET_CODE,
                SECURITY_REGISTER_CHECK_CODE,
                SECURITY_REGISTER,
                SECURITY_HTTP_ERROR,
                SECURITY_SAVE_ACCOUNT,
                SECURITY_GETLONGQUESTION,
                SECURITY_UPDATE_ADDNAME,
                LAN_OK,
                NET_STATE_CHANGE,
                SECURITY_UPDATEDEFENSE,
                SECURITY_CHECKAPPVERSION,
                SECURITY_UPDATEDEPWD,
                SECURITY_UPDATEDEPHONE,
                SECURITY_UPDATEDEPHONECODE,
                SECURITY_UPDATEEMAIL,
                SECURITY_UPDATECONTACT,
                SECURITY_UPDATESTANDBY,
                SECURITY_GETQUESTION,
                SECURITY_GETWEATHER,
                SECURITY_FORGET_PW_CHECK_CODE,
                SECURITY_FORGETPWCODE,
                SECURITY_FORGETPW,
                SECURITY_SUBMITQUESTION,
                SECURITY_ALARMMONTH,
                SECURITY_ALARMLIST,
                SECURITY_UPDATEDEPHONEValidateCODE,
                SECURITY_UPDATEPHONEVALIDATE,
                SECURITY_GETSHORTQUESTION,
                SECURITY_UPDATE_TYPE,
                SECURITY_GETTWOLINKAGE,
                SECURITY_SAVE_TWO_LINKAGE,
                SECURITY_SAVE_THREE_LINKAGE,
                SECURITY_GETTHREELINKAGE,
                SECURITY_STATEREPORT,
                DEVICE_TYPE_CHANGE,
                DEVICE_FIRST_LOGIN,
                SECURITY_GETALARMS,
                SECURITY_UPDATE_NICK,
                SECURITY_CHECK_CODE,
                SECURITY_GET_LOCK_KEY,
                SECURITY_GET_DEFENCE_STATUS,
                SECURITY_GET_CATEYE_INFO,
                SECURITY_SET_PUSH_STRATEGY,
                SECURITY_GET_VISITORS,
                DEVICE_LIST_WITHSTATE,
                SECURITY_GET_ALARM_INFO,
                SECURITY_GET_ALARM_TIPS,
                SECURITY_DEVICE_BIND,
                SECURITY_DEVICE_UNBIND,
                SECURITY_SET_CODE,
                JPUSH_MESSAGE,
                GET_DEVICE_CAN_MODIFY,
                SECURITY_ALARM_STATUS,
                SECURITY_CHECKQUESTION,
                SECURITY_GET_PUSH_STRATEGY,
                SECURITY_UPDATE_CODE,
                SECURITY_UPDATE_DEVICE_STATE,
                LETV_FEED_BACK,
                LETV_GET_NEWS
        })
        @Retention(RetentionPolicy.SOURCE)
        public @interface NotificationPostName {
        }
    }

    /**
     * 用来记录activity常量
     */
    public static class StartActivity {
        public final static String MAIN_TONG_FANG_ACTIVITY = "MainSocketActivity";
        public final static String BASE_TONG_FANG_ACTIVITY = "BaseActivity";
    }

    /**
     * ThreadName
     */
    public static class ThreadName {
        public static final String WS_HEART = "wsHeart";
    }

    /**
     * HttpURL
     */
    public static class UrlOrigin {

        public static final String security_devicelist_ContainShare="/device/listContainShare";

        /**
         * 分享并接收设备
         */
        public static final String share_add_share_sure = "/share/add_share_sure";

        /**
         * 分享设备列表
         */
        public static final String revicer_share_info = "/share/revicer_share_info";
        /**
         * 拒绝分享
         */
        public static final String cancel_share_revicer= "/share/cancel_share_revicer";
        /**
         * 接受分享
         */
        public static final String share_sure_share = "/share/sure_share";

        /**
         * 取消分享
         */
        public static final String share_cancel_share = "/share/cancel_share_share";

        /**
         * 分享设备
         */
        public static final String share_add_share = "/share/add_share";
        /**
         * 分享账号检测
         */
        public static final String share_check_account = "/share/check_account";
        /**
         * 分享接收列表
         */
        public static final String share_accept_list = "/share/accept_list";
        /**
         * 分享设备列表
         */
        public static final String share_revicer_list = "/share/revicer_list";
        /**
         * 登录
         */
        public static final String user_login = "/login";
        /**
         * 登出
         */
        public static final String user_logout = "/mobile/logout";
        /**
         * 通过手机号注册第一步
         */
        public static final String user_registerbyphone1 = "/rp/1";
        /**
         * 通过手机号注册第二步
         */
        public static final String user_registerbyphone2 = "/rp/2";
        /**
         * 通过手机号注册第三步
         */
        public static final String user_registerbyphone3 = "/rp/3";
        /**
         * 验证验证码
         */
        public static final String check_code = "/sepia";
        /**
         * 获取手机号码绑定信息
         */
        public static final String get_bindphone_info = "/mobile/phonebindinfo";
        /**
         * 获取websocket信息
         */
        public static final String user_getwebsocketinfo = "/mobile/cometadr";
        /**
         * 获取用户设备列表
         */
        public static final String user_devices = "/mobile/devices";
        /**
         * 修改用户昵称
         */
        public static final String user_alter_nick = "/mobile/cn";
        /**
         * 用户绑定设备
         */
        public static final String device_bind = "/mobile/bind";
        /**
         * 用户通过旧密码修改密码第一步
         */
        public static final String user_change_pass1 = "/mobile/cop/1";
        /**
         * 用户通过旧密码修改密码第二步
         */
        public static final String user_change_pass2 = "/mobile/cop/2";
        /**
         * 用户通过手机号修改密码第一步
         */
        public static final String user_change_pass_byphone1 = "/cpp/1";
        /**
         * 用户通过手机号修改密码第二步
         */
        public static final String user_change_pass_byphone2 = "/cpp/2";
        /**
         * 用户通过手机号修改密码第三步
         */
        public static final String user_change_pass_byphone3 = "/cpp/3";
        /**
         * 用户修改手机号第一步
         */
        public static final String user_change_phone1 = "/mobile/cp/1";
        /**
         * 用户修改手机号第二步
         */
        public static final String user_change_phone2 = "/mobile/cp/2";
        /**
         * 用户修改手机号第三步
         */
        public static final String user_change_phone3 = "/mobile/cp/3";
        /**
         * 用户修改手机号第四步
         */
        public static final String user_change_phone4 = "/mobile/cp/4";
        /**
         * 下载用户头像
         */
        public static final String user_download_portrait = "/mobile/facedown";
        /**
         * 添加家庭成员
         */
        public static final String family_add = "/mobile/invitation";
        /**
         * 确认加入家庭
         */
        public static final String family_add_confirm = "/mobile/invitationcfm";
        /**
         * 删除家庭成员
         */
        public static final String family_delete = "/mobile/demission";
        /**
         * 用户退出家庭
         */
        public static final String family_quit = "/mobile/quit";
        /**
         * 解散家庭
         */
        public static final String family_demission = "/mobile/demissionFamily";
        /**
         * 获取用户信息
         */
        public static final String get_userinfo = "/mobile/usr";
        /**
         * 用户解绑设备
         */
        public static final String device_unbind = "/mobile/unbind";
        /**
         * 获取家庭列表
         */
        public static final String get_families = "/mobile/families";
        /**
         * 获取用户日志
         */
        public static final String get_userlog = "/mobile/getUserLog";
        /**
         * 上传用户头像
         */
        public static final String user_upload_portrait = "/mobile/faceup";
        /**
         * 修改设备名称
         */
        public static final String changing_device_name = "/mobile/chdn";
        /**
         * 查询设备绑定信息
         */
        public static final String wan_device_info = "/mobile/device";
        /**
         * 升级请求
         */
        public static final String upgrade_device = "/backstage/chips_info";
        /**
         * 获取访问令牌（token）
         */
        public static final String security_token = "/authorize";

        /**
         * 获取通用盒子可修改的设备类型
         */
        public static final String security_device_can_modify = "/device/get_device_can_modify";
        /**
         * 刷新访问令牌（token过期时使用）
         */
        public static final String security_refresh_token = "/refresh_token";
        /**
         * 获取所有设备类型
         */
        public static final String security_getType = "/device/get_type_all";
        /**
         * 用户登录
         */
        public static final String security_login = "/login";
        /**
         * 用户注册获取验证码
         */
        public static final String security_register_get_code = "/register/code";
        /**
         * 用户注册验证验证码
         */
        public static final String security_register_check_code = "/register/checkRegisterCode";
        /**
         * 用户注册
         */
        public static final String security_register = "/register/create_customer";
        /**
         * 版本检测
         */
        public static final String security_checkversion = "/checkversion";
        /**
         * 设备版本检测
         */
        public static final String security_check_device_version = "/device/version";
        /**
         * 退出登录
         */
        public static final String security_logout = "/loginOff";
        /**
         * 个性账号保存
         */
        public static final String security_saveAccount = "/user/saveAccount";
        /**
         * 获取设备列表
         */
        public static final String security_deviceList = "/device/list";
        /**
         * 可查看状态的设备列表
         */
        public static final String security_deviceListWithState = "/device/listWithState";
        /**
         * 根据设备MAC查询设备
         */
        public static final String security_deviceByMac = "/device/getByMac";
        /**
         * 布防撤防
         */
        public static final String security_updateDefense = "/device/updateDefense";
        /**
         * 修改设备位置
         */
        public static final String security_updateName = "/device/updateName";
        /**
         * 获取用户信息
         */
        public static final String security_userinfo = "/user/info";
        /**
         * 获取修改手机的验证码
         */
        public static final String security_updatePhone_code = "/user/updatePhone/code";
        /**
         * 获取验证原始手机号的验证码
         */
        public static final String security_updatePhone_validate_code = "/user/updatePhone/validate/code";
        /**
         * 验证原始手机号码
         */
        public static final String security_updatePhone_validate = "/user/updatePhone/validate";
        /**
         * 找回密码验证安全码
         */
        public static final String security_check_code_by_phone = "/admin/checkSecurityCodeByPhone";
        /**
         * 获取忘记密码的验证码
         */
        public static final String security_forgetPW_code = "/back_psd/code";
        /**
         * 修改手机号码
         */
        public static final String security_updatePhone = "/user/updatePhone";
        /**
         * 忘记密码
         */
        public static final String security_forgetPW = "/password/getback";
        /**
         * 修改密码
         */
        public static final String security_updatePwd = "/user/updatePwd";
        /**
         * 修改邮箱
         */
        public static final String security_updateEmail = "/user/updateEmail";
        /**
         * 修改紧急联系人
         */
        public static final String security_updateContact = "/user/updateContact";
        /**
         * 修改备用联系人
         */
        public static final String security_updateStandby = "/user/updateStandby";
        /**
         * 获取设备历史报警记录
         */
        public static final String security_alarmList = "/device/alarm/list";
        /**
         * 获取设备有报警记录的月份
         */
        public static final String security_alarmMonth = "/device/alarm/month";

        /**
         * 根据服务ID获取报警详情
         */
        public static final String security_alarmInfo_byServiceId = "/device/alarm/info/serviceId";
        /**
         * 获取所有的告警历史记录
         */
        public static final String security_getalarms = "/alarm/getalarms";
        /**
         * 获取单个设备的告警历史记录
         */
        public static final String security_device_getalarms = "/alarm/getalarmsByDevId";
        /**
         * 删除单个设备的告警历史记录
         */
        public static final String security_delete_alarms_byid = "/alarm/deletevAlarmsByDevId";
        /**
         * 删除猫眼
         */
        public static final String security_delete_cat_alarms_byid = "/alarm/cateye/deleteAlarmsByDevId";
        /**
         * 用户是否做过问卷
         */
        public static final String security_check_question = "/check/question";
        /**
         * 获取问卷列表
         */
        public static final String security_questionnaire_data = "/questionnaire/data";
        /**
         * 获取长问卷连接
         */
        public static final String security_get_long_question = "/get_long_question";
        /**
         * 获取短问卷列表
         */
        public static final String security_get_short_questionnaire = "/get_short_questionnaire";
        /**
         * 保存用户答卷
         */
        public static final String security_questionnaire_submit = "/questionnaire/submit";
        /**
         * 获取天气资讯
         */
        public static final String security_weather_info = "/weather/info";
        /**
         * 修改通用盒子的设备类型
         */
        public static final String security_deviceType = "/device/update_devType";
        /**
         * 根据所选择的探测设备获取匹配的动作设备
         */
        public static final String security_gettwolinkage = "/get_two_linkage";
        /**
         * 保存用户设置的二级联动方案
         */
        public static final String security_savetwolinkage = "/save_two_linkage";
        /**
         * 获取用户设置的三级联动方案
         */
        public static final String security_getthreelinkage = "/get_three_linkage";
        /**
         * 保存用户设置的三级联动方案
         */
        public static final String security_savethreelinkage = "/save_three_linkage";
        /**
         * 获取设备各状态报告
         */
        public static final String security_get_statereport = "/device/get_deviceState_report";
        /**
         * 更新昵称
         */
        public static final String security_update_nick = "/user/name/update";
        /**
         * 更新分享昵称
         */
        public static final String security_update_share_nick = "/share/update_nick";
        /**
         * 验证安全码
         */
        public static final String security_check_code = "/admin/checkSecurityCodeByToken";
        /**
         * 获取门锁的授权解锁码
         */
        public static final String security_get_lock_key = "/device/lock/getAuthKey";
        /**
         * 获取用户的布撤防状态
         */
        public static final String security_get_defence_status = "/customer/defence/status";
        /**
         * 获取猫眼的告警详情
         */
        public static final String security_get_cateye_info = "/alarm/cateye/getInfo";
        /**
         * 更改门铃的红外推送策略
         */
        public static final String security_set_push_strategy = "/device/push/stragegy";
        /**
         * 获取用户访客记录
         */
        public static final String security_get_visitors = "/alarm/cateye/getalarms";

        /**
         * 根据设备查用户访客记录
         */
        public static final String security_get_visitors_byid="/alarm/cateye/getalarmsByDevId";
        /**
         * 获取用户告警信息
         */
        public static final String security_get_alarm_info = "/alarm/getAlarmInfo";
        /**
         * 获取设备报警详情
         */
        public static final String security_alarmInfo = "/device/alarm/info";

        /**
         * 获取告警类型的提示
         */
        public static final String security_get_alarm_tips = "/alarm/getTips";
        /**
         * 设备绑定
         */
        public static final String security_device_bind = "/device/bind";
        /**
         * 设备解绑
         */
        public static final String security_device_unbind = "/device/unbind";
        /**
         * 设置安全码
         */
        public static final String security_set_code = "/user/updateSecurityCode";
        /**
         * 获取用户告警状态
         */
        public static final String security_alarm_status = "/service/alarmstatus";
        /**
         * 获取门铃的红外推送策略
         */
        public static final String security_get_push_strategy = "/device/get/push/stragegy";
        /**
         * 重置安全码（上传身份证照片）
         */
        public static final String security_update_code = "/user/security/request";

        /**
         * 反馈
         */
        public static final String letv_feed_back = "/letv/feed_back";

        /**
         * 用户评价
         */
        public static final String letv_add_assess = "/letv/add_assess";

        /**
         * 获取消息列表
         */
        public static final String letv_get_news = "/letv/get_message_list";

        public static final String letv_get_notes = "/letv/get_lock_log";

        public static final String letv_update_fingerprint= "/letv/update_fingerprint";
        /**
         * 获取区域列表
         */
        public static final String letv_get_areaAll = "/letv/get_area_all";

        /**
         * 获取工单列表
         */
        public static final String letv_get_orderlist = "/letv/get_order_list";
        /**
         * 创建工单
         */
        public static final String letv_create_order = "/letv/create_order";
        /**
         * 同步京东订单
         */
        public static final String letv_syn_jdorder = "/letv/syn_jd_order";

        /**
         * 确认收货扫二维码
         */

        public static final String letv_check_reciver_lock = "/letv/check_reciver_lock";

        /**
         * 获取订单详情
         */

        public static final String letv_get_orderDetail = "/letv/get_order_detail";

        /**
         * 获取订单进度
         */
        public static final String letv_get_communication_list = "/letv/get_communication_list";

        /**
         * 获取用户绑定申请
         */
        public static final String letv_get_district_apply="/user/get_district_apply";
        /**
         * 获取所有小区信息
         */
        public static final String letv_get_all_district ="/user/get_all_district";
        /**
         * 申请绑定物业
         */
        public static final String letv_add_district_apply = "/user/add_district_apply";

        /**
         *  会员信息
         */
        public static final String letv_vip_me = "/vip/me";

        /**
         *  开通会员列表
         */
        public static final String letv_vip_mem = "/vip/mem";

        /**
         * 开通会员页面开通按钮
         */
        public static final String letv_vip_bm = "/vip/bm";

        /**
         * 选择支付方式后去支付
         */
        public static final String letv_vip_go = "/vip/go";

        /**
         * 支付宝的验签
         */
        public static final String letv_vip_final = "/vip/alipay_final";

        /**
         * 缴费记录
         */
        public static final String letv_vip_his = "/vip/his";

        /**
         * 登陆绑定小区不再提示
         */
        public static final String letv_user_showbindno = "/user/no_notify";

        /**
         * 服务地址验证
         */
        public static final String letv_check_orderAdd = "/letv/check_order_address";

        /**
         * 锁猫眼确认升级
         */
        public static final String letv_sure_upgrade = "/device/sureUpgrade";

        /**
         * 查首页告警信息
         */
        public static final String security_getCurrentAlarms = "/alarm/getCurrentAlarms";
        /**
         * 获取可接收绑定关系设备
         */
        public static final String security_getUnLinkBindDevices = "/device/getUnLinkbindDevices";

        /**
         * 绑定猫眼门锁关系
         */
        public static final String security_bindlinkdevice = "/device/bindLinkDevice";

        /**
         *解除猫眼门锁关系
         */
        public static final String security_deletebindlinkdevice = "/device/deleteLinkBindDeviceId";

    }

    /**
     * 类的名称:OUT_TARGET</br>
     * 主要功能描述:输出目标</br>
     * 创建日期:2014-2-28
     *
     * @author ZhaoQing
     *         <table>
     *         <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     *         <tr><td>0.1</td><td>2014-2-28</td><td>ZhaoQing</td><td>初始创建</td>
     *         </table>
     */
    public static class OUT_TARGET {
        /**
         * 用户
         */
        public static final int User = 0;
        /**
         * 测试
         */
        public static final int Info = 1;
        /**
         * 开发
         */
        public static final int DeBug = 2;
    }

    /**
     * 类的名称:OUT_TYPE</br>
     * 主要功能描述:输出类型</br>
     * 创建日期:2014-2-28
     *
     * @author ZhaoQing
     *         <table>
     *         <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
     *         <tr><td>0.1</td><td>2014-2-28</td><td>ZhaoQing</td><td>初始创建</td>
     *         </table>
     */
    public static class OUT_TYPE {
        /**
         * log输出
         */
        public static final int Type_Log = 0;
        /**
         * Toast输出
         */
        public static final int Type_Toast = 1;
        /**
         * 弹出框
         */
        public static final int Type_Alert = 2;
        /**
         * 文件
         */
        public static final int Type_File = 3;
    }

    public static class NetWork {


        public static final int WAN_NET_PROXY = 1 << 1;
        /**
         * 广域网
         */
        public static final int WAN_NET = 1 << 2;
        /**
         * 局域网
         */
        public static final int LAN_NET = 1 << 3;
        /**
         * 网关子设备
         */
        public static final int GATEWAY_NET = 1 << 4;
        /**
         * zigBee
         */
        public static final int ZIG_BEE_NET = 1 << 5;
        /**
         * Http
         */
        public static final int HTTP = 1 << 6;
        /**
         * 当前最大网络状态
         */
        public static final int MAX_NETWORK = HTTP;

        @IntDef(flag = true,
                value = {
                        WAN_NET_PROXY,
                        LAN_NET,
                        GATEWAY_NET,
                        WAN_NET,
                        ZIG_BEE_NET,
                        MAX_NETWORK
                })
        @Retention(RetentionPolicy.SOURCE)
        public @interface DeviceNetWork {
        }
    }

    /**
     * 存储路径
     */
    public static class Path {
        /**
         * 本地存储路径
         */
        public static final String LOCAL_PATH =
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/smarthome";
        /**
         * 错误日志路径
         */
        public static final String ERROR_LOG_PATH = "/errorLog";
        /**
         * 产品信息路径
         */
        public static final String PRODUCT_INFO_PATH = "/productInfo";
        /**
         * 下载路径
         */
        public static final String DOWNLOAD_PATH = "/download";
        /**
         * 照片路径
         */
        public static final String PHOTO_PATH = "/photo";
    }
}

