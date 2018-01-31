package com.meizu.code.frame.base.frame.mvp;

import android.app.Application;

import com.meizu.code.frame.utils.CodeFrameUtil;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application基类
 * <p>
 * Created by mxm on 31/01/18.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 调试内存泄露时打开
        LeakCanary.install(this);
        CodeFrameUtil.getInstance().setApplicationContext(this);
    }
}
