package com.vision.smarthomeapi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhanglong on 2015/10/13.
 */
public class TimeUtils {

    /**
     * 时间转换成字符串,默认为"yyyy-MM-dd HH:mm:ss"
     *
     * @param date 当前日期
     */
    public static String dateTimeToString(Date date) {
        return dateTimeToString(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 时间转换成字符串,指定格式
     *
     * @param date 当前日期
     */
    public static String dateTimeToString(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 时间转换成字符串,指定格式
     */
    public static String dateTimeToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = new Date(time * 1000);
        String string = sdf.format(dt);
        return string;
    }

    /**
     * 计算持续时间
     *
     * @param time
     * @param startTime
     * @param endTime
     */
    public static void date(long time, String startTime, String endTime) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;try {
        //获得两个时间的毫秒时间差异
        long diff = 0;
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long day = diff / nd;//计算差多少天
        long hour = diff % nd / nh;//计算差多少小时
        long min = diff % nd % nh / nm;//计算差多少分钟
        long sec = diff % nd % nh % nm / ns;//计算差多少秒//输出结果
        OutPutMessage.LogCatInfo("时间","时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
    }


    /**
     * \
     * 对比2个时间大小
     *
     * @param newTime 当前时间
     * @param oldTime 旧时间
     * @return true 当前时间大, false 旧时间大
     */
    public static boolean compareDate(String newTime, String oldTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(newTime);
            Date dt2 = df.parse(oldTime);
            if (dt1.getTime() > dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    /**
     * 0 为 星期日
     * 6 为 星期六
     *
     * @param dt
     * @return
     */
    public static int dataToWeekIndex(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
            week = 0;
        }
        return week;
    }


    /**
     * 时间转时间戳
     * @return
     */
    public static int time2Timestamp(String time) throws ParseException {

        if (time == null){
            return 0;
        }
        OutPutMessage.LogCatInfo("定时时间",time+"");
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");
        Date date=simpleDateFormat.parse(time);
        int timeStemp = (int)(date.getTime()/1000);

        OutPutMessage.LogCatInfo("定时时间戳",timeStemp+"");

        return timeStemp;
    }

    /**
     * 时间戳转换成日期格式字符串
     * @param seconds 精确到秒的字符串

     * @return
     */
    public static String timeStamp2Time(String seconds) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        String format = "yyyy-M-d";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds+"000")));
    }


    /**
     * 输入时间戳变星期
     *
     * @param time
     * @return
     */
    public static int changeweek(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        Date date = null;
        int mydate = 0;
        String week = null;
        try {
            date = sdr.parse(times);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            mydate = cd.get(Calendar.DAY_OF_WEEK);
            // 获取指定日期转换成星期几
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (mydate == 1) {
            week = "星期日";
        } else if (mydate == 2) {
            week = "星期一";
        } else if (mydate == 3) {
            week = "星期二";
        } else if (mydate == 4) {
            week = "星期三";
        } else if (mydate == 5) {
            week = "星期四";
        } else if (mydate == 6) {
            week = "星期五";
        } else if (mydate == 7) {
            week = "星期六";
        }

        return mydate;

    }


}
