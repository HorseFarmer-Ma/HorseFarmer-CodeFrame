package com.meizu.code.frame.base.mvp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.base.interport.ILifeCycle;

/**
 * 基本View
 *
 * Created by maxueming on 17-11-9.
 */
public abstract class BeamView<T extends BasePresenter> implements ILifeCycle {

    private Activity mActivity;
    private View mRootView;

    private void setContainer(Object container) {

        if (container instanceof Activity) {
            mActivity = (Activity) container;
        } else if (container instanceof Fragment) {
            mActivity = ((Fragment) container).getActivity();
        } else {
            if (container instanceof View) {
                Context context = ((View) container).getContext();
                if (context instanceof Activity) {
                    mActivity = (Activity) context;
                    return;
                }
            }
            throw new IllegalArgumentException("container of BeamView should be instance of Activity or Fragment");
        }
    }

    protected Activity getActivity() {
        return mActivity;
    }

    public View getRootView() {
        return mRootView;
    }

    protected View doCreateView(ViewGroup parent, LayoutInflater inflater) {
        mRootView = onCreateView (parent, inflater);
        return mRootView;
    }

    protected abstract View onCreateView (ViewGroup parent, LayoutInflater inflater);

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void doCreate(Object container, Bundle savedInstanceState) {
        this.onCreate(savedInstanceState);
        setContainer(container);
    }

    protected void doStart() {
        this.onStart();
    }

    protected void doRestart() {
        this.onRestart();
    }

    protected void doResume() {
        this.onResume();
    }

    protected void doPause() {
        this.onPause();
    }

    protected void doStop() {
        this.onStop();
    }

    protected void doDestroy() {
        this.onDestroy();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onRestart() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }
}
