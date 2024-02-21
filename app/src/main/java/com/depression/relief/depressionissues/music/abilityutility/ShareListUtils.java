package com.depression.relief.depressionissues.music.abilityutility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import com.depression.relief.depressionissues.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class ShareListUtils {
    private static boolean bIsTimerSet = false;
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static void initFirebase(Context context) {
        FirebaseAnalytics instance = FirebaseAnalytics.getInstance(context);
        mFirebaseAnalytics = instance;
        instance.setAnalyticsCollectionEnabled(true);
        mFirebaseAnalytics.setSessionTimeoutDuration(500);
    }

    public static void logFirebaseEvent(String str) {
        String replaceAll = str.replaceAll(" ", "_");
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, replaceAll);
//        mFirebaseAnalytics.logEvent(replaceAll, bundle);
    }

    public static void BatteryPopup(final Context context, final Boolean bool) {
        if (Build.VERSION.SDK_INT >= 23) {
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            AlertDialog show = new AlertDialog.Builder(context).setTitle(R.string.battery_optimization).setMessage(R.string.battery_opt_description_settings).setPositiveButton(R.string.turn_off, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (bool.booleanValue()) {
                        ShareListUtils.logFirebaseEvent("CLKD_Battery_Optimization_in_Settings");
                    } else {
                        ShareListUtils.logFirebaseEvent("CLKD_Battery_Optimization_in_TIMER");
                    }
                    Intent intent = new Intent();
                    intent.setAction("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS");
                    context.startActivity(intent);
                }
            }).setNegativeButton(R.string.maybe_later, (DialogInterface.OnClickListener) null).setIcon(R.drawable.green_battery).show();
            show.getButton(-1).setTextColor(context.getColor(R.color.popup_positive));
            show.getButton(-2).setTextColor(context.getColor(R.color.popup_negative));
        }
    }

    public static int getPendingIntentFlag() {
        return Build.VERSION.SDK_INT >= 31 ? 201326592 : 134217728;
    }

    public static boolean isbIsTimerSet() {
        return bIsTimerSet;
    }

    public static void setbIsTimerSet(boolean z) {
        bIsTimerSet = z;
    }
}
