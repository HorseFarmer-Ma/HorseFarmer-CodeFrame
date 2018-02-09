package com.meizu.code.app;

import com.meizu.code.frame.base.frame.mvp.BaseApplication;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;

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
