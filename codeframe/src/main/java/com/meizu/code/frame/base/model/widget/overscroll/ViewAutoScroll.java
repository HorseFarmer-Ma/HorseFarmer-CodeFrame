/*
 * ************************************************************
 * Class：AutoScroll.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-07 20:18:13
 * Last modified time：2018-11-07 20:18:13
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll;

import android.support.annotation.IntDef;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.model.widget.overscroll.adapter.DefaultOverScroller;
import com.meizu.code.frame.base.model.widget.overscroll.dao.IOverScroller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * View滑动辅助类
 * <p>
 * Author: maxueming
 * Date:2018/11/07
 */
public class ViewAutoScroll implements Runnable {

    private static final String TAG = "ViewAutoScroll";

    private final WeakReference<View> mWr;
    private IOverScroller mOverScroller;
    private OnScrollFinishListener mListener;

    public ViewAutoScroll(View view) {
        mWr = new WeakReference<>(view);
    }

    /**
     * 设置越界滑动适配器
     */
    public void setOverScroller(IOverScroller overScroller) {
        mOverScroller = overScroller;
    }

    @Override
    public void run() {
        View scrollLayout = mWr.get();
        if (scrollLayout == null) {
            Logger.logE(TAG, "run: scrollLayout == null");
            return;
        }
        ensureOverScroller(scrollLayout);
        boolean finished = !mOverScroller.computeScrollOffset();
        if (!finished) {
            scrollLayout.scrollTo(mOverScroller.getCurrX(), mOverScroller.getCurrY());
            scrollLayout.post(this);
        } else {
            stop();
            if (mListener != null) {
                mListener.onScrollFinish();
            }
        }
    }

    /**
     * 停止滑动
     */
    public void stop() {
        View view = mWr.get();
        if (view == null) {
            Logger.logE(TAG, "stop: scrollLayout == null");
            return;
        }
        ensureOverScroller(view);
        view.removeCallbacks(this);
        if (!mOverScroller.isFinished()) {
            mOverScroller.forceFinished(true);
        }
    }

    /**
     * @param toX       X位置
     * @param toY       Y位置
     * @param duration  时长
     * @param direction 方向
     */
    private void scrollTo(int toX, int toY, int duration, @DirectionDefined int direction) {
        View scrollLayout = mWr.get();
        if (scrollLayout == null) {
            Logger.logE(TAG, "scrollTo: scrollLayout == null");
            return;
        }
        ensureOverScroller(scrollLayout);
        stop();
        if (direction == Direction.VERTICAL) {
            mOverScroller.startScroll(0, scrollLayout.getScrollY(), 0, toY - scrollLayout.getScrollY(), duration);
        } else if (direction == Direction.HORIZONTAL) {
            mOverScroller.startScroll(scrollLayout.getScrollX(), 0, toX - scrollLayout.getScrollX(), 0, duration);
        } else {
            mOverScroller.startScroll(scrollLayout.getScrollX(), scrollLayout.getScrollY(),
                    toX - scrollLayout.getScrollX(), toY - scrollLayout.getScrollY(), duration);
        }

        scrollLayout.post(this);
    }

    /**
     * X方向滑动
     */
    public void scrollXTo(int to, int duration) {
        scrollTo(to, 0, duration, Direction.HORIZONTAL);
    }

    /**
     * Y方向滑动
     */
    public void scrollYTo(int to, int duration) {
        scrollTo(0, to, duration, Direction.VERTICAL);
    }

    /**
     * Y方向滑动
     */
    public void springBack() {
        View scrollLayout = mWr.get();
        if (scrollLayout == null) {
            Logger.logE(TAG, "scrollTo: scrollLayout == null");
            return;
        }
        ensureOverScroller(scrollLayout);
        mOverScroller.springBack(scrollLayout.getScrollX(), scrollLayout.getScrollY(), 0, 0, 0, 0);
        scrollLayout.post(this);
    }

    /**
     * 双向滑动
     */
    public void scrollXYTo(int toX, int toY, int duration) {
        scrollTo(toX, toY, duration, Direction.BOTH_DIRECTION);
    }

    /**
     * 双向边界惯性滑动，且 startX = 0 && startY = 0
     *
     * @param vx   横向速度
     * @param vy   纵向速度
     * @param minX 最小X偏移
     * @param maxX 最大X偏移
     * @param minY 最小Y偏移
     * @param maxY 最大Y偏移
     */
    public void overXYFling(int vx, int vy, int minX, int maxX, int minY, int maxY) {
        View view = mWr.get();
        if (view == null) {
            Logger.logE(TAG, "overXYFling: scrollLayout == null");
            return;
        }
        ensureOverScroller(view);
        stop();
        mOverScroller.fling(0, 0, vx, vy, minX, maxX, minY, maxY);
        view.post(this);
    }

    /**
     * Y方向
     */
    public void overYFling(int vy, int minY, int maxY) {
        overXYFling(0, vy, 0, 0, minY, maxY);
    }

    /**
     * X方向
     */
    public void overXFling(int vx, int minX, int maxX) {
        overXYFling(vx, 0, minX, maxX, 0, 0);
    }

    public void setOnScrollFinishListener(OnScrollFinishListener listener) {
        mListener = listener;
    }

    /**
     * 确保OverScroller不为空
     */
    private void ensureOverScroller(View scrollLayout) {
        if (scrollLayout != null && mOverScroller == null) {
            mOverScroller = new DefaultOverScroller(scrollLayout.getContext());
        }
    }

    public interface OnScrollFinishListener {
        void onScrollFinish();
    }

    /**
     * 方向
     */
    public interface Direction {
        int VERTICAL = -1;      // 竖向
        int HORIZONTAL = 1;     // 横向
        int BOTH_DIRECTION = 0; // 横竖向
    }

    @IntDef({Direction.VERTICAL, Direction.HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    @interface DirectionDefined {
    }
}