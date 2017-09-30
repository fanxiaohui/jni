package com.vision.smarthomeapi.util;


import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import com.vision.smarthomeapi.bll.manage.SecurityUserManage;
import com.vision.smarthomeapi.dal.user.SecurityUserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    /**
     * 验证用户UsdID是否为null
     *
     * @return
     */
    public static String getUserID() {
        SecurityUserInfo userInfo = SecurityUserManage.getShare().getUser();
        if (userInfo == null) {
            return "";
        }
        if (userInfo.getUserID() == null || userInfo.getUserID().equals("")) {
            return "";
        }
        return userInfo.getUserID();
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    private static boolean isEmail(String email) {


        //String str = "^([a-zA-Z0-9_\\-\\.]+){1,20}@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        //String str = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
        String str = "^(\\w)+(\\.\\w+)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();

//		if (email == null || email.trim().length() == 0)
//			return false;
//		Pattern emailer = Pattern.compile("[\\w\\.\\-\\_\\\\]{4,20}@[\\w\\.\\-\\_\\\\]+(\\.[\\w\\.\\-\\_\\\\]+){1,2}");
//		return emailer.matcher(email).matches();
    }


    /**
     * 判断是不是一个合法的分组名
     *
     * @return
     */
    private static boolean isGroupName(String groupName) {
        if (groupName == null || groupName.trim().length() == 0)
            return false;
        Pattern emailer = Pattern.compile("[\\w\\.\\-\\_\\\\]{1,20}");
        return emailer.matcher(groupName).matches();
    }


    /**
     * 判断是否是一个有效的密码类型
     *
     * @param psd 密码
     * @return 校验结果
     */
    private static boolean isPassword(String psd) {
        //Pattern pattern=Pattern.compile("[\\\\\\w\\+\\-\\*\\/\\.\\,\\<\\>\\?\\`\\~\\{\\}\\[\\]\\|\\;\\:\\'\"]+");
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_.~!@#$%\\^\\+\\*&\\\\\\/\\?\\|:\\.{}()';=\"]{6,20}");
//        Pattern pattern=Pattern.compile("[a-zA-Z0-9_\\-()]{6,20}");
        return pattern.matcher(psd).matches();
    }

    /**
     * 判断是否是设备密码
     *
     * @param psw 密码
     * @return 判断结果
     */

    private static boolean isDevicePass(String psw) {

        // 只允许字母和数字
        String regEx = "^[a-zA-Z0-9]{4,20}";
        Pattern p = Pattern.compile(regEx);
        return p.matcher(psw).matches();
    }

    /**
     * 获取后4位Mac
     *
     * @param mac mac
     * @return
     */
    public static String getMacAfterFour(String mac) {
        String src = "";
        if (mac.length() < 16) {
            return src;
        }
        return src = mac.substring(12, 16);
    }

    /**
     * 是否包含下划线，包含的话取下划线之前的数据
     *
     * @param name
     * @return
     */
    public static String getName(String name) {
        if (!name.contains("_")) {
            return name;
        } else {
            String[] strArray = name.split("_");
            if (strArray != null) {
                return strArray[0];
            }
        }
        return "";
    }

    /**
     * 判断昵称是否合法
     */
    public static boolean isNameNick(String nick) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5_<>(){}\\[\\]-]{1,31}");
        return pattern.matcher(nick).matches();
    }

    /**
     * 判断昵称是否合法
     */
    public static boolean isNick(String nick) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5_<>(){}\\[\\]-]{1,20}");
        return pattern.matcher(nick).matches();
    }

    /**
     * 判断锁昵称合法性
     * @param nick
     * @return
     */

    public static boolean isLockNick(String nick){
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5]{1,20}");
        return pattern.matcher(nick).matches();
    }

    /**
     * 判断是否为WIFI密码
     *
     * @param password 密码
     * @return true 正确
     */
    public static boolean isWIFIPassword(String password) {
        String[] regulars = new String[]{"[\\x00-\\x7f]{5}", "[0-9,A-F,a-f]{10}", "[\\x00-\\x7f]{13}", "[0-9,A-F,a-f]{26}", "[\\x00-\\x7f]{8,63}"};
        for (String str : regulars) {
            Pattern pattern = Pattern.compile(str);
            if (pattern.matcher(password).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测密码的位数是否合法
     *
     * @param psd
     * @return 正确返回TRUE
     */
    public static boolean checkPsd(String psd) {
        if (isEmpty(psd)) {
            //showToast(R.string.user_input_password_not_empty);
            return false;
        }
        if (psd.length() < 6 || psd.length() > 20) {
            //showToast(R.string.user_input_psd_len_not_valid);
            return false;
        } else if (!isPassword(psd)) {
            //showToast(R.string.user_input_psd_not_valid);
            return false;
        }
        return true;
    }


    /**
     * 检测邮箱的位数是否合
     *
     * @return 正确返回TRUE
     */
    public static boolean checkEmail(String email) {
        if (isEmpty(email)) {
            //		showToast(R.string.user_input_email_not_empty);
            return false;
        }
        /*else if(email.length()<1||email.length()>24){
        //	showToast(R.string.user_input_email_len_not_valid);
			return false ;
		}*/
        else if (!isEmail(email)) {
            //	showToast(R.string.user_input_email_not_valid);
            return false;
        }
        return true;
    }


    /**
     * 检测分组名是否合法
     *
     * @param groupName
     * @return
     */
    public static boolean checkGroupName(String groupName) {

        if (isEmpty(groupName)) {
            return false;
        } else if (groupName.length() < 1 || groupName.length() > 20) {
            return false;
        } else if (!isGroupName(groupName)) {
            return false;
        }
        return true;


    }


    /**
     * 检测设备密码是否合法
     *
     * @param devicePass
     * @return
     */
    public static boolean checkDevicePass(String devicePass) {

        if (isEmpty(devicePass)) {
            return false;
        } else if (devicePass.length() < 4 || devicePass.length() > 20) {
            return false;
        } else if (!isDevicePass(devicePass)) {
            return false;
        }
        return true;


    }


    /**
     * 检测设备名称是否合法
     *
     * @param deviceName
     * @return
     */
    public static boolean checkDeviceName(String deviceName) {

        if (isEmpty(deviceName)) {
            return false;
        } else if (deviceName.length() < 4 || deviceName.length() > 20) {
            return false;
        }
        return true;

    }

    /**
     * 检测昵称是否合法
     */
    public static boolean checkNick(String nick) {
        if (isEmpty(nick)) {
            return false;
        } else if (nick.length() > 20) {
            return false;
        }
        return true;
    }

//	private static void showToast(int resId){
//		OutPutMessage.outputToast(SocketApplication.baseActivity.getResources().getString(resId));
//	}

    /**
     * 判断昵称是否合法
     */
    public static boolean isDeviceNick(String nick) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9\\u4e00-\\u9fa5_<>(){}\\[\\]-]{1,30}");
        return pattern.matcher(nick).matches();
    }

    /**
     * 复制字符串
     * @param context
     * @param textCopy
     */
    public static void copyText(Context context, CharSequence textCopy) {
        if (Build.VERSION.SDK_INT > 11) {
            ClipboardManager c = (ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            c.setText(textCopy);

        } else {
            android.text.ClipboardManager c = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            c.setText(textCopy);
        }
    }
}
