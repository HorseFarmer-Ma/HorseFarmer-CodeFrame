/*
 * ************************************************************
 * Class：WeekShowLayout.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 21:02:35
 * Last modified time：2018-11-15 21:02:35
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
import com.meizu.code.app.meituan.blockitem.WeekShowItem;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;

public class WeekShowLayout extends DelegateBlockLayout<WeekShowItem> {
    private ViewHolder mViewHolder;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.week_show_layout, rootView);
    }

    @Override
    protected void updateView(WeekShowItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(getView());
        }
        mViewHolder.mWeekName.setText(item.getWeekName());
    }

    private static class ViewHolder {
        private final TextView mWeekName;

        public ViewHolder(View view) {
            mWeekName = view.findViewById(R.id.week_name);
        }
    }
}
