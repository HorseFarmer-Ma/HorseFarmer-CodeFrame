package com.meizu.code.app.test;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.app.R;
import com.meizu.code.app.example.blockitem.EditTextBlockItem;
import com.meizu.code.app.example.widget.GridItemDecoration;
import com.meizu.code.app.example.widget.NoFocusGridLayoutManager;
import com.meizu.code.frame.base.annotations.RequirePresenter;
import com.meizu.code.frame.base.frame.mvp.BeamDataView;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerView;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerViewAdapter;
import com.meizu.code.frame.base.model.widget.overscroll.ColorOverScrollLayout;
import com.meizu.code.frame.base.model.widget.overscroll.stratey.RecyclerViewFlingStratey;

import java.util.List;

/**
 * 测试View类
 * <p>
 * Created by mxm on 12/11/17.
 */
@RequirePresenter(TestPresenter.class)
public class TestView extends BeamDataView<TestPresenter, List<DelegateBlockItem>> {

    private static final String TAG = "TestView";

    private DelegateRecyclerView mRecyclerView;
    private DelegateRecyclerViewAdapter mAdapter;
    private ColorOverScrollLayout mColorOverScrollLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.activity_test, parent, false);
        mColorOverScrollLayout = rootView.findViewById(R.id.overscroll_layout);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mColorOverScrollLayout.setFlingStrategy(new RecyclerViewFlingStratey(mColorOverScrollLayout));
        Activity activity = getActivity();
        mAdapter = new DelegateRecyclerViewAdapter(activity);
        mRecyclerView.setLayoutManager(new NoFocusGridLayoutManager(activity, 4));
        mRecyclerView.addItemDecoration(new GridItemDecoration(activity));
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    private void initView() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        List<DelegateBlockItem> items = mAdapter.getItems();
        if (items != null && items.size() > 0) {
            DelegateBlockItem blockItem = items.get(0);
            if (blockItem instanceof EditTextBlockItem) {
                String str = ((EditTextBlockItem) blockItem).getData();
            }
        }
    }

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
    }

    @Override
    public void setData(List<DelegateBlockItem> data) {
        mAdapter.swapData(data);
    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {
    }
}
