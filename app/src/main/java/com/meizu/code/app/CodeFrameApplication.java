package com.meizu.code.app;

import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.frame.mvp.BaseApplication;

/**
 * Application类，唯一
 * <p>
 * Created by mxm on 11/11/17.
 */

public class CodeFrameApplication extends BaseApplication {
    private static final String TAG = "CodeFrameApplication";

    @Override
    protected void onCreateInit() {
        super.onCreateInit();
        // 调试内存泄露时打开
//        LeakCanary.install(this);
    }

    @Override
    public void onTerminate() {
        Logger.logD(TAG, "onTerminate execute!");
        super.onTerminate();
    }
}
