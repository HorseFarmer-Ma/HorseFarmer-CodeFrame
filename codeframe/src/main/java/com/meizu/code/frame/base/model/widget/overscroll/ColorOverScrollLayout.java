/*
 * ************************************************************
 * Class：ColorOverScrollLayout.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-04 16:21:49
 * Last modified time：2018-11-04 16:21:49
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import android.widget.LinearLayout;
import android.widget.ListView;

import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.model.widget.overscroll.dao.IOverScrollView;
import com.meizu.code.frame.base.model.widget.overscroll.dao.IOverScroller;
import com.meizu.code.frame.base.model.widget.overscroll.stratey.DefaultFlingStratey;
import com.meizu.code.frame.base.model.widget.overscroll.stratey.FlingStrategyAbsImpl;

/**
 * 支持滑动越界惯性回弹，支持多手势
 * 理论上支持Recyclerview，ListView，ScrollView，普通View等
 * 当前仅做竖直方向
 * <p>
 * Author: maxueming
 * Date:2018-11-04
 */
public class ColorOverScrollLayout extends LinearLayout implements IOverScrollView {
    private static final String TAG = "ColorOverScrollLayout";
    private static final int DEFAULT_MAX_OVER_FLING_HEIGHT = 250;               // 默认最大越界高度
    private static final int DEFAULT_AUTO_SCROLL_DURATION = 350;                // 默认滑动复位时间
    private static final float DEFAULT_DAMPING_MULTIPLIER = 3.f;                // 默认阻尼倍数

    private View mTargetView;                                                   // ColorOverScrollLayout包裹的目标View
    private float mInitialDownY;                                                // 初始点击Y坐标
    private int mOverScrollDistance;                                            // 屏幕高度，用于计算阻尼
    private float mLastY;                                                       // 记录上次点击的Y坐标
    private int mTouchSlop;                                                     // 最小滑动距离
    private ViewAutoScroll mViewAutoScroll;                                     // 当前Layout的滚动计算器
    private OnChildScrollCallback mChildScrollCallback;                         // 子类View是否可滚动回调
    private boolean mHasSendCancelEvent;                                        // 用于边界交换事件
    private int mActivePointerId = 0;                                           // 多手势记录Id
    private int mMaxOverFlingHeight = DEFAULT_MAX_OVER_FLING_HEIGHT;            // 最大的越界高度
    private FlingStrategyAbsImpl mFlingStrategy;                                // 越界滑动策略
    private float mDampingMultiplier = DEFAULT_DAMPING_MULTIPLIER;              // 阻尼倍数，越大越难滑动

    public ColorOverScrollLayout(Context context) {
        this(context, null);
    }

