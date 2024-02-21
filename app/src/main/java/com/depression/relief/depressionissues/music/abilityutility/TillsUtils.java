package com.depression.relief.depressionissues.music.abilityutility;

import android.content.Context;
import android.util.SparseArray;

import com.depression.relief.depressionissues.music.customhelper.Helpers;
import com.depression.relief.depressionissues.music.modelizer.AudioItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TillsUtils {
    public static List<AudioItem> copy(List<AudioItem> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (AudioItem soundItem : list) {
            arrayList.add(new AudioItem(soundItem));
        }
        return arrayList;
    }

    public static <C> List<C> asList(SparseArray<C> sparseArray) {
        if (sparseArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++) {
            arrayList.add(sparseArray.valueAt(i));
        }
        return arrayList;
    }

    public static void saveDayCheck(Context context, boolean[] zArr) {
        String str = "";
        for (int i = 0; i < zArr.length; i++) {
            if (i < zArr.length - 1) {
                str = str + zArr[i] + ";";
            } else {
                str = str + zArr[i];
            }
        }
        SharedPrefsUtils.setStringPreference(context, Helpers.KEY_ALARM_DAY, str);
    }

    public static boolean[] dayParcerCheck(Context context) {
        boolean[] zArr = new boolean[7];
        String stringPreference = SharedPrefsUtils.getStringPreference(context, Helpers.KEY_ALARM_DAY);
        if (stringPreference == null) {
            stringPreference = "";
        }
        String[] split = stringPreference.split(";");
        if (split.length == 7) {
            for (int i = 0; i < 7; i++) {
                zArr[i] = Boolean.parseBoolean(split[i]);
            }
        } else {
            for (int i2 = 0; i2 < 7; i2++) {
                zArr[i2] = false;
            }
        }
        return zArr;
    }

    public static boolean remindtheDays(Context context) {
        if (dayParcerCheck(context)[Calendar.getInstance().get(7) - 1]) {
            return true;
        }
        return false;
    }
}
