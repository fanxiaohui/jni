package com.smarthome.head;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zhaoqing on 2016/8/1.
 */
public class SmartHomeConstant {

    public static class OPCode{

        //与平台通讯
        public static final short WAN_PLATFORM = 0x02;
        //平台转发数据
        public static final short WAN_FORWARD = 0x03;
        //广域网代理
        public static final short WAN_PROXY = 0x05;

    }

    public static class Key {
        //无密钥
        public static final byte NO_SECRET_KEY = 0;
        //随机密钥（登录之前使用）
        public static final byte RANDOM_SECRET_KEY = 1;
        //密证
        public static final byte KEY_CERTIFICATE = 2;
        //密证+随机密钥（登录之后使用）。
        public static final byte LOGIN_SECRET_KEY = 3;

        @Retention(RetentionPolicy.SOURCE)
        public @interface KeyState {
        }
    }

    public static class Encrypt {
        //加密类型，0-RC4，1-DES 加密，2-AES 加密。
        public static final byte ENCRYPT_TYPE_RC4 = 0;
        public static final byte ENCRYPT_TYPE_DES = 1;
        public static final byte ENCRYPT_TYPE_AES = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EncryptState {
        }
    }

    //二进制数据格式。
    public static final byte BINARY = 0;
    //JSON 格式数据
    public static final byte Json = 1;
    //XML 格式数据
    public static final byte XML = 2;

    

    /**
     * 消息ID
     */
    public static class CMD {
        /**
         * 局域网搜索  0x01
         */
        public static final short LAN_RETRIEVE = 0x01;
        /**
         * 登录认证 0x03
         */
        public static final short DEVICE_LOGIN = 0x03;
        /**
         * 重启设备 0x10
         */
        public static final short DEVICE_CONTROL_RESTART = 0x10;
        /**
         * 控制状态改变 0x11
         */
        public static final short DEVICE_CONTROL_CHANGE = 0x11;
        /**
         * 运行状态同步 0x30
         */
        public static final short DEVICE_STATE_SYNC = 0x30;
        /**
         * 报警信息同步 0x31
         */
        public static final short DEVICE_ALARM_SYNC = 0x31;
        /**
         * 设备故障信息同步 0x32
         */
        public static final short DEVICE_FAULT_SYNC = 0x32;
        /**
         * 子设备上下线通知 0x33
         */
        public static final short DEVICE_CHILD_LINE = 0x33;
        /**
         * 平台推送消息到数手机 0x35
         */
        public static final short MESSAGE_PLATFORM_INFO = 0x35;
        /**
         * 恢复默认配置 0x50
         */
        public static final short DEVICE_RESTORE_DEFAULT = 0x50;
        /**
         * 系统时间 0x51
         */
        public static final short DEVICE_SYSTEM_TIME = 0x51;
        /**
         * 参数设置 0x52
         */
        public static final short DEVICE_PARAMETER_SET = 0x52;
        /**
         * 定时设置,小家电使用 0x53
         */
        public static final short DEVICE_TIMING_SET = 0x53;
        /**
         * 设置网络 0x54
         */
        public static final short DEVICE_NETWORK_SET = 0x54;
        /**
         * 通用定时设置,插座使用 0x55
         */
        public static final short DEVICE_TIMING = 0x55;
        /**
         * 心跳 0x70
         */
        public static final short DEVICE_CONTROL_HEARTBEAT = 0x70;
        /**
         * 退出登录 0x71
         */
        public static final short DEVICE_LOGOUT = 0x71;
        /**
         * 设备运行查询  0x72
         */
        public static final short DEVICE_RUN_STATE = 0x72;
        /**
         * 报警状态查询  0x73
         */
        public static final short DEVICE_FAULT_STATE = 0x73;
        /**
         * 故障状态查询  0x74
         */
        public static final short DEVICE_ALARM_STATE = 0x74;
        /**
         * 请求升级 0x75
         */
        public static final short DEVICE_UPDATE_INFO = 0x75;
        /**
         * 数据数据  0x76
         */
        public static final short DEVICE_UPDATE_DATA = 0x76;
        /**
         * 获取子设备列表  0x77
         */
        public static final short DEVICE_CHILD_DEVICE_LIST = 0x77;

        /**
         * 添加子设备 0x78
         */
        public static final short ADD_CHILD_DEVICE = 0x78;

        /**
         * 手机通知平台设备通讯中断 0xC7
         */
        public static final short DEVICE_NOT_ONLINE = 0xC7;

        /**
         *  广域网连接成功 0xCC
         */
        public static final short WAN_ONLINE = 0xCC;

    }
}
