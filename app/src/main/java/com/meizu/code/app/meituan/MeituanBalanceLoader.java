/*
 * ************************************************************
 * Class：MeituanBalanceLoader.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 19:57:28
 * Last modified time：2018-11-08 17:15:09
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan;

import com.meizu.code.app.meituan.blockitem.CalculateBalDataItem;
import com.meizu.code.app.meituan.blockitem.DateDetailItem;
import com.meizu.code.app.meituan.blockitem.MonthChooseItem;
import com.meizu.code.app.meituan.blockitem.WeekShowItem;
import com.meizu.code.app.meituan.data.MeituanBalanceBean;
import com.meizu.code.app.meituan.db.MeituanDbUtils;
import com.meizu.code.frame.base.frame.mvp.BaseDataLoader;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.blockitem.EmptyBlockItem;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

/**
 * 测试类
 * <p>
 * Created by maxueming on 17-11-20.
 */

public class MeituanBalanceLoader extends BaseDataLoader<List<DelegateBlockItem>> {
    private final CalendarUtils.DateBean mDateBean;

    public MeituanBalanceLoader() {
        mDateBean = new CalendarUtils.DateBean().copyOf(CalendarUtils.getTodayBean());
    }

    @Override
    protected Observable<List<DelegateBlockItem>> onStart() {
        return loadData();
    }

    @Override
    protected Observable<List<DelegateBlockItem>> onRefresh() {
        return loadData();
    }

    private Observable<List<DelegateBlockItem>> loadData() {
        return Observable.defer(new Func0<Observable<List<DelegateBlockItem>>>() {
            @Override
            public Observable<List<DelegateBlockItem>> call() {
                return Observable.just(createData());
            }
        });
    }

    /**
     * 根据日期生成列表
     */
    private List<DelegateBlockItem> createData() {
        List<DelegateBlockItem> items = new ArrayList<>();
        addMonthItem(items);
        addWeekItem(items);
        addDayItem(items);
        addCalBalDataItem(items);
        return items;
    }

    private void addMonthItem(List<DelegateBlockItem> items) {
        items.add(new MonthChooseItem(mDateBean));
    }

    private void addWeekItem(List<DelegateBlockItem> items) {
        for (int i = 0; i < 7; i++) {
            items.add(new WeekShowItem(i));
        }
    }

    private void addDayItem(List<DelegateBlockItem> items) {
        List<CalendarUtils.DateBean> curMonthBeanList = CalendarUtils.getCurMonthDayList(mDateBean);
        if (!CollectionUtils.isEmpty(curMonthBeanList)) {
            CalendarUtils.DateBean dateBean = curMonthBeanList.get(0);
            int index = dateBean.getIndex();
            for (int i = 0; i < index; i++) {
                items.add(new EmptyBlockItem(null));
            }
            List<MeituanBalanceBean> meituanBalanceBeans = new ArrayList<>();
            for (CalendarUtils.DateBean bean : curMonthBeanList) {
                meituanBalanceBeans.add(new MeituanBalanceBean(bean, -1));
            }
            MeituanDbUtils.queryRecord2BalanceBean(meituanBalanceBeans);
            for (MeituanBalanceBean bean : meituanBalanceBeans) {
                items.add(new DateDetailItem(bean));
            }
        }
    }

    private void addCalBalDataItem(List<DelegateBlockItem> items) {
        items.add(new CalculateBalDataItem(mDateBean));
    }
}
