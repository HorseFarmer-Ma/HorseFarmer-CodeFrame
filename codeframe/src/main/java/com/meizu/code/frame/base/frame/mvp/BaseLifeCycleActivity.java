package com.meizu.code.frame.base.frame.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.meizu.code.frame.swipeback.SwipeBackActivity;
import com.meizu.code.frame.utils.ActivityManager;
import com.meizu.code.frame.utils.CodeFrameStaticUiUtils;
import com.meizu.code.frame.utils.StatusbarColorUtils;

/**
 * 基本Acticity，存放通用操作，网络管理等初始化
 * <p>
 * Created by maxueming on 17-11-14.
 */

public abstract class BaseLifeCycleActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActivityManager.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
        CodeFrameStaticUiUtils.initThemeActionBar(this);
        StatusbarColorUtils.setStatusBarDarkIcon(this, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
}
