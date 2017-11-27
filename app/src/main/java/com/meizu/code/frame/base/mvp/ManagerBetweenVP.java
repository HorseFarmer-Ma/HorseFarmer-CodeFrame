package com.meizu.code.frame.base.mvp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 中介，处理BeamView和Presenter逻辑
 *
 * Created by maxueming on 17-11-16.
 */

public class ManagerBetweenVP<T extends BasePresenter> {
    private T mPresenter;

    public ManagerBetweenVP(BeamView view) {
        mPresenter = PresenterManager.getInstance().createPresenter(PresenterManager.NON_PRESENTER_ID, view);
    }

    protected void setContainer(Activity activity) {
        mPresenter.setContainer(activity);
    }

    public T getPresenter() {
        return mPresenter;
    }

    protected void doActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    protected void doCreate(Bundle savedInstanceState) {
        mPresenter.onCreate(savedInstanceState);
    }

    protected void doStart() {
        mPresenter.onStart();
    }

    protected void doRestart() {
        mPresenter.onRestart();
    }

    protected void doResume() {
        mPresenter.onResume();
    }

    protected void doPause() {
        mPresenter.onPause();
    }

    protected void doStop() {
        mPresenter.onStop();
    }

    protected void doDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
    }
}
