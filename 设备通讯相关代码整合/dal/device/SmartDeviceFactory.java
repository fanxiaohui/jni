package com.vision.smarthome.dal.device;


import android.support.annotation.IntDef;

import com.vision.smarthome.R;
import com.vision.smarthomeapi.bean.RSecurityTypeInfo;
import com.vision.smarthomeapi.bean.SecurityTypeInfo;
import com.vision.smarthomeapi.bll.manage.SmartDeviceManage;
import com.vision.smarthomeapi.dal.data.HeadData;
import com.vision.smarthomeapi.dal.data.SmartDevice;
import com.vision.smarthomeapi.dal.data.SmartDeviceConstant;
import com.vision.smarthomeapi.dal.impl.SmartInitDevice;
import com.vision.smarthomeapi.dal.sql.SmartDeviceSQL;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanglong on 2016/1/19.
 */
public class SmartDeviceFactory implements SmartInitDevice {

    private static SmartDeviceFactory control;
    private static Map<String, SecurityTypeInfo> typeMap = new HashMap<>();
    private static RSecurityTypeInfo rSecurityTypeInfo;


    public static SmartDeviceFactory defaultManager() {
        return control == null ? control = new SmartDeviceFactory() : control;
    }

    private SmartDeviceFactory() {
        SmartDeviceManage.defaultManager().setSmartInitDevice(this);
    }

    @Override
    public void smartDeviceDataInit(HeadData headData) {
        SmartDevice smartDevice = InitDeviceTypes(headData.devType, headData.devVersion);
        if (headData.wanDevice != null && smartDevice != null) {
            smartDevice.getSmartDeviceLogic().setDeviceName(SmartDeviceFactory.getTypeName(headData.devType, headData.devVersion, headData.wanDevice.getMac()));
        }
        OutPutMessage.LogCatInfo("设备回复", "类型：" + headData.devType + "!!! " + headData.devVersion + "!!" + smartDevice);
        if (smartDevice != null) {
            SmartDeviceManage.defaultManager().addInitDevice(smartDevice, headData);

            //是否包含告警
            if (smartDevice.getSmartDeviceLogic().getAlarmStatus() == 0 || smartDevice.getSmartDeviceLogic().getAlarmStatus() == 2) {
                boolean isAlarm = smartDevice.getSmartDeviceLogic().isDeviceState(SmartDeviceConstant.State.ALARM);
                if (isAlarm) {
                    smartDevice.getSmartDeviceLogic().deleteDeviceState(SmartDeviceConstant.State.ALARM);
                }
            }

        }
    }

    @Override
    public void setTypeInfo(RSecurityTypeInfo rSecurityTypeInfo) {

        this.rSecurityTypeInfo = rSecurityTypeInfo;
        for (int i = 0; i < rSecurityTypeInfo.getList().length; i++) {

            typeMap.put(rSecurityTypeInfo.getList()[i].getDevTypeId() + "-" + rSecurityTypeInfo.getList()[i].getCode(), rSecurityTypeInfo.getList()[i]);

            OutPutMessage.LogCatInfo("类型汇总",rSecurityTypeInfo.getList()[i].getId() +"   "+rSecurityTypeInfo.getList()[i].getDevName());
        }

    }

    public static String getDeviceImg(String devCode){
        String img = "";
        for (int i = 0; i < rSecurityTypeInfo.getList().length; i++) {
            if (rSecurityTypeInfo.getList()[i].getVersionCode()!= null &&rSecurityTypeInfo.getList()[i].getVersionCode().equals(devCode)){
                img = rSecurityTypeInfo.getList()[i].getDeviceImg();
            }
        }
        return img;
    }

    /**
     * 根据类型和型号获取设备类型名称
     *
     * @return String
     */
    public static String getTypeName(int type, int version, String mac) {

        String typeName = "家居安防设备";


        SecurityTypeInfo info = typeMap.get(type + "-" + version);
        if (mac == null){
            typeName = info.getDevName();
        }else {
            if (info != null) {
                typeName = info.getDevName() + StringUtil.getMacAfterFour(mac);
            }
        }
        return typeName;
    }


