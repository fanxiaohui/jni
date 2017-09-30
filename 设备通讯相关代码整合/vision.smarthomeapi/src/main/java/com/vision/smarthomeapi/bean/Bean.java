package com.vision.smarthomeapi.bean;


import com.vision.smarthomeapi.net.http.RequestParams;
import com.vision.smarthomeapi.util.OutPutMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Bean {

    /**
     * 用于区分Http返回码
     */
    public static int ERROR = 0;
    public static int OK = 1;
    public static int TOAST = 2;
    public static int DIALOG = 3;
    public static int UICHANGE = 4;
    public static int LOCATTOAST = 5;
    protected String statusMsg;
    protected String urlOrigin;
    protected String status;

    public RequestParams setRequestParams() {
        RequestParams params = new RequestParams();
        Class<?> clz = this.getClass();
        try {            // 只使用public类型字段
            Field[] fields = clz.getDeclaredFields();

            if (fields != null)
                for (Field field : fields) {
                    Method m;
                    try {
                        // 如果json中没有相应的key，会抛出异常，继续扫描下一个key
                        // Object objValue = jsonObject.get(field.getName());
                        // 字符串类型
                        if (field.getGenericType() == String.class) {
                            m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                            params.put(field.getName(), m.invoke(this));
                        }
                        // long类型
                        else if (field.getType() == long.class) {
                            m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                            params.put(field.getName(), m.invoke(this));
                        }
                        // int类型
                        else if (field.getType() == int.class) {
                            m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                            params.put(field.getName(), m.invoke(this));
                        }
                        // boolean类型
                        else if (field.getType() == boolean.class) {
                            m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                            params.put(field.getName(), m.invoke(this));
                        }
                        // short类型
                        else if (field.getType() == short.class) {
                            m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                            params.put(field.getName(), m.invoke(this));
                        }
                    } catch (Exception e) {
                        e.toString();
                    }

                }

        } catch (Exception e) {

        }

        return params;
    }

    private static String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

    public String toString() {
        Class<?> clz = this.getClass();
        Field[] fields = clz.getDeclaredFields();
        StringBuffer sb = new StringBuffer();

        String s = "";
        for (Field field : fields) {

            OutPutMessage.LogCatInfo("JSON转String",field.getName());
            if (!field.getName().equals("service_id") && (!field.getName().equals("urlOrigin"))) {
                sb.append(s);
                s = "&";
                sb.append(field.getName());
                sb.append("=");
                Method m;
                try {
                    OutPutMessage.LogCatInfo("JSON转String",field.getGenericType().toString());
                    if (field.getGenericType().toString().equals(
                            "class java.lang.Boolean")) {
                        m = (Method) clz.getMethod(
                                field.getName());
                        sb.append(m.invoke(this));

                    } else if (field.getGenericType().toString().equals("boolean")) {
                        m = (Method) clz.getMethod(field.getName());
                        sb.append(m.invoke(this));

                    } else if (field.getGenericType().toString().equals("java.util.ArrayList<com.power.socket.info.GroupDevices>")) {

                        m = (Method) clz.getMethod("get" + getMethodName(field.getName()));

//                        ArrayList<GroupDevices>  groupValue = (ArrayList<GroupDevices>) m.invoke(this);

//					GroupValue info = ;
//					info.setGrpName(null);

                    }
//
                    else {
                        m = (Method) clz.getMethod("get" + getMethodName(field.getName()));
                        sb.append(m.invoke(this));

                    }
                } catch (NoSuchMethodException e) {
                    // `TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public int mode() {
//        UserManage userManage = UserManage.getShare();
        switch (status) {
            case "0000":
                return Bean.OK;

            case "FFFF":
                statusMsg = "连接服务器异常，请重试。";
                OutPutMessage.showToast(statusMsg);
                return Bean.LOCATTOAST;

            case "0001":
                //用户强制登出
                statusMsg = "系统错误，请重新登录";
                OutPutMessage.showToast(statusMsg);
//                userManage.cancelLogin();
//                userManage.startActivity();
                return Bean.LOCATTOAST;

            case "0002":
                //用户强制登出
                statusMsg = "登录密码已修改，请重新登录";
                OutPutMessage.showToast(statusMsg);
//                userManage.cancelLogin();
//                userManage.startActivity();
                return Bean.LOCATTOAST;

            case "0006":
                //用户强制登出
                statusMsg = "用户不存在";
                //OutPutMessage.showToast(statusMsg);
                return Bean.TOAST;

            case "0031":
                //用户强制登出
                statusMsg = "用户已在别处登录，请确认是否为本人操作";
                OutPutMessage.showToast(statusMsg);
//                userManage.cancelLogin();
//                userManage.startActivity();
                return Bean.LOCATTOAST;
        }
        return Bean.ERROR;

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrlOrigin() {
        return urlOrigin;
    }

    public void setUrlOrigin(String urlOrigin) {
        this.urlOrigin = urlOrigin;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    /**
     * 将JAVA对象转换成JSON字符串
     * @param obj
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    public static String simpleObjectToJsonStr(Object obj,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{
        if(obj==null){
            return "null";
        }
        String jsonStr = "{";
        Class<?> cla = obj.getClass();
        Field fields[] = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.getType() == long.class){
                jsonStr += "\""+field.getName()+"\":"+field.getLong(obj)+",";
            }else if(field.getType() == double.class){
                jsonStr += "\""+field.getName()+"\":"+field.getDouble(obj)+",";
            }else if(field.getType() == float.class){
                jsonStr += "\""+field.getName()+"\":"+field.getFloat(obj)+",";
            }else if(field.getType() == int.class){
                jsonStr += "\""+field.getName()+"\":"+field.getInt(obj)+",";
            }else if(field.getType() == boolean.class){
                jsonStr += "\""+field.getName()+"\":"+field.getBoolean(obj)+",";
            }else if(field.getType() == Integer.class||field.getType() == Boolean.class
                    ||field.getType() == Double.class||field.getType() == Float.class
                    ||field.getType() == Long.class){
                jsonStr += "\""+field.getName()+"\":"+field.get(obj)+",";
            }else if(field.getType() == String.class){
                jsonStr += "\""+field.getName()+"\":\""+field.get(obj)+"\",";
            }else if(field.getType() == List.class){
                if (field.getName().equals("answerIds")){
                    jsonStr += "\"" + field.getName() + "\":" + field.get(obj) + ",";
                }else {
                    String value = simpleListToJsonStr((List<?>) field.get(obj), claList);
                    jsonStr += "\"" + field.getName() + "\":" + value + ",";
                }
            }else{
                if(claList!=null&&claList.size()!=0&&claList.contains(field.getType())){
                    String value = simpleObjectToJsonStr(field.get(obj),claList);
                    jsonStr += "\""+field.getName()+"\":"+value+",";
                }else{
//                    jsonStr += "\""+field.getName()+"\":null,";
                }
            }
        }
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);
        jsonStr += "}";
        return jsonStr;
    }

    /**
     * 将JAVA的LIST转换成JSON字符串
     * @param list
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("rawtypes")
    public static String simpleListToJsonStr(List<?> list,List<Class> claList) throws IllegalArgumentException, IllegalAccessException{
        if(list==null||list.size()==0){
            return "[]";
        }
        String jsonStr = "[";
        for (Object object : list) {
            jsonStr += simpleObjectToJsonStr(object,claList)+",";
        }
        jsonStr = jsonStr.substring(0,jsonStr.length()-1);
        jsonStr += "]";
        return jsonStr;
    }
}

