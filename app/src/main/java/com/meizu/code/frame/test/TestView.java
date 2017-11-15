package com.meizu.code.frame.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.mvp.BeamView;
import com.meizu.code.frame.base.annotations.RequirePresenter;

/**
 * Created by mxm on 12/11/17.
 */
@RequirePresenter(TestPresenter.class)
public class TestView extends BeamView<TestPresenter> {

    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        return inflater.inflate(R.layout.activity_test, parent, false);
    }
}
