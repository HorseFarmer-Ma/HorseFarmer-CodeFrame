package com.meizu.code.frame.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.BaseView;

/**
 * Created by mxm on 12/11/17.
 */

public class TestView extends BaseView<TestPresenter> {

    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_test, parent, false);
    }
}
