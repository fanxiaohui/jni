package com.vision.smarthomeapi.dal.data;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 设备常量
 */
public class SmartDeviceConstant {

    /**
     * 设备状态
     */
    public static class CommunicationType {
        /**
         * 通讯类型：无源设备
         */
        public static final int PASSIVE_DEVICE = 0x02;

        /**
         * 通讯类型：有源设备
         */
        public static final int ACTIVE_DEVICE = 0x05;
    }


    /**
     * 设备状态
     */
    public static class State {
        /**
         * 芯片升级状态
         **/
        public static final int UPGRADE_CPU = 1;//
        /**
         * 单片机升级
         **/
        public static final int UPGRADE_MCU = 1 << 1;//

        /**
         * 门锁猫眼升级
         */
        public static  final int UPGRADE_SPECIAL = 1 << 2;
        /**
         * 绑定
         **/
        public static final int BINDING = 1 << 2;//
        /**
         * 定时
         **/
        public static final int TIMING = 1 << 3;//
        /**
         * 告警
         **/
        public static final int ALARM = 1 << 4;//
        /**
         * 云平台
         **/
        public static final int LOGIN_CLOUD = 1 << 5;//
        /**
         * 501锁定位置
         **/
        public static final int DEVICE_LOCK = 1 << 6;//
        /**
         * 网关设备
         **/
        public static final int GATEWAY_DEVICES = 1 << 7;//
        /**
         * zigbee设备
         **/
        public static final int ZIG_BEE_DEVICES = 1 << 8;//


    }


    /**
     * 消息ID
     */
    public static class MsgID {
        /**
         * 局域网搜索
         */
        public static final short LAN_RETRIEVE = 0x01;
        /**
         * 登录认证
         */
        public static final short DEVICE_LOGIN = 0x03;
        /**
         * 重启设备
         */
        public static final short DEVICE_CONTROL_RESTART = 0x10;
        /**
         * 控制状态改变
         */
        public static final short DEVICE_CONTROL_CHANGE = 0x11;
        /**
         * 运行状态同步
         */
        public static final short DEVICE_STATE_SYNC = 0x30;
        /**
         * 报警信息同步
         */
        public static final short DEVICE_ALARM_SYNC = 0x31;
        /**
         * 设备故障信息同步
         */
        public static final short DEVICE_FAULT_SYNC = 0x32;
        /**
         * 子设备上下线通知
         */
        public static final short DEVICE_CHILD_LINE = 0x33;
        /**
         * 平台推送消息到数手机
         */
        public static final short MESSAGE_PLATFORM_INFO = 0x35;
        /**
         * 恢复默认配置
         */
        public static final short DEVICE_RESTORE_DEFAULT = 0x50;
        /**
         * 系统时间
         */
        public static final short DEVICE_SYSTEM_TIME = 0x51;
        /**
         * 参数设置
         */
        public static final short DEVICE_PARAMETER_SET = 0x52;
        /**
         * 定时设置,小家电使用
         */
        public static final short DEVICE_TIMING_SET = 0x53;
        /**
         * 设置网络
         */
        public static final short DEVICE_NETWORK_SET = 0x54;
        /**
         * 通用定时设置,插座使用
         */
        public static final short DEVICE_TIMING = 0x55;
        /**
         * 心跳
         */
        public static final short DEVICE_CONTROL_HEARTBEAT = 0x70;
        /**
         * 退出登录
         */
        public static final short DEVICE_LOGOUT = 0x71;
        /**
         * 设备运行查询
         */
        public static final short DEVICE_RUN_STATE = 0x72;
        /**
         * 报警状态查询
         */
        public static final short DEVICE_FAULT_STATE = 0x73;
        /**
         * 故障状态查询
         */
        public static final short DEVICE_ALARM_STATE = 0x74;
        /**
         * 请求升级
         */
        public static final short DEVICE_UPDATE_INFO = 0x75;
        /**
         * 数据数据
         */
        public static final short DEVICE_UPDATE_DATA = 0x76;
        /**
         * 获取子设备列表
         */
        public static final short DEVICE_CHILD_DEVICE_LIST = 0x77;

        /**
         * 添加子设备
         */
        public static final short ADD_CHILD_DEVICE = 0x78;

        /**
         * 手机通知平台设备通讯中断
         */
        public static final short DEVICE_NOT_ONLINE = 0xC7;

        /**
         *  广域网连接成功
         */
        public static final short WAN_ONLINE = 0xCC;



    }


    public static final int addOk = 0x00;//添加设备成功
    public static final int addExist = 0x01;//添加设备已存在
    public static final int addError = 0x02;//添加设备错误

    public static boolean PARSE_OK = true;

    public static boolean PARSE_ERROR_LENGTH = false;


    public static int PARSE_CODE_OK = 0;
    public static int PARSE_CODE_ERROR = -1;
    public static int PARSE_CODE_NO_EXIST = -2;

    /**
     * 数据头状态
     */
    public class OperateState {
        /**
         * 不是应答帧
         */
        public static final boolean No_ACk = false;
        /**
         * 应答帧
         */
        public static final boolean ACk = true;

        /**
         * 读请求操作
         */
        public static final boolean Read = true;

        /**
         * 写请求操作
         */
        public static final boolean No_Read = false;

        /**
         * 重发
         */
        public static final boolean Resend = true;

        /**
         * 不是重发
         */
        public static final boolean No_Resend = false;
    }

    /**
     * 排序状态
     *
     * @deprecated
     */
    public class OrderStatus {

        public static final int INIT_DEVICE = 1;

        public static final int LOGIN_DEVICE = 2;

        public static final int CONNECT_DEVICE = 3;

        public static final int DISCONNECT_DEVICE = 4;
    }

    /**
     * 状态机事件管理
     *
     * @deprecated
     */
    public static class EventStatus {
        /**
         * WebSocket断开
         */
        public static final int WEB_SOCKET_DISCONNECT = 1;
        /**
         * 登录超时
         */
        public static final int LOGIN_TIME_OUT = 2;
        /**
         * 设备绑定
         */
        public static final int DEVICE_BINDING = 3;
        /**
         * 设备解绑
         */
        public static final int DEVICE_UN_BINDING = 4;
        /**
         * 设备掉线
         */
        public static final int DEVICE_DISCONNECT = 5;

        /**
         * 设备信息
         */
        public static final int DEVICE_INFO = 6;

        @Retention(RetentionPolicy.SOURCE)
        /**
         *  @deprecated
         */
        public @interface Event {
        }
    }

}
