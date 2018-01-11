package com.meizu.code.frame.base;

import android.app.Application;

import com.meizu.code.frame.common.utils.ThreadPoolServiceHelper;

/**
 * Application类，唯一
 * <p>
 * Created by mxm on 11/11/17.
 */

public class CodeFrameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ThreadPoolServiceHelper.getInstance().init();
    }
}
