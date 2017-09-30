package com.vision.smarthomeapi.util;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;

import com.vision.smarthomeapi.bean.RBean;
import com.vision.smarthomeapi.dal.data.Constant;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationManager {

    private final static String TAG = "NotificationManager";
    private ConcurrentHashMap<String, ConcurrentHashMap<Object, String>> methodList;

    private static NotificationManager manager = null;

    /**
     * 构造
     */
    public NotificationManager() {
        methodList = new ConcurrentHashMap<String, ConcurrentHashMap<Object, String>>();
    }

    /**
     * 获取 NotificationManager对象
     *
     * @return NotificationManager对象
     */
    public static NotificationManager defaultManager() {
        if (manager == null) {
            manager = new NotificationManager();
        }
        return manager;
    }


    /**
     * 加入到队列
     *
     * @param observer
     * @param name
     * @param methodName
     */
    public void addObserver(Object observer,  @NonNull String name, @NonNull String methodName) {
        OutPutMessage.LogCatInfo("加入队列",observer.toString() +"   addObserver");
        ConcurrentHashMap<Object, String> observerList = methodList.get(name);
        if (observerList != null) {
            observerList.put(observer, methodName);
        } else {
            observerList = new ConcurrentHashMap<Object, String>();
            observerList.put(observer, methodName);
            methodList.put(name, observerList);
        }

    }


    public void postNotification(@Constant.NotificationType.NotificationPostName @NonNull String name) {
        postNotification(name, null, null);
    }

    public void postNotification(@Constant.NotificationType.NotificationPostName @NonNull String name, Object sender) {
        postNotification(name, sender, null);
    }

    public void postNotification( @NonNull String name, Object sender, Object arg) {
        postNotification(name, sender, arg,null, null);
    }
    public void postNotification(@Constant.NotificationType.NotificationPostName @NonNull String name, Object sender, Object arg,RBean rBean) {
        postNotification(name, sender, arg,rBean, null);
    }
    public void postNotification(String name, Object sender, Object arg, RBean rBean,Map<String, Object> info) {

        Notification not = new Notification(name, sender, info, arg,rBean);
        ConcurrentHashMap<Object, String> map = methodList.get(name);
        if (map == null) {
            return;
        }
        Iterator<Map.Entry<Object, String>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {//检测元素(长度)
            Map.Entry<Object, String> entry = iterator.next();//获取元素
            String method = entry.getValue();
            Class<? extends Object> clazz = entry.getKey().getClass();
            Method m1;
            try {
                m1 = clazz.getDeclaredMethod(method, Notification.class);
                m1.setAccessible(true);
                final Method _m = m1;
                final Notification _n = not;
                final Object _o = entry.getKey();
                boolean needRunUIThread = false;
                Activity _a = null;
                if (entry.getKey() instanceof Activity) {
                    needRunUIThread = true;
                    _a = (Activity) entry.getKey();
                } else if (entry.getKey() instanceof Fragment) {
                    needRunUIThread = true;
                    _a = ((Fragment) entry.getKey()).getActivity();
                } else if (entry.getKey() instanceof android.support.v4.app.Fragment) {
                    needRunUIThread = true;
                    _a = ((android.support.v4.app.Fragment) entry.getKey()).getActivity();
                }

                if (needRunUIThread && _a != null) {
                    _a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                _m.invoke(_o, _n);
                                OutPutMessage.LogCatInfo("回调测试", "------------------>时间触发" + _o.toString() + "!!!" + _n.toString());
                            } catch (IllegalAccessException e) {
                                throw new NotificationException(printStackTrace(e));
                            } catch (InvocationTargetException e) {
                                OutPutMessage.LogCatInfo("Toast提示", "---------------->发送广播" + e.toString());
                                throw new NotificationException(printStackTrace(e));
                            }
                        }
                    });
                } else {
                    m1.invoke(entry.getKey(), not);
                }
            } catch (NoSuchMethodException e) {
                throw new NotificationException(printStackTrace(e));
            } catch (IllegalAccessException e) {
                throw new NotificationException(printStackTrace(e));
            } catch (IllegalArgumentException e) {
                throw new NotificationException(printStackTrace(e));
            } catch (InvocationTargetException e) {
                throw new NotificationException(printStackTrace(e));
            }
        }
    }


    public void removeObserver(Object observer) {

        Iterator<Map.Entry<String, ConcurrentHashMap<Object, String>>> iterator = methodList.entrySet().iterator();
        while (iterator.hasNext()) {//检测元素(长度)
            Map.Entry<String, ConcurrentHashMap<Object, String>> entry = iterator.next();//获取元素
            Iterator<Map.Entry<Object, String>> map = entry.getValue().entrySet().iterator();
            while (map.hasNext()) {//检测元素(长度)
                Map.Entry<Object, String> objEntry = map.next();
                if (objEntry.getKey().getClass().toString().equals(observer.getClass().toString())) {
                    map.remove();
                }
            }
        }
    }

    public void removeObserver(Object observer, String name, String methodName) {
        methodList.get(name).remove(observer);
    }

    private String printStackTrace(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        return result;
    }


}
