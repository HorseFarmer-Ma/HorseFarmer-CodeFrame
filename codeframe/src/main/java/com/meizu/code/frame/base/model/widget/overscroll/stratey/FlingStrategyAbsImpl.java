/*
 * ************************************************************
 * Class：FlingStrategyAbsImpl.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 10:19:53
 * Last modified time：2018-11-08 10:04:03
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.stratey;

import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.meizu.code.frame.base.model.widget.overscroll.dao.IOverScrollView;

import java.lang.ref.WeakReference;

public abstract class FlingStrategyAbsImpl<T extends IOverScrollView> {
    protected final WeakReference<T> mOverScrollViewWr;
    protected int mMaxVy;
    protected int mMinVy;
    protected int mVy;            // Y轴方向速度

    public FlingStrategyAbsImpl(T overScrollView) {
        mOverScrollViewWr = new WeakReference<>(overScrollView);
        if (overScrollView != null) {
            ViewConfiguration vc = ViewConfiguration.get(overScrollView.getContext());
            mMaxVy = vc.getScaledMaximumFlingVelocity();
            mMinVy = vc.getScaledMinimumFlingVelocity();
        }
    }

    public abstract boolean dealTouchEvent(MotionEvent ev);

    /**
     * 确定边界，不超过最大值和最小值
     */
    protected boolean ensureVyValid() {
        if (Math.abs(mVy) < mMinVy) {
            mVy = 0;
            return false;
        }

        mVy = Math.max(-mMaxVy, Math.min(mVy, this.mMaxVy));
        return true;
    }
}
