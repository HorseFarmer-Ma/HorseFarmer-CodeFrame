package com.meizu.code.frame.utils;

import android.content.Context;

/**
 * 储存全局变量等
 * <p>
 * Created by mxm on 21/01/18.
 */

public class CodeFrameUtils {
    private Context mApplicationContext;
    private static CodeFrameUtils sInstance;

    public static CodeFrameUtils getInstance() {
        if (sInstance == null) {
            synchronized (CodeFrameUtils.class) {
                if (sInstance == null) {
                    sInstance = new CodeFrameUtils();
                }
            }
        }
        return sInstance;
    }

    public Context getApplicationContext() {
        return mApplicationContext;
    }

    public void setApplicationContext(Context context) {
        this.mApplicationContext = context;
    }

    // 在退出APP后需要销毁
    public void onDestroy() {
    }
}
