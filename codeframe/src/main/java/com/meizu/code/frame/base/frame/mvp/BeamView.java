package com.meizu.code.frame.base.frame.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.base.frame.interport.BaseLifeCycle;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;

/**
 * 基本View，页面View渲染层
 * <p>
 * Created by maxueming on 17-11-9.
 */
public abstract class BeamView<T extends BasePresenter> extends BaseLifeCycle {

    private static final String TAG = "BeamView";
    private Activity mActivity;
    private View mRootView;
    private ManagerBetweenVP<T> mManagerBetweenVP;

    public BeamView() {
        mManagerBetweenVP = new ManagerBetweenVP<>(this);
    }

    protected void setContainer(Object container) {
        if (container instanceof Activity) {
            mActivity = (Activity) container;
        } else if (container instanceof Fragment) {
            mActivity = ((Fragment) container).getActivity();
        } else {
            if (container instanceof View) {
                mActivity = CodeFrameStaticUtils.scanActivityFromView((View) container);
            } else {
                throw new IllegalArgumentException("container of BeamView should be instance of Activity or Fragment or View");
            }
        }
        if (mActivity == null) throw new IllegalArgumentException("mActivity is null, please check your code first!");
        mManagerBetweenVP.setContainer(mActivity);
    }

    protected T getPresenter() {
        return mManagerBetweenVP.getPresenter();
    }

    protected Activity getActivity() {
        return mActivity;
    }

    protected View findViewById(int id) {
        return getActivity().findViewById(id);
    }

    protected View getRootView() {
        return mRootView;
    }

    protected void doActivityResult(int requestCode, int resultCode, Intent data) {
        this.onActivityResult(requestCode, resultCode, data);
        mManagerBetweenVP.doActivityResult(requestCode, resultCode, data);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    @CallSuper
    protected void doCreate(Bundle savedInstanceState) {
        this.onCreate(savedInstanceState);
        mManagerBetweenVP.doCreate(savedInstanceState);
    }

    @CallSuper
    protected View doCreateView(ViewGroup parent, LayoutInflater inflater) {
        mRootView = onCreateView(parent, inflater);
        if (mRootView == null) throw new IllegalArgumentException("BeamView: mRootView is null");
        onActivityCreate();
        mManagerBetweenVP.doActivityCreate();
        return mRootView;
    }

    @CallSuper
    protected void doStart() {
        this.onStart();
        mManagerBetweenVP.doStart();
    }

    @CallSuper
    protected void doRestart() {
        this.onRestart();
        mManagerBetweenVP.doRestart();
    }

    @CallSuper
    protected void doResume() {
        this.onResume();
        mManagerBetweenVP.doResume();
    }

    @CallSuper
    protected void doPause() {
        this.onPause();
        mManagerBetweenVP.doPause();
    }

    @CallSuper
    protected void doStop() {
        this.onStop();
        mManagerBetweenVP.doStop();
    }

    @CallSuper
    protected void doDestroy() {
        this.onDestroy();
        mManagerBetweenVP.doDestroy();
        mManagerBetweenVP = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityCreate() {

    }

    @Override
    protected boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onOptionsMenuClosed(Menu menu) {
    }

    protected abstract View onCreateView(ViewGroup parent, LayoutInflater inflater);

    @Override
    protected void onStart() {

    }

    @Override
    protected void onRestart() {

    }

    @Override
    protected void onResume() {

    }

    @Override
    protected void onPause() {

    }

    @Override
    protected void onStop() {

    }

    @Override
    protected void onDestroy() {
        mActivity = null;
    }
}
