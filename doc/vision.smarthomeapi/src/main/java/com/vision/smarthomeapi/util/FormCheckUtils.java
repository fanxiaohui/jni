package com.vision.smarthomeapi.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式检查
 * Created by yangle on 2015/10/26.
 */
public class FormCheckUtils {

    /**
     * 验证是否是手机号码
     *
     * @param str 手机号码
     * @return 是否为手机号码
     */
    public static boolean isMobile(String str) {
        Pattern pattern = Pattern.compile("1[0-9]{10}");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证密码格式
     *
     * @param str 密码
     * @return 密码格式是否正确
     */
    public static boolean isPassword(String str) {
        Pattern p1 = Pattern.compile("^[\\S]{6,16}$");
        Pattern p2 = Pattern.compile("^\\d{9,16}$");
        Pattern p3 = Pattern.compile("^\\d+$");
        if (!(p3.matcher(str).find()
                && p2.matcher(str).find())
                && !(!p3.matcher(str).find() && p1.matcher(str).find())) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 密码格式错误提示
     *
     * @param str 密码
     * @return 错误提示
     */
    public static String psdFormErrorMsg(String str) {
        Pattern p1 = Pattern.compile("^\\d{1,8}$");
        Pattern p2 = Pattern.compile("^\\d{17,}$");
        Pattern p3 = Pattern.compile("^[\\S]{1,5}$");
        Pattern p4 = Pattern.compile("^[\\S]{17,}$");
        Pattern p5 = Pattern.compile("\\s");

        if (TextUtils.isEmpty(str)) {
            return "请输入密码";

        } else if (p1.matcher(str).find()) {
            return "纯数字密码长度请高于9位";

        } else if (p2.matcher(str).find()) {
            return "纯数字密码长度请低于16位";

        } else if (p3.matcher(str).find()) {
            return "混合密码长度请高于6位";

        } else if (p4.matcher(str).find()) {
            return "混合密码长度请低于16位";

        } else if (p5.matcher(str).find()) {
            return "请勿在密码中包含空格";
        }
        return "密码格式错误";
    }

    /**
     * 验证验证码格式
     *
     * @param str 验证码
     * @return 验证码格式是否正确
     */
    public static boolean isIdenCode(String str) {
        if (str.length() == 6 && !str.contains("&")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检测邮箱的位数
     *
     * @return 正确返回TRUE
     */
    public static boolean isEmail(String email) {
        if (StringUtil.isEmpty(email)) {
            return false;
        } else if (!checkEmail(email)) {
            return false;
        }
        return true;
    }

    /**
     * 验证设备名称格式
     *
     * @param str 名称
     * @return 设备名称是否正确
     */
    public static boolean isDeviceName(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$");
        if (str.length() >= 1 && str.length() <= 15 && pattern.matcher(str).find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设备名称格式错误提示
     *
     * @param str 设备名称
     * @return 错误提示
     */
    public static String deviceNameFormErrorMsg(String str) {
        if (str.length() < 1) {
            return "请输入设备名称";

        } else if (str.length() > 15) {
            return "设备名称长度必须低于15位";

        } else {
            return "设备名称中请勿包含特殊字符";
        }
    }

    /**
     * 验证账号
     *
     * @param str 账号
     * @return 账号是否正确
     */
    public static boolean isSecurityID(String str) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        if (str.length() >= 1 && str.length() <= 20 &&
                pattern.matcher(str).find() && !str.contains("_")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是一个合法的邮箱地址
     *
     * @param email 邮箱
     * @return 邮箱地址是否合法
     */
    private static boolean checkEmail(String email) {
        String str = "^(\\w)+(\\.\\w+)*@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证安全码格式
     *
     * @param str 安全码
     * @return 安全码格式是否正确
     */
    public static boolean isSecurityCode(String str) {
        if (str.length() == 6) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 验证昵称格式
     *
     * @param str 昵称
     * @return 昵称格式是否正确
     */
    public static boolean isNick(String str) {
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5_a-zA-Z0-9]+$");
        if (str.length() >= 1 && str.length() <= 10 && pattern.matcher(str).find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 昵称格式错误提示
     *
     * @param str 昵称
     * @return 错误提示
     */
    public static String nickFormErrorMsg(String str) {
        if (str.length() < 1) {
            return "请输入姓名";

        } else if (str.length() > 10) {
            return "姓名长度必须低于10位";

        } else {
            return "姓名中请勿包含特殊字符";
        }
    }

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr
     *            身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws ParseException
     */
    public static boolean IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return false;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return false;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return false;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return false;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            //有大写X的话通过toLowerCase转成小写
            if (Ai.equals(IDStr.toLowerCase()) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return false;
            }
        } else {
            return true;
        }
        // =====================(end)=====================
        return true;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 验证日期字符串是否是YYYY-MM-DD格式
     *
     * @param str
     * @return
     */
    private static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
            flag = true;
        }
        return flag;
    }
}
