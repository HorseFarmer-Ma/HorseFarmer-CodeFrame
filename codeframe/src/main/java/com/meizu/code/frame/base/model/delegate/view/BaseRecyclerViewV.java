package com.meizu.code.frame.base.model.delegate.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.base.frame.mvp.BeamDataView;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerView;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerViewAdapter;
import com.meizu.code.frame.base.model.delegate.RecyclerViewObserver;
import com.meizu.code.frame.base.model.delegate.presenter.BaseRecyclerViewP;

import java.util.List;

public class BaseRecyclerViewV<P extends BaseRecyclerViewP> extends BeamDataView<P, List<DelegateBlockItem>> implements RecyclerViewObserver.IRvObserver {

    private DelegateRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.AdapterDataObserver mAdapterDataObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapterDataObserver = new RecyclerViewObserver(this);
        getAdapter().registerAdapterDataObserver(mAdapterDataObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getAdapter().unregisterAdapterDataObserver(mAdapterDataObserver);
    }

    @Override
    public void setData(List<DelegateBlockItem> data) {


    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {

    }

    @Override
    public void showContentView() {
        super.showContentView();
        if (mRecyclerView == null) {
            mRecyclerView = new DelegateRecyclerView(getActivity());
            View rootView = getRootView();
            if (rootView instanceof ViewGroup) {
                ((ViewGroup) rootView).addView(mRecyclerView);
            }
        }
    }

    @Override
    public boolean checkIfListEmpty() {
        RecyclerView.Adapter adapter = getAdapter();
        return adapter == null
                || (adapter instanceof DelegateRecyclerViewAdapter
                && ((DelegateRecyclerViewAdapter) adapter).getRealItemCount() == 0)
                || adapter.getItemCount() == 0;
    }

    /**
     * 获取适配器
     */
    public RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = createAdapter();
        }
        if (mAdapter == null) {
            throw new IllegalArgumentException("BaseRecyclerViewV: mAdapter is null");
        }
        return mAdapter;
    }

    protected RecyclerView.Adapter createAdapter() {
        return new DelegateRecyclerViewAdapter(getActivity());
    }
}