    public ColorOverScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorOverScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProperty(context);
    }

    private void initProperty(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mOverScrollDistance = metrics.heightPixels;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mViewAutoScroll = new ViewAutoScroll(this);
        mViewAutoScroll.setOnScrollFinishListener(new ViewAutoScroll.OnScrollFinishListener() {
            @Override
            public void onScrollFinish() {
                if (getScrollY() != 0) {
                    mViewAutoScroll.scrollYTo(0, DEFAULT_AUTO_SCROLL_DURATION);
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new RuntimeException(TAG + ": onFinishInflate - 只能有一个子控件");
        }
        mTargetView = getChildAt(0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mFlingStrategy == null) {
            mFlingStrategy = new DefaultFlingStratey(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            View view = getChildAt(0);
            view.layout(left, top, right, bottom);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mFlingStrategy != null) {
            mFlingStrategy.dealTouchEvent(ev);
        }
        float y = ev.getY(ev.findPointerIndex(mActivePointerId));
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                mInitialDownY = y;
                mLastY = y;
                mHasSendCancelEvent = false;
                mViewAutoScroll.stop();
                mActivePointerId = ev.getPointerId(0);
                super.dispatchTouchEvent(ev);
                return true;
            }

            case MotionEvent.ACTION_MOVE: {
                float yDiff = y - mLastY;
                mLastY = y;
                if (Math.abs(y - mInitialDownY) > mTouchSlop) {
                    if ((yDiff > 0 && !canChildScrollY(-1)) || (yDiff < 0 && !canChildScrollY(1))) {
                        yDiff = calcRealOverScrollDist(yDiff);
                        scrollBy(0, (int) (-yDiff));
                        if (!mHasSendCancelEvent) {
                            mHasSendCancelEvent = true;
                            sendCancelEvent(ev);
                        }
                        return true;
                    } else {
                        int scrollY = getScrollY();
                        if (scrollY == 0 && mHasSendCancelEvent) {
                            sendDownEvent(ev);
                            mHasSendCancelEvent = false;
                        } else if ((scrollY < 0 && yDiff < 0) || (scrollY > 0 && yDiff > 0)) {
                            int offset = (int) ((-yDiff) * 0.4);
                            offset = Math.abs(offset) > Math.abs(scrollY) ? -scrollY : offset;
                            scrollBy(0, offset);
                        }
                    }
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = 0;
                int scrollY = getScrollY();
                if (scrollY != 0) {
                    mViewAutoScroll.scrollYTo(0, DEFAULT_AUTO_SCROLL_DURATION);
                }
                break;
            }

            case MotionEvent.ACTION_POINTER_DOWN: {
                mHasSendCancelEvent = false;
                int pointerIndex = ev.getActionIndex();
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return super.dispatchTouchEvent(ev);
                }
                mLastY = ev.getY(pointerIndex);
                mActivePointerId = ev.getPointerId(pointerIndex);
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                int pointerIndex = ev.getActionIndex();
                int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    // 交换多手势
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 计算阻尼后的位移
     */
    private float calcRealOverScrollDist(float dist) {
        return dist * (1.0F - (float) Math.abs(getScrollY()) * 1.0F / (float) this.mOverScrollDistance) / mDampingMultiplier;
    }

    private void sendCancelEvent(MotionEvent ev) {
        if (ev == null) return;
        MotionEvent event = MotionEvent.obtain(ev);
        event.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(event);
    }

    private void sendDownEvent(MotionEvent ev) {
        if (ev == null) return;
        MotionEvent event = MotionEvent.obtain(ev);
        event.setAction(MotionEvent.ACTION_DOWN);
        super.dispatchTouchEvent(event);
    }

    public interface OnChildScrollCallback {
        boolean canChildVertically(@Nullable View child, int direction);
    }

    public void setOnChildScrollCallback(@Nullable OnChildScrollCallback callback) {
        mChildScrollCallback = callback;
    }

    @Override
    public boolean canChildScrollY(int direction) {
        if (mChildScrollCallback != null) {
            return mChildScrollCallback.canChildVertically(mTargetView, direction);
        }
        if (mTargetView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mTargetView, direction);
        }
        return mTargetView.canScrollVertically(direction);
    }

    @Override
    public void overYFling(int vy, int minY, int maxY) {
        if (mViewAutoScroll != null) {
            mViewAutoScroll.overYFling(vy, minY, maxY);
        }
    }

    @Override
    public View getTargetView() {
        return mTargetView;
    }

    @Override
    public int getMaxOverFlingHeight() {
        return mMaxOverFlingHeight;
    }

    // -------------------------------------------------- 开放方法区 ------------------------------------------------------------ //

    /**
     * 设置越界滑动最大高度
     */
    public void setMaxOverFlingHeight(int maxOverFlingHeight) {
        mMaxOverFlingHeight = maxOverFlingHeight;
    }

    /**
     * 设置越界滑动策略
     */
    public void setFlingStrategy(FlingStrategyAbsImpl flingStrategy) {
        Logger.logD(TAG, "setFlingStrategy");
        if (flingStrategy == null) {
            mFlingStrategy = new DefaultFlingStratey(this);
        }
        mFlingStrategy = flingStrategy;
    }

    /**
     * 设置采用的越界滑动Scroller规则
     */
    public void setOverScroller(IOverScroller overScroller) {
        mViewAutoScroll.setOverScroller(overScroller);
    }

    /**
     * 设置阻尼倍数
     */
    public void setDampingMultiplier(float dampingMultiplier) {
        mDampingMultiplier = dampingMultiplier;
    }
}