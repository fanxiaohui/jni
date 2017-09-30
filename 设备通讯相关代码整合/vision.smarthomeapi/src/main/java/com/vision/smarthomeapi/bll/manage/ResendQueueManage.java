package com.vision.smarthomeapi.bll.manage;



import com.vision.smarthomeapi.dal.DeviceSendHeadData;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.data.SmartDeviceState;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.PhoneUtil;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * 重发队列
 */
public class ResendQueueManage {
    private static String RESEND_TAG = "重发队列";
    private volatile static ResendQueueManage mQueue;
    private QueueLoop mThread;
    private Map<String, ConcurrentHashMap<String, ResendMessage>> resendCache;

    /**
     * 单例模式
     *
     * @return
     */
    public static ResendQueueManage defaultQueue() {
        if (null == mQueue) {
            synchronized (ResendQueueManage.class) {
                if (null == mQueue) {
                    mQueue = new ResendQueueManage();
                }
            }
        }
        return mQueue;
    }

    /**
     * 重发状态
     *
     * @deprecated
     */
    public enum ResendStatus {
        /**
         * 第一次重发
         **/
        STATUS_FIRST,
        /**
         * 重发进行中
         **/
        STATUS_RESENDING,
        /**
         * 重发完成
         **/
        STATUS_FINISH,
    }

    private ResendQueueManage() {
        resendCache = new ConcurrentHashMap();
        mThread = new QueueLoop();
        mThread.start();

    }

