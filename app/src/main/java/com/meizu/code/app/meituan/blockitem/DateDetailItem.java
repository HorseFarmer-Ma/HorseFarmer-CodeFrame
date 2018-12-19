/*
 * ************************************************************
 * Class：DateDetailBlockItem.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 21:07:44
 * Last modified time：2018-11-15 21:07:44
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blockitem;

import com.meizu.code.app.meituan.blocklayout.DateDetailLayout;
import com.meizu.code.app.meituan.data.MeituanBalanceBean;
import com.meizu.code.app.meituan.db.MeituanDbUtils;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.ThreadPoolServiceHelper;

public class DateDetailItem extends DelegateBlockItem<MeituanBalanceBean> {
    private int mDay;
    private int mBalCount;      // 单量
    private int mIsToday;

    public DateDetailItem(MeituanBalanceBean data) {
        super(data);
        if (data != null) {
            mBalCount = data.getSingleQuantity();
            CalendarUtils.DateBean dateBean = data.getDateBean();
            if (dateBean != null) {
                mDay = dateBean.getDay();
                mIsToday =  CalendarUtils.isToday(dateBean);
            }
        }
    }

    @Override
    public Class getBlockLayoutClazz() {
        return DateDetailLayout.class;
    }

    public int getDay() {
        return mDay;
    }

    public void setDay(int day) {
        mDay = day;
    }

    public int getBalCount() {
        return mBalCount;
    }

    public void setBalCount(int balCount) {
        mBalCount = balCount;
        mData.setSingleQuantity(balCount);
        ThreadPoolServiceHelper.getInstance().addTask(new Runnable() {
            @Override
            public void run() {
                MeituanDbUtils.recordOrUpdateSq(mData);
            }
        });
    }

    public boolean isToday() {
        return mIsToday == 0;
    }
}
