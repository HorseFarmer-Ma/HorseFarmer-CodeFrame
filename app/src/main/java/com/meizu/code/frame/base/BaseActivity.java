package com.meizu.code.frame.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.app.AppCompatActivity;

import com.meizu.code.frame.R;

public abstract class BaseActivity<T extends BaseView> extends AppCompatActivity {

    private T mBeamView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ensureCreateBeamView();
    }

    private void ensureCreateBeamView() {
        if (mBeamView == null) {
            mBeamView = createBeamView();
        }
    }

    protected abstract T createBeamView();

    @CallSuper
    protected abstract T getBeamViewClass();

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 重载Bundle数据
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 保存bundle数据，在OnDestroy之前调用
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
