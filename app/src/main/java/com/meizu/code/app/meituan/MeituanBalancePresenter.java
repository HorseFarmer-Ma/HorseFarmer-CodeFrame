/*
 * ************************************************************
 * Class：MeituanBalancePresenter.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 19:56:40
 * Last modified time：2018-09-04 11:49:39
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan;

import com.meizu.code.frame.base.frame.interport.IDataLoader;
import com.meizu.code.frame.base.frame.mvp.BaseDataPresenter;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;

import java.util.List;

/**
 * 测试Presenter
 * <p>
 * Created by mxm on 12/11/17.
 */
public class MeituanBalancePresenter extends BaseDataPresenter<MeituanBalanceView, List<DelegateBlockItem>> {

    @Override
    protected IDataLoader<List<DelegateBlockItem>> createLoader() {
        return new MeituanBalanceLoader();
    }

    /**
     * 刷新页面数据
     */
    public void refreshDate() {
        IDataLoader<List<DelegateBlockItem>> loader = getLoader();
        if (loader != null) {
            loader.doRefresh();
        }
    }
}