    /**
     * 根据类型和型号获取设备图片
     *
     * @return String
     */
    public static int getTypeImage(int type, int version) {

        int resID = R.drawable.device_image_wifi;
        switch (type) {
            case DeviceType.Portable_Socket:

                if (version == Portable_Socket_Version.Generation_portable_five_hole.getVersion()) {
                    resID = R.drawable.device_image_wifi;
                } else if (version == Portable_Socket_Version.Route_platooninsert.getVersion()) {
                } else if (version == Portable_Socket_Version.Household_safety_generation.getVersion()) {
                } else {
                }
                break;
            case DeviceType.Arcing_fault:


                if (version == Arcing_fault.Fault_arc_protector_6A_2P.getVersion()) {
                    resID = R.drawable.device_image_6a;
                } else if (version == Arcing_fault.Fault_arc_protector_10A_2P.getVersion()) {
                    resID = R.drawable.device_image_10a;
                } else if (version == Arcing_fault.Fault_arc_protector_16A_2P.getVersion()) {
                    resID = R.drawable.device_image_16a;
                } else if (version == Arcing_fault.Fault_arc_protector_20A_2P.getVersion()) {
                    resID = R.drawable.device_image_20a;
                } else if (version == Arcing_fault.Fault_arc_protector_25A_2P.getVersion()) {
                    resID = R.drawable.device_image_25a;
                } else if (version == Arcing_fault.Fault_arc_protector_32A_2P.getVersion()) {
                    resID = R.drawable.device_image_32a;
                } else if (version == Arcing_fault.Fault_arc_protector_40A_2P.getVersion()) {
                    resID = R.drawable.device_image_40a;
                } else if (version == Arcing_fault.Fault_arc_protector_50A_2P.getVersion()) {
                    resID = R.drawable.device_image_50a;
                }

//                else if (version == Arcing_fault.Fault_arc_protector_63A_2P.getVersion()) {
//                    resID = R.drawable.device_image_63a;
//                }

            case DeviceType.Wall_Socket:
                if (version == Wall_Plate_version.Wall_protection_socket_16A.getVersion()) {
                }
                break;
            case DeviceType.Harmful_gas:
                if (version == Gas_sensor_Version.Smoke_sensor_WIFI.getVersion()) {
                    resID = R.drawable.device_image_smoke_active;
                } else if (version == Gas_sensor_Version.Methane_sensor_WIFI.getVersion()) {
                    resID = R.drawable.device_image_gas_active;
                } else if (version == Gas_sensor_Version.CO_sensor_WIFI.getVersion()) {
                    resID = R.drawable.device_image_co_active;
                } else if (version == Gas_sensor_Version.CO_sensor_OEM.getVersion()) {
                    resID = R.drawable.device_image_co;
                } else if (version == Gas_sensor_Version.Methane_sensor_OEM.getVersion()) {
                    resID = R.drawable.device_image_gas;
                } else if (version == Gas_sensor_Version.Smoke_sensor_CC2630.getVersion()) {
                    resID = R.drawable.device_image_smoke;
                }
                break;
            case DeviceType.Water_sensor:
                if (version == Water_sensor_Version.OEM_leakage_sensor.getVersion()) {
                    resID = R.drawable.device_image_water;
                }
                break;

            case DeviceType.Invasion_sensor:
                if (version == Invasion_sensor_Version.Infrared_regional_intrusion_sensor.getVersion()) {

                } else if (version == Invasion_sensor_Version.Infrared_curtain_sensor.getVersion()) {

                } else if (version == Invasion_sensor_Version.Magnetic_sensor_Door_sensor.getVersion()) {
                    resID = R.drawable.device_image_lock;
                } else if (version == Invasion_sensor_Version.Video_surveillance_camera.getVersion()) {

                } else if (version == Invasion_sensor_Version.OEM_intrusion_sensor.getVersion()) {
                    resID = R.drawable.device_image_intrusion;
                } else if (version == Invasion_sensor_Version.OEM_curtain_sensor.getVersion()) {
                    resID = R.drawable.device_image_curtain;
                } else if (version == Invasion_sensor_Version.OEM_Infrared_curtain_sensor.getVersion()) {

                } else if (version == Invasion_sensor_Version.OEM_glasses_broken.getVersion()) {
                    resID = R.drawable.device_image_glasses_broken;
                } else if (version == Invasion_sensor_Version.Magnetic_sensor_Door_sensor_passive.getVersion()) {

                }
                break;
            case DeviceType.Valve_failure:
                if (version == Valve_failure.Pipe_valve_manipulator.getVersion()) {
                    resID = R.drawable.device_image_manipulator;
                } else if (version == Valve_failure.cylinder_valve_manipulator.getVersion()) {
                    resID = R.drawable.device_image_water_valve_mechanical;
                } else if (version == Valve_failure.industrial_electromagnetic_valve.getVersion()) {
                    resID = R.drawable.device_image_solenoid;
                } else if (version == Valve_failure.civil_solenoid_valve.getVersion()) {
                    resID = R.drawable.device_image_valve_manipulator;
                }
                break;
            case DeviceType.Access_gateway_products:

                if (version == Access_gateway_products.Lowpan_WIFI_access_gateway_X1.getVersion()) {
                    resID = R.drawable.device_image_gateway;
                } else if (version == Access_gateway_products.Lowpan_WIFI_access_gateway_X1.getVersion()) {
                    resID = R.drawable.device_image_gateway;
                }
                break;
            //0xA 扩展IO报警接入类产品
            case DeviceType.Expand_IO_alarm_access_products:

                if (version == Expand_IO_alarm_access_products.Expand_Alarm_Module.getVersion()) {
                    resID = R.drawable.device_image_box;
                }
                break;

            default:
                break;


        }

        return resID;
    }

