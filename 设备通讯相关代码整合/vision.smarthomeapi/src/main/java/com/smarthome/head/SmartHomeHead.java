package com.smarthome.head;

import android.R.string;


public class SmartHomeHead {

    static{
        System.loadLibrary("smarthome_head");
    }
	/**
	 * 添加普通数据包头信息
	 * @param data 需要添加包头的数据和相关信息参见{@link SmartHomeData}
	 * @return 添加完成后头的数据
	 */
	public synchronized static native byte[] addHead(SmartHomeData data,byte[] passwd,byte[] sessionAuth);

    /**
     * 解析普通数据包内容
     * @param data 需要解析的数据
     * @param sequence 包序号
     * @param dataSequence 协议包序号
     * @param password 设备控制密码
     * @return 无论解析是否成功都会返回 {@link SmartHomeData}对象 具体解析结果以 {@link SmartHomeData}的code值为准。
     */
	public synchronized static native SmartHomeData parseData(byte[] data, int sequence,short dataSequence, byte[] password,byte[] sessionAuth,boolean needCheck);

    /**
     * 添加Zigbee数据包头信息
     * @param data 需要添加包头的数据和相关信息参见{@link SmartHomeData}
     * @return 添加完成后头的数据
     */
	public synchronized static native byte[] addZigbeeHead(SmartHomeData data,byte[] passwd,byte[] session_auth);

    /**
     * 解析Zigbee数据包内容
     * @param data 需要解析的数据
     * @param sequence 包序号
     * @param dataSequence 协议包序号
     * @param password 设备控制密码
     * @return 无论解析是否成功都会返回 {@link SmartHomeData}对象 具体解析结果以 {@link SmartHomeData}的code值为准。
     */
	public synchronized static native SmartHomeData parseZigbeeData(byte[] data, int sequence,short dataSequence, byte[] password,long mac ,byte[] sessionAuth,boolean needCheck);
}
