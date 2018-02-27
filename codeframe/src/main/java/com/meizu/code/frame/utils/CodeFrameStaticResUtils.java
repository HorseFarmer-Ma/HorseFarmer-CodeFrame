package com.meizu.code.frame.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

/**
 * 本地资源调用公共方法
 * <p>
 * Created by maxueming on 17-11-15.
 */

public class CodeFrameStaticResUtils {
    public static Drawable getDrawable(@DrawableRes int id) {
        return CodeFrameUtils.getInstance().getApplicationContext().getResources().getDrawable(id);
    }
}