    /**
     * 设备状态
     */
    public static class State {
        /**
         * 芯片升级状态
         **/
        public static final int UPGRADE_CPU = 1;//
        /**
         * 单片机升级
         **/
        public static final int UPGRADE_MCU = 1 << 1;//
        /**
         * 绑定
         **/
        public static final int BINDING = 1 << 2;//
        /**
         * 定时
         **/
        public static final int TIMING = 1 << 3;//
        /**
         * 告警
         **/
        public static final int ALARM = 1 << 4;//
        /**
         * 云平台
         **/
        public static final int LOGIN_CLOUD = 1 << 5;//
        /**
         * 501锁定位置
         **/
        public static final int DEVICE_LOCK = 1 << 6;//
        /**
         * 网关设备
         **/
        public static final int GATEWAY_DEVICES = 1 << 7;//
        /**
         * zigbee设备
         **/
        public static final int ZIG_BEE_DEVICES = 1 << 8;//

        @IntDef(flag = true,
                value = {
                        UPGRADE_CPU,
                        UPGRADE_MCU,
                        BINDING,
                        TIMING,
                        ALARM,
                        LOGIN_CLOUD,
                        DEVICE_LOCK,
                        GATEWAY_DEVICES,
                        ZIG_BEE_DEVICES,
                })
        @Retention(RetentionPolicy.SOURCE)
        public @interface DeviceState {
        }
    }


