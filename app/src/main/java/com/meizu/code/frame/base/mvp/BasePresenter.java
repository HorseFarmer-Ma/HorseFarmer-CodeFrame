package com.meizu.code.frame.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;

import com.meizu.code.frame.base.mvp.interport.BaseLifeCycle;

/**
 * 基本Presenter，数据逻辑处理层
 *
 * Created by maxueming on 17-11-9.
 */

public abstract class BasePresenter<M extends BeamView> extends BaseLifeCycle{

    private static final String TAG = "BasePresenter";
    private Activity mActivity;
    private M mBeamView;
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

    protected M getView() {
        return mBeamView;
    }

    protected void setBeamView(M view) {
        this.mBeamView = view;
    }

    protected Activity getActivity() {
        return mActivity;
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
