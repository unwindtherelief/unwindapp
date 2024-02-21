package com.depression.relief.depressionissues.music.abilityutility;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class Screentils {
    private static DisplayMetrics getDisplayMetrics(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getScreenHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getNavBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return resources.getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static int getActionBarHeight(Context context) {
        Resources resources = context.getResources();
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843499, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, resources.getDisplayMetrics());
        }
        return 0;
    }

    public static int dpToPx(float f) {
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int i) {
        return (int) (((float) i) / Resources.getSystem().getDisplayMetrics().density);
    }

    public static void marginStatusBar(Activity activity, View view, int i) {
        int statusBarHeight = getStatusBarHeight(activity);
        activity.getWindow().setStatusBarColor(i);
        activity.getWindow().getDecorView().setSystemUiVisibility(1280);
        view.setPadding(0, statusBarHeight, 0, 0);
    }

    public static void hideActionBar(AppCompatActivity appCompatActivity) {
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().hide();
        }
    }

    public static void showActionBar(AppCompatActivity appCompatActivity) {
        if (appCompatActivity.getSupportActionBar() != null) {
            appCompatActivity.getSupportActionBar().show();
        }
    }

    public static void setFullScreenActivity(AppCompatActivity appCompatActivity) {
        appCompatActivity.getWindow().getDecorView().setSystemUiVisibility(1280);
        appCompatActivity.getWindow().setStatusBarColor(1140850688);
    }

    public static void marginStatusBar(Activity activity, View view) {
        view.setPadding(0, getStatusBarHeight(activity), 0, 0);
    }

    public static void resetMarginStatusBar(Activity activity, View view) {
        view.setPadding(0, 0, 0, 0);
    }

    public static void setStatusColor(Activity activity, int i) {
        if (i == 0) {
            activity.getWindow().setStatusBarColor(1140850688);
        } else {
            activity.getWindow().setStatusBarColor(i);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(1280);
    }

    public void turnOffToolbarScrolling(Toolbar toolbar, AppBarLayout appBarLayout) {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setScrollFlags(0);
        toolbar.setLayoutParams(layoutParams);
        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParams2.setBehavior((CoordinatorLayout.Behavior) null);
        appBarLayout.setLayoutParams(layoutParams2);
    }

    public void turnOnToolbarScrolling(Toolbar toolbar, AppBarLayout appBarLayout) {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        layoutParams.setScrollFlags(5);
        toolbar.setLayoutParams(layoutParams);
        CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        layoutParams2.setBehavior(new AppBarLayout.Behavior());
        appBarLayout.setLayoutParams(layoutParams2);
    }

    public static int m18447a(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int m18448a(Context context, float f, float f2) {
        int screenWidth = getScreenWidth(context);
        int screenHeight = getScreenHeight(context);
        double d = (double) (((float) screenWidth) * f);
        Double.isNaN(d);
        int i = (int) (d * 0.01d);
        double d2 = (double) (((float) screenHeight) * f2);
        Double.isNaN(d2);
        int i2 = (int) (d2 * 0.01d);
        return i2 < i ? i2 : i;
    }

    public static boolean isBiggerDevice(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) > 6.9d;
    }

    public static boolean isSmallerDevice(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        return Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) < 5.8d;
    }

    public static boolean isLargeSizeDevice(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static String getUsableAndRealHeightInInches(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float f = ((float) displayMetrics.widthPixels) / displayMetrics.xdpi;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        float f2 = ((float) displayMetrics.heightPixels) / displayMetrics.ydpi;
        double d = (double) f;
        return String.format("R %.2f U %.2f", new Object[]{Double.valueOf(Math.sqrt(Math.pow(d, 2.0d) + Math.pow((double) f2, 2.0d))), Double.valueOf(Math.sqrt(Math.pow(d, 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)))});
    }

    public static String displayTempData(Activity activity) {
        String str = isLargeSizeDevice(activity) ? "LSize" : "Phone";
        return str + " " + getUsableAndRealHeightInInches(activity);
    }
}