    public class DeviceType {
        // 0x01  智能家居类产品（控制类）
        public static final int Portable_Socket = 0x01;
        //0x02  用电安全监测产品--漏电保护器形式
        public static final int Arcing_fault = 0x02;
        //0x03  用电安全监测产品--墙面插座类产品
        public static final int Wall_Socket = 0x03;
        //0x04 有害气体及火灾传感器类
        public static final int Harmful_gas = 0x04;
        //0x05 水传感器产品
        public static final int Water_sensor = 0x05;
        //0x06 入侵检测产品
        public static final int Invasion_sensor = 0x06;
        //0x07 阀门控制类产品
        public static final int Valve_failure = 0x07;
        //0x08 入户管理类产品
        public static final int Household_management = 0x08;
        //0x09 接入网关类产品
        public static final int Access_gateway_products = 0x09;
        //0xA 扩展IO报警接入类产品
        public static final int Expand_IO_alarm_access_products = 0x0A;
        //0x11 电暖炉
        public static final int Electric_stove = 0x0B;
    }

    /**
     * 气体类
     *
     * @param smartDevice
     * @return
     */
    public static boolean isReadVersionGas(SmartDevice smartDevice) {
        if (smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.CO_sensor_WIFI.getVersion()
                || smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.Methane_sensor_OEM.getVersion()
                || smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.CO_sensor_OEM.getVersion()
                || smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.Methane_sensor_WIFI.getVersion()) {
            return true;
        }
        return false;
    }

    /**
     * @param smartDevice
     * @return
     */
    public static boolean isReadVersionGas(SmartDeviceSQL smartDevice) {
        if (smartDevice.getDevVersion() == Gas_sensor_Version.CO_sensor_WIFI.getVersion()
                || smartDevice.getDevVersion() == Gas_sensor_Version.Methane_sensor_OEM.getVersion()
                || smartDevice.getDevVersion() == Gas_sensor_Version.CO_sensor_OEM.getVersion()
                || smartDevice.getDevVersion() == Gas_sensor_Version.Methane_sensor_WIFI.getVersion()) {
            return true;
        }
        return false;
    }

    /**
     * 火类
     *
     * @param smartDevice
     * @return
     */
    public static boolean isReadVersionFire(SmartDevice smartDevice) {
        if (smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.Smoke_sensor_CC2630.getVersion()
                || smartDevice.getSmartDeviceLogic().getDevVersion() == Gas_sensor_Version.Smoke_sensor_WIFI.getVersion()) {
            return true;
        }
        return false;
    }

    public static boolean isReadVersionFire(SmartDeviceSQL smartDevice) {
        if (smartDevice.getDevVersion() == Gas_sensor_Version.Smoke_sensor_CC2630.getVersion()
                || smartDevice.getDevVersion() == Gas_sensor_Version.Smoke_sensor_WIFI.getVersion()) {
            return true;
        }
        return false;
    }


    /**
     * 智能家居类产品（控制类）
     */
    public enum Portable_Socket_Version {

        //wifi插座
        Generation_portable_five_hole(0x01),
        //便携排插
        Route_platooninsert(0x02),
        //安全排插
        Household_safety_generation(0x03);

        private int version;

        Portable_Socket_Version(int version) {
            this.version = version;
        }

        public static Portable_Socket_Version initPortable_Socket_Version(int ver) {
            switch (ver) {
                case 0x01:
                    return Generation_portable_five_hole;
                case 0x02:
                    return Route_platooninsert;
                case 0x03:
                    return Household_safety_generation;
            }
            return null;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }


    }

    /**
     * 用电安全监测产品--漏电保护器形式
     */
    public enum Arcing_fault {

        /**
         * 6A漏电保护器
         */
        Fault_arc_protector_6A_2P(0x01),
        /**
         * 10A漏电保护器
         */
        Fault_arc_protector_10A_2P(0x02),
        /**
         * 16A漏电保护器
         */
        Fault_arc_protector_16A_2P(0x03),
        /**
         * 20A漏电保护器
         */
        Fault_arc_protector_20A_2P(0x04),
        /**
         * 25A漏电保护器
         */
        Fault_arc_protector_25A_2P(0x05),
        /**
         * 32A漏电保护器
         */
        Fault_arc_protector_32A_2P(0x06),
        /**
         * 40A漏电保护器
         */
        Fault_arc_protector_40A_2P(0x07),
        /**
         * 50A漏电保护器
         */
        Fault_arc_protector_50A_2P(0x08);

