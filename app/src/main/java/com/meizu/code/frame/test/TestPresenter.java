package com.meizu.code.frame.test;

import android.os.Bundle;

import com.meizu.code.frame.base.mvp.BaseDataPresenter;
import com.meizu.code.frame.base.mvp.interport.IDataLoader;

import java.util.List;

/**
 * 测试Presenter
 * <p>
 * Created by mxm on 12/11/17.
 */

public class TestPresenter extends BaseDataPresenter<TestView, List<String>> {

    @Override
    protected IDataLoader<List<String>> createLoader() {
        return new TestLoader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoader().doExtraTask(TestLoadTypeParmas.LOAD2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
