package com.vision.smarthome.dal.function;

import com.vision.smarthomeapi.util.OutPutMessage;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 自动模式参数
 * Created by yangle on 2016/12/5.
 */
public class ParameterData {

    /**
     * 在自动模式下此时间段设置是否有效
     * 0-无效，1-有效。
     */
    public boolean isSetParameter;
    /**
     * 小时值
     * 取值0～23。
     */
    public int hour;
    /**
     * 分钟值
     * 取值0～59。
     */
    public int minute;
    /**
     * 室温
     * 室温控制温度数值，取值15～30摄氏度。
     */
    public int roomTemp;
    /**
     * 回水温度
     * 回水温度控制数值，取值35～65摄氏度。
     */
    public int backwaterTemp;

    /**
     * 默认
     */
    public ParameterData(){
        this.isSetParameter = false;
        this.hour = 0;
        this.minute = 0;
        this.roomTemp = 0;
        this.backwaterTemp = 0;

    }
    public ParameterData(boolean isSetParameter, int hour, int minute, int roomTemp, int backwaterTemp) {
        this.isSetParameter = isSetParameter;
        this.hour = hour;
        this.minute = minute;
        this.roomTemp = roomTemp;
        this.backwaterTemp = backwaterTemp;
    }

    public byte[] getParameter() {
        byte hourByte = 0;
        hourByte |= (isSetParameter ? 1 : 0) << 7;
        hourByte |= hour;

        ByteBuffer byteBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.put(hourByte);
        byteBuffer.put((byte) minute);
        byteBuffer.put((byte)roomTemp);
        byteBuffer.put((byte)backwaterTemp);

        return byteBuffer.array();
    }

    public boolean setParameter(byte[] date){
        if (date.length != 4 ){
            return false;
        }
        isSetParameter = (date[0] << 7)== 1 ? true : false;
        hour = date[0] & 0x1F;
        minute = date[1] & 0x3F;
        roomTemp = date[2];
        backwaterTemp = date[3];

        OutPutMessage.LogCatInfo("电暖炉","读取参数："+isSetParameter+"  小时： "+hour+"  分钟： "+minute+"  室温： "+roomTemp+"  回水温度： "+backwaterTemp);
        return true;

    }

    public String getMinute(){
        if (minute<10){
            return "0"+minute;
        }
        return minute+"";
    }

    public String getHour(){
        if (hour<10){
            return "0"+hour;
        }
        return hour+"";
    }
}
