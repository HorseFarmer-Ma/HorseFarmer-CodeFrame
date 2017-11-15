package com.meizu.code.frame.test;

import com.meizu.code.frame.base.annotations.RequireBeamView;
import com.meizu.code.frame.base.mvp.BaseLifeCycleActivity;

/**
 * Created by mxm on 12/11/17.
 */
@RequireBeamView(TestView.class)
public class TestActicity extends BaseLifeCycleActivity<TestView> {
}
