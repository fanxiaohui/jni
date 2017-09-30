package com.vision.smarthomeapi.util;


import com.vision.smarthomeapi.dal.data.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lal on 2015/7/8.
 */
public class QRParse {


    public static DeviceQR parseDeviceQR(String QRString) {
        if (QRString == null || QRString.equals("")) {
            return null;
        }
        String regEx = "[a-zA-Z0-9+/=]+";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(QRString);
        if (!mat.matches()) {
            return null;
        }
        byte[] data = Base64.decode(QRString);

        if (data.length != 18) {
            return null;
        }
        OutPutMessage.LogCatInfo("扫描二维码", ByteUtil.byteArrayToHexString(data, true));

        String mac = String.format("%02X%02X%02X%02X%02X%02X%02X%02X", data[0] & 0xff, data[1] & 0xff, data[2] & 0xff, data[3] & 0xff, data[4] & 0xff, data[5] & 0xff, data[6] & 0xff, data[7] & 0xff);
        OutPutMessage.LogCatInfo("扫描二维码", "mac：" + mac);
        int deviceType = data[9] & 0xFF;
        int deviceVersion = data[8] & 0xFF;
        OutPutMessage.LogCatInfo("扫描二维码", "设备类型：" + deviceType + " 设备型号 " + deviceVersion);
        byte firm = data[10];
        OutPutMessage.LogCatInfo("扫描二维码", "厂商代码：" + firm);
        int communicationType = data[11] & 0x07;
        int netWork = 0;
        if (communicationType == 1)
            netWork = Constant.NetWork.LAN_NET;
        else if (communicationType == 2)
            netWork = Constant.NetWork.ZIG_BEE_NET;
        OutPutMessage.LogCatInfo("扫描二维码", "通讯类型：" + communicationType);


//        int netWork = 0;
//
//        if (data[13] == 1)
//            netWork = Constant.NetWork.LAN_NET;
//        else if (data[13] == 2)
//            netWork = Constant.NetWork.ZIG_BEE_NET;
//
//        OutPutMessage.LogCatInfo("扫描二维码", data[13] + "!!!!!!33333333333333");
//
//        byte firm = data[12];
//
//        OutPutMessage.LogCatInfo("扫描二维码", firm + "!!!!!!444444444444444444");
//        short batch = (short) ((data[16] << 8) | data[17]);
//        OutPutMessage.LogCatInfo("扫描二维码", batch + "!!!!!!5555555555555555");
//        short batchNum = (short) ((data[18] << 8) | data[19]);
//        OutPutMessage.LogCatInfo("扫描二维码", batchNum + "!!!!!!666666666666666");

        DeviceQR deviceQR = new DeviceQR(mac, deviceType, deviceVersion, netWork, firm);
        return deviceQR;
    }

    public static class DeviceQR {
        /**
         * mac地址
         */
        public String mac;
        /**
         * 设备类型
         */
        public int deviceType;
        /**
         * 设备型号
         */
        public int deviceVersion;
        /**
         * 通讯类型
         */
        public int communicationType;

        /**
         * 厂商代码
         */
        public byte firm;


        public DeviceQR(String mac, int deviceType, int deviceVersion, int communicationType, byte firm) {
            this.mac = mac;
            this.deviceType = deviceType;
            this.deviceVersion = deviceVersion;
            this.communicationType = communicationType;
            this.firm = firm;

        }
    }

}
