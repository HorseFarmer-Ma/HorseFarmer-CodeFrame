package com.meizu.code.frame.base.frame.data;

/**
 * 数据封装类
 * <p>
 * Created by maxueming on 17-11-20.
 */

public class DataHolder<T> {

    // 加载数据类型
    private Enum mLoadTypeParmas;
    private T mData;

    public Enum getLoadTypeParmas() {
        return mLoadTypeParmas;
    }

    public void setLoadTypeParmas(Enum mLoadTypeParmas) {
        this.mLoadTypeParmas = mLoadTypeParmas;
    }

    public T getData() {
        return mData;
    }

    public void setData(T mData) {
        this.mData = mData;
    }
}
