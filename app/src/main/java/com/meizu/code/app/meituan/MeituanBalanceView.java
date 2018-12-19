/*
 * ************************************************************
 * Class：MeituanBalanceView.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 19:56:13
 * Last modified time：2018-11-15 19:56:13
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meizu.code.app.R;
import com.meizu.code.app.example.widget.NoFocusGridLayoutManager;
import com.meizu.code.app.meituan.blocklayout.MonthChooseLayout;
import com.meizu.code.app.meituan.data.MeituanSpConstants;
import com.meizu.code.frame.base.annotations.RequirePresenter;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.frame.mvp.BeamDataView;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerView;
import com.meizu.code.frame.base.model.delegate.DelegateRecyclerViewAdapter;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.widget.overscroll.ColorOverScrollLayout;
import com.meizu.code.frame.base.model.widget.overscroll.stratey.RecyclerViewFlingStratey;
import com.meizu.code.frame.utils.AnimationUtils;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;
import com.meizu.code.frame.utils.DialogUtils;
import com.meizu.code.frame.utils.ToastUtils;

import java.util.List;

/**
 * 美团View类
 * <p>
 * Created by mxm on 12/11/17.
 */
@RequirePresenter(MeituanBalancePresenter.class)
public class MeituanBalanceView extends BeamDataView<MeituanBalancePresenter, List<DelegateBlockItem>> {

    private static final String TAG = "MeituanBalanceView";
    @NonNull
    private static final String TARGET_BAL_COUNT = CodeFrameStaticResUtils.getString(R.string.target_bal_count);

    private DelegateRecyclerView mRecyclerView;
    private DelegateRecyclerViewAdapter mAdapter;
    private ColorOverScrollLayout mColorOverScrollLayout;
    private Menu mMenu;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        View rootView = inflater.inflate(R.layout.activity_meituan_balance, parent, false);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mColorOverScrollLayout = rootView.findViewById(R.id.overscroll_layout);
        mColorOverScrollLayout.setFlingStrategy(new RecyclerViewFlingStratey(mColorOverScrollLayout));
        final Activity activity = getActivity();
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mAdapter = new DelegateRecyclerViewAdapter(activity);
        mAdapter.setEnableLoadMore(false);
        mAdapter.setElementClickListener(new MonthChooseLayout.OnMonthChangeListener() {
            @Override
            public void onMonthChange() {
                // 月份改变
                if (CodeFrameStaticUtils.checkActivityIsAlive(activity)) {
                    AnimationUtils.startViewGradientAnimation(activity.getWindow().getDecorView(), 300L);
                    MeituanBalancePresenter presenter = getPresenter();
                    if (presenter != null) {
                        presenter.refreshDate();
                    }
                }
            }
        });
        mRecyclerView.setLayoutManager(new NoFocusGridLayoutManager(activity, 7));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected boolean onCreateOptionsMenu(Menu menu) {
        Activity activity = getActivity();
        mMenu = menu;
        if (activity != null) {
            activity.getMenuInflater().inflate(R.menu.meituan_menu, menu);
        }
        return true;
    }

    @Override
    protected boolean onPrepareOptionsMenu(Menu menu) {
        setTargetBalCount(MeituanSpConstants.getTargetCount());
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * set balance count
     */
    private void setTargetBalCount(int count) {
        if (mMenu != null) {
            MenuItem item = mMenu.findItem(R.id.menu_meituan_bal_count);
            if (item != null) {
                item.setTitle(String.format(TARGET_BAL_COUNT, count));
            }
        }
        if (count != MeituanSpConstants.getTargetCount()) {
            MeituanSpConstants.setTargetCount(count);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_meituan_bal_count:{
                DialogUtils.createEtAlertDialog(getActivity(), CodeFrameStaticResUtils.getString(R.string.input_target_count), new DialogUtils.OnEtClickListener() {
                    @Override
                    public void onDetermine(String text) {
                        super.onDetermine(text);
                        if (CodeFrameStaticUtils.isNumeric(text)) {
                            setTargetBalCount(Integer.parseInt(text));
                        } else {
                            ToastUtils.showToast(CodeFrameStaticResUtils
                                    .getString(R.string.input_pure_digital), Toast.LENGTH_SHORT);
                        }
                    }
                }).show();
                break;
            }
            default:{
                Logger.logD(TAG, "you have not deal the item event yet");
                break;
            }
        }
        return true;
    }

    @Override
    public void setData(List<DelegateBlockItem> data) {
        mAdapter.swapData(data);
    }

    @Override
    public void setExtraData(Object data, Enum loadTypeParmas) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.destroy();
        }
    }
}
