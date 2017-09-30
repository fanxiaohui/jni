package com.vision.smarthomeapi.dal.impl;


import com.vision.smarthomeapi.bean.RSecurityTypeInfo;
import com.vision.smarthomeapi.bean.SecurityTypeInfo;
import com.vision.smarthomeapi.dal.data.HeadData;

import java.util.Map;

/**
 * 初始化设备接口
 */
public interface SmartInitDevice {

    /**
     * 接收设备响应数据回调方法
     * @param headData
     */
    public void smartDeviceDataInit(HeadData headData);
    public void setTypeInfo(RSecurityTypeInfo rSecurityTypeInfo);
}
