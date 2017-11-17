package com.meizu.code.frame.test;

import android.os.Bundle;

import com.meizu.code.frame.base.mvp.BasePresenter;
import com.orhanobut.logger.Logger;

/**
 * Created by mxm on 12/11/17.
 */

public class TestPresenter extends BasePresenter<TestView> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.e("测试", "....");
    }
}
