package com.meizu.code.frame.utils;

/**
 * Created by mxm on 21/01/18.
 */

public class CodeFrameSettingUtil {
    private static CodeFrameSettingUtil sInstance;

    private boolean mIsNight;

    public static CodeFrameSettingUtil getInstance() {
        if (sInstance == null) {
            synchronized (CodeFrameSettingUtil.class) {
                if (sInstance == null) {
                    sInstance = new CodeFrameSettingUtil();
                }
            }
        }
        return sInstance;
    }

    public boolean isNight() {
        return mIsNight;
    }

    public void setNight(boolean isNight) {
        mIsNight = isNight;
    }
}
