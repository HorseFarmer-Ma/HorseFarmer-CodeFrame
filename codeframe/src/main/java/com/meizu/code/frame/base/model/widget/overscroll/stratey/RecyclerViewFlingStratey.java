/*
 * ************************************************************
 * Class：RecyclerViewFlingStratey.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 12:08:27
 * Last modified time：2018-11-08 12:08:26
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.stratey;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.meizu.code.frame.base.model.widget.overscroll.ColorOverScrollLayout;

import java.lang.ref.WeakReference;

/**
 * RecyclerView检测惯性滑动策略
 * <p>
 * Author: maxueming
 * Date: 2018/11/07
 */

public class RecyclerViewFlingStratey extends FlingStrategyAbsImpl<ColorOverScrollLayout> {

    private boolean mIsNeedCheckFling;

    public RecyclerViewFlingStratey(ColorOverScrollLayout view) {
        super(view);
        if (view != null) {
            View targetView = view.getTargetView();
            if (targetView != null && targetView instanceof RecyclerView) {
                ((RecyclerView) targetView).addOnScrollListener(new OnFlingCheckScrollListener(this));
            }
        }
    }

    @Override
    public boolean dealTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP) {
            ColorOverScrollLayout scrollLayout = mOverScrollViewWr.get();
            if (scrollLayout != null && scrollLayout.getScrollY() == 0) {
                mIsNeedCheckFling = true;
            }
        }
        return false;
    }

    private static class OnFlingCheckScrollListener extends RecyclerView.OnScrollListener {
        private final WeakReference<RecyclerViewFlingStratey> mWr;
        private long mLastTime;     // 上次滑动检测时间

        public OnFlingCheckScrollListener(RecyclerViewFlingStratey stratey) {
            mWr = new WeakReference<>(stratey);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            long currentTime = System.currentTimeMillis();
            RecyclerViewFlingStratey stratey = mWr.get();
            if (mLastTime != 0 && stratey != null) {
                float diffTime = (float) (currentTime - mLastTime) / 1000;
                stratey.mVy = (int) (dy / diffTime);
            }
            mLastTime = currentTime;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerViewFlingStratey stratey = mWr.get();
            if (stratey == null) return;
            // 滚动停止
            if (newState == RecyclerView.SCROLL_STATE_IDLE && stratey.mIsNeedCheckFling) {
                ColorOverScrollLayout scrollLayout = stratey.mOverScrollViewWr.get();
                if (scrollLayout == null) return;
                // 不可滑动的情况，启动fling
                boolean isValid = stratey.ensureVyValid();

                if (isValid && (!scrollLayout.canChildScrollY(-1) || !scrollLayout.canChildScrollY(1)) && stratey.mVy != 0) {
                    int maxOverFlingHeight = scrollLayout.getMaxOverFlingHeight();
                    scrollLayout.overYFling(stratey.mVy, -maxOverFlingHeight, maxOverFlingHeight);
                }
                stratey.mVy = 0;
                stratey.mIsNeedCheckFling = false;
                mLastTime = 0;
            }
        }
    }
}
