package com.meizu.code.frame.utils;

/**
 * Created by mxm on 21/01/18.
 */

public class CodeFrameSettingUtils {
    private static CodeFrameSettingUtils sInstance;

    private boolean mIsNight;

    public static CodeFrameSettingUtils getInstance() {
        if (sInstance == null) {
            synchronized (CodeFrameSettingUtils.class) {
                if (sInstance == null) {
                    sInstance = new CodeFrameSettingUtils();
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
