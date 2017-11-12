package com.meizu.code.frame.test;

import com.meizu.code.frame.base.BaseActivity;

/**
 * Created by mxm on 12/11/17.
 */

public class TestActicity extends BaseActivity<TestView>{
    @Override
    protected Class<TestView> getBeamViewClass() {
        return TestView.class;
    }

    @Override
    protected boolean fitsSystemWindows() {
        return true;
    }
}
