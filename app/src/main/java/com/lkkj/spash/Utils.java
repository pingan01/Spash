package com.lkkj.spash;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.lkkj.spash.SpashActivity.getDeviceBrand;

/**
 * Created on 2019/5/22.17:40
 *
 * @author Administrator
 */
public class Utils {

    /**
     * 沉浸 状态栏黑色字体
     *
     * @param activity
     */
    public static void setStatusBlackTransparent(Activity activity) {
        Window window = activity.getWindow();
        window.getAttributes().flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        String brand = getDeviceBrand();
        if ("Xiaomi".equalsIgnoreCase(brand)) {
            //针对小米
            Class clazz = window.getClass();
            try {
                int darkModeFlag;

                @SuppressLint("PrivateApi")
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");

                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");

                darkModeFlag = field.getInt(layoutParams);

                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);

                extraFlagField.invoke(window, darkModeFlag, darkModeFlag);

            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e(e.getMessage(), String.valueOf(e));
                }
            }
        } else if ("Meizu".equalsIgnoreCase(brand)) {
            //针对魅族
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                value = value | bit;
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
            } catch (Exception e) {
                if (BuildConfig.DEBUG) {
                    Log.e(e.getMessage(), String.valueOf(e));
                }
            }

        }
        if ("Xiaomi".equalsIgnoreCase(brand)) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                @SuppressLint("PrivateApi")
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                //  //改为透明
                field.setInt(window.getDecorView(), Color.TRANSPARENT);
                field.setAccessible(false);
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * 沉浸白色字体
     * @param activity
     */
    public static void setStatusWhiteTransparent(Activity activity) {
        Window window = activity.getWindow();
        window.getAttributes().flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                @SuppressLint("PrivateApi")
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                //改为透明
                field.setInt(window.getDecorView(), Color.TRANSPARENT);
                field.setAccessible(false);
            } catch (Exception ignore) {
            }
        }
    }
}
