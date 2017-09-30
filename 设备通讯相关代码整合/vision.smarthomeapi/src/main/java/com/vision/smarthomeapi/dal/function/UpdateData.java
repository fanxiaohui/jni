package com.vision.smarthomeapi.dal.function;


import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.bll.manage.DeviceUpgradeManage;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.impl.IDeviceNetMode;
import com.vision.smarthomeapi.dal.sql.DeviceUpgradeInfo;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.FileIO;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 设备升级
 */
public class UpdateData {
    //升级数据MD5值
    private byte[] updateMd5;
    //升级数据长度
    private int updateDataLength;
    //支持的芯片型号
    private short updateChipType;
    //Pcb板子版本号
    private byte updatePCB;
    //升级文件类型
    private byte updateType;
    //文件版本号
    private short updateVersions;
    //升级设备类型
    private int updateDeviceType;
    //保留
    private short updateCRC16;
    //块号
    private short blockNumber;
    //块长度
    private short blockLength;
    //块数据
    private byte[] mByte;
    //文件名称
    private String CPUFileName;
    //单片机升级文件
    private String MCUFileName;
    //下载文件路径
    private String url;
    //路径
    private static final String UPGRADED_DATA = "/upgradeData/";

    private SmartDevice smartDevice;
    //芯片型号
    private short deviceChipType;


    private String deviceCPUNewVer;

    private OnDeviceUpdateStatus status;

    private Timer timer;

    private TimeOutTimerTask timeOutTimerTask;

    private boolean isStop;


    //单片机版本
    private String deviceMCUNewVer;

    //请求升级文件
    private boolean deviceUpdateMode;
    //应用软件版本号
    private short appVersionCPU;
    private short appVersionMCU;
    //当前升级模式
    private int mode;
    //升级单片机 比如： 6D010101
    private String deviceMCUTYPE;
    //升级芯片   比如： 6D01FFFF
    private String deviceCPUTYPE;

    public final static int CPU_UPDATE = 1;
    public final static int MCU_UPDATE = 2;

   // private Map<Integer, Boolean> dataContainsNum;

    private int deviceUpdateType;


    public UpdateData(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
        this.isStop = false;
       // dataContainsNum = new HashMap<>();

    }

    public void isStop() {
        this.isStop  = true;

    }
    public void isStart() {
        this.isStop  = false;
    }


    /**
     * 注册升级事件，并发送升级请求
     *
     * @param status
     */
    public boolean setOnDeviceUpdateStatus(OnDeviceUpdateStatus status,int mode) {
        this.status = status;
        this.mode = mode;
        return sendData(mode);
    }

    private boolean sendData(int mode) {
        if (mode == 0) {
            return false;
        }
        if (initFileDataByte(mode) > 0) {
            if (timer == null) {
                timer = new Timer();
                timeOutTimerTask = new TimeOutTimerTask();
                timer.schedule(timeOutTimerTask, 30 * 1000);
            }
            smartDevice.getSmartDeviceLogic().sendDeviceRequestUpgrade();//发送升级请求
            return true;
        } else {
            status.upgradeResult(UpdateStatus.ERROR_FILE, smartDevice);
        }//初始化本地数据
        return false;
    }

