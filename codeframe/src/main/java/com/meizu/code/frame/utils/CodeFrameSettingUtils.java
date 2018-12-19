package com.meizu.code.frame.utils;

/**
 * Created by mxm on 21/01/18.
 */

public class CodeFrameSettingUtils {
    private CodeFrameSettingUtils() {
    }

    private boolean mIsNight;

    private static class CodeFrameSettingHolder {
        private static final CodeFrameSettingUtils HOLDER = new CodeFrameSettingUtils();
    }

    public static CodeFrameSettingUtils getInstance() {
        return CodeFrameSettingHolder.HOLDER;
    }

    public boolean isNight() {
        return mIsNight;
    }

    public void setNight(boolean isNight) {
        mIsNight = isNight;
    }
}
