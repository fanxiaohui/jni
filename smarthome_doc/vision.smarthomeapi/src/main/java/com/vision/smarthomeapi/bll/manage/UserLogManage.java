package com.vision.smarthomeapi.bll.manage;

import com.vision.smarthomeapi.bean.RGetUserLog;
import com.vision.smarthomeapi.dal.data.Constant;
import com.vision.smarthomeapi.dal.user.HomeUserLogInfo;
import com.vision.smarthomeapi.dal.user.UserLogInfo;
import com.vision.smarthomeapi.sqlutil.crud.DataSupport;
import com.vision.smarthomeapi.util.NotificationManager;
import com.vision.smarthomeapi.util.OutPutMessage;
import com.vision.smarthomeapi.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户日志管理类
 * Created by yangle on 2015/9/21.
 */
public class UserLogManage {

    private static UserLogManage userLogManage;
    private List<UserLogInfo> userLogInfoList;

    /**
     * 获取UserLogManage对象
     *
     * @return UserLogManage对象
     */
    public static UserLogManage defaultManager() {
        return userLogManage == null ? userLogManage = new UserLogManage() : userLogManage;
    }

    /**
     * 构造方法
     */
    public UserLogManage() {
        userLogInfoList = new ArrayList<>();
    }

//    /**
//     * 从网络获取用户日志
//     */
//    public void getUserLogNetWork() {
//        //判断是否可以发码
//        if (Controller.defaultController().APPSendCode()) {
//            String cookie = UserManage.getShare().getUser().getUserCookie();
//            String lastStamp = getLastStamp();
//            CGetUserLog cGetUserLog = new CGetUserLog(cookie, lastStamp);
//            NetworkMessage message = UserManage.getShare().buildBean(cGetUserLog);
//            NetworkManager.defaultNetworkManager().sendMessage(message);
//        }
//    }

    /**
     * 从网络获取用户日志回调方法
     *
     * @param rGetUserLog 用户日志类
     */
    public void getUserLogNetWorkResponse(RGetUserLog rGetUserLog) {
        if (rGetUserLog == null) {
            return;
        }
        NotificationManager.defaultManager().postNotification(
                Constant.NotificationType.GET_USER_LOGS, null, rGetUserLog);
    }

    /**
     * 获取数据库中最后一条数据的时间戳
     *
     * @return 截止时间戳
     */
    private String getLastStamp() {
        if (userLogInfoList != null && !userLogInfoList.isEmpty()) {
            UserLogInfo userLogInfo = userLogInfoList.get(userLogInfoList.size() - 1);
            if (userLogInfo != null) {
                return userLogInfo.getCreateTime();
            }
        }
        return "";
    }

    /**
     * 添加用户日志到数据库
     *
     * @param userlogList 用户日志集合
     * @return 是否添加成功
     */
    public boolean addUserLogToDatabase(List<UserLogInfo> userlogList) {
        for (UserLogInfo userLogInfo : userlogList) {
            userLogInfo.setuId(StringUtil.getUserID());
        }
        DataSupport.saveAll(userlogList);

        int userLogsSize = findUserLogFromDatabase("all").size();
        if (userLogsSize > 100) {
            //删除多余的用户日志，保留后100条
            int deleteNum = deleteUserLogFromDatabase(userLogsSize - 100);
            if (deleteNum == userLogsSize - 100) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 查找用户日志从数据库
     * 如果筛选条件为”all”，读取数据库全部数据
     *
     * @param condition 筛选条件
     * @return 用户日志集合
     */
    public List<UserLogInfo> findUserLogFromDatabase(String condition) {
        userLogInfoList = DataSupport.where("uId = ?",
                StringUtil.getUserID()).find(UserLogInfo.class);
        if ("all".equals(condition)) {
            return userLogInfoList;
        } else {
            List<UserLogInfo> userLogInfoListFilter = new ArrayList<>();
            if (userLogInfoList != null && !userLogInfoList.isEmpty()) {
                for (UserLogInfo userLogInfo : userLogInfoList) {
                    //当前搜索条件不包含空格
                    if (!condition.contains(" ")) {
                        if (userLogInfo.toString().contains(condition)) {
                            userLogInfoListFilter.add(userLogInfo);
                        }
                    } else {
                        String[] conditionArray = condition.split(" ");
                        for (int i = 0; i < conditionArray.length; i++) {
                            if (!conditionArray[i].equals("")) {
                                if (userLogInfo.toString().contains(conditionArray[i])) {
                                    userLogInfoListFilter.add(userLogInfo);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            return userLogInfoListFilter;
        }
    }

    /**
     * 删除用户日志从数据库
     *
     * @param userLogQuantity 删除当前用户前x条数据
     * @return 删除的用户日志条数
     */
    private int deleteUserLogFromDatabase(int userLogQuantity) {
        if (userLogQuantity > 0) {
            if (userLogInfoList != null && !userLogInfoList.isEmpty()) {
                OutPutMessage.LogCatInfo("用户日志数量:", userLogInfoList.size() + "");
                UserLogInfo userLogInfo = userLogInfoList.get(userLogQuantity - 1);
                int lastId = 0;
                if (userLogInfo != null) {
                    lastId = userLogInfo.getId();
                }
                return DataSupport.deleteAll(UserLogInfo.class, "uId = ? and id <= ?",
                        StringUtil.getUserID(), lastId + "");
            }
        }

        return 0;
    }

    /**
     * 设置首页日志显示数据_带参数
     *
     * @param msg         日志信息
     * @param logCategory 日志分类
     *                    LOG_NORMAL(普通日志)
     *                    LOG_DEVICEALARM(告警日志)
     */
    public void setHomePageLog(String msg, LogCategory logCategory) {
        HomeUserLogInfo homePageLog = new HomeUserLogInfo(StringUtil.getUserID(), msg, logCategory.getIndex());
        //添加日志到数据库，每个用户最多保存一百条数据
        List<HomeUserLogInfo> homeUserLogInfoList =
                DataSupport.where("uId = ?", StringUtil.getUserID()).find(HomeUserLogInfo.class);
        if (homeUserLogInfoList != null && !homeUserLogInfoList.isEmpty()) {
            if (homeUserLogInfoList.size() >= 100) {
                DataSupport.deleteAll(HomeUserLogInfo.class, "uId = ?", StringUtil.getUserID());
                homePageLog.save();
            }
        }
        homePageLog.save();
        homePageLog.saveThrows();
        //如果当前所在页面有日志框则刷新UI
//        if (Controller.defaultController().getCurrentActivity() instanceof MainSocketActivity) {
//            NotificationManager.defaultManager().postNotification(
//                    Constant.NotificationType.SET_USER_LOG, null, homePageLog);
//        }
    }

    /**
     * 设置首页日志显示数据_不带参数
     * 进入带日志提示框的页面时调用
     *
     * @return 当前日志消息&消息类型封装
     */
    public HomeUserLogInfo getHomePageLog() {
        List<HomeUserLogInfo> homeUserLogInfoList =
                DataSupport.where("uId = ?", StringUtil.getUserID()).find(HomeUserLogInfo.class);
        if (homeUserLogInfoList != null && !homeUserLogInfoList.isEmpty()) {
            return homeUserLogInfoList.get(homeUserLogInfoList.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 日志分类
     */
    public enum LogCategory {
        LOG_NORMAL(1),
        LOG_DEVICEALARM(2);

        private int index;

        LogCategory(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
}