    /**
     * 放入重发消息
     *
     * @param entityData 发送数据模型
     * @param total      总重发次数
     * @return 是否成功
     */
    public int add(DeviceSendHeadData entityData, int total) {
        if (entityData == null || "".equals(entityData.getMac())) {
            return -1;
        }
        ResendMessage qm = new ResendMessage();
        qm.resendData = entityData;
        qm.resendTime = System.currentTimeMillis() / 1000;
        qm.resendStatus = ResendStatus.STATUS_FIRST;
        qm.resendDadaSeuq = entityData.getDadaSeuq() & 0xff;
        qm.resendTotal = total;
        qm.resendCyclesTime = resendSeconds(entityData.getNetWork(), entityData.getMsgID());
//        qm.childMac = entityData.childMac;
        qm.mac = entityData.getMac();
        qm.msgID = entityData.getMsgID();

        ConcurrentHashMap<String, ResendMessage> resendMap = resendCache.get(entityData.getMac());
        if (resendMap == null) {
            resendMap = new ConcurrentHashMap();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(entityData.getMsgID());
        ResendMessage _qm = resendMap.get(sb.toString());
        if (_qm != null) {
            int oldDataSeuq = _qm.resendDadaSeuq & 0xff;
            int newDataSeuq = qm.resendDadaSeuq & 0xff;

            if (oldDataSeuq < newDataSeuq) {
                resendMap.put(sb.toString(), qm);
            }
        } else {
            resendMap.put(sb.toString(), qm);
        }
        resendCache.put(entityData.getMac(), resendMap);
        OutPutMessage.LogCatInfo(RESEND_TAG, "添加数据：" +sb.toString()+"   "+ qm.toString());
        return 0;
    }

    /**
     * 根据网络获取不同重发时间
     *
     * @param netWork 网络类型
     * @return
     */
    private int resendSeconds(int netWork, int msgID) {

        int headTimeSECONDS = 3;//默认3秒,2秒太短
        if (netWork == Constant.NetWork.LAN_NET) {
            if (msgID == SmartDeviceConstant.MsgID.DEVICE_UPDATE_INFO) {
                headTimeSECONDS = 15;
            } else if (msgID == SmartDeviceConstant.MsgID.DEVICE_UPDATE_DATA) {
                headTimeSECONDS = 5;
            } else {
                headTimeSECONDS = 3;
            }
        } else if (netWork == Constant.NetWork.WAN_NET) {
            headTimeSECONDS = 3;
        } else if (netWork == Constant.NetWork.ZIG_BEE_NET) {
            headTimeSECONDS = 5;
        }
        OutPutMessage.LogCatInfo(RESEND_TAG,"消息ID:" + Integer.toHexString(msgID) + "!!时间" + headTimeSECONDS + "!! 网络标记："  + netWork);
        return headTimeSECONDS;
    }

    /**
     * 移除需要重发的消息
     *
     * @param dadaSeuq 数据包序号
     * @param mac      设备mac
     * @param msgID    消息id
     */
    public void remove(int dadaSeuq, String mac, int msgID) {
        OutPutMessage.LogCatInfo(RESEND_TAG, "删除数据：--->"+mac+"   "+msgID);
        ConcurrentHashMap<String, ResendMessage> removeMap = resendCache.get(mac);
        if (removeMap != null) {
            ResendMessage qm = removeMap.get("" + msgID);
            if (qm != null) {
                int oldMsgID = qm.resendDadaSeuq & 0xff;
                if (oldMsgID <= dadaSeuq) {
                    removeMap.remove("" + msgID);
                    if (removeMap.isEmpty()) {
                        if (resendCache.containsKey(mac)) {
                            resendCache.remove(mac);
                        }
                    }
                }
            }
            OutPutMessage.LogCatInfo(RESEND_TAG, "删除数据：--->" + "!dadaSeuq!" + dadaSeuq + "!mac!" + mac + "!msgID!" + Integer.toHexString(msgID) + "!长度!" + resendCache.size());
        }
    }
    public void remove(String mac, int msgID) {
        ConcurrentHashMap<String, ResendMessage> removeMap = resendCache.get(mac);
        if (removeMap != null) {
            ResendMessage qm = removeMap.get("" + msgID);
            if (qm != null) {

                    removeMap.remove("" + msgID);
                    if (removeMap.isEmpty()) {
                        if (resendCache.containsKey(mac)) {
                            resendCache.remove(mac);
                        }
                    }

            }
            OutPutMessage.LogCatInfo(RESEND_TAG, "删除数据：--->" + "!dadaSeuq!" + "!mac!" + mac + "!msgID!" + Integer.toHexString(msgID) + "!长度!" + resendCache.size());
        }
    }


    public boolean isResending(String mac, int msgID) {
        ConcurrentHashMap<String, ResendMessage> removeMap = resendCache.get(mac);
        if (removeMap != null) {
            ResendMessage qm = removeMap.get("" + msgID);
            if (qm != null) {

             return true;

            }
            OutPutMessage.LogCatInfo(RESEND_TAG, "查询重发数据：--->" + "!dadaSeuq!" + "!mac!" + mac + "!msgID!" + Integer.toHexString(msgID) + "!长度!" + resendCache.size());
        }
        return false;
    }
    /**
     * 移除重发设备
     *
     * @param mac 设备mac
     */
    public void remove(String mac) {
        if (resendCache != null && resendCache.containsKey(mac)) {
            resendCache.remove(mac);
        }
    }

    /**
     * 清空
     */
    public void removeAll(){
        if (resendCache != null){
            resendCache = new ConcurrentHashMap();
        }
    }

    /**

     */
    class QueueLoop extends Thread {
        @Override
        public void run() {

            while (true) {

                synchronized (resendCache) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (resendCache.size() == 0) {
                        continue;
                    }
                    if (resendCache.size() > 0) {
                        Iterator<Map.Entry<String, ConcurrentHashMap<String, ResendMessage>>> getMacKey = resendCache.entrySet().iterator();
                        while (getMacKey.hasNext()) {
                            Map.Entry<String, ConcurrentHashMap<String, ResendMessage>> macEntry = getMacKey.next();
                            String mac = macEntry.getKey();
                            ConcurrentHashMap msgIDMap = macEntry.getValue();
                            if (mac == null) {
                                continue;
                            } else if (msgIDMap.size() == 0) {
                                continue;
                            }
                            boolean isFinish = false;
                            SmartDevice smartDevice = SmartDeviceManage.defaultManager().getDeviceHashMap().get(mac);
                            Iterator<Map.Entry<String, ResendMessage>> getMsgIDKey = msgIDMap.entrySet().iterator();

                            while (getMsgIDKey.hasNext()) {
                                Map.Entry<String, ResendMessage> msgIDEntry = getMsgIDKey.next();
                                String key = msgIDEntry.getKey();
                                ResendMessage qm = msgIDEntry.getValue();
                                if (qm == null) {
                                    continue;
                                }
                                short msgID = Short.valueOf(key).shortValue();
                                long _time = System.currentTimeMillis() / 1000;
                                qm.resendSmartDevice = smartDevice;
                                if (_time - qm.resendTime >= qm.resendCyclesTime) {
                                    if (qm.resendCount == 0 && qm.resendStatus == ResendStatus.STATUS_FIRST) {//第一次发送
                                        qm.resendStatus = ResendStatus.STATUS_RESENDING;
                                        OutPutMessage.LogCatInfo(RESEND_TAG, "重发第一次：" + (_time - qm.resendTime) + "!!!!!!" + qm.toString());
                                        resendDeviceData(qm, _time, msgID);
                                    } else if (qm.resendCount < qm.resendTotal && qm.resendStatus == ResendStatus.STATUS_RESENDING) {//第resendCount发送
                                        if (qm.resendCount == qm.resendTotal - 1) {
                                            qm.resendStatus = ResendStatus.STATUS_FINISH;
                                        } else {
                                            qm.resendStatus = ResendStatus.STATUS_RESENDING;
                                        }
                                        OutPutMessage.LogCatInfo(RESEND_TAG, "重发中：" + qm.toString());
                                        resendDeviceData(qm, _time, msgID);
                                    } else if (qm.resendCount == qm.resendTotal && qm.resendStatus == ResendStatus.STATUS_FINISH) {//重发完成
//                                    FileOperations.defaultManager().addFileHolder(new FileOperations.FileHolder(qm.resendSmartDevice.getSmartDeviceLogic().getDeviceMac(),
//                                            qm.resendSmartDevice.getSmartDeviceLogic().getDeviceName(),
//                                            System.currentTimeMillis(), "网络标记->" + StringUtil.netWorkToString(qm.resendSmartDevice) + "->" + "mac:" + qm.resendSmartDevice.getSmartDeviceLogic().getDeviceMac() + "->重发"));
                                        resendOverHandle(qm.resendSmartDevice, msgID);
                                        if (msgID == SmartDeviceConstant.MsgID.DEVICE_CONTROL_CHANGE){
                                            OutPutMessage.showToast("设备控制失败");
                                        }
                                        isFinish = true;
                                        OutPutMessage.LogCatInfo(RESEND_TAG, "重发最后一次：" + qm.toString());
                                        clearObject(qm.resendData);

                                    }
                                }
                            }
                            if (isFinish) {//如果重发最后一次不回复则删除
                                getMacKey.remove();
                                isFinish = false;
                            }
                        }
                    }

                }
            }
        }
    }

