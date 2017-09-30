package com.vision.smarthomeapi.bll.manage;


import android.content.ContentValues;

import com.vision.smarthomeapi.bean.RUpgradeInfo;
import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.function.UpdateData;
import com.vision.smarthomeapi.dal.sql.DeviceUpgradeInfo;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.FileIO;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;
import com.vision.smarthomeapi.util.TimeUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 设备升级管理类
 */
public class DeviceUpgradeManage {

    public static String UPDATE_DEVICE_TAG = "升级管理";


    private static DeviceUpgradeManage deviceUpgradeManager;
    //路径
    private static final String UPGRADED_DATA = "/upgradeData/";


    public DeviceUpgradeManage() {
        deviceMap = new ConcurrentHashMap<>();
    }

    public static DeviceUpgradeManage defaultDeviceUpgradeManager() {
        if (deviceUpgradeManager == null) {
            deviceUpgradeManager = new DeviceUpgradeManage();
        }
        return deviceUpgradeManager;
    }


    /**
     * 校验升级
     */
    private void startHttpDeviceCheckVersion(SmartDevice smartDevice, String url, int mode) {
////        String deviceType;
//        short deviceChip;
//        String deviceCheckUrl;
//        //当前硬件的版本号
//        short deviceAppNum;
//
//        UpdateData newUpdateData = smartDevice.getSmartDeviceLogic().getUpdateData();
//
        OutPutMessage.LogCatInfo("升级管理","MODE--------------->" + mode);
        SecurityUserManage.getShare().getUser().checkDeviceVersion(url,smartDevice.getSmartDeviceLogic().getDeviceMac(), mode + "");

    }


    private Map<String, Boolean> deviceMap;


