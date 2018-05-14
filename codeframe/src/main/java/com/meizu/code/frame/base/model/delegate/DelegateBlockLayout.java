package com.meizu.code.frame.base.model.delegate;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * 抽象化列表item对应Layout
 * <p>
 * Created by mxm on 16/02/18.
 */
public abstract class DelegateBlockLayout<ITEM extends DelegateBlockItem> {

    private ITEM mItem;
    protected View mView;
    private int mPosition;
    private Context mContext;

    /**
     * 生成视图
     */
    @CallSuper
    public View createView(Context context, @Nullable ViewGroup rootView, boolean attachToRoot) {
        mContext = context;
        mView = createView(rootView, attachToRoot);
        return mView;
    }

    protected abstract View createView(@Nullable ViewGroup rootView, boolean attachToRoot);

    public View getView() {
        return mView;
    }

    /**
     * 更新视图
     */
    @CallSuper
    protected void updateView(ITEM item, int position) {
        mItem = item;
        mPosition = position;
        updateView(item);
    }

    protected abstract void updateView(ITEM item);

    protected View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
        return LayoutInflater.from(mContext).inflate(resource, root, false);
    }

    protected View inflate(@LayoutRes int resource,
                           @Nullable ViewGroup root, boolean attachToRoot) {
        return LayoutInflater.from(mContext).inflate(resource, root, attachToRoot);
    }

    public ITEM getItem() {
        return mItem;
    }

    public int getPosition() {
        return mPosition;
    }

    public Context getContext() {
        return mContext;
    }

    public void onBindView() {

    }

    /**
     * 回收视图
     */
    public void onViewRecycled() {
    }
}
