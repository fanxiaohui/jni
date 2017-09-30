package com.vision.smarthomeapi.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.vision.smarthomeapi.bll.Controller;

/**
 * SharedPreferences封装
 * Created by yangle on 2017/2/7.
 */
public class PrefUtils {

    private static final String PREF_NAME = "smarthome_config";

    private static SharedPreferences sp;

    public static void putBoolean(String key, boolean value) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = Controller.getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }
}