    /**
     * 设备查询是否可以升级，添加升级标记
     *
     * @return true 代表更新
     */
    public void isUpgradeDevice(SmartDevice smartDevice) {
        UpdateData newUpdateData = smartDevice.getSmartDeviceLogic().getUpdateData();
        String CPU_URL = "";
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " ->升级标记->" + newUpdateData.isDeviceUpdateMode());
        if (newUpdateData.isDeviceUpdateMode()) {
            int unsigned_MAX_VALUE = Integer.valueOf(0x0000FFFF).intValue();
            int deviceType = newUpdateData.getDeviceUpdateType();
            int type = deviceType | unsigned_MAX_VALUE;
            CPU_URL = Integer.toHexString(type).toUpperCase();
            newUpdateData.setDeviceCPUTYPE(CPU_URL);

            if (!CPU_URL.equals("")) {//芯片升级
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> cpu是否可以升级:" + CPU_URL);
                boolean cpu = queryDeviceUpdateInfo(smartDevice, CPU_URL, UpdateData.CPU_UPDATE);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> cpu是否可以升级:" + cpu);
                if (cpu) {
                    OutPutMessage.LogCatInfo("CPU升级","添加升级标记1");
                    smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.UPGRADE_CPU);
                }
            }
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> CPU_URL:" + CPU_URL);
            String MCU_URL = Integer.toHexString(newUpdateData.getDeviceUpdateType()).toUpperCase();
            if (!MCU_URL.equals("")) {
                newUpdateData.setDeviceMCUTYPE(MCU_URL);
                boolean mcu = queryDeviceUpdateInfo(smartDevice, MCU_URL, UpdateData.MCU_UPDATE);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> mcu是否可以升级:" + mcu);
                if (mcu) {
                    smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.UPGRADE_MCU);
                }
            }
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> MCU_URL:" + MCU_URL);
        } else {
            CPU_URL = Integer.toHexString(newUpdateData.getDeviceUpdateType()).toUpperCase();
            newUpdateData.setDeviceCPUTYPE(CPU_URL);
            if (!CPU_URL.equals("")) {//芯片升级
                boolean cpu = queryDeviceUpdateInfo(smartDevice, CPU_URL, UpdateData.CPU_UPDATE);
                if (cpu) {
                    OutPutMessage.LogCatInfo("CPU升级","添加升级标记2");
                    smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.UPGRADE_CPU);
                }
            }
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> CPU_URL:" + CPU_URL);
        }


    }

    /**
     * 查询本地信息是否升级
     *
     * @param smartDevice
     * @param url
     * @return
     */
    private boolean queryDeviceUpdateInfo(SmartDevice smartDevice, String url, int mode) {
        UpdateData newUpdateData = smartDevice.getSmartDeviceLogic().getUpdateData();
        String deviceUrl = url + "_" + newUpdateData.getDeviceChipType()  ;
        List<DeviceUpgradeInfo> deviceUpgradeList = DataSupport.where("upgradeUrl = ?", deviceUrl).find(DeviceUpgradeInfo.class);
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> deviceUrl:" + deviceUrl);
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询列表设备：" + deviceUpgradeList);
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac()  + " -> 查询cpu app Ver：" + newUpdateData.getAppVersionCPU());
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac()  + " -> 查询MCU  app Ver：" + newUpdateData.getAppVersionMCU());
        if (deviceUpgradeList.size() > 0) {
            DeviceUpgradeInfo deviceUpgradeInfo = deviceUpgradeList.get(0);
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, deviceUpgradeInfo.toString());
            if (checkTime(deviceUpgradeInfo.getUpgradeDate())
                    && checkVersion(smartDevice, newUpdateData, deviceUpgradeInfo, mode)
                    && fileIsExists(deviceUpgradeInfo.getFileName())) {
                switch (mode) {
                    case UpdateData.CPU_UPDATE:
                        newUpdateData.setDeviceCPUNewVer(ByteUtil.getDeviceAppNum(Short.valueOf(deviceUpgradeInfo.getUpgradeVer())));
                        break;
                    case UpdateData.MCU_UPDATE:
                        newUpdateData.setDeviceMCUNewVer(ByteUtil.getDeviceAppNum(Short.valueOf(deviceUpgradeInfo.getUpgradeVer())));
                        break;
                }
                if (!deviceMap.containsKey(deviceUrl)) {
                    deviceMap.put(deviceUrl, true);
                }
                return true;
            }
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询MCUNewVer：" + newUpdateData.getDeviceMCUNewVer());
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询CPUNewVer：" + newUpdateData.getDeviceCPUNewVer());
        }
        if (deviceUpgradeList.size() > 0) {
            if (checkVersion(smartDevice, newUpdateData, deviceUpgradeList.get(0), mode)) {
                String deviceType = "";
                switch (mode) {
                    case UpdateData.CPU_UPDATE:
                        deviceType = newUpdateData.getDeviceCPUTYPE();
                        if (!deviceMap.containsKey(deviceUrl)) {
                            deviceMap.put(deviceUrl, true);
                            startHttpDeviceCheckVersion(smartDevice, deviceUrl, UpdateData.CPU_UPDATE);
                        }
                        break;
                    case UpdateData.MCU_UPDATE:
                        deviceType = newUpdateData.getDeviceMCUTYPE();
                        if (!deviceMap.containsKey(deviceUrl)) {
                            deviceMap.put(deviceUrl, true);
                            startHttpDeviceCheckVersion(smartDevice, deviceUrl, UpdateData.MCU_UPDATE);
                        }
                        break;
                }

            }
        } else {
            if (!deviceMap.containsKey(deviceUrl)) {
                String deviceType = "";
                switch (mode) {
                    case UpdateData.CPU_UPDATE:
                        deviceType = newUpdateData.getDeviceCPUTYPE();
                        deviceMap.put(deviceUrl, true);
                        startHttpDeviceCheckVersion(smartDevice, deviceUrl, UpdateData.CPU_UPDATE);
                        break;
                    case UpdateData.MCU_UPDATE:
                        deviceType = newUpdateData.getDeviceMCUTYPE();
                        deviceMap.put(deviceUrl, true);
                        startHttpDeviceCheckVersion(smartDevice, deviceUrl, UpdateData.MCU_UPDATE);
                        break;
                }

            }
        }
        return false;
    }

    /**
     * 校验时间
     */
    private boolean checkTime(String time) {
        if (time == null && time.equals("")) {
            return false;
        }
        String currentTime = TimeUtils.dateTimeToString(new Date(), "yyyy-MM-dd");
        String oldTime = time;
        if (currentTime.equals(oldTime)) {//对比是否相同
            return true;
        }
        return TimeUtils.compareDate(currentTime, oldTime);
    }

    /**
     * 校验版本
     *
     * @param updateData 设备版本
     * @return true 需要更新
     */
    private boolean checkVersion(SmartDevice smartDevice, UpdateData updateData, DeviceUpgradeInfo deviceUpgradeInfo, int mode) {
        OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询mode：" + mode);
        if (mode == 0) {
            return false;
        }
        switch (mode) {
            case UpdateData.CPU_UPDATE:
                short cpu_deviceVer = updateData.getAppVersionCPU();
                String cpu_newVer = deviceUpgradeInfo.getUpgradeVer();
                if (cpu_newVer == null && cpu_newVer.equals("")) {
                    return false;
                }
                short cpu_newVerStr = Short.valueOf(cpu_newVer).shortValue();
                OutPutMessage.LogCatInfo("版本号-----------> :", "本地保存版本号:" + cpu_newVerStr);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询cpu_deviceVer：" + cpu_deviceVer);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询cpu_newVerStr：" + cpu_newVerStr);
                if (cpu_deviceVer < cpu_newVerStr) {
                    return true;
                }
                return false;
            case UpdateData.MCU_UPDATE:
                short mcu_deviceVer = updateData.getAppVersionMCU();

                String mcu_newVer = deviceUpgradeInfo.getUpgradeVer();
                if (mcu_newVer == null && mcu_newVer.equals("")) {
                    return false;
                }

                short mcu_newVerStr = Short.valueOf(mcu_newVer).shortValue();
                OutPutMessage.LogCatInfo("版本号-----------> :", "本地保存版本号:" + mcu_newVer);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询mcu_deviceVer：" + mcu_deviceVer);
                OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "查询设备：" + smartDevice.getSmartDeviceLogic().getDeviceMac() + " -> 查询mcu_newVerStr：" + mcu_newVer);
                if (mcu_deviceVer < mcu_newVerStr) {
                    return true;
                }
                return false;

        }
        return false;
    }