        private int version;

        Arcing_fault(int version) {
            this.version = version;
        }

        public static Arcing_fault initArcing_fault(int ver) {
            switch (ver) {
                case 0x01:
                    return Fault_arc_protector_6A_2P;
                case 0x02:
                    return Fault_arc_protector_10A_2P;
                case 0x03:
                    return Fault_arc_protector_16A_2P;
                case 0x04:
                    return Fault_arc_protector_20A_2P;
                case 0x05:
                    return Fault_arc_protector_25A_2P;
                case 0x06:
                    return Fault_arc_protector_32A_2P;
                case 0x07:
                    return Fault_arc_protector_40A_2P;
                case 0x08:
                    return Fault_arc_protector_50A_2P;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }

    /**
     * 用电安全监测产品--墙面插座类产品
     */
    public enum Wall_Plate_version {
        /**
         * 16A安全墙插
         */
        Wall_protection_socket_16A(0x01),
        /**
         * 10A安全墙插
         */
        Wall_protection_socket_10A(0x02);

        private int version;

        Wall_Plate_version(int version) {
            this.version = version;
        }

        public static Wall_Plate_version initWall_Plate_version(int ver) {
            switch (ver) {
                case 0x01:
                    return Wall_protection_socket_16A;
                case 0x02:
                    return Wall_protection_socket_10A;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }


    }

    /**
     * 有害气体及火灾传感器类
     */

    public enum Gas_sensor_Version {

        /**
         * 烟雾探测器 有源WIFI
         */
        Smoke_sensor_WIFI(0x01),
        /**
         * 燃气探测器 有源WIFI
         */
        Methane_sensor_WIFI(0x02),
        /**
         * CO探测器 有源WIFI
         */
        CO_sensor_WIFI(0x03),

        /**
         * oem CO探测器
         */
        CO_sensor_OEM(0x04),
        /**
         * oem 燃气探测器
         */
        Methane_sensor_OEM(0x05),
        /**
         * 光学迷宫烟雾传感器
         */
        Smoke_sensor_CC2630(0x06),
        /**
         * 光学迷宫烟雾传感器oem
         */
        OEM_Smoke_sensor(0x07);

        private int version;

        Gas_sensor_Version(int version) {
            this.version = version;
        }

        public static Gas_sensor_Version initGas_sensor_Version(int ver) {
            switch (ver) {
                case 0x01:
                    return Smoke_sensor_WIFI;
                case 0x02:
                    return Methane_sensor_WIFI;
                case 0x03:
                    return CO_sensor_WIFI;
                case 0x04:
                    return CO_sensor_OEM;
                case 0x05:
                    return Methane_sensor_OEM;
                case 0x06:
                    return Smoke_sensor_CC2630;
                case 0x07:
                    return OEM_Smoke_sensor;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }

    /**
     * 水浸探测器
     */
    public enum Water_sensor_Version {

        OEM_leakage_sensor(0x01);

        private int version;

        Water_sensor_Version(int version) {
            this.version = version;
        }

        public static Water_sensor_Version initWater_sensor_Version(int ver) {
            switch (ver) {
                case 0x01:
                    return OEM_leakage_sensor;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }

    /**
     * 入侵检测产品
     */
    public enum Invasion_sensor_Version {

        /**
         * 红外入侵探测器
         */
        Infrared_regional_intrusion_sensor(0x01),
        /**
         * 红外幕帘探测器
         */
        Infrared_curtain_sensor(0x02),
        /**
         * 门磁探测器
         */
        Magnetic_sensor_Door_sensor(0x03),
        /**
         * 视频监控摄像机
         */
        Video_surveillance_camera(0x04),
        /**
         * 双鉴入侵探测器
         */
        OEM_intrusion_sensor(0x05),
        /**
         * 双鉴幕帘探测器
         */
        OEM_curtain_sensor(0x06),
        /**
         * 红外幕帘探测器
         */
        OEM_Infrared_curtain_sensor(0x07),
        /**
         * 玻璃破碎探测器
         */
        OEM_glasses_broken(0x08),

        /**
         * 门磁探测器 无源
         */
        Magnetic_sensor_Door_sensor_passive(0x09);


        private int version;

        Invasion_sensor_Version(int version) {

            this.version = version;

        }

        public static Invasion_sensor_Version initInvasion_sensor_Version(int ver) {
            switch (ver) {
                case 0x01:
                    return Infrared_regional_intrusion_sensor;
                case 0x02:
                    return Infrared_curtain_sensor;
                case 0x03:
                    return Magnetic_sensor_Door_sensor;
                case 0x04:
                    return Video_surveillance_camera;
                case 0x05:
                    return OEM_intrusion_sensor;
                case 0x06:
                    return OEM_curtain_sensor;
                case 0x07:
                    return OEM_Infrared_curtain_sensor;
                case 0x08:
                    return OEM_glasses_broken;
                case 0x09:
                    return Magnetic_sensor_Door_sensor_passive;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }


    /**
     * 阀门控制类产品
     */
    public enum Valve_failure {


        /**
         * 管道切断阀机械手
         */
        Pipe_valve_manipulator(0x01),
        /**
         * 钢瓶切断阀机械手
         */
        cylinder_valve_manipulator(0x02),
        /**
         * 工业电磁阀
         */
        industrial_electromagnetic_valve(0x11),
        /**
         * 民用电磁阀
         */
        civil_solenoid_valve(0x12);

        private int version;

        Valve_failure(int version) {

            this.version = version;

        }

        public static Valve_failure initValve_failure(int ver) {
            switch (ver) {
                case 0x01:
                    return Pipe_valve_manipulator;
                case 0x02:
                    return cylinder_valve_manipulator;
                case 0x011:
                    return industrial_electromagnetic_valve;
                case 0x012:
                    return civil_solenoid_valve;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }

    /**
     * 入户管理类产品
     */
    public enum Household_management {
        /**
         * 电子猫眼
         */
        Electronic_eye(0x01),
        /**
         * 电控锁
         */
        Electronic_door_lock(0x02);

        private int version;

        Household_management(int version) {
            this.version = version;

        }

        public static Household_management initHousehold_management(int ver) {
            switch (ver) {
                case 0x01:
                    return Electronic_eye;
                case 0x02:
                    return Electronic_door_lock;
            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }


    /**
     * 接入网关类产品
     */
    public enum Access_gateway_products {


        /**
         * 网关X1
         */
        Lowpan_WIFI_access_gateway_X1(0x01),
        /**
         * 网关2
         */
        Lowpan_WIFI_access_gateway_X2(0x02);


        private int version;

        Access_gateway_products(int version) {
            this.version = version;
        }

        public static Access_gateway_products initAccess_gateway_products(int ver) {
            switch (ver) {
                case 0x01:
                    return Lowpan_WIFI_access_gateway_X1;
                case 0x02:
                    return Lowpan_WIFI_access_gateway_X2;

            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }

    /**
     * 扩展IO报警接入类产品
     */
    public enum Expand_IO_alarm_access_products {
        /**
         * 报警接入盒
         */
        Expand_Alarm_Module(0x01),
        /**
         * 报警接入盒
         */
        WIFI_Expand_Alarm_Module(0x02);

        private int version;

        Expand_IO_alarm_access_products(int version) {
            this.version = version;
        }

        public static Expand_IO_alarm_access_products initExpand_IO_alarm_access_products(int ver) {
            switch (ver) {
                case 0x01:
                    return Expand_Alarm_Module;
                case 0x02:
                    return WIFI_Expand_Alarm_Module;

            }
            return null;
        }


        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

    }


    /**
     * 用来初始化设备
     * 6D01 + type + version
     *
     * @param type
     * @return
     */
    public static SmartDevice InitDeviceTypes(int type, int version) {
        SmartDevice smartDevice = null;
        switch (type) {
            case DeviceType.Portable_Socket:
                Portable_Socket_Version portable_socket_version = Portable_Socket_Version.initPortable_Socket_Version(version);
                if (portable_socket_version != null) {
                    smartDevice = new Socket();
//                    if(smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(portable_socket_version.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(portable_socket_version.info);
//                        smartDevice.getSmartDeviceLogic().setModel(portable_socket_version.model);
//                    }
                }
                break;
            case DeviceType.Arcing_fault:
                Arcing_fault arcing_fault = Arcing_fault.initArcing_fault(version);
                if (arcing_fault != null) {
                    smartDevice = new EarthLeakageProtective();
                    if (smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(arcing_fault.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(arcing_fault.info);
//                        smartDevice.getSmartDeviceLogic().setModel(arcing_fault.model);
                    }
                }
                break;
            case DeviceType.Wall_Socket:
                Wall_Plate_version wall_plate_version = Wall_Plate_version.initWall_Plate_version(version);
                if (wall_plate_version != null) {
                    smartDevice = new WallSocket();
                    if (smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(wall_plate_version.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(wall_plate_version.info);
//                        smartDevice.getSmartDeviceLogic().setModel(wall_plate_version.model);
                    }
                }
                break;

            case DeviceType.Harmful_gas:
                Gas_sensor_Version gas_sensor_version = Gas_sensor_Version.initGas_sensor_Version(version);
                if (gas_sensor_version != null) {
                    switch (gas_sensor_version) {

                        case Smoke_sensor_CC2630:
                        case Smoke_sensor_WIFI:
                        case OEM_Smoke_sensor:
                            smartDevice = new SmokeSensor();

                            break;
                        case Methane_sensor_OEM:
                        case Methane_sensor_WIFI:
                            smartDevice = new MethaneSensor();
                            break;
                        case CO_sensor_WIFI:
                        case CO_sensor_OEM:
                            smartDevice = new CardSensor();
                            break;
                    }
                    if (smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(gas_sensor_version.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(gas_sensor_version.info);
//                        smartDevice.getSmartDeviceLogic().setModel(gas_sensor_version.model);
                    }
                }

                break;

            case DeviceType.Water_sensor:
                Water_sensor_Version water_sensor = Water_sensor_Version.initWater_sensor_Version(version);
                if (water_sensor != null) {
                    smartDevice = new LeakingSensor();
                    if (smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(water_sensor.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(water_sensor.info);
//                        smartDevice.getSmartDeviceLogic().setModel(water_sensor.model);
                    }
                }
                break;
            case DeviceType.Invasion_sensor:
                Invasion_sensor_Version invasion_sensor_version = Invasion_sensor_Version.initInvasion_sensor_Version(version);
                if (invasion_sensor_version != null) {
                    switch (invasion_sensor_version) {
                        case OEM_intrusion_sensor:
                        case OEM_curtain_sensor:
                        case OEM_glasses_broken:
                        case OEM_Infrared_curtain_sensor:
                        case Infrared_regional_intrusion_sensor:
                        case Infrared_curtain_sensor:
                            smartDevice = new Infrared();
                            break;
                        case Magnetic_sensor_Door_sensor:
                        case Magnetic_sensor_Door_sensor_passive:
                            smartDevice = new Magnetometer();
                            break;
                        case Video_surveillance_camera:
                            smartDevice = new VideoMonitoring();
                            break;
                    }
                    if (smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(invasion_sensor_version.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(invasion_sensor_version.info);
//                        smartDevice.getSmartDeviceLogic().setModel(invasion_sensor_version.model);
//                        OutPutMessage.LogCatInfo("设备信息", "Magnetic_sensor_Door_sensor------------->" + smartDevice.getSmartDeviceLogic().getDeviceName());
                    }
                }
                break;
            case DeviceType.Valve_failure:
                Valve_failure valve_failure = Valve_failure.initValve_failure(version);
                if (valve_failure != null) {
                    smartDevice = new SolenoidValveWater();
//                    if(smartDevice != null) {
//                        smartDevice.getSmartDeviceLogic().setDeviceName(valve_failure.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(valve_failure.info);
//                        smartDevice.getSmartDeviceLogic().setModel(valve_failure.model);
//                    }
                }
                break;
            //0x08 入户管理类产品
            case DeviceType.Household_management:
                Household_management household_management = Household_management.initHousehold_management(version);
                if (household_management != null) {
                    switch (household_management) {
                        case Electronic_door_lock:
                            smartDevice = new ElectricLock();
                            break;
                        case Electronic_eye:
                            smartDevice = new DigitalDoorViewer();
                            break;

                    }
//                    if(smartDevice != null){
//                        smartDevice.getSmartDeviceLogic().setDeviceName(household_management.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(household_management.info);
//                        smartDevice.getSmartDeviceLogic().setModel(household_management.model);
//                    }
                }
                break;

            case DeviceType.Access_gateway_products:
                Access_gateway_products access_gateway_products = Access_gateway_products.initAccess_gateway_products(version);
                if (access_gateway_products != null) {
                    switch (access_gateway_products) {
                        case Lowpan_WIFI_access_gateway_X1:
                            smartDevice = new ExtensionEquipment();
                            break;
                        case Lowpan_WIFI_access_gateway_X2:
                            smartDevice = new ExtensionEquipment();
                            break;

                    }
//                    if(smartDevice != null){
//                        smartDevice.getSmartDeviceLogic().setDeviceName(access_gateway_products.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(access_gateway_products.info);
//                        smartDevice.getSmartDeviceLogic().setModel(access_gateway_products.model);
//                    }
                }
                break;
            //0xA 扩展IO报警接入类产品
            case DeviceType.Expand_IO_alarm_access_products:
                Expand_IO_alarm_access_products expand_io_alarm_access_products = Expand_IO_alarm_access_products.initExpand_IO_alarm_access_products(version);
                if (expand_io_alarm_access_products != null) {
//                    switch (expand_io_alarm_access_products) {
//                        case Expand_Alarm_Module:
//                        case WIFI_Expand_Alarm_Module:
                    smartDevice = new ExtensionEquipment();
//                            break;
//                    }
//                    if(smartDevice != null){
//                        smartDevice.getSmartDeviceLogic().setDeviceName(expand_io_alarm_access_products.name);
//                        smartDevice.getSmartDeviceLogic().setDeviceInfo(expand_io_alarm_access_products.info);
//                        smartDevice.getSmartDeviceLogic().setModel(expand_io_alarm_access_products.model);
//                    }
                }
                break;

            case DeviceType.Electric_stove:
                smartDevice = new ElectricStove();
                break;
            default:
                break;
        }

        if (smartDevice != null) {

            SecurityTypeInfo info = typeMap.get(type + "-" + version);
            OutPutMessage.LogCatInfo("设备类型信息", type + "-" + version);

            if (info != null) {

                OutPutMessage.LogCatInfo("设备类型信息", info.getDevName() + "   " + info.getInfo() + "   " + info.getVersionCode());
                smartDevice.getSmartDeviceLogic().setDeviceName(info.getDevName());
//                smartDevice.getSmartDeviceLogic().setDeviceInfo(info.getInfo());
//                smartDevice.getSmartDeviceLogic().setModel(info.getVersioncode());

            }

        }

        return smartDevice;
    }
}
