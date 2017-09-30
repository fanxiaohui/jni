package com.vision.smarthomeapi.dal.function;

import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.util.ByteUtil;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.SocketHead;
import com.vision.smarthomeapi.util.TimeUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 告警信息显示.
 */
public class AlarmData {


    private List<Alarm> alarmErrors;


    private SmartDevice smartDevice;

    public AlarmData(SmartDevice smartDevice) {
        this.smartDevice = smartDevice;
        this.alarmErrors = new ArrayList<>();
    }

    /**
     * 解析告警数据
     *
     * @param _data
     * @return
     */
    public boolean parseAlarmData(byte[] _data) {
        //有效数据至少3个字节
        if (_data.length < (7 + 1)) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }
        SocketHead.SocketHeadData socketHeadData = SocketHead.parseSocketHead(_data);
        if (socketHeadData != null) {
            byte[] data = socketHeadData.data;
            ByteBuffer alarmByte = ByteBuffer.allocate(data.length).order(ByteOrder.LITTLE_ENDIAN);
            alarmByte.put(data, 0, data.length);
            alarmErrors.clear();

            return parseAlarmInfo(alarmByte);
        }
        return SmartDeviceConstant.PARSE_ERROR_LENGTH;
    }

    /**
     * 解析告警信息
     *
     * @param alarmByte
     */
    private boolean parseAlarmInfo(ByteBuffer alarmByte) {

        int length = alarmByte.get(0);
        OutPutMessage.LogCatInfo("告警", "回复长度" + length + "   " + ByteUtil.byteArrayToHexString(alarmByte.array(), true));
        if (alarmByte.array().length < (length * 2 + 1)) {
            return SmartDeviceConstant.PARSE_ERROR_LENGTH;
        }

        for (int i = 0; i < length; i++) {

            short alarmByteShort = alarmByte.getShort(i * 2 + 1);


            int state = (alarmByteShort >> 15) & 0x01;

            int a = alarmByteShort & 0xFFF;
            OutPutMessage.LogCatInfo("告警", "state;;;;" +  state);



            Alarm alarm = new Alarm(a, state == 1);

            alarmErrors.add(alarm);


        }
//        //存储7位告警信息
//        // (15-0.过压，15-1.过流 15-2漏电. 15-3过温，15-4气体，15-5水侵， 15-6 光线，15-7红外,15-8门磁,15-9压力)
//
//        for (int i = 0; i < AlarmError.values().length; i++) {
//            int type = value & (1 << i);
//            //得出告警信息
//            if (type != 0) {
//                AlarmError alarmError = AlarmError.values()[i];
//                alarmError.time = TimeUtils.dateTimeToString(new Date());
//                alarmErrors.add(alarmError.toString());
//            }
//        }
        return SmartDeviceConstant.PARSE_OK;
    }

    public List<Alarm> getAlarmInfo() {

        /**
         * 有效的报警信息
         */
        List<Alarm> validAlarm = new ArrayList<>();
        for (int i = 0; i < alarmErrors.size(); i++) {
            if (alarmErrors.get(i).alarmState) {
                validAlarm.add(alarmErrors.get(i));
            }
        }
        return validAlarm;
    }


    public boolean parseFaultInfo(byte[] data) {
        return SmartDeviceConstant.PARSE_OK;
    }


//    public enum AlarmError {
//        over_voltage("过压", 1),
//        over_current("过流", 1 << 1),
//        electric_leakage("漏电", 1 << 2),
//        over_temperature("过温", 1 << 3),
//        gas("气体", 1 << 4),
//        water_logging("水浸", 1 << 5),
//        ray_of_light("光线", 1 << 6),
//        infrared("红外", 1 << 7),
//        door_contact("门磁", 1 << 8),
//        pressure("压力", 1 << 9);
//
//        private String info;
//        private int value;
//        private String time;
//
//        AlarmError(String info, int value) {
//            this.info = info;
//            this.value = value;
//        }
//
//        public void setTime(String time) {
//            this.time = time;
//        }
//
//        public String getTime() {
//            return time;
//        }
//
//        public String getInfo() {
//            return info;
//        }
//
//        public int getValue() {
//            return value;
//        }
//
//        @Override
//        public String toString() {
//            StringBuilder sb = new StringBuilder();
//            sb.append(time).append("—————").append(info);
//            return sb.toString();
//        }
//    }


    public class Alarm {

        private int alarmCode;
        private boolean alarmState;
        private String alarmTime;

        public Alarm(int alarmCode, boolean alarmState) {
            this.alarmCode = alarmCode;
            this.alarmState = alarmState;
            this.alarmTime = TimeUtils.dateTimeToString(new Date());
        }

        public int getAlarmCode() {
            return alarmCode;
        }

        public void setAlarmCode(int alarmCode) {
            this.alarmCode = alarmCode;
        }

        public boolean isAlarmState() {
            return alarmState;
        }

        public void setAlarmState(boolean alarmState) {
            this.alarmState = alarmState;
        }

        public String getAlarmTime() {
            return alarmTime;
        }

        public void setAlarmTime(String alarmTime) {
            this.alarmTime = alarmTime;
        }

        public String getAlarmName() {
            switch (alarmCode) {
                case 0x001:
                    return "过压";
                case 0x002:
                    return "过流";
                case 0x003:
                    return "超功率";
                case 0x004:
                    return "漏电";
                case 0x005:
                    return "电弧";
                case 0x010:
                    return "烟雾浓度超标";
                case 0x011:
                    return "甲烷浓度超标";
                case 0x012:
                    return "一氧化碳浓度超标";
                case 0x020:
                    return "水浸检测漏水";
                case 0x030:
                    return "红外检测入侵";
                case 0x031:
                    return "门磁" + (isAlarmState() ? "开" : "关");
                case 0x040:
                    return "过温告警";
                case 0x041:
                    return "主电源AC丢失告警";
                case 0x042:
                    return "电池容量告警";
                case 0x043:
                    return "系统监测故障告警";

                default:
                    return "未定义" + alarmCode;
            }

        }
    }


}
