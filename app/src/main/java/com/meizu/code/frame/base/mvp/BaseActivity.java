package com.meizu.code.frame.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.meizu.code.frame.common.utils.CodeFrameStaticUIUtil;

/**
 * 基本Acticity，存放通用操作，网络管理等初始化
 *
 * Created by maxueming on 17-11-14.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CodeFrameStaticUIUtil.setWindowTranslucentStatus(getWindow());
        CodeFrameStaticUIUtil.initBeamViewActionBar(getSupportActionBar());
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
        super.onDestroy();
    }
}
