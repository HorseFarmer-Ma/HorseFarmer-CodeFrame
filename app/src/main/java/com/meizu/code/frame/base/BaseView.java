package com.meizu.code.frame.base;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by maxueming on 17-11-9.
 */

public abstract class BaseView<T extends BasePresenter> implements ILifeCycle{

    private Activity mActivity;

    protected void setActivity(Context context) {
        if (context instanceof Activity) {
            mActivity = (Activity) context;
        }
    }

    protected Activity getActicity () {
        return mActivity;
    }

    public void doCreate(Context context, Bundle savedInstanceState) {
        setActivity(context);
        this.onCreate(savedInstanceState);
    }

    protected abstract View onCreateView (ViewGroup parent, LayoutInflater inflater);

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
