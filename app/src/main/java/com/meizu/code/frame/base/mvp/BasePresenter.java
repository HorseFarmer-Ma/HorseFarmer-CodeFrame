package com.meizu.code.frame.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.meizu.code.frame.base.interport.BaseLifeCycle;

/**
 * 基本Presenter
 *
 * Created by maxueming on 17-11-9.
 */

public abstract class BasePresenter<M extends BeamView> extends BaseLifeCycle {

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

    protected BeamView getBeamView() {
        return mBeamView;
    }

    protected void setBeamView(BeamView view) {
        this.mBeamView = (M) view;

    }

    protected Activity getActivity() {
        return mActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

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
        mBeamView = null;
        PresenterManager.getInstance().destroyPresenter(mPresenterId);
    }
}
