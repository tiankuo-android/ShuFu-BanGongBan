package com.wfkj.android.smartoffice.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wangdongyang on 16/11/17.
 * 本地存储和查看工具
 */
public class FileUtil {

    public static void saveString(Context ctx, String name, String value) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public static String loadString(Context ctx, String key) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        String result = sp.getString(key, "");

        return result;
    }

    public static void saveInt(Context ctx, String name, int value) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public static int loadInt(Context ctx, String name) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME,
                Integer.MIN_VALUE);
        int result = sp.getInt(name, -1);
        return result;
    }

    public static int loadInt(Context ctx, String name, int defaultValue) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        int result = sp.getInt(name, defaultValue);
        return result;
    }

    public static void saveLong(Context ctx, String name, long value) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(name, value);
        editor.commit();
    }

    public static long loadLong(Context ctx, String name) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        long result = sp.getLong(name, 0);
        return result;
    }

    public static void saveBoolean(Context ctx, String name, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean loadBoolean(Context ctx, String name,
                                      boolean defaultvalue) {
        SharedPreferences sp = ctx.getSharedPreferences(Constants.APP_NAME, 0);
        boolean result = sp.getBoolean(name, defaultvalue);
        return result;
    }

    public static void clearValue(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constants.APP_NAME,
                0);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }


}
