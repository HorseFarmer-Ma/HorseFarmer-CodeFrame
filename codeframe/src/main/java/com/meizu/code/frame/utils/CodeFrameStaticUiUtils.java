package com.meizu.code.frame.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.meizu.code.frame.R;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

/**
 * UI设置公共方法
 * <p>
 * Created by maxueming on 17-11-15.
 */

public class CodeFrameStaticUiUtils {

    /**
     * Android N 设置沉浸式状态栏
     *
     * @param activity
     */
    public static void setWindowTranslucentStatus(@NonNull Activity activity) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                // 改为透明
                field.setInt(window.getDecorView(), Color.TRANSPARENT);
            } catch (Exception e) {
                Logger.e("Android N setWindowTranslucentStatus failed!");
            }
        }
    }

    /**
     * 去除ActionBar阴影，设置actionBar颜色为白色
     *
     * @param activity
     */
    public static void initThemeActionBar(@NonNull Activity activity) {
        ActionBar actionBar = getActionBar(activity);
        if (actionBar == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            actionBar.setElevation(0);
        }
        actionBar.setBackgroundDrawable(CodeFrameStaticResUtils.getDrawable(
                CodeFrameSettingUtils.getInstance().isNight() ?
                        R.color.night_background : R.color.day_background));
    }

    public static ActionBar getActionBar(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            return ((AppCompatActivity) activity).getSupportActionBar();
        }
        return null;
    }
}