        /**
         * 查找设备,并重发数据
         *
         * @param qm    重发对象
         * @param _time 时间
         * @param msgID 消息ID
         */
        private void resendDeviceData(ResendMessage qm, long _time, short msgID) {
            qm.resendTime = _time;
            qm.resendCount++;
            if (qm.resendSmartDevice != null) {
                if (msgID == SmartDeviceConstant.MsgID.LAN_RETRIEVE) {
                    qm.resendSmartDevice.getSmartDeviceLogic().sendControllerByte(msgID, qm.childMac,
                            qm.resendData.isRead(), SmartDeviceConstant.OperateState.No_Resend,
                            SmartDeviceConstant.OperateState.No_ACk);
                } else {
                    if (msgID == SmartDeviceConstant.MsgID.DEVICE_LOGIN) {
                        byte[] data = qm.resendSmartDevice.getSmartDeviceLogic().sendDeviceLoginData(false);
                        qm.resendSmartDevice.getSmartDeviceLogic().sendControllerByte(msgID, data,
                                qm.resendData.isRead(), SmartDeviceConstant.OperateState.No_Resend,
                                SmartDeviceConstant.OperateState.No_ACk);
                    } else {
                        qm.resendSmartDevice.getSmartDeviceLogic().sendControllerByte(msgID, qm.resendData.getData(),
                                qm.resendData.isRead(), SmartDeviceConstant.OperateState.No_Resend,
                                SmartDeviceConstant.OperateState.No_ACk);
                    }
                }
            }
        }

        /**
         * 清理掉NetWork
         *
         * @param deviceSendHeadData 要被清理对象
         */
        private void clearObject(DeviceSendHeadData deviceSendHeadData) {
            WeakReference weakReference = new WeakReference(deviceSendHeadData);
            weakReference.clear();
        }

        /**
         * 处理超时
         *
         * @param smartDevice 重发设备
         * @param msgID       消息ID
         */
        private void resendOverHandle(SmartDevice smartDevice, int msgID) {
            if (smartDevice != null) {
                SmartDeviceState smartDeviceState = smartDevice.getSmartDeviceLogic().getSmartDeviceState();
                switch (msgID) {
                    case SmartDeviceConstant.MsgID.DEVICE_LOGIN://登录未回复，超时
                        smartDeviceState.deviceEvent(smartDevice, SmartDeviceConstant.EventStatus.LOGIN_TIME_OUT);
                        break;
                }
            }
        }

        /**
         * 重发实体
         */
        public class ResendMessage {
            /**
             * 重发对象
             **/
            public DeviceSendHeadData resendData;
            /**
             * 重发时间
             **/
            public long resendTime;
            /**
             * 重发状态
             **/
            public ResendStatus resendStatus;
            /**
             * 重发数据包号
             **/
            public int resendDadaSeuq;
            /**
             * 重发次数
             **/
            public int resendCount;

            /**
             * 重发总次数
             */
            public int resendTotal;

            /**
             * 重发间隔时间
             */
            public int resendCyclesTime;

            public long childMac;
            /**
             * 重发设备
             */
            public SmartDevice resendSmartDevice;

            public String mac;

            public int msgID;

            @Override
            public String toString() {
                return "ResendMessage{" +
                        ", mac=" + "重发设备mac地址:" + mac +
                        ", msgID=" + Integer.toHexString(msgID) +
                        ", resendTime=" + resendTime +
                        ", resendStatus=" + resendStatus +
                        ", resendDadaSeuq=" + resendDadaSeuq +
                        ", resendCount=" + resendCount +
                        ", resendTotal=" + resendTotal +
                        ", resendCyclesTime=" + resendCyclesTime +
                        '}';
            }
        }
    }
