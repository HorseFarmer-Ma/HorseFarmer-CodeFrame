package com.meizu.code.frame.base.frame.mvp;

import android.app.Application;

import com.meizu.code.frame.utils.CodeFrameUtils;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;

/**
 * Application基类
 * <p>
 * Created by mxm on 31/01/18.
 */
public class BaseApplication extends Application {

    @Override
    public final void onCreate() {
        super.onCreate();
        CodeFrameUtils.getInstance().setApplicationContext(this);
        ThreadPoolServiceHelper.getInstance().init();
        onCreateInit();
    }

    protected void onCreateInit() {

    }
}
