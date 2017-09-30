package com.smarthome.head;

import android.support.annotation.IntDef;

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

        @IntDef(flag = true,
                value = {
                        NO_SECRET_KEY,
                        RANDOM_SECRET_KEY,
                        KEY_CERTIFICATE,
                        LOGIN_SECRET_KEY,
                })
        @Retention(RetentionPolicy.SOURCE)
        public @interface KeyState {
        }
    }

    public static class Encrypt {
        //加密类型，0-RC4，1-DES 加密，2-AES 加密。
        public static final byte ENCRYPT_TYPE_RC4 = 0;
        public static final byte ENCRYPT_TYPE_DES = 1;
        public static final byte ENCRYPT_TYPE_AES = 2;

        @IntDef(flag = true,
                value = {
                        ENCRYPT_TYPE_RC4,
                        ENCRYPT_TYPE_DES,
                        ENCRYPT_TYPE_AES,
                })
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

}
