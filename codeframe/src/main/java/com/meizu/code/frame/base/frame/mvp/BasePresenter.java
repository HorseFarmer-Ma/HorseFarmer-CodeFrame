package com.meizu.code.frame.base.frame.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.Menu;
import android.view.MenuItem;

import com.meizu.code.frame.base.frame.interport.BaseLifeCycle;

/**
 * 基本Presenter，数据逻辑处理层
 * <p>
 * Created by maxueming on 17-11-9.
 */

public abstract class BasePresenter<V> extends BaseLifeCycle {

    private static final String TAG = "BasePresenter";
    private Activity mActivity;
    private V mBeamView;
    private String mPresenterId;

    protected void setContainer(Activity activity) {
        mActivity = activity;
    }

    protected void setPresenterId(String id) {
        this.mPresenterId = id;
    }

    public String getPresenterId() {
        return mPresenterId;
    }

    protected V getView() {
        return mBeamView;
    }

    protected void setBeamView(V view) {
        this.mBeamView = view;
    }

    protected Activity getActivity() {
        return mActivity;
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
    }

    @Override
    @CallSuper
    protected void onActivityCreate() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @Override
    protected boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onOptionsMenuClosed(Menu menu) {

    }

    @Override
    @CallSuper
    protected void onStart() {
    }

    @Override
    @CallSuper
    protected void onRestart() {
    }

    @Override
    @CallSuper
    protected void onResume() {
    }

    @Override
    @CallSuper
    protected void onPause() {
    }

    @Override
    @CallSuper
    protected void onStop() {
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        mActivity = null;
        mBeamView = null;
        PresenterManager.getInstance().destroyPresenter(mPresenterId);
    }
}
