package com.meizu.code.app.test;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meizu.code.app.R;
import com.meizu.code.frame.base.annotations.RequirePresenter;
import com.meizu.code.frame.base.frame.mvp.BeamDataView;
import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerView;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerViewAdapter;

import java.util.List;

/**
 * 测试View类
 *
 * Created by mxm on 12/11/17.
 */
@RequirePresenter(TestPresenter.class)
public class TestView extends BeamDataView<TestPresenter, List<DelegateBlockItem>> {

    private DelegateRecyclerView mRecyclerView;
    private DelegateRecyclerViewAdapter mAdapter;

    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.activity_test, parent, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mAdapter = new DelegateRecyclerViewAdapter(getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnItemClickListener(new DelegateRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Toast.makeText(getActivity(), "点击了第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setOnItemLongClickListener(new DelegateRecyclerView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int position) {
                Toast.makeText(getActivity(), "长按了第" + position + "项", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void setData(List<DelegateBlockItem> data) {
        mAdapter.swapData(data);
    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {
    }
}