    /**
     * 获取升级文件数据
     *
     * @return
     */
    private int initFileDataByte(int mode) {
        byte _data[] = null;
        switch (mode) {
            case CPU_UPDATE:
                try {
                    String deviceUrl = deviceCPUTYPE + "_" + deviceChipType;
                    List<DeviceUpgradeInfo> deviceUpgradeList = DataSupport.where("upgradeUrl = ?", deviceUrl).find(DeviceUpgradeInfo.class);
                    if (deviceUpgradeList.size() > 0) {
                        CPUFileName = deviceUpgradeList.get(0).getFileName();
                        _data = FileIO.getShareFielIo(Controller.getContext()).readFileToByteArray(UPGRADED_DATA + CPUFileName);
                    } else {
                        status.upgradeResult(UpdateStatus.ERROR_FILE, smartDevice);
                        return 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case MCU_UPDATE:
                try {
                    String deviceUrl =  deviceMCUTYPE + "_" + deviceChipType;
                    List<DeviceUpgradeInfo> deviceUpgradeList = DataSupport.where("upgradeUrl = ?", deviceUrl).find(DeviceUpgradeInfo.class);
                    if (deviceUpgradeList.size() > 0) {
                        MCUFileName = deviceUpgradeList.get(0).getFileName();
                        _data = FileIO.getShareFielIo(Controller.getContext()).readFileToByteArray(UPGRADED_DATA + MCUFileName);
                    } else {
                        status.upgradeResult(UpdateStatus.ERROR_FILE, smartDevice);
                        return 0;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        if (_data == null) {
            return 0;
        }
        head = new byte[32];
        data = new byte[_data.length - head.length];
        head = Arrays.copyOf(_data, head.length);
        System.arraycopy(_data, head.length, data, 0, data.length);
        return data.length;
    }


    /**
     * 发送升级请求
     *
     * @return
     */
    public byte[] sendUpdateInfo() {
        byte[] fileHead = updateFileHeadData(16, 32);
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始发送校验数据:" + ByteUtil.byteArrayToHexString(fileHead, true));

        if (fileHead == null) {
            return new byte[0];
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(fileHead.length).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(fileHead);
        this.updateMd5 = getFileHeadMD5();

        if (  this.updateMd5 == null) {
            return new byte[0];
        }
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始发送校验数据:" + ByteUtil.byteArrayToHexString(updateMd5, true));
        //getCalculateMD5();
        this.updateDataLength = byteBuffer.getInt(0);//17-20
        this.updateChipType = byteBuffer.getShort(4);//
        this.updatePCB = byteBuffer.get(6);
        this.updateType = byteBuffer.get(7);
        this.updateVersions = byteBuffer.getShort(8);
        this.updateDeviceType = byteBuffer.getInt(10);
        this.updateCRC16 = byteBuffer.getShort(14);//31-32
        ByteBuffer mByteBuffer = ByteBuffer.allocate(16 + 4 + 2 + 1 + 1 + 2 + 4 + 2).order(ByteOrder.LITTLE_ENDIAN);
        mByteBuffer.put(updateMd5).putInt(updateDataLength)
                .putShort(updateChipType)
                .put(updatePCB).put(updateType)
                .putShort(updateVersions).putInt(updateDeviceType).putShort(updateCRC16);
        byte[] b = mByteBuffer.array();
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始发送校验数据:" + ByteUtil.byteArrayToHexString(b, true));

        return b;
    }

    public void parseUpdateInfo(byte[] data) {
        if (data.length < 4) {
            return;
        }

        if (timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
            timeOutTimerTask.cancel();
        }
        ByteBuffer mByteBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        mByteBuffer.array();
        blockNumber = mByteBuffer.getShort(0);
        length = mByteBuffer.getShort(2);
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "数据块号:--->" + blockNumber + "！数据长度:" + length);
      //  dataContainsNum.clear();
        //length = 512;
        // OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "得到的长度:" + len);
        startDeviceUpdateData();
    }

    /**
     * 开始发送数据
     */
    public void startDeviceUpdateData() {
        if (status != null) {
            OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级包号--5-->:" + data.length);
            smartDevice.getSmartDeviceLogic().pauseHeartbeatThread();//暂停心跳

            if (timer == null) {
                timer = new Timer();
                timeOutTimerTask = new TimeOutTimerTask();
                timer.schedule(timeOutTimerTask, 30 * 1000);
                smartDevice.getSmartDeviceLogic().sendDeviceRequestData();
                int len = length == 0 ? 1024 : length;
//                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始接收数据块DEVICE_UPDATE_DATA:" + blockNumber + "!!!" + (updateDataLength / len));
                int i = updateDataLength / len;
//                BigDecimal b1 = new BigDecimal(blockNumber);
//                BigDecimal b2 = new BigDecimal(i);
//                BigDecimal b = b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP);
//                pro = (int) (b.floatValue() * 100);
//                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始接收数据块DEVICE_UPDATE_DATA:" + pro);
                status.upgradeProgress(UpdateStatus.SEND_OK, 0, i, smartDevice);
            }
        }
    }

    /**
     * 解析数据
     *
     * @param data
     */
    public boolean parserUpdateData(byte[] data) {
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级包号--1-->:" + isStop);
        if(isStop){
            stopDeviceUpdateData();
            return false;
        }
        if (data.length == 2) {
            ByteBuffer mByteBuffer = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
            int i = mByteBuffer.getShort(0);
            //if (!dataContainsNum.containsKey(i)) {
             //   dataContainsNum.put(i, true);
                blockNumber = (short) i;
                //块号
                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级包号--2-->:" + i);
                sendPieceData(blockNumber);
//            } else {
//                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "数据重复:" + i);
//                OutPutMessage.showToast("数据重复:" + i);
//            }
        } else if (data.length > 2) {
            if (status != null) {
                status.upgradeResult(UpdateStatus.SUCCESS, smartDevice);//升级成功
                if (smartDevice instanceof IDeviceNetMode) {
                    IDeviceNetMode iDeviceNetMode = (IDeviceNetMode) smartDevice;
                    iDeviceNetMode.sendRestartDevice();//重启
                }

                OutPutMessage.LogCatInfo("升级列表","升级成功"+smartDevice.getSmartDeviceLogic().getDeviceMac()+"    "+mode);
                switch (mode) {
                    case CPU_UPDATE:
                        OutPutMessage.LogCatInfo("CPU升级","删除升级标记2");
                        smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.UPGRADE_CPU);
                        break;
                    case MCU_UPDATE:
                        smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.UPGRADE_MCU);
                        break;
                }
                List<SmartDevice> list1  = SmartDeviceManage.defaultManager().findUpdateDeviceList(SmartDeviceConstant.State.UPGRADE_CPU);
                List<SmartDevice> list2  =  SmartDeviceManage.defaultManager().findUpdateDeviceList(SmartDeviceConstant.State.UPGRADE_MCU);

                for (int i = 0;i<list1.size();i++){
                    OutPutMessage.LogCatInfo("升级列表","升级成功后第"+i+"个CPU  "+list1.get(i).getSmartDeviceLogic().getDeviceMac());
                }

                for (int i = 0;i<list2.size();i++){
                    OutPutMessage.LogCatInfo("升级列表","升级成功后第"+i+"个MCU  "+list2.get(i).getSmartDeviceLogic().getDeviceMac());
                }


                stopDeviceUpdateData();
            }
        }
        return SmartDeviceConstant.PARSE_OK;
    }

//    private int value = 0;
//    private int pro = 0;

    /**
     * 发送一块数据
     */
    private void sendPieceData(int pro) {
        if (status != null) {

//            float progress = () / 100.0f;//一块长度
//            value++;
//            float f = value / progress;
//            OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级包号3----->:" + f);
//            if (f > 1) {
//                pro++;
//                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级数3----->:" + pro);
//                if (pro >= 93) {
//                    pro += 2;
//                }
//                if (pro >= 99) {
//                    pro = 99;
//                }
//
//                value = 0;
//            }
            int len = length == 0 ? 1024 : length;
            status.upgradeProgress(UpdateStatus.SEND_ING, pro, updateDataLength / len, smartDevice);
            if (timer != null) {
                OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "升级包号4----->关闭超时");
                timer.cancel();
                timer.purge();
                timeOutTimerTask.cancel();
                timer = new Timer();
                timeOutTimerTask = new TimeOutTimerTask();
                timer.schedule(timeOutTimerTask, 30 * 1000);
            }
            smartDevice.getSmartDeviceLogic().sendDeviceRequestData();
        }
    }


    private class TimeOutTimerTask extends TimerTask {

        @Override
        public void run() {
            if (status != null) {
                status.upgradeResult(UpdateStatus.TIME_OUT, smartDevice);
                stopDeviceUpdateData();
            }
        }
    }

    /**
     * 升级状态
     */
    public enum UpdateStatus {
        SEND_OK,
        SEND_ING,
        SUCCESS,
        ERROR,
        ERROR_FILE,
        CHECK_ERROR,
        TIME_OUT
    }


    /**
     * 设备下载功能接口
     */
    public interface OnDeviceUpdateStatus {
        /**
         * 设备发送数据状态
         *
         * @param status 当前操作
         */
        public void upgradeProgress(UpdateStatus status, int progress, int max, SmartDevice smartDevice);

        /**
         * 设备升级结果
         *
         * @param status 升级结果
         */
        public void upgradeResult(UpdateStatus status, SmartDevice smartDevice);
    }


    /**
     * 停止升级
     */
    public void stopDeviceUpdateData() {
        OutPutMessage.LogCatInfo("升级测试", "关闭");
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            timeOutTimerTask.cancel();
            timeOutTimerTask = null;
        }
        clearData();
        smartDevice.getSmartDeviceLogic().continueHeartbeatThread();//重新开启心跳
        status = null;
    }


    /**
     * 升级出错
     */
    public void informDataError() {
        if (status != null) {
            status.upgradeResult(UpdateStatus.ERROR, smartDevice);
            stopDeviceUpdateData();
        }
    }


    /**
     * 升级校验出错
     */
    public void informCheckDataError() {
        if (status != null) {
            status.upgradeResult(UpdateStatus.CHECK_ERROR, smartDevice);
            stopDeviceUpdateData();
        }
    }

    /**
     * 清除数据
     */
    private void clearData() {
        data = null;
        head = null;
        blockNumber = 0;
        // pro = 0;
        mode = 0;
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "清理数据---->:" + blockNumber);
    }

