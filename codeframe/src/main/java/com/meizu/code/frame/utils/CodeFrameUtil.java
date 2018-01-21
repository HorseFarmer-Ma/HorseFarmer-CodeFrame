package com.meizu.code.frame.utils;

import android.content.Context;

/**
 * 储存全局变量等
 * <p>
 * Created by mxm on 21/01/18.
 */

public class CodeFrameUtil {
    private Context mGlobeContext;
    private static CodeFrameUtil sInstance;

    public static CodeFrameUtil getInstance() {
        if (sInstance == null) {
            synchronized (CodeFrameUtil.class) {
                if (sInstance == null) {
                    sInstance = new CodeFrameUtil();
                }
            }
        }
        return sInstance;
    }

    public Context getGlobeContext() {
        return mGlobeContext;
    }

    public void setGlobeContext(Context globeContext) {
        this.mGlobeContext = globeContext;
    }

    // 在退出APP后需要销毁
    public void onDestroy() {
    }
}
