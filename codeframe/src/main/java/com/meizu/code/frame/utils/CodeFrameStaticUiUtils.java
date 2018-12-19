package com.meizu.code.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.frame.common.log.Logger;

import java.lang.reflect.Field;

/**
 * UI设置公共方法
 * <p>
 * Created by maxueming on 17-11-15.
 */

public class CodeFrameStaticUiUtils {
    private CodeFrameStaticUiUtils() {
    }

    private static final String TAG = "CodeFrameStaticUiUtils";

    /**
     * @param activity
     * @deprecated Android N 设置沉浸式状态栏
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
                Logger.logE(TAG, "Android N setWindowTranslucentStatus failed!");
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
        // 去除ActionBar阴影
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

    public static void hideActionBar(Activity activity) {
        ActionBar actionBar = getActionBar(activity);
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    public static void showKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) return null;
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        // 创建拷贝，因为禁用cache后位图会被回收
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
