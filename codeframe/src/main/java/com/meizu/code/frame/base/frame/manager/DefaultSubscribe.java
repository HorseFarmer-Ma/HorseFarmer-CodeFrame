package com.meizu.code.frame.base.frame.manager;

import android.util.Log;

import com.meizu.code.frame.base.frame.common.log.Logger;

import rx.Subscriber;

/**
 * 默认订阅
 * <p>
 * Created by mxm on 17:20.
 */
public class DefaultSubscribe<T> extends Subscriber<T> {
    private static final String TAG = "DefaultSubscribe";

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Logger.logE(TAG, "onError() = " + Log.getStackTraceString(e));
    }

    @Override
    public void onNext(T t) {
        Logger.logD(TAG, "onNext() = " + t.toString());
    }
}
