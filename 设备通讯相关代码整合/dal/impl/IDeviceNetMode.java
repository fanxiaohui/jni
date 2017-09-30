package com.vision.smarthome.dal.impl;

/**
 * 网络改变模式
 */
public interface IDeviceNetMode {

    /**
     * 发送WIFI账号和密码
     *
     * @param ssId wifi账号
     * @param pw   密码
     * @return
     */
    public boolean sendSsIdPassDevice(String ssId, String pw);

    /**
     * 发送重启设备
     *
     * @return
     */
    public boolean sendRestartDevice();

}