    private int length = 0;

    /**
     * 发送数据内容
     *
     * @return
     */
    public byte[] sendUpdateData() {
        //组合数据
        if (data == null) {
            return new byte[0];
        }
        int len = length == 0 ? 1024 : length;
        if (blockNumber * len > data.length) {
            len = (blockNumber * len) - data.length;
        }
        mByte = Arrays.copyOfRange(data, blockNumber * len, blockNumber * len + len);
        blockLength = (short) mByte.length;
        ByteBuffer mByteBuffer = ByteBuffer.allocate(blockLength + 4).order(ByteOrder.LITTLE_ENDIAN);
        mByteBuffer.putShort(blockNumber).putShort(blockLength).put(mByte);
        byte[] b = mByteBuffer.array();
        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "sendUpdateData的长度:" + b.length + "!!!!" + ByteUtil.byteArrayToHexString(b, true));
        return b;
    }


    private byte[] head;
    private byte[] data;


    /**
     * 获取设备升级头信息
     *
     * @param start
     * @param length
     * @return
     */
    private byte[] updateFileHeadData(int start, int length) {
        if (head == null) {
            return null;
        }
        if (start + length > head.length) {
            length = head.length - start;
        }
        return Arrays.copyOfRange(head, start, start + length);
    }

//    public byte[] getCalculateMD5() {
//        byte[] type = MD5ToText.byteArrayMd5(data);
//        OutPutMessage.LogCatInfo(DeviceUpgradeManage.UPDATE_DEVICE_TAG, "开始发送校验数据,自己计算:" + ByteUtil.byteArrayToHexString(type, true));
//        return type;
//    }

    /**
     * 读取升级文件中的md5
     */
    private byte[] getFileHeadMD5() {
        if (head == null){
            return null;
        }
        byte[] _type = new byte[16];
        for (int i = 0; i < 16; i++) {
            _type[i] = head[i];
        }
        return (_type);
    }


    public short getDeviceChipType() {
        return deviceChipType;
    }

    public void setDeviceChipType(short deviceChipType) {
        this.deviceChipType = deviceChipType;
    }

    public String getDeviceMCUNewVer() {
        return deviceMCUNewVer;
    }

    public void setDeviceMCUNewVer(String deviceMCUNewVer) {
        this.deviceMCUNewVer = deviceMCUNewVer;
    }

    public boolean isDeviceUpdateMode() {
        return deviceUpdateMode;
    }

    public void setDeviceUpdateMode(boolean deviceUpdateMode) {
        this.deviceUpdateMode = deviceUpdateMode;
    }

    public short getAppVersionMCU() {
        return appVersionMCU;
    }

    public short getAppVersionCPU() {
        return appVersionCPU;
    }

    public String getDeviceCPUNewVer() {
        return deviceCPUNewVer;
    }

    public void setAppVersionCPU(short appVersionCPU) {
        this.appVersionCPU = appVersionCPU;
    }

    public void setDeviceCPUNewVer(String deviceCPUNewVer) {
        this.deviceCPUNewVer = deviceCPUNewVer;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }


    public void setAppVersionMCU(short appVersionMCU) {
        this.appVersionMCU = appVersionMCU;
    }

    public String getDeviceCPUTYPE() {
        return deviceCPUTYPE;
    }

    public void setDeviceCPUTYPE(String deviceCPUTYPE) {
        this.deviceCPUTYPE = deviceCPUTYPE;
    }

    public String getDeviceMCUTYPE() {
        return deviceMCUTYPE;
    }

    public void setDeviceMCUTYPE(String deviceMCUTYPE) {
        this.deviceMCUTYPE = deviceMCUTYPE;
    }

    public void setDeviceUpdateType(int deviceUpdateType) {
        this.deviceUpdateType = deviceUpdateType;
    }

    public int getDeviceUpdateType() {
        return deviceUpdateType;
    }

}
