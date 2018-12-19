/*
 * ************************************************************
 * Class：DefaultFlingStratey.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 10:19:53
 * Last modified time：2018-11-08 10:11:01
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.stratey;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;

import com.meizu.code.frame.base.model.widget.overscroll.ColorOverScrollLayout;

import java.lang.ref.WeakReference;

/**
 * 普通View延时检测惯性滑动策略
 * 【用在Recyclerview/ListView/ScrollView/WebView上效果不会太佳】
 * <p>
 * Author: maxueming
 * Date: 2018/11/07
 */
public class DefaultFlingStratey extends FlingStrategyAbsImpl<ColorOverScrollLayout> {

    private static final int CHECK_FLING = 1000;            // 惯性检测
    private static final int CHECK_ENABLE_COUNTS = 30;      // 越界检测次数
    private VelocityTracker mVelocityTracker;               // 速度检测器
    private int mCurrentCheckCount;                         // 当前检测惯性滑动的次数
    private Handler mCheckFlingH;                           // 检测Fling Handler

    public DefaultFlingStratey(ColorOverScrollLayout overScrollView) {
        super(overScrollView);
        mCheckFlingH = new CheckFlingHandler(this);
    }

    @Override
    public boolean dealTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                } else {
                    mVelocityTracker.clear();
                }
                mVelocityTracker.addMovement(ev);
                mCheckFlingH.removeCallbacksAndMessages(null);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                mVelocityTracker.addMovement(ev);
                break;
            }
            case MotionEvent.ACTION_UP: {
                ColorOverScrollLayout scrollLayout = mOverScrollViewWr.get();
                if (scrollLayout != null && scrollLayout.getScrollY() == 0) {
                    mVelocityTracker.computeCurrentVelocity(1000);
                    mVy = (int) mVelocityTracker.getYVelocity();
                    mCurrentCheckCount = 0;
                    mCheckFlingH.sendEmptyMessageDelayed(CHECK_FLING, 10);
                }
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mVelocityTracker.recycle();
                break;
            }
        }
        return false;
    }

    private static class CheckFlingHandler extends Handler {

        private final WeakReference<DefaultFlingStratey> mWr;

        CheckFlingHandler(DefaultFlingStratey stratey) {
            mWr = new WeakReference<>(stratey);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case CHECK_FLING: {
                    DefaultFlingStratey stratey = mWr.get();
                    if (stratey != null && stratey.mCurrentCheckCount < CHECK_ENABLE_COUNTS) {
                        stratey.mCurrentCheckCount++;
                        ColorOverScrollLayout scrollLayout = stratey.mOverScrollViewWr.get();
                        if (scrollLayout == null) return;
                        if (!scrollLayout.canChildScrollY(-1) || !scrollLayout.canChildScrollY(1)) {
                            int vy = -(int) (stratey.mVy * (1 - ((float) stratey.mCurrentCheckCount / CHECK_ENABLE_COUNTS)));
                            int maxOverFlingHeight = scrollLayout.getMaxOverFlingHeight();
                            scrollLayout.overYFling(vy, -maxOverFlingHeight, maxOverFlingHeight);
                        } else {
                            sendEmptyMessageDelayed(CHECK_FLING, 10);
                        }
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }
}
