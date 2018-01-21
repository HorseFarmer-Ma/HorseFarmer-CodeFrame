package com.meizu.code.frame;

import android.app.Application;

import com.meizu.code.frame.utils.CodeFrameUtil;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application类，唯一
 * <p>
 * Created by mxm on 11/11/17.
 */

public class CodeFrameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        LeakCanary.install(this);
        CodeFrameUtil.getInstance().setGlobeContext(this);
        ThreadPoolServiceHelper.getInstance().init();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
