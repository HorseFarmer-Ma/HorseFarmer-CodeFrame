package com.meizu.code.frame.base.mvp.data;

/**
 * 数据封装类
 *
 * Created by maxueming on 17-11-20.
 */

public class DataHolder<T> {

    private LoadTypeParmas mLoadTypeParmas;
    private T mData;

    public LoadTypeParmas getLoadTypeParmas() {
        return mLoadTypeParmas;
    }

    public void setLoadTypeParmas(LoadTypeParmas mLoadTypeParmas) {
        this.mLoadTypeParmas = mLoadTypeParmas;
    }

    public T getData() {
        return mData;
    }

    public void setData(T mData) {
        this.mData = mData;
    }
}
