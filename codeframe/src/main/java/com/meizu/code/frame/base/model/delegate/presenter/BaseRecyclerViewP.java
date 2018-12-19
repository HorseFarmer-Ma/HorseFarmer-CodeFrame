package com.meizu.code.frame.base.model.delegate.presenter;

import android.view.Menu;
import android.view.MenuItem;

import com.meizu.code.frame.base.frame.interport.IDataLoader;
import com.meizu.code.frame.base.frame.mvp.BaseDataPresenter;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.view.BaseRecyclerViewV;

import java.util.List;

public class BaseRecyclerViewP<V extends BaseRecyclerViewV> extends BaseDataPresenter<V, List<DelegateBlockItem>> {
    @Override
    protected IDataLoader<List<DelegateBlockItem>> createLoader() {
        return null;
    }
}
