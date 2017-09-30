package com.vision.smarthomeapi.util;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.vision.smarthomeapi.bll.Controller;
import com.vision.smarthomeapi.dal.data.Constant;


/**
 * Created by zhaoqing on 2015/5/22.
 */
public class OutPutMessage {

    public static Toast UIToast;

    public static Handler handler;

    /**
     * 当前使用对象。包括：开发，测送，用户
     */
    public static int currentOutTarget = Constant.OUT_TARGET.DeBug;

    /**
     * 函数名称：outPut</br> 功能描述：
     *
     * @param message 输出信息
     * @param outType 输出类型 修改日志:</br>
     * @author ZhaoQing
     */
    public static void outPut(String message,
                              int outType, int outTarget) {
        switch (outType) {
            case Constant.OUT_TYPE.Type_Log:
                //只要不是用户就有输出
                if (currentOutTarget != Constant.OUT_TARGET.User) {
                    System.out.println(message);
                }
                break;

            case Constant.OUT_TYPE.Type_Toast:

                //用户 测试 开发。当前使用对象大于等于输出目标
                if (currentOutTarget >= outTarget) {
                    showToast(message);
                }

                break;
            case Constant.OUT_TYPE.Type_Alert:

                break;
            case Constant.OUT_TYPE.Type_File:

                break;
            default:
                break;
        }

    }

    /**
     * 输出Log
     *
     * @param TAG
     * @param message
     */
    public static void LogCatInfo(String TAG, String message) {
        if (currentOutTarget != Constant.OUT_TARGET.User) {
            Log.i(TAG, message);
        }
    }

    /**
     * 创建Toast用的Handler
     */
    public static void createToastHandler() {
        if (handler == null) {
            if (Thread.currentThread().getName().equals("main")) {
                handler = new Handler();
            }
        }
    }

    /**
     * Toast显示
     *
     * @param str Toast显示内容
     */
    public static void showToast(final String str) {
        if (handler == null) {
            if (Thread.currentThread().getName().equals("main")) {
                handler = new Handler();
            }
        }
        if (handler != null) {
            OutPutMessage.LogCatInfo("Toast提示","---------------->弹出Toast");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (UIToast == null) {
                        UIToast = Toast.makeText(
                                Controller.getContext(), str,
                                Toast.LENGTH_SHORT);
                        UIToast.show();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                UIToast.cancel();
                            }
                        }, 1000);

                    } else {

                        UIToast.setText(str);

                        UIToast.show();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                UIToast.cancel();
                            }
                        }, 2000);

                    }
                }

                ;
            });
        }
    }

}
