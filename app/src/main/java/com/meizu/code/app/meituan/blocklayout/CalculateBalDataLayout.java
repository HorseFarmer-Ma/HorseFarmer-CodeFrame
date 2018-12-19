/*
 * ************************************************************
 * Class：CacularBalDataLayout.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-20 11:15:13
 * Last modified time：2018-11-20 11:15:13
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blocklayout;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.code.app.R;
import com.meizu.code.app.meituan.blockitem.CalculateBalDataItem;
import com.meizu.code.app.meituan.data.MeituanBalMoneyBean;
import com.meizu.code.app.meituan.data.MessageEvent;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CalculateBalDataLayout extends DelegateBlockLayout<CalculateBalDataItem> {

    private ViewHolder mViewHolder;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.calculate_bal_layout, rootView);
    }

    @Override
    protected void updateView(CalculateBalDataItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(getView());
        }
        requestReport();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onViewRecycled() {
        super.onViewRecycled();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateBalData(MessageEvent msgEvent) {
        if (msgEvent != null && msgEvent.getMessage() == MessageEvent.MESSAGE_UPDATE_BAL_DATA) {
            requestReport();
        }
    }

    /**
     * 请求报告
     */
    private void requestReport() {
        ThreadPoolServiceHelper.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                CalculateBalDataItem item = getItem();
                if (item != null) {
                    final MeituanBalMoneyBean moneyBean = item.getMoneyBean();
                    mViewHolder.mBalanceData.post(new Runnable() {
                        @Override
                        public void run() {
                            mViewHolder.mBalanceData.setText(String.format(CalculateBalDataItem.REPOTR_STR,
                                    moneyBean.getMonthAveBalCount(),
                                    moneyBean.getRealWorkAveBalCount(),
                                    moneyBean.getAllBalCount(),
                                    moneyBean.getRealWorkDays()));
                        }
                    });
                }
            }
        });
    }

    private static class ViewHolder {
        private final TextView mBalanceData;

        public ViewHolder(View view) {
            mBalanceData = view.findViewById(R.id.balance_data);
        }
    }
}
