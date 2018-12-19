/*
 * ************************************************************
 * Class：CacularBalDataItem.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-11-20 11:15:05
 * Last modified time：2018-11-20 11:15:05
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.meituan.blockitem;

import com.meizu.code.app.R;
import com.meizu.code.app.meituan.blocklayout.CalculateBalDataLayout;
import com.meizu.code.app.meituan.data.MeituanBalMoneyBean;
import com.meizu.code.app.meituan.data.MeituanBalanceCaculateUtils;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.utils.CalendarUtils;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;

public class CalculateBalDataItem extends DelegateBlockItem<CalendarUtils.DateBean> {

    public static final String REPOTR_STR = CodeFrameStaticResUtils.getString(R.string.meituan_balance_report);

    public CalculateBalDataItem(CalendarUtils.DateBean data) {
        super(data);
    }

    @Override
    public Class getBlockLayoutClazz() {
        return CalculateBalDataLayout.class;
    }

    @Override
    public boolean isSpanFull() {
        return true;
    }

    /**
     * 获取战绩
     */
    public MeituanBalMoneyBean getMoneyBean() {
        return MeituanBalanceCaculateUtils.getMoneyRecord(mData);
    }
}
