package com.meizu.code.frame.base.mvp.interport;

import android.os.Bundle;

/**
 * 生命周期接口
 * <p>
 * Created by maxueming on 17-11-10.
 */

public abstract class BaseLifeCycle {

    protected abstract void onCreate(Bundle savedInstanceState);

    protected abstract void onActivityCreate();

    protected abstract void onStart();

    protected abstract void onRestart();

    protected abstract void onResume();

    protected abstract void onPause();

    protected abstract void onStop();

    protected abstract void onDestroy();
}
