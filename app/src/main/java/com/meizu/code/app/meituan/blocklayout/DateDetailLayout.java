/*
 * ************************************************************
 * Class：DateDetailLayout.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 21:10:25
 * Last modified time：2018-11-15 21:10:25
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blocklayout;

import android.app.AlertDialog;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.meizu.code.app.R;
import com.meizu.code.app.meituan.blockitem.DateDetailItem;
import com.meizu.code.app.meituan.data.MeituanSpConstants;
import com.meizu.code.app.meituan.data.MessageEvent;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;
import com.meizu.code.frame.utils.DialogUtils;
import com.meizu.code.frame.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

public class DateDetailLayout extends DelegateBlockLayout<DateDetailItem> {

    private ViewHolder mViewHolder;
    private AlertDialog mAlertDialog;
    private AlertDialog mDeleteAlertDialog;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.date_detail_layout, rootView);
    }

    @Override
    protected void updateView(DateDetailItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(getView());
        }
        mViewHolder.mDay.setText(String.valueOf(item.getDay()));
        setBalCountStyle(item.getBalCount());
        mViewHolder.mDateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAlertDialog == null) {
                    mAlertDialog = DialogUtils.createEtAlertDialog(
                            getActivity(),
                            CodeFrameStaticResUtils.getString(R.string.meituan_write_balance),
                            new DialogUtils.OnEtClickListener() {
                                @Override
                                public void onDetermine(String text) {
                                    super.onDetermine(text);
                                    if (CodeFrameStaticUtils.isNumeric(text)) {
                                        setBalCountStyle(Integer.parseInt(text));
                                        EventBus.getDefault().post(new MessageEvent(MessageEvent.MESSAGE_UPDATE_BAL_DATA));
                                    } else {
                                        ToastUtils.showToast(CodeFrameStaticResUtils
                                                .getString(R.string.input_pure_digital), Toast.LENGTH_SHORT);
                                    }
                                }
                            });
                }
                mAlertDialog.show();
            }
        });

        mViewHolder.mDateItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mDeleteAlertDialog == null) {
                    mDeleteAlertDialog = DialogUtils.createChooseAlertDialog(
                            getActivity(), CodeFrameStaticResUtils.getString(R.string.meituan_clear_date),
                            CodeFrameStaticResUtils.getString(R.string.text_ok),
                            CodeFrameStaticResUtils.getString(R.string.text_cancel),
                            new DialogUtils.OnChooseClickListener() {
                                @Override
                                public void onDetermine() {
                                    super.onDetermine();
                                    setBalCountStyle(-1);
                                    EventBus.getDefault().post(new MessageEvent(MessageEvent.MESSAGE_UPDATE_BAL_DATA));
                                }
                            });
                }
                mDeleteAlertDialog.show();
                return true;
            }
        });
    }

    @Override
    public void onViewRecycled() {
        super.onViewRecycled();
        mAlertDialog = null;
    }

    /**
     * 设置达标颜色值等
     */
    private void setBalCountStyle(int balCount) {
        DateDetailItem item = getItem();
        String balCountShow = CodeFrameStaticResUtils.getString(R.string.meituan_bal);
        if (item != null && mViewHolder != null && !TextUtils.isEmpty(balCountShow)) {
            item.setBalCount(balCount);
            mViewHolder.mBalCount.setText(balCount >= 0 ? String.format(balCountShow, balCount) :
                    (item.isToday() ? CodeFrameStaticResUtils.getString(R.string.today) : ""));
            mViewHolder.mBalCount.setTextColor(CodeFrameStaticResUtils
                    .getColor(balCount >= 0 ? android.R.color.white : R.color.fifty_percent_black));
            mViewHolder.mDay.setTextColor(CodeFrameStaticResUtils
                    .getColor(balCount >= 0 ? android.R.color.white : R.color.fifty_percent_black));
            mViewHolder.mBackground.setBackground(CodeFrameStaticResUtils
                    .getDrawable(balCount >= 0 ? (balCount < MeituanSpConstants.getTargetCount() ?
                            R.drawable.meituan_bal_red_background : (balCount < MeituanSpConstants.getTargetCount()
                            * 1.33 ? R.drawable.meituan_bal_green_background : R.drawable.meituan_bal_gold_background))
                            : R.color.transparent));
        }
    }

    private static class ViewHolder {
        private final TextView mDay;
        private final TextView mBalCount;
        private final FrameLayout mDateItem;
        private final LinearLayout mBackground;

        public ViewHolder(View view) {
            mDay = view.findViewById(R.id.day);
            mDateItem = view.findViewById(R.id.date_item);
            mBackground = view.findViewById(R.id.background);
            mBalCount = view.findViewById(R.id.bal_count);
        }
    }
}
