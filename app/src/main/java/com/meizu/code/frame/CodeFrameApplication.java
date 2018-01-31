package com.meizu.code.frame;

import android.app.Application;

import com.meizu.code.frame.base.frame.mvp.BaseApplication;
import com.meizu.code.frame.utils.CodeFrameUtil;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;
import com.squareup.leakcanary.LeakCanary;

/**
 * Application类，唯一
 * <p>
 * Created by mxm on 11/11/17.
 */

public class CodeFrameApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        ThreadPoolServiceHelper.getInstance().init();
    }
}
