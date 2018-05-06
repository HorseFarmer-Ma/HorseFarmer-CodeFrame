package com.meizu.code.app.test;

import android.os.Bundle;

import com.meizu.code.frame.base.frame.mvp.BaseDataPresenter;
import com.meizu.code.frame.base.frame.interport.IDataLoader;
import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;

import java.util.List;

/**
 * 测试Presenter
 * <p>
 * Created by mxm on 12/11/17.
 */
public class TestPresenter extends BaseDataPresenter<TestView, List<DelegateBlockItem>> {

    @Override
    protected IDataLoader<List<DelegateBlockItem>> createLoader() {
        return new TestLoader();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
