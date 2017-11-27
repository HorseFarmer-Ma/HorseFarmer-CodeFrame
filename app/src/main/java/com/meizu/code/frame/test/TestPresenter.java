package com.meizu.code.frame.test;

import com.meizu.code.frame.base.mvp.BaseDataPresenter;
import com.meizu.code.frame.base.mvp.data.CommonLoadParmas;
import com.meizu.code.frame.base.mvp.interport.IDataLoader;

import java.util.List;

/**
 * 测试Presenter
 *
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
        getLoader().doOtherTask(CommonLoadParmas.TEST_LOAD_PARMAS2);
    }
}
