package com.vision.smarthomeapi.dal.data;


import com.vision.smarthomeapi.bean.RSecurityDevice;
import com.vision.smarthomeapi.dal.DeviceParseHeadData;
import com.vision.smarthomeapi.dal.sql.SmartDeviceSQL;

import java.util.List;

/**
 * 回复数据头
 */
public class HeadData {

    /**
     * 记录广域网设备
     */
    public List<SmartDevice> deviceWanNewList;

    public int netType;
    /**
     * 数据解析类
     */
    public DeviceParseHeadData deviceParseHeadData;
    /**
     * 本地保存数据
     */
    public SmartDeviceSQL mSQLSmartDevice;
    /**
     * 广域网数据
     */
    public RSecurityDevice wanDevice;
    /**
     * 设置ip
     */
    public String ip;
    /**
     * 设备类型
     */
    public int devType;

    /**
     * 设备型号
     */
    public int devVersion;

    /**
     * 通讯型号
     */
    public int communicationType;

    /**
     * 厂商编码
     */
    public int manufacturerEncoding;

    /**
     * 是否最后一个数据
     */
    public boolean isOver;

    public int deviceeUpdateType;


}
