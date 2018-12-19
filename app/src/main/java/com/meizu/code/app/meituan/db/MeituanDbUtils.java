/*
 * ************************************************************
 * Class：MeituanDbUtils.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-16 11:22:58
 * Last modified time：2018-11-16 11:22:58
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.db;

import com.meizu.code.app.meituan.data.MeituanBalanceBean;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.frame.manager.DataBaseManager;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.CollectionUtils;
import com.meizu.code.greendao.MeituanRecordBeanDao;
import com.meizu.code.greendao.bean.MeituanRecordBean;

import java.util.List;

public class MeituanDbUtils {

    private static final String TAG = "MeituanDbUtils";
    private static final Object LOCK_MEITUAN = new Object();

    /**
     * 将单量查询出来后加进美团挣单详情
     */
    public static void queryRecord2BalanceBean(List<MeituanBalanceBean> list) {
        if (!CollectionUtils.isEmpty(list)) {
            CalendarUtils.DateBean dateBean = list.get(0).getDateBean();
            int year = dateBean.getYear();
            int month = dateBean.getMonth();
            MeituanRecordBeanDao recordBeanDao = DataBaseManager.getInstance().getDaoSession().getMeituanRecordBeanDao();
            List<MeituanRecordBean> meituanRecordBeans;
            synchronized (LOCK_MEITUAN) {
                meituanRecordBeans = recordBeanDao.queryBuilder()
                        .where(MeituanRecordBeanDao.Properties.Year.eq(year),
                                MeituanRecordBeanDao.Properties.Month.eq(month))
                        .orderAsc(MeituanRecordBeanDao.Properties.Day)
                        .build().list();
            }
            for (MeituanRecordBean bean : meituanRecordBeans) {
                int day = bean.getDay();
                list.get(day - 1).setSingleQuantity(bean.getSingleQuantity());
            }
        }
    }

    /**
     * 记录美团挣单详情
     */
    public static void recordOrUpdateSq(MeituanBalanceBean meituanBalanceBean) {
        if (meituanBalanceBean != null) {
            CalendarUtils.DateBean dateBean = meituanBalanceBean.getDateBean();
            int year = dateBean.getYear();
            int month = dateBean.getMonth();
            int day = dateBean.getDay();
            int singleQuantity = meituanBalanceBean.getSingleQuantity();
            MeituanRecordBeanDao recordBeanDao = DataBaseManager.getInstance().getDaoSession().getMeituanRecordBeanDao();

            synchronized (LOCK_MEITUAN) {
                MeituanRecordBean recordBean = recordBeanDao.queryBuilder()
                        .where(MeituanRecordBeanDao.Properties.Year.eq(year),
                                MeituanRecordBeanDao.Properties.Month.eq(month),
                                MeituanRecordBeanDao.Properties.Day.eq(day))
                        .build().unique();
                // 单量小于0，不保存
                if(singleQuantity < 0){
                    if (recordBean != null) {
                        recordBeanDao.delete(recordBean);
                    }
                } else {
                    if (recordBean != null) {
                        recordBean.setSingleQuantity(singleQuantity);
                        recordBeanDao.update(recordBean);
                        Logger.logD(TAG, "recordOrUpdateSq: update data");
                    } else {
                        recordBean = new MeituanRecordBean();
                        recordBean.setYear(year);
                        recordBean.setMonth(month);
                        recordBean.setDay(day);
                        recordBean.setSingleQuantity(singleQuantity);
                        recordBeanDao.insert(recordBean);
                        Logger.logD(TAG, "recordOrUpdateSq: add data");
                    }
                }
            }
        }
    }
}
