/*
 * ************************************************************
 * Class：MeituanBalMoneyBean.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-20 20:34:59
 * Last modified time：2018-11-20 20:34:59
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.data;

/**
 * 战绩数据
 */
public class MeituanBalMoneyBean {
    private float mMonthAveBalCount;              // 月平均单数
    private float mRealWorkAveBalCount;           // 实际出勤平均单数
    private int mAllBalCount;                   // 所有单数
    private int mRealWorkDays;                  // 实际出勤天数

    public void setMonthAveBalCount(float monthAveBalCount) {
        mMonthAveBalCount = monthAveBalCount;
    }

    public void setRealWorkAveBalCount(float realWorkAveBalCount) {
        mRealWorkAveBalCount = realWorkAveBalCount;
    }

    public void setAllBalCount(int allBalCount) {
        mAllBalCount = allBalCount;
    }

    public void setRealWorkDays(int realWorkDays) {
        mRealWorkDays = realWorkDays;
    }

    public float getMonthAveBalCount() {
        return mMonthAveBalCount;
    }

    public float getRealWorkAveBalCount() {
        return mRealWorkAveBalCount;
    }

    public int getAllBalCount() {
        return mAllBalCount;
    }

    public int getRealWorkDays() {
        return mRealWorkDays;
    }
}
