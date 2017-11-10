package com.meizu.code.frame.base;

import android.os.Bundle;

/**
 * 生命周期接口
 * <p>
 * Created by maxueming on 17-11-10.
 */

public interface ILifeCycle {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
