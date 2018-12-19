/*
 * ************************************************************
 * Class：DelegateBlockLayout.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:56:49
 * Last modified time：2018-11-06 14:26:33
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.delegate.blocklayout;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;

import java.lang.ref.WeakReference;

/**
 * 抽象化列表item对应Layout
 * <p>
 * Created by mxm on 16/02/18.
 */
public abstract class DelegateBlockLayout<T extends DelegateBlockItem> {

    private T mItem;
    protected View mView;
    private int mPosition;
    private WeakReference<Context> mWr;
    protected OnElementClickListener mOnElementClickListener;

    /**
     * 生成视图
     */
    public View createView(Context context, @Nullable ViewGroup rootView) {
        mWr = new WeakReference<>(context);
        mView = createView(rootView);
        return mView;
    }

    /**
     * 更新视图
     */
    public void updateView(T item, int position) {
        mItem = item;
        mPosition = position;
        updateView(item);
    }

    protected abstract View createView(@Nullable ViewGroup rootView);

    protected abstract void updateView(T item);

    protected View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
        return inflate(resource, root, false);
    }

    protected View inflate(@LayoutRes int resource,
                           @Nullable ViewGroup root, boolean attachToRoot) {
        if (mWr != null) {
            return LayoutInflater.from(mWr.get()).inflate(resource, root, attachToRoot);
        }
        return null;
    }

    public View getView() {
        return mView;
    }

    public T getItem() {
        return mItem;
    }

    public int getPosition() {
        return mPosition;
    }

    public Context getContext() {
        return mWr.get();
    }

    public Activity getActivity() {
        return CodeFrameStaticUtils.scanActivityFromContext(mWr.get());
    }

    /**
     * 回收视图
     */
    public void onViewRecycled() {
    }

    public void setOnElementClickListener(OnElementClickListener onElementClickListener) {
        mOnElementClickListener = onElementClickListener;
    }

    public interface OnElementClickListener {
    }
}
