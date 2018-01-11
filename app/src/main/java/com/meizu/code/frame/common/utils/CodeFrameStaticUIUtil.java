package com.meizu.code.frame.common.utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.view.Window;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;

/**
 * UI设置公共方法
 * <p>
 * Created by maxueming on 17-11-15.
 */

public class CodeFrameStaticUIUtil {

    /**
     * Android N 设置沉浸式状态栏
     *
     * @param window
     */
    public static void setWindowTranslucentStatus(Window window) {
        if (window == null) {
            return;
        }
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                field.setAccessible(true);
                //改为透明
                field.setInt(window.getDecorView(), Color.TRANSPARENT);
            } catch (Exception e) {
                Logger.e("Android N setWindowTranslucentStatus failed!");
            }
        }
    }

    /**
     * 去除ActionBar阴影，设置actionBar颜色为白色
     *
     * @param actionBar
     */
    public static void initBeamViewActionBar(ActionBar actionBar) {
        if (actionBar == null) {
            return;
        }
        actionBar.setElevation(0);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
    }
}
