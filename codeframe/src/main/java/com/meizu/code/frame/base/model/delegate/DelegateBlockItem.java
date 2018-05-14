package com.meizu.code.frame.base.model.delegate;

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

    protected abstract Class getBlockLayoutClazz();

    public D getData() {
        return mData;
    }
}