//    /**
//     * 校验版本
//     *
//     * @param deviceVer 设备版本
//     * @return true 需要更新
//     */
//    private boolean checkVersion(short deviceVer, String oldVer) {
//        if (oldVer == null && oldVer.equals("")) {
//            return false;
//        }
//        short oldVerStr = Short.valueOf(oldVer).shortValue();
//        OutPutMessage.LogCatInfo("版本号-----------> :", "本地保存版本号:" + oldVer);
//        if (deviceVer < oldVerStr) {
//            return true;
//        }
//        return false;
//    }

    /**
     * 更新文件是否存在
     *
     * @return true 存在
     */
    private boolean fileIsExists(String fileName) {
        return FileIO.getShareFielIo(Controller.getContext()).fileIsExists(UPGRADED_DATA + fileName);
    }

    /**
     * 更新当前在线设备的升级标记
     *
     * @param deviceChip
     * @param deviceType
     */
    public void updateSmartDeviceUpgradeState(int deviceChip, String deviceType, int deviceNewAppNum , String mode,short newver) {
        switch (mode){
            case "1":
                List<SmartDevice> smartDeviceCpu= SmartDeviceManage.defaultManager().findDeviceList();
                for (int i = 0; i < smartDeviceCpu.size(); i++) {
                    SmartDevice smartDevice = smartDeviceCpu.get(i);
                    UpdateData updateData = smartDevice.getSmartDeviceLogic().getUpdateData();
                    int newTpye = Integer.parseInt(deviceType,16);
                    int cpuTpye = updateData.getDeviceUpdateType();


                    short deviceVer = updateData.getAppVersionCPU();
                    OutPutMessage.LogCatInfo("zzz升级  软件升级",smartDevice.getSmartDeviceLogic().getDeviceMac()+"设备版本号："+deviceVer+" 最新版本号："+newver);
                    if (deviceVer != 0 && deviceVer < newver && smartDevice.getSmartDeviceLogic().getUpdateData().getDeviceChipType() == deviceChip) {
                        OutPutMessage.LogCatInfo("zzz升级  软件升级",smartDevice.getSmartDeviceLogic().getDeviceMac()+"  标记为软件升级");
                        OutPutMessage.LogCatInfo("CPU升级","添加升级标记3");
                        smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.UPGRADE_CPU);
                        updateData.setDeviceCPUNewVer(ByteUtil.getDeviceAppNum((short) deviceNewAppNum));
                    }
                    OutPutMessage.LogCatInfo("升级管理",smartDevice.getSmartDeviceLogic().getDeviceMac()+"   cpu-->" + cpuTpye + "!!!!newType--->" + newTpye);
                }
                break;
            case "2":
                List<SmartDevice> smartDeviceMcu = SmartDeviceManage.defaultManager().findDeviceList();
                for (int i = 0; i < smartDeviceMcu.size(); i++) {
                    SmartDevice smartDevice = smartDeviceMcu.get(i);
                    UpdateData updateData = smartDevice.getSmartDeviceLogic().getUpdateData();
                    int newTpye = Integer.parseInt(deviceType,16);
                    int mcuTpye = updateData.getDeviceUpdateType();
                    short deviceVer = updateData.getAppVersionMCU();

                    if (mcuTpye == newTpye  && smartDevice.getSmartDeviceLogic().getUpdateData().getDeviceChipType() == deviceChip) {
                        OutPutMessage.LogCatInfo("zzz升级  软件升级",smartDevice.getSmartDeviceLogic().getDeviceMac()+"设备版本号："+deviceVer+" 最新版本号："+newver);
                        if (deviceVer != 0 && deviceVer < newver) {
                            OutPutMessage.LogCatInfo("zzz升级", smartDevice.getSmartDeviceLogic().getDeviceMac() + "  标记为固件升级");
                            smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.UPGRADE_MCU);
                            updateData.setDeviceMCUNewVer(ByteUtil.getDeviceAppNum((short) deviceNewAppNum));
                        }
                    }
                    OutPutMessage.LogCatInfo("升级管理",smartDevice.getSmartDeviceLogic().getDeviceMac()+"   MCU--->" + mcuTpye + "!!!!newType--->" + newTpye);
                }
                break;
        }


    }




    /**
     * Http更新是否升级信息
     *
     * @param rUpgradeInfo
     */
    public void updateDeviceInfo(final RUpgradeInfo rUpgradeInfo) {
        OutPutMessage.LogCatInfo("升级管理","RUpgradeInfo--->" + rUpgradeInfo);
        if (rUpgradeInfo != null) {

            short deviceChip = 0;
            //当前硬件的版本号
            short deviceAppNum = 0;

            String mac = rUpgradeInfo.getMac();
            if (StringUtil.isEmpty(mac)){
                return;
            }
            String mode = rUpgradeInfo.getMode();
            if (StringUtil.isEmpty(mac)){
                return;
            }

            SmartDevice smartDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(mac);



            UpdateData newUpdateData = smartDevice.getSmartDeviceLogic().getUpdateData();
            deviceChip = newUpdateData.getDeviceChipType();
            switch (mode){
                case "1":
                    deviceAppNum = newUpdateData.getAppVersionCPU();
                    break;
                case "2":
                    deviceAppNum = newUpdateData.getAppVersionMCU();
                    break;

            }
            //设备URL
            String  deviceCheckUrl =   rUpgradeInfo.getDevType();


            DeviceUpgradeInfo deviceUpgradeInfo = new DeviceUpgradeInfo();
            List<DeviceUpgradeInfo> deviceUpgradeList = DataSupport.where("upgradeUrl = ?", deviceCheckUrl).find(DeviceUpgradeInfo.class);
            if (deviceUpgradeList.size() == 0) {
                deviceUpgradeInfo.setUpgradeUrl(deviceCheckUrl);
                deviceUpgradeInfo.setUpgradeDate(TimeUtils.dateTimeToString(new Date(), "yyyy-MM-dd"));
                deviceUpgradeInfo.setUpgradeType(Integer.toHexString(newUpdateData.getDeviceUpdateType()));
                deviceUpgradeInfo.setUpgradeVer(rUpgradeInfo.getCurVer());
                deviceUpgradeInfo.setFileName(rUpgradeInfo.getFileName());
                deviceUpgradeInfo.save();
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("upgradeVer", rUpgradeInfo.getCurVer());
                contentValues.put("upgradeDate", TimeUtils.dateTimeToString(new Date(), "yyyy-MM-dd"));
                contentValues.put("upgradeType", Integer.toHexString(newUpdateData.getDeviceUpdateType()));
                contentValues.put("fileName", rUpgradeInfo.getFileName());
                DataSupport.updateAll(DeviceUpgradeInfo.class, contentValues, "upgradeUrl = ?", deviceCheckUrl);
            }

           // 更新文件是否存在,如果不存在则直接下载
            boolean isExists = fileIsExists(rUpgradeInfo.getFileName());
            final short newVer = Short.valueOf(rUpgradeInfo.getCurVer()).shortValue();
            OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "下载数据---->isExists-->" + isExists + "!!!newVer + " + newVer + "!!!!deviceAppNum" + deviceAppNum);

            OutPutMessage.LogCatInfo("zzz升级",smartDevice.getSmartDeviceLogic().getDeviceMac()+"   "+deviceAppNum+"   "+newVer);
//            if (deviceAppNum < newVer) {
                final RUpgradeInfo upgradeInfo = rUpgradeInfo;
                final short chip  = deviceChip;
                if (!isExists) {//如果不存在

                    /**
                     * 下载固件
                     *
                     * @return
                     */
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                URL urlStr = new URL(upgradeInfo.getUrl());
                                HttpURLConnection conn = (HttpURLConnection) urlStr.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setConnectTimeout(5000);
                                if (conn.getResponseCode() == 200) {
                                    InputStream inStream = conn.getInputStream();
                                    int count = conn.getContentLength();
                                    byte[] b = new byte[count];
                                    byte[] buffer = new byte[102400];
                                    int read = 0;
                                    int number = 0;
                                    while ((read = inStream.read(buffer)) != -1) {
                                        System.arraycopy(buffer, 0, b, number, read);
                                        number += read;
                                    }
                                    //升级文件写到本地
                                    FileIO.getShareFielIo(Controller.getContext()).writeFile(b, UPGRADED_DATA + upgradeInfo.getFileName(), false);
                                    OutPutMessage.LogCatInfo(UPDATE_DEVICE_TAG, "下载固件完成");
                                    short endVer = Short.valueOf(upgradeInfo.getCurVer()).shortValue();
                                    String dy[] = rUpgradeInfo.getDevType().split("_");
                                    if(dy.length == 2){
                                        updateSmartDeviceUpgradeState(chip, dy[0] , endVer , upgradeInfo.getMode(),newVer);
                                    }

                                }
                            } catch (ProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } else {//如果存在则直接更新本地标记
                    String dy[] =  rUpgradeInfo.getDevType().split("_");
                    if(dy.length == 2){
                        updateSmartDeviceUpgradeState(deviceChip, dy[0] , newVer , mode,newVer);
                    }

                }
            }
//        }



    }










}