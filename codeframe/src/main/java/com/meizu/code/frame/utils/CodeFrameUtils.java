package com.meizu.code.frame.utils;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * 储存全局变量等
 * <p>
 * Created by mxm on 21/01/18.
 */
public class CodeFrameUtils {
    private Context mApplicationContext;

    private CodeFrameUtils() {
    }

    @SuppressLint("StaticFieldLeak")
    private static class CodeFrameUtilsHolder {
        private static final CodeFrameUtils HOLDER = new CodeFrameUtils();
    }

    public static CodeFrameUtils getInstance() {
        return CodeFrameUtilsHolder.HOLDER;
    }

    public Context getGlobalContext() {
        if (mApplicationContext == null) {
            throw new IllegalArgumentException("CodeFrameUtils: The context has not been inited yet!!!");
        }
        return mApplicationContext;
    }

    public void setApplicationContext(Context context) {
        this.mApplicationContext = context;
    }

    // 在退出APP后需要销毁
    public void destroyInternal() {
    }
}
