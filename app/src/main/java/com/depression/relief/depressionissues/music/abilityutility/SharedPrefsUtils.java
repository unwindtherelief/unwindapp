package com.depression.relief.depressionissues.music.abilityutility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.depression.relief.depressionissues.music.customhelper.Helpers;


public final class SharedPrefsUtils {
    private SharedPrefsUtils() {
    }

    public static String getStringPreference(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getString(str, (String) null);
        }
        return null;
    }

    public static boolean setStringPreference(Context context, String str, String str2) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences == null || TextUtils.isEmpty(str)) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(str, str2);
        return edit.commit();
    }

    public static float getFloatPreference(Context context, String str, float f) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        return sharedPreferences != null ? sharedPreferences.getFloat(str, f) : f;
    }

    public static boolean setFloatPreference(Context context, String str, float f) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(str, f);
        return edit.commit();
    }

    public static long getLongPreference(Context context, String str, long j) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        return sharedPreferences != null ? sharedPreferences.getLong(str, j) : j;
    }

    public static boolean setLongPreference(Context context, String str, long j) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(str, j);
        return edit.commit();
    }

    public static int getIntegerPreference(Context context, String str, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        return sharedPreferences != null ? sharedPreferences.getInt(str, i) : i;
    }

    public static boolean setIntegerPreference(Context context, String str, int i) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(str, i);
        return edit.commit();
    }

    public static boolean getBooleanPreference(Context context, String str, boolean z) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        return sharedPreferences != null ? sharedPreferences.getBoolean(str, z) : z;
    }

    public static boolean setBooleanPreference(Context context, String str, boolean z) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Helpers.KEY_USER_DATA, 0);
        if (sharedPreferences == null) {
            return false;
        }
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(str, z);
        return edit.commit();
    }
}
