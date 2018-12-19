/*
 * ************************************************************
 * Class：MeituanBalanceBean.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 21:08:14
 * Last modified time：2018-11-15 21:08:14
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.data;

import com.meizu.code.frame.utils.CalendarUtils;

public class MeituanBalanceBean {
    private CalendarUtils.DateBean mDateBean;
    private int mSingleQuantity;

    public MeituanBalanceBean(CalendarUtils.DateBean dateBean) {
        mDateBean = dateBean;
        mSingleQuantity = -1;
    }

    public MeituanBalanceBean(CalendarUtils.DateBean dateBean, int singleQuantity) {
        mDateBean = dateBean;
        mSingleQuantity = singleQuantity;
    }

    public CalendarUtils.DateBean getDateBean() {
        return mDateBean;
    }

    public void setDateBean(CalendarUtils.DateBean dateBean) {
        mDateBean = dateBean;
    }

    public int getSingleQuantity() {
        return mSingleQuantity;
    }

    public void setSingleQuantity(int singleQuantity) {
        mSingleQuantity = singleQuantity;
    }
}
