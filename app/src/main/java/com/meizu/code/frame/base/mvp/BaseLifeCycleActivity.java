package com.meizu.code.frame.base.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meizu.code.frame.base.annotations.AnnotationsHelper;

/**
 * 构造MVP框架相关，不可轻易改动
 *
 * @param <T>
 */
public abstract class BaseLifeCycleActivity<T extends BeamView> extends BaseActivity {

    protected T mBeamView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doCreate(savedInstanceState);
        FrameLayout container = new FrameLayout(this);
        setContentView(mBeamView.doCreateView(container, LayoutInflater.from(this)));
    }

    @CallSuper
    private void doCreate(Bundle savedInstanceState) {
        ensureCreateBeamView();
        mBeamView.setContainer(this);
        mBeamView.doCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(createContentView(LayoutInflater.from(this).inflate(layoutResID, null)));
    }

    @Override
    public void setContentView(@NonNull View view) {
        super.setContentView(createContentView(view));
    }

    @Override
    public void setContentView(@NonNull View view, ViewGroup.LayoutParams params) {
        super.setContentView(createContentView(view), params);
    }

    /**
     * 生成底层View
     */
    private View createContentView(@NonNull View view) {
        FrameLayout baseLayout = new FrameLayout(this);
        baseLayout.setFitsSystemWindows(fitsSystemWindows());
        baseLayout.addView(view, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return baseLayout;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBeamView.doActivityResult(requestCode, resultCode, data);
    }

    protected boolean fitsSystemWindows() {
        return true;
    }

    private void ensureCreateBeamView() {
        if (mBeamView == null) {
            mBeamView = AnnotationsHelper.createBeamClass(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBeamView.doStart();
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
        mBeamView.doRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBeamView.doResume();
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
        mBeamView.doPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBeamView.doStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBeamView.doDestroy();
        mBeamView = null;
    }
}
