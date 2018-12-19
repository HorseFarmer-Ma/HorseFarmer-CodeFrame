/*
 * ************************************************************
 * Class：MonthChooseItem.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:52:38
 * Last modified time：2018-11-15 20:52:38
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blockitem;

import android.support.annotation.NonNull;

import com.meizu.code.app.R;
import com.meizu.code.app.meituan.blocklayout.MonthChooseLayout;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;

public class MonthChooseItem extends DelegateBlockItem<CalendarUtils.DateBean> {

    private static final String DATE_SHOW_STR = CodeFrameStaticResUtils.getString(R.string.meituan_balance_date_show);
    private int mYear;
    private int mMonth;

    public MonthChooseItem(@NonNull CalendarUtils.DateBean data) {
        super(data);
        mYear = data.getYear();
        mMonth = data.getMonth();
    }

    @Override
    public Class getBlockLayoutClazz() {
        return MonthChooseLayout.class;
    }

    public String getDateShow() {
        return String.format(DATE_SHOW_STR, mYear, mMonth);
    }

    public void delMonth() {
        if (mMonth == 1) {
            mData.setYear(--mYear);
            mMonth = 12;
            mData.setMonth(mMonth);
        } else {
            mData.setMonth(--mMonth);
        }
    }

    public void addMonth() {
        if (mMonth == 12) {
            mData.setYear(++mYear);
            mMonth = 1;
            mData.setMonth(mMonth);
        } else {
            mData.setMonth(++mMonth);
        }
    }

    @Override
    public boolean isSpanFull() {
        return true;
    }
}
