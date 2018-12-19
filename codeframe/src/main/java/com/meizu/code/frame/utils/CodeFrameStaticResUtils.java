package com.meizu.code.frame.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

/**
 * 本地资源调用公共方法
 * <p>
 * Created by maxueming on 17-11-15.
 */

public class CodeFrameStaticResUtils {
    private CodeFrameStaticResUtils() {
    }

    public static Drawable getDrawable(@DrawableRes int id) {
        Context context = CodeFrameUtils.getInstance().getGlobalContext();
        if (context != null) {
            return context.getResources().getDrawable(id);
        }
        return null;
    }

    public static int getColor(@ColorRes int id) {
        Context context = CodeFrameUtils.getInstance().getGlobalContext();
        if (context != null) {
            return context.getResources().getColor(id);
        }
        return 0;
    }

    public static String getString(@StringRes int id) {
        Context context = CodeFrameUtils.getInstance().getGlobalContext();
        if (context != null) {
            return context.getResources().getString(id);
        }
        return "";
    }

    public static int getDimensionPixelOffset(@DimenRes int id) {
        Context context = CodeFrameUtils.getInstance().getGlobalContext();
        if (context != null) {
            return context.getResources().getDimensionPixelOffset(id);
        }
        return 0;
    }
}
