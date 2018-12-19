/*
 * ************************************************************
 * Class：MeituanBalanceCaculate.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-20 14:25:42
 * Last modified time：2018-11-20 14:25:42
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.data;

import com.meizu.code.app.meituan.db.MeituanDbUtils;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 美团单数，金钱计算
 */
public class MeituanBalanceCaculateUtils {
    /**
     * 战绩计算
     */
    public static MeituanBalMoneyBean getMoneyRecord(CalendarUtils.DateBean dateBean) {
        MeituanBalMoneyBean moneyBean = new MeituanBalMoneyBean();
        List<CalendarUtils.DateBean> curMonthBeanList = CalendarUtils.getCurMonthDayList(dateBean);
        if (!CollectionUtils.isEmpty(curMonthBeanList)) {
            List<MeituanBalanceBean> meituanBalanceBeans = new ArrayList<>();
            for (CalendarUtils.DateBean bean : curMonthBeanList) {
                meituanBalanceBeans.add(new MeituanBalanceBean(bean, -1));
            }
            MeituanDbUtils.queryRecord2BalanceBean(meituanBalanceBeans);

            int mAllBalCount = 0;                   // 所有单数
            int mRealWorkDays = 0;                  // 实际出勤天数
            for (MeituanBalanceBean bean : meituanBalanceBeans) {
                int singleQuantity = bean.getSingleQuantity();
                if (singleQuantity != -1) {
                    mAllBalCount += singleQuantity;
                    ++mRealWorkDays;
                }
            }

            if (mRealWorkDays != 0) {
                moneyBean.setRealWorkAveBalCount(mAllBalCount / (float)mRealWorkDays);
            }
            moneyBean.setMonthAveBalCount(mAllBalCount / (float)meituanBalanceBeans.size());
            moneyBean.setAllBalCount(mAllBalCount);
            moneyBean.setRealWorkDays(mRealWorkDays);
        }
        return moneyBean;
    }
}
