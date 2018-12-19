/*
 * ************************************************************
 * Class：MonthChooseLayout.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:53:14
 * Last modified time：2018-11-15 20:53:13
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
import android.widget.ImageView;
import android.widget.TextView;

import com.meizu.code.app.R;
import com.meizu.code.app.meituan.blockitem.MonthChooseItem;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;

public class MonthChooseLayout extends DelegateBlockLayout<MonthChooseItem> {
    private ViewHolder mViewHolder;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.month_choose_layout, rootView);
    }

    @Override
    protected void updateView(MonthChooseItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(getView());
        }
        mViewHolder.mDate.setText(item.getDateShow());

        mViewHolder.mAddMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthChooseItem item = getItem();
                if (item != null) {
                    item.addMonth();
                    sendMonthChangeEvent();
                }
            }
        });

        mViewHolder.mDelMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonthChooseItem item = getItem();
                if (item != null) {
                    item.delMonth();
                    sendMonthChangeEvent();
                }
            }
        });
    }

    /**
     * 通知月份改变，刷新页面数据
     */
    private void sendMonthChangeEvent() {
        if (mOnElementClickListener instanceof OnMonthChangeListener) {
            ((OnMonthChangeListener) mOnElementClickListener).onMonthChange();
        }
    }

    private static class ViewHolder {
        private final TextView mDate;
        private final ImageView mDelMonth;
        private final ImageView mAddMonth;

        public ViewHolder(View view) {
            mDate = view.findViewById(R.id.date);
            mDelMonth = view.findViewById(R.id.del_month);
            mAddMonth = view.findViewById(R.id.add_month);
        }
    }

    public interface OnMonthChangeListener extends OnElementClickListener {
        void onMonthChange();
    }
}
