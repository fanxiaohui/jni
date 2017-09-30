package com.vision.smarthome.dal.impl;

import com.vision.smarthome.dal.function.TimeDataNew;
import com.vision.smarthome.dal.function.TimeDataNew;

import java.util.List;

/**
 * 设备定时
 */
public interface IDeviceTime {

    /**
     * 获取时间的发码
     */
    public boolean sendDeviceTime(boolean isRead);

//    /**
//     * 校时
//     *
//     * @return
//     */
//    public boolean sendDeviceCheckTime(boolean isRead);
//
//    /**
//     * 校时
//     *
//     * @return
//     */
//    public boolean parseDeviceCheckTime(byte[] data);

    /**
     * 获取所有定时
     *
     * @return
     */
    public List<TimeDataNew.SubInsTime> getSubInsTimeList();

    /**
     * 增加一条
     *
     * @return
     */
    public boolean addSubInsTime(TimeDataNew.SubInsTime subInsTime);

    /**
     * 删除一条定时
     *
     * @param subID 要被删除ID
     * @return
     */
    public boolean deleteSubInsTime(int subID);

    /**
     * 获取新的定时
     *
     * @return
     */
    public TimeDataNew.SubInsTime getNewSubInsTime();

    /**
     * 备份定时
     *
     * @return
     */
    public void backupSubInfTime(TimeDataNew.SubInsTime subInsTime);


    /**
     * 查找当前定时
     *
     * @param id
     * @return
     */
    public TimeDataNew.SubInsTime findSubInfTime(int id);


    /**
     * 获取备份定时
     *
     * @return
     */
    public void recoverBackupSubInfTime();

}
