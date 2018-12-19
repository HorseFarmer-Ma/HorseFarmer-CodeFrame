/*
 * ************************************************************
 * Class：WeekShowItem.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-15 21:02:05
 * Last modified time：2018-11-15 21:02:05
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blockitem;

import com.meizu.code.app.meituan.blocklayout.WeekShowLayout;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;

public class WeekShowItem extends DelegateBlockItem<Integer> {

    private static final String[] WEEK_NAME = {"日", "一", "二", "三", "四", "五", "六"};

    public WeekShowItem(Integer data) {
        super(data);
    }

    @Override
    public Class getBlockLayoutClazz() {
        return WeekShowLayout.class;
    }

    public String getWeekName() {
        Integer date = getData();
        if (date != null && date < 7) {
            return WEEK_NAME[date];
        }
        return "";
    }
}
