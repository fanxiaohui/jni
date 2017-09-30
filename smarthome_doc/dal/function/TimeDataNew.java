package com.vision.smarthome.dal.function;

import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.TimeUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by zhanglong on 2015/10/14.
 */
public class TimeDataNew {

    public final static String TIME_DATA_TAG = "时间打印";

    private SmartDevice smartDevice;

    //byte 1 bit_1_3 (表示后面总共有几个定时规则)
    private static final int TIME_NUMBER = 7;

    private List<SubInsTime> subInsTimes;

    //获取一个日历对象
    private static Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    //获取日期格式器对象
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public TimeDataNew(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
        this.subInsTimes = new ArrayList();
        for (int i = 0; i < TIME_NUMBER; i++) {
            SubInsTime subInsTime = new SubInsTime();
            subInsTime.timeId = i;
            subInsTimes.add(subInsTime);
        }
        oldBoolean = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            oldBoolean.add(false);
        }
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前内容:" + subInsTimes);
    }


    /**
     * 添加一个定时
     *
     * @param s
     * @return
     */
    public boolean addSubInfTimes(SubInsTime s) {
//        s.updateTime();
//        s.updateWeek();
//        s.isTimeValid = true;
//        timeData(s);
//        subInsTimes.set(s.timeId, s);
//        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "添加设备" + s.toString());
//        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "添加设备的集合：" + subInsTimes);
        return true;
    }

    /**
     * 删除一条定时
     *
     * @param subID
     */
    public boolean removeSubInsTimes(int subID) {
        SubInsTime subInsTime = subInsTimes.get(subID);
        if (subInsTime == null) {
            return false;
        }

        subInsTime.iniSubInsTime();
        subInsTimes.set(subID, subInsTime);
        return SmartDeviceConstant.PARSE_OK;
    }

    /**
     * 获取最新的定时
     *
     * @return
     */
    public SubInsTime getNewSubInsTime() {
        SubInsTime subInsTime = null;
        for (int i = 0; i < subInsTimes.size(); i++) {
            SubInsTime s = subInsTimes.get(i);
            if (!s.isShow) {
                subInsTime = s;
                OutPutMessage.LogCatInfo(TIME_DATA_TAG, "查找新设备:" + s.toString());
                return subInsTime;
            }
        }
        return subInsTime;
    }

    /**
     * 根据ID查找定时
     *
     * @param id
     * @return
     */
    public SubInsTime findListSubInsTime(int id) {
        SubInsTime subInsTime = null;
        for (int i = 0; i < subInsTimes.size(); i++) {
            SubInsTime s = subInsTimes.get(i);
            if (s.timeId == id) {
                subInsTime = s;
                return subInsTime;
            }
        }
        return subInsTime;
    }

    /**
     * 获取当前有效定时
     *
     * @return
     */
    public List<SubInsTime> getSubInsTimes() {
        List<SubInsTime> subInsTimeLists = new ArrayList<>();
        for (int i = 0; i < subInsTimes.size(); i++) {
            SubInsTime subInsTime = subInsTimes.get(i);
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, subInsTimes.get(i).isShow + "!!!!!");
            if (subInsTimes.get(i).isShow) {
                subInsTimeLists.add(subInsTime);
            }
        }
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, subInsTimeLists + "!!!!!");
        return subInsTimeLists;
    }



    private SubInsTime oldSubInfTime;

    private List<Boolean> oldBoolean;
    private Boolean onceTag;
    private int onceTime;

    /**
     * 备份定时
     *
     * @param subInsTime
     */
    public void backupSubInfTime(SubInsTime subInsTime) {
        this.oldSubInfTime = (SubInsTime) subInsTime.clone();
        Collections.copy(oldBoolean, oldSubInfTime.week);
        onceTag = oldSubInfTime.onceTag;
        onceTime = oldSubInfTime.onceTime;
    }


    /**
     * 定时
     *
     * @return
     */
    public void getBackupSubInfTime() {
        if (oldSubInfTime != null) {
            SubInsTime subInsTime = findListSubInsTime(oldSubInfTime.timeId);
            if (subInsTime != null) {
                Collections.copy(oldSubInfTime.week, oldBoolean);
                oldSubInfTime.onceTag = onceTag;
                oldSubInfTime.onceTime = onceTime;

                subInsTimes.set(oldSubInfTime.timeId, oldSubInfTime);
            }
        }
    }





    public static class SubInsTime implements Cloneable {

        public boolean isShow;

        public int timeId;
        /**
         * 有效：byte_1_bit_7 (0-此条定时规则无效，不执行；1-此条定时规则有效，执行)
         */
        public boolean isTimeValid;
        /**
         * 执行预约的周日～周六 ：byte_1_bit_0_bit_6
         * (每位代表一天，Bit0位代码周日，Bit1~Bit6代表周一到周六，当前位为1执行预约定时，0不执行)
         */
        public List<Boolean> week;
        /**
         * 开始动作
         */
        public boolean startAction;
        /**
         * 开始小时
         */
        public int startHour;
        /**
         * 开始分钟
         */
        public int startMinute;
        /**
         * 结束动作
         */
        public boolean endAction;
        /**
         * 结束小时
         */
        public int endHour;
        /**
         * 结束分钟
         */
        public int endMinute;

        /**
         * 时间戳标记
         */
        public boolean onceTag;

        /**
         * 单次的时间戳
         */
        public int onceTime;


        // public String amPm;
        //是否滚动过
        public boolean isRoll;

        //public byte[] data;

        public SubInsTime() {
            iniSubInsTime();
        }

        public void iniSubInsTime(){
            isTimeValid = false;
            week = new ArrayList();
            for (int i = 0; i < 7; i++) {
                week.add(false);
            }
            startAction = true;
            startHour = 0;
            startMinute = 0;
            endAction = false;
            endHour = 0;
            endMinute = 0;
            onceTag = false;
            onceTime = 0;
            isRoll = false;
            //    data = new byte[]{0, 0, 0};
            isShow = false;
        }
        private void isShow() {
            int weekIndex = weekSize();
            if (weekIndex == -1) {//如果没有时间则取当天
                isShow = false;
            } else {
                isShow = true;
            }

            if (!isShow) {
                if (onceTag) {
                    isShow = true;
                } else {
                    isShow = false;
                }
            }
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "设备条件---->" + weekIndex + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+isShow);

        }
        public int weekSize() {
            int weekIndex = -1;
            for (int i = 0; i < week.size(); i++) {
                boolean b = week.get(i);
                if (b) {
                    weekIndex++;
                }
            }
            return weekIndex;
        }


        @Override
        protected Object clone() {
            SubInsTime subInsTime = null;
            try {
                subInsTime = (SubInsTime) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return subInsTime;
        }

        public String getWeekToString() {
            if (weekSize() == 6) {
                return "每天";
            }

            boolean one = week.get(1);
            boolean two = week.get(2);
            boolean three = week.get(3);
            boolean four = week.get(4);
            boolean five = week.get(5);


            boolean zero = week.get(0);
            boolean six = week.get(6);

            if (one && two && three && four && five && !zero && !six) {
                return "工作日";
            }

            if (zero && six && !one && !two && !three && !four && !five) {
                return "周末";
            }

            return "";
        }
        /**
         * 根据新增或者更新来初始化时间
         *
         * @param isAdd 是否是新添加的设备
         *              是否是点击重复后的返回
         */
        public void initCurrentTime(boolean isAdd) {
            Calendar c = Calendar.getInstance();
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前ID：" + timeId + "!!!初始化设备:" + toString());
            if (isAdd) {
                startHour = c.get(Calendar.HOUR_OF_DAY);
                startMinute = c.get(Calendar.MINUTE);
                endHour =  c.get(Calendar.HOUR_OF_DAY);
                endMinute =  c.get(Calendar.MINUTE)+1;
                week = new ArrayList();
                for (int i = 0; i < 7; i++) {
                    week.add(false);
                }
                onceTag = false;
                onceTime = 0;


            }
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前ID：" + timeId + "!!!初始化时间:" + toString());
        }

        public String getStartTimeString(){
            String starthour = "" + startHour;
            String startmin = "" + startMinute;

            if (startHour < 10) {
                starthour = "0" + startHour;
            }
            if (startMinute < 10) {
                startmin = "0" + startMinute;
            }

            return starthour+":"+startmin;
        }

        public String getEndTimeString(){
           String endhour = ""+endHour;
            String endmin = ""+endMinute;



            if (endHour < 10) {
                endhour = "0" + endhour;
            }
            if (endMinute < 10) {
                endmin = "0" + endMinute;
            }

            return endhour+":"+endmin;
        }

        public boolean workDay(String title) {
            boolean isWork = false;
            switch (title) {
                case "每天":
                    if (weekSize() == 6) {
                        return true;
                    }
                    break;
                case "工作日":
                    boolean one = week.get(1);
                    boolean two = week.get(2);
                    boolean three = week.get(3);
                    boolean four = week.get(4);
                    boolean five = week.get(5);
                    if (one && two && three && four && five) {
                        return true;
                    }
                    break;
                case "周末":
                    boolean zero = week.get(0);
                    boolean six = week.get(6);
                    if (zero && six) {
                        return true;
                    }
                    break;
            }
            return isWork;
        }
    }



    /**
     * 发送定时
     *
     * @return
     */
    public byte[] sendTimeData() {
        ByteBuffer data = ByteBuffer.allocate(1 + (TIME_NUMBER * 9)).order(ByteOrder.LITTLE_ENDIAN);
        //定时数量
        data.put((byte) TIME_NUMBER);
        int index = 0;
        for (SubInsTime subInsTime : subInsTimes) {
            //byte 1 有效  星期
            byte byte_1 = ((byte) (subInsTime.isTimeValid ? 0x80 : 0));
            if (subInsTime.isTimeValid) {
                index++;
            }
            List<Boolean> weekList = subInsTime.week;
            for (int i = 0; i < weekList.size(); i++) {
                boolean bit_1_7 = weekList.get(i);
                if (bit_1_7) {
                    byte_1 |= ((1 << i) & 0xff);
                }
            }
            data.put(byte_1);
            //byte 2 开始的小时
            byte byte_2 = 0;
            byte_2 |= (subInsTime.startHour & 0xff);
            data.put(byte_2);
            //byte3 开始动作  开始的分钟值
            byte byte_3 = ((byte) (subInsTime.startAction ? 0xC0 : 0));//默认为1执行单次
            byte_3 |= (subInsTime.startMinute & 0xff);
            data.put(byte_3);

            //byte4 时间戳标记与结束的小时值
            byte byte_4 = ((byte) (subInsTime.onceTag ? 0x20 : 0));//默认为1执行单次
            byte_4 |= (subInsTime.endHour & 0xff);
            data.put(byte_4);

            //byte5 结束动作  结束的分钟值
            byte byte_5 = ((byte) (subInsTime.endAction ? 0xC0 : 0));//默认为1执行单次
            byte_5 |= (subInsTime.endMinute & 0xff);
            data.put(byte_5);

            //6-9 时间戳
            data.putInt(subInsTime.onceTime);

            //   subInsTime.data = ByteBuffer.allocate(3).put(byte_1).put(byte_2).put(byte_3).array();
            subInsTime.isShow();
        }
        if (index > 0) {
            smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.TIMING);
        } else if (index == 0) {
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.TIMING);
        }
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "发送定时数据长度:" + data.array().length + "!!!" + ByteUtil.byteArrayToHexString(data.array(), true));
        return data.array();
    }

    /**
     * 解析定时
     *
     * @param data
     * @return
     */
    public boolean parseTimeData(byte[] data) {
        int timeIndex = 0;
        ByteBuffer bb = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN);
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "解析定时数据长度:" + bb.array().length + "!!!" + ByteUtil.byteArrayToHexString(bb.array(), true));

        byte byte_1 = bb.get(0);//定时数量

        for (int i = 0; i < TIME_NUMBER; i++) {
            SubInsTime subInsTime = subInsTimes.get(i);
            byte byte_2 = bb.get(1 + (i * 9));


            subInsTime.isTimeValid = ((byte_2 >> 7) & 0x01) == 1 ? true : false;
            if (subInsTime.isTimeValid) {
                timeIndex++;
            }
            for (int j = 0; j < 7; j++) {
                boolean item = (((byte_2 >> j) & 0x01) == 1) ? true : false;
                subInsTime.week.set(j, item);
            }

            byte byte_3 = bb.get(2 + (i * 9));

            subInsTime.startHour = byte_3 & 0x1f;


            byte byte_4 = bb.get(3 + (i * 9));
            subInsTime.startAction = (((byte_4 >> 6) & 0x03) == 1) ? true : false;
            subInsTime.startMinute = byte_4 & 0x3F;

            byte byte_5 = bb.get(4 + (i * 9));
            subInsTime.endHour = byte_5 & 0x1f;
            subInsTime.onceTag = (((byte_5 >> 5) & 0x01) == 1) ? true : false;

            byte byte_6 = bb.get(5 + (i * 9));
            subInsTime.endAction = (((byte_6 >> 6) & 0x03) == 1) ? true : false;
            subInsTime.endMinute = byte_6 & 0x3F;


            subInsTime.onceTime = bb.getInt(6+(i*9));

            subInsTime.isShow();
        }

        if (timeIndex > 0) {
            smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.TIMING);
        } else {
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.TIMING);
        }
        return SmartDeviceConstant.PARSE_OK;

    }




}
