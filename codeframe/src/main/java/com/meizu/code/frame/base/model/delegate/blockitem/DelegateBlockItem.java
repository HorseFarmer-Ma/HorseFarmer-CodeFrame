/*
 * ************************************************************
 * Class：DelegateBlockItem.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:56:43
 * Last modified time：2018-11-06 14:24:05
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.delegate.blockitem;

/**
 * 列表Item抽象化
 * <p>
 * Created by mxm on 16/02/18.
 */
public abstract class DelegateBlockItem<D> {
    protected D mData;

    public DelegateBlockItem(D data) {
        mData = data;
    }

    public abstract Class getBlockLayoutClazz();

    public D getData() {
        return mData;
    }

    public void setData(D data) {
        mData = data;
    }

    // 是否需要占满一行，网格或瀑布布局需要，默认为false
    public boolean isSpanFull() {
        return false;
    }
}
