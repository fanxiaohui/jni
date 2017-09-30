package com.vision.smarthomeapi.bean;

import com.vision.smarthomeapi.util.OutPutMessage;

/**
 * 类的名称:RUserLogin</br>
 * 主要功能描述:用户登录</br>
 * 创建日期:2014-3-19
 *
 * @author ZhaoQing
 *         <table>
 *         <tr><th>版本</th><th>日期</th><th>作者</th><th>描述</th>
 *         <tr><td>0.1</td><td>2014-3-19</td><td>ZhaoQing</td><td>初始创建</td>
 *         </table>
 */
public class RWanDeviceInfo extends Bean {

    private String mac;
    private String id;
    private String name;
    private String type;


    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RWanDeviceInfo() {

    }


    /**
     * http请求返回码处理
     *
     * @return 处理方式
     */
    public int mode() {
        int rValue = super.mode();
        OutPutMessage.LogCatInfo("消息推送", "平台回复设备信息:::" + toString());
        if (rValue == Bean.OK) {
            //String n = (name != null && !name.equals("")) ? name : getName(Integer.valueOf(type));
            //String msg = "设备绑定成功 :" + n + "!";
//            UserLogManage.defaultManager().setHomePageLog(msg, UserLogManage.LogCategory.LOG_NORMAL);
//            NotificationManager.defaultManager().postNotification(Constant.NotificationType.PUSH_DEVICE_BIND, null);
        //    UserManage.getShare().getUser().getUserDevices();
        }
        if (rValue != Bean.ERROR) {
            return rValue;
        }
        return Bean.ERROR;
    }


}

