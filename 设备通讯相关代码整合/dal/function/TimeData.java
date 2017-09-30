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
public class TimeData {

    public final static String TIME_DATA_TAG = "时间打印";

    private SmartDevice smartDevice;

    //byte 1 bit_1_3 (表示后面总共有几个定时规则)
    private static final int TIME_NUMBER = 8;

    private List<SubInsTime> subInsTimes;

    //获取一个日历对象
    private static Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    //获取日期格式器对象
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

    public TimeData(SmartDevice smartDevice) {
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

//    /**
//     * 当前定时定期存在
//     *
//     * @param subInsTime
//     * @return
//     */
//    private boolean containSubInfTimes(SubInsTime subInsTime) {
//        List<SubInsTime> subInsTimeList = getSubInsTimes();
//        for (int i = 0; i < subInsTimeList.size(); i++) {
//            SubInsTime st = subInsTimes.get(i);
//            if (st.timeId != subInsTime.timeId && !st.equals(subInsTime)) {
//                OutPutMessage.LogCatInfo(TIME_DATA_TAG, "--------111--------" + st.timeId);
//                return false;
//            }
//            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "----------------" + st.timeId);
//        }
//        return true;
//    }


    /**
     * 添加一个定时
     *
     * @param s
     * @return
     */
    public boolean addSubInfTimes(SubInsTime s) {
        s.updateTime();
        s.updateWeek();
        s.isTimeValid = true;
        timeData(s);
//        if (!containSubInfTimes(s)) {
//            return false;
//        }
        subInsTimes.set(s.timeId, s);
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "添加设备" + s.toString());
        OutPutMessage.LogCatInfo(TIME_DATA_TAG, "添加设备的集合：" + subInsTimes);
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
        subInsTime.isTimeValid = false;
        for (int i = 0; i < subInsTime.week.size(); i++) {
            subInsTime.week.set(i, false);
        }
        subInsTime.isTimeOnce = false;
        subInsTime.isTimeAction = false;
        subInsTime.timeMinute = 0;
        subInsTime.timePowerNumber = 0;
        subInsTime.timeHour = 0;
        //subInsTime.amPm = "";
        subInsTime.isRoll = false;
        subInsTime.isShow = false;
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

    /**
     * 备份定时
     *
     * @param subInsTime
     */
    public void backupSubInfTime(SubInsTime subInsTime) {
        this.oldSubInfTime = (SubInsTime) subInsTime.clone();
        Collections.copy(oldBoolean, oldSubInfTime.week);
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
                subInsTimes.set(oldSubInfTime.timeId, oldSubInfTime);
            }
        }
    }

    /**
     * 设备时间
     */
    private int deviceTime;

    //设置时区
    private int deviceTimeZone;

    /**
     * 校验返回
     *
     * @param data
     */
    public void parseCheckTimeData(byte[] data) {
        ByteBuffer timeByte = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
        timeByte.put(data, 0, data.length);
        int time = timeByte.getInt(0);
        int timeZone = timeByte.get(4);
        deviceTime = time;
        deviceTimeZone = timeZone;

    }

    public int getDeviceTime() {
        return deviceTime;
    }

    public int getDeviceTimeZone() {
        return deviceTimeZone;
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
         * 单次：byte_2_bit_7 (表示此条定时规则执行的次数；0-重复执行；1-单次执行；如果设置为1)
         */
        public boolean isTimeOnce;
        /**
         * 动作：byte_2_bit_6 1-打开开关，0-关闭开关
         */
        public boolean isTimeAction;
        /**
         * 分钟值 byte_2_bit_0_bit_5 (预约定时的分钟值，取值0～59)
         */
        public int timeMinute;
        /**
         * 开关编号：byte_3_bit_5_bit_7 (表示哪个开关执行开关动作)
         */
        public int timePowerNumber;
        /**
         * 小时值: byte_3_bit_0_bit_4 (预约定时的小时值，取值0～23)
         */
        public int timeHour;

        // public String amPm;
        //是否滚动过
        public boolean isRoll;

        //public byte[] data;

        public SubInsTime() {
            isTimeValid = false;
            week = new ArrayList();
            for (int i = 0; i < 7; i++) {
                week.add(false);
            }
            isTimeOnce = false;//默认执行1次
            isTimeAction = false;
            isRoll = false;
            timeMinute = 0;
            timePowerNumber = 0;
            timeHour = 0;
            //    data = new byte[]{0, 0, 0};
            isShow = false;
        }

        /**
         * 获取上午下午字符串
         *
         * @return
         */
//        public String toAmPmString(boolean isAdd) {
//            amPm = "";
//            if (isAdd) {
//                GregorianCalendar ca = new GregorianCalendar();
//                int i = ca.get(GregorianCalendar.AM_PM);
//                amPm = i == 0 ? "上午" : "下午";
//            } else {
//                amPm = timeHour < 12 ? "上午" : "下午";
//            }
//            return amPm;
//        }

        /**
         * 获取小时和分钟字符串
         *
         * @return
         */
//        public String toHourMinuteString() {
//            int time = timeHour;
////            if (amPm.equals("上午") && time == 0) {
////                time = 12;
////            } else if (time > 12) {
////                time -= 12;
////            }
//            dateAndTime.set(Calendar.HOUR_OF_DAY, time % 13);
//            dateAndTime.set(Calendar.MINUTE, timeMinute);
//            return simpleDateFormat.format(dateAndTime.getTime());
//        }
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
                timeHour = c.get(Calendar.HOUR_OF_DAY);
                timeMinute = c.get(Calendar.MINUTE);
            }
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前ID：" + timeId + "!!!初始化时间:" + toString());
        }

        /**
         * 更新时间
         */
        public void updateTime() {
            if (!isRoll) {
                initCurrentTime(true);
            }
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前ID：" + timeId + "更新时间:" + "!!!!" + timeHour);
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "当前ID：" + timeId + "更新时间:" + "!!!!" + timeHour);
        }

        /**
         * 更新星期
         */
        public void updateWeek() {
            int weekIndex = weekSize();
            if (weekIndex == -1) {//如果没有时间则取当天
                weekIndex = TimeUtils.dataToWeekIndex(new Date());
                week.set(weekIndex, true);
            }
        }

        private void isShow() {
            int weekIndex = weekSize();
            if (weekIndex == -1) {//如果没有时间则取当天
                isShow = false;
            } else {
                isShow = true;
            }
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "设备条件---->" + weekIndex + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

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
         * 更新星期
         */
        public void clearWeek() {
            int weekIndex = TimeUtils.dataToWeekIndex(new Date());
            for (int i = 0; i < week.size(); i++) {
                if (i == weekIndex) {
                    week.set(i, true);
                } else {
                    week.set(i, false);
                }
            }
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


//        @Override
//        public boolean equals(Object o) {
//            if (o == null || getClass() != o.getClass()) return false;
//
//            SubInsTime that = (SubInsTime) o;
////            if (timeMinute == that.timeMinute &&
////                    timeHour == that.timeHour &&
////                    (amPm != null ? amPm.equals(that.amPm) : that.amPm != null) &&
////                    !Collections.disjoint(week, that.week)) {
////                return true;
////            }
////            if (Arrays.equals(data, that.data)) {
////                return false;
////            }
//            OutPutMessage.LogCatInfo(TIME_DATA_TAG, !Collections.disjoint(week, that.week) + "");
//            return true;
//        }

        @Override
        public int hashCode() {
            int result = timeId;
            result = 31 * result + (isTimeValid ? 1 : 0);
            result = 31 * result + (week != null ? week.hashCode() : 0);
            result = 31 * result + (isTimeOnce ? 1 : 0);
            result = 31 * result + (isTimeAction ? 1 : 0);
            result = 31 * result + timeMinute;
            result = 31 * result + timePowerNumber;
            result = 31 * result + timeHour;
            result = 31 * result + (isRoll ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            return "SubInsTime{" +
                    "timeId=" + timeId +
                    ", isTimeValid=" + isTimeValid +
                    ", week=" + week +
                    ", isTimeOnce=" + isTimeOnce +
                    ", isTimeAction=" + isTimeAction +
                    ", timeMinute=" + timeMinute +
                    ", timePowerNumber=" + timePowerNumber +
                    ", timeHour=" + timeHour +
                    '}';
        }
    }

    private void timeData(SubInsTime sit) {
        ByteBuffer data = ByteBuffer.allocate(3).order(ByteOrder.LITTLE_ENDIAN);
        //byte 1
        byte byte_1 = ((byte) (sit.isTimeValid ? 0x80 : 0));
        List<Boolean> weekList = sit.week;
        for (int i = 0; i < weekList.size(); i++) {
            boolean bit_1_7 = weekList.get(i);
            if (bit_1_7) {
                byte_1 |= ((1 << i) & 0xff);
            }
        }
        data.put(byte_1);
        //byte 2
        byte byte_2 = ((byte) (sit.isTimeOnce ? 0x80 : 0));//默认为1执行单次
        byte byte_2_bit_6 = ((byte) (sit.isTimeAction ? 0x40 : 0));
        byte_2 |= (byte_2_bit_6 & 0xff);
        byte_2 |= (sit.timeMinute & 0xff);
        data.put(byte_2);
        //byte 3
        byte byte_3 = 0;
        byte_3 |= ((sit.timePowerNumber & 0xff) << 5);
        byte_3 |= (sit.timeHour & 0xff);
        data.put(byte_3);

        //  sit.data = data.array();
    }

    /**
     * 发送定时
     *
     * @return
     */
    public byte[] sendTimeData() {
        ByteBuffer data = ByteBuffer.allocate(1 + (TIME_NUMBER * 3)).order(ByteOrder.LITTLE_ENDIAN);
        data.put((byte) TIME_NUMBER);
        int index = 0;
        for (SubInsTime subInsTime : subInsTimes) {
            //byte 1
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
            //byte 2
            byte byte_2 = ((byte) (subInsTime.isTimeOnce ? 0x80 : 0));//默认为1执行单次
            byte byte_2_bit_6 = ((byte) (subInsTime.isTimeAction ? 0x40 : 0));
            byte_2 |= (byte_2_bit_6 & 0xff);
            byte_2 |= (subInsTime.timeMinute & 0xff);
            data.put(byte_2);
            //byte 3
            byte byte_3 = 0;
            byte_3 |= ((subInsTime.timePowerNumber & 0xff) << 5);
            byte_3 |= (subInsTime.timeHour & 0xff);
            data.put(byte_3);
            //   subInsTime.data = ByteBuffer.allocate(3).put(byte_1).put(byte_2).put(byte_3).array();
            subInsTime.isShow();
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "发送定时---->" + "小时：" + subInsTime.timeHour + "!!分钟：" + subInsTime.timeMinute);
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
            byte byte_2 = bb.get(1 + (i * 3));


            subInsTime.isTimeValid = ((byte_2 >> 7) & 0x01) == 1 ? true : false;
            if (subInsTime.isTimeValid) {
                timeIndex++;
            }
            for (int j = 0; j < 7; j++) {
                boolean item = (((byte_2 >> j) & 0x01) == 1) ? true : false;
                subInsTime.week.set(j, item);
            }

            byte byte_3 = bb.get(2 + (i * 3));
            subInsTime.isTimeOnce = (((byte_3 >> 7) & 0x01) == 1) ? true : false;
            subInsTime.isTimeAction = (((byte_3 >> 6) & 0x01) == 1) ? true : false;

            subInsTime.timeMinute = byte_3 & 0x3F;

            byte byte_4 = bb.get(3 + (i * 3));
            subInsTime.timePowerNumber = ((byte_4 >> 5) & 0xFF);
            subInsTime.timeHour = byte_4 & 0x1F;
            OutPutMessage.LogCatInfo(TIME_DATA_TAG, "解析定时---->"+subInsTime.isTimeValid+"  "+subInsTime.isTimeAction + "小时：" + subInsTime.timeHour + "!!分钟：" + subInsTime.timeMinute);
            for (int t=0;t<subInsTime.week.size();t++){
                OutPutMessage.LogCatInfo(TIME_DATA_TAG, "解析定时---->"+t+"    "+subInsTime.week.get(t).toString());
            }
            subInsTime.isShow();
        }

        if (timeIndex > 0) {
            smartDevice.getSmartDeviceLogic().addDeviceState(SmartDeviceConstant.State.TIMING);
        } else {
            smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.TIMING);
        }
        return SmartDeviceConstant.PARSE_OK;

    }

    /**
     * 校时
     *
     * @return
     */
    public byte[] sendCheckTimeData() {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        long l = curDate.getTime();
        int time = (int) (l / 1000);
        byte zoneByte = (byte) deviceTimeZone;
        byte[] content = ByteBuffer.allocate(4 + 1).order(ByteOrder.LITTLE_ENDIAN)
                .put(ByteUtil.intToByte(time)).put(zoneByte)
                .array();
        return content;
    }


    public enum TimeZone {
        UTC_negative_12("国际换日线", -12, "UTC-12", 1),
        UTC_negative_11("阿拉斯加诺姆时间", -11, "UTC-11", 2),
        UTC_negative_10("夏威夷标准时间", -10, "UTC-10", 3),
        UTC_negative_9("阿拉斯加标准时间", -9, "UTC-9", 4),
        UTC_negative_8("太平洋标准时间", -8, "UTC-8", 5),
        UTC_negative_7("山地标准时间", -7, "UTC-7", 6),
        UTC_negative_6("中部标准时间", -6, "UTC-6", 7),
        UTC_negative_5("东部标准时间", -5, "UTC-5", 8),
        UTC_negative_4("大西洋标准时间", -4, "UTC-4", 9),
        UTC_negative_3("巴西利亚时间", -3, "UTC-3", 10),
        UTC_negative_2("巴西费尔南多·迪诺罗尼亚岛时间", -2, "UTC-2", 10),
        UTC_negative_1("西非时间", -1, "UTC-1", 11),
        UTC("格林尼治标准时间", 0, "UTC", 12),
        UTC_positive_1("中欧时间", 1, "UTC+1", 13),
        UTC_positive_2("东欧时间", 2, "UTC+2", 14),
        UTC_positive_3("希腊地中海时间", 3, "UTC+3", 15),
        UTC_positive_4("毛里求斯时间", 4, "UTC+4", 16),
        UTC_positive_5("马尔代夫时间", 5, "UTC+5", 17),
        UTC_positive_6("哈萨克斯坦阿拉木图", 6, "UTC+6", 18),
        UTC_positive_7("澳大利亚圣诞岛时间", 7, "UTC+7", 19),
        UTC_positive_8("中国北京时间", 8, "UTC+8", 20),
        UTC_positive_9("日本标准时间", 9, "UTC+9", 21),
        UTC_positive_10("关岛标准时间", 10, "UTC+10", 22),
        UTC_positive_11("澳大利亚东部标准夏时制", 11, "UTC+11", 23),
        UTC_positive_12("新西兰标准时间", 12, "UTC+12", 24);
        private String name;
        private int value;
        private String utc;
        private int index;

        private TimeZone(String name, int value, String utc, int index) {
            this.name = name;
            this.value = value;
            this.utc = utc;
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public String getUtc() {
            return utc;
        }

        public int getIndex() {
            return index;
        }
    }
}
