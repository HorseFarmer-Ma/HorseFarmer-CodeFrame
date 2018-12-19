package com.meizu.code.frame.base.model.widget.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ListViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Scroller;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;

import java.lang.ref.WeakReference;

/**
 * 下拉刷新通用化组件
 * <p>
 * Author: maxueming 502048
 * Date: 18-7-31
 */

public class SwipeRefreshLayout extends FrameLayout implements IRefresh {

    private static final String TAG = "SwipeRefreshLayout";

    private static final float DRAG_RATE = 1f;
    private static final int START_POSITION = 0;
    private static final int SCROLL_TO_COMPLETE_DURATION = 500;

    private IAdapter<IRefresh> mAdapter;
    private int mTouchSlop;
    private OnChildScrollUpCallback mChildScrollUpCallback;
    private boolean mIsBeingDragged;
    private View mHeadView;
    private View mTarget;                   // the target of the gesture
    private int mHeadViewHeight;            // 头部View高度
    private int mCurrentOffsetTop;          // 距离头部的偏移>=0
    private boolean mHasMeasureHeader;
    private int mEnableRefreshHeight;         // 需要超过这个大小才进入下拉刷新状态 >= mHeadViewHeight
    private int mEnableCrossHeight;            // 允许手拖动越界的高度
    private int mActivePointerId;
    private float mInitialDownY;
    private boolean mIsTouch;               // 手指是否按下
    private boolean mHasSendCancelEvent;
    private int mLastOffsetTop;             // 上一次target的偏移高度
    private float mLastMotionY;
    private MotionEvent mLastEvent;

    @RefreshState
    private int mState = State.RESET;
    private AutoScroll mAutoScoll;

    public SwipeRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        if (context == null) return;
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mAutoScoll = new AutoScroll(this);
        mEnableCrossHeight = CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_50dp);
    }

//    private final AutoScroll.OnFinishScrollListener mOnFinishScrollListener = new AutoScroll.OnFinishScrollListener() {
//        @Override
//        public void onNormalFinish() {
//
//        }
//    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // IDE显示模式下,可用于显示预览界面
        if (isInEditMode()) {
            return;
        }

        if (getChildCount() > 1) {
            throw new RuntimeException("SwipeRefreshLayout can only exists 1 child widget!");
        }

        ensureTarget();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setAdapter(getAdapter());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        destroyInternal();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }
        mTarget.measure(MeasureSpec.makeMeasureSpec(
                getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
                getMeasuredHeight() - getPaddingTop() - getPaddingBottom(), MeasureSpec.EXACTLY));
        measureChild(mHeadView, widthMeasureSpec, heightMeasureSpec);
        // 防止header重复测量
        if (!mHasMeasureHeader) {
            mHasMeasureHeader = true;
            mHeadViewHeight = mHeadView.getMeasuredHeight(); // header高度
            mEnableRefreshHeight = mHeadViewHeight;   // 需要pull这个距离才进入松手刷新状态
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();
        if (getChildCount() == 0) {
            return;
        }

        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return;
        }

        // target铺满屏幕
        final View child = mTarget;
        final int childLeft = getPaddingLeft();
        final int childTop = getPaddingTop() + mCurrentOffsetTop;
        final int childWidth = width - getPaddingLeft() - getPaddingRight();
        final int childHeight = height - getPaddingTop() - getPaddingBottom();
//        Logger.logD(TAG, "mCurrentOffsetTop = " + mCurrentOffsetTop + "childTop = " + childTop + "getPaddingTop() = " + getPaddingTop());
        child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);

        // header放到target的上方，水平居中
        int refreshViewWidth = mHeadView.getMeasuredWidth();
        mHeadView.layout((width / 2 - refreshViewWidth / 2),
                -mHeadViewHeight + mCurrentOffsetTop,
                (width / 2 + refreshViewWidth / 2),
                mCurrentOffsetTop);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isEnabled() || mTarget == null || canChildScrollUp()) {
            return super.dispatchTouchEvent(ev);
        }

        int action = ev.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (mAutoScoll != null) {
                    mAutoScoll.stop();
                }
                mActivePointerId = ev.getPointerId(0);
                mIsTouch = true;
                mHasSendCancelEvent = false;
                mIsBeingDragged = false;
                mLastOffsetTop = mCurrentOffsetTop;
                mCurrentOffsetTop = mTarget.getTop(); // 当前target偏移高度
                int pointerIndex = ev.findPointerIndex(mActivePointerId);
                mInitialDownY = ev.getY(pointerIndex);
                super.dispatchTouchEvent(ev);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (mActivePointerId < 0) {
                    Logger.logE(TAG, "Got ACTION_MOVE event but have an invalid active pointer id.");
                    return super.dispatchTouchEvent(ev);
                }
                mLastEvent = ev; // 最后一次move事件

                final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                float y = ev.getY(pointerIndex);
                float yDiff = y - mLastMotionY;

                int i = mEnableRefreshHeight + mEnableCrossHeight;   // 允许越界的高度
                int diff = i - mCurrentOffsetTop;
                double ratio = (float) (diff < 0 ? 0 : diff) / (float) i;
                float offsetY = yDiff * DRAG_RATE * (float) Math.pow(ratio, 2);
                mLastMotionY = y;
                startDragging(y);

                if (mIsBeingDragged) {
                    if ((offsetY > 0 && !canChildScrollUp()) || (offsetY <= 0 && mCurrentOffsetTop > START_POSITION)) {
                        moveSpinner(offsetY);
                        return true;
                    } /*else if (offsetY < 0 && mCurrentOffsetTop == 0) {
                        if (mHasSendCancelEvent) {
                            mHasSendCancelEvent = false;
                            sendDownEvent();
                        }
                        return super.dispatchTouchEvent(ev);
                    }*/
                }
                break;
            }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsTouch = false;
                mActivePointerId = -1;

                if (mAutoScoll != null) {
                    if (mCurrentOffsetTop >= mEnableRefreshHeight) {
                        mAutoScoll.scrollTo(mEnableRefreshHeight, SCROLL_TO_COMPLETE_DURATION);
                    } else {
                        mAutoScoll.scrollTo(START_POSITION, SCROLL_TO_COMPLETE_DURATION);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                int pointerIndex = ev.getActionIndex();
                if (pointerIndex < 0) {
                    Log.e(TAG, "Got ACTION_POINTER_DOWN event but have an invalid action index.");
                    return super.dispatchTouchEvent(ev);
                }
                mLastMotionY = ev.getY(pointerIndex);
                mActivePointerId = ev.getPointerId(pointerIndex);
                Logger.logD(TAG, "activePointerId = " + mActivePointerId);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = ev.getActionIndex();
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mLastMotionY = ev.getY(newPointerIndex);
                    mActivePointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 移动HeadView和TargetView
     *
     * @param diff
     */
    private void moveSpinner(float diff) {
//        Logger.logD(TAG, "偏移量diff = " + diff);
        int offset = Math.round(diff);
        if (offset == 0) {
            return;
        }
        // 发送cancel事件给child
        if (!mHasSendCancelEvent && mIsTouch && mCurrentOffsetTop > START_POSITION) {
            sendCancelEvent();
            mHasSendCancelEvent = true;
        }

        int targetY = Math.max(0, mCurrentOffsetTop + offset); // target不能移动到小于0的位置……
        offset = targetY - mCurrentOffsetTop;
        setOffsetTopAndBottom(offset);

        if (mIsTouch && mState == State.RESET) {
            changeState(State.PULL);
        } else if (!mIsTouch && mCurrentOffsetTop == mEnableRefreshHeight && mState != State.REFRESHING) {
            changeState(State.REFRESHING);
        } else if (mCurrentOffsetTop == START_POSITION) {
            changeState(State.RESET);
        }

        getAdapter().onPositionChange(mCurrentOffsetTop, mLastOffsetTop, mEnableRefreshHeight, mIsTouch, mState);
    }

    /**
     * 更改状态
     *
     * @param state 状态值
     */
    private void changeState(@RefreshState int state) {
        Logger.logD(TAG, "changeState(): state = [" + state + "]");
        mState = state;
        switch (mState) {
            case State.RESET: {
                getAdapter().reset();
                break;
            }
            case State.PULL: {
                getAdapter().pull();
                break;
            }
            case State.REFRESHING: {
                getAdapter().refreshing();
                break;
            }
            case State.COMPLETED: {
                getAdapter().complete();
                break;
            }
        }
    }

    /**
     * 取消子控件移动事件
     */
    private void sendCancelEvent() {
        if (mLastEvent == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(mLastEvent);
        ev.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(ev);
    }

    /**
     * 添加子控件Down事件
     */
    private void sendDownEvent() {
        if (mLastEvent == null) {
            return;
        }
        MotionEvent ev = MotionEvent.obtain(mLastEvent);
        ev.setAction(MotionEvent.ACTION_DOWN);
        super.dispatchTouchEvent(ev);
    }

    private void startDragging(float y) {
        final float yDiff = y - mInitialDownY;
        if (Math.abs(yDiff) > mTouchSlop && !mIsBeingDragged) {
            mIsBeingDragged = true;
        }
    }

    @Override
    public void refreshComplete() {
        if (mState != State.RESET) {
            if (mCurrentOffsetTop > START_POSITION && mAutoScoll != null) {
                mAutoScoll.scrollTo(START_POSITION, SCROLL_TO_COMPLETE_DURATION);
            }
            changeState(State.COMPLETED);
        }
    }

    @Override
    public void scrollToRefresh() {

    }

    private View getHeadView() {
        return getAdapter().createHeadView();
    }

    public IAdapter getAdapter() {
        if (mAdapter == null) {
            mAdapter = new SwipeRefreshAdapter(getContext());
            mAdapter.registerObserver(this);
        }
        return mAdapter;
    }

    public void setAdapter(IAdapter<IRefresh> adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterObserver();
        }

        // 设置为默认Adapter
        mAdapter = (adapter == null) ? new SwipeRefreshAdapter(getContext()) : adapter;
        mAdapter.registerObserver(this);
        View headView = getAdapter().createHeadView();
        setHeadView(headView);
    }

    private void setHeadView(View view) {
        if (view != null && view != mHeadView) {
            Logger.logD(TAG, "设置HeadView");
            removeView(mHeadView);

            // 为header添加默认的layoutParams
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                view.setLayoutParams(layoutParams);
            }
            mHeadView = view;
            addView(mHeadView);
        }
    }

    void setOffsetTopAndBottom(int offset) {
        if (mHeadView == null || mTarget == null) {
            return;
        }
        mHeadView.bringToFront();
        ViewCompat.offsetTopAndBottom(mTarget, offset);
        ViewCompat.offsetTopAndBottom(mHeadView, offset);
        mCurrentOffsetTop = mTarget.getTop();
//        Logger.logD(TAG, "mCurrentOffsetTop = " + mCurrentOffsetTop);
    }

    public boolean canChildScrollUp() {
        if (mChildScrollUpCallback != null) {
            return mChildScrollUpCallback.canChildScrollUp(this, mTarget);
        }
        if (mTarget instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mTarget, -1);
        }
        return ViewCompat.canScrollVertically(mTarget, -1);
    }

    private void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(mHeadView)) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    public int getEnableRefreshHeight() {
        return mEnableRefreshHeight;
    }

    public void setEnableRefreshHeight(int enableRefreshHeight) {
        mEnableRefreshHeight = enableRefreshHeight;
    }

    public void setOnChildScrollUpCallback(@Nullable OnChildScrollUpCallback callback) {
        mChildScrollUpCallback = callback;
    }

    public interface OnChildScrollUpCallback {
        boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child);
    }

    private static class AutoScroll implements Runnable {
        private final WeakReference<SwipeRefreshLayout> mWr;
        private Scroller mScroller;
        private int mLastY;
        private OnFinishScrollListener mListener;

        AutoScroll(SwipeRefreshLayout layout) {
            mWr = new WeakReference<>(layout);
            SwipeRefreshLayout swipeRefreshLayout = mWr.get();
            if (swipeRefreshLayout != null) {
                mScroller = new Scroller(swipeRefreshLayout.getContext());
            }
        }

        @Override
        public void run() {
            SwipeRefreshLayout swipeRefreshLayout = mWr.get();
            if (swipeRefreshLayout == null) {
                return;
            }
            boolean finished = !mScroller.computeScrollOffset() || mScroller.isFinished();
            if (!finished) {
                int currY = mScroller.getCurrY();
                int offset = currY - mLastY;
                mLastY = currY;
                swipeRefreshLayout.moveSpinner(offset); // 调用此方法移动header和target
                swipeRefreshLayout.post(this);
            } else {
                stop();
                if (mListener != null) {
                    mListener.onNormalFinish();
                }
            }
        }

        void scrollTo(int to, int duration) {
            SwipeRefreshLayout swipeRefreshLayout = mWr.get();
            if (swipeRefreshLayout == null) {
                return;
            }
            int from = swipeRefreshLayout.mCurrentOffsetTop;
            int distance = to - from;
            stop();
            if (distance == 0) {
                return;
            }
            mScroller.startScroll(0, 0, 0, distance, duration);
            swipeRefreshLayout.post(this);
        }

        private void stop() {
            SwipeRefreshLayout swipeRefreshLayout = mWr.get();
            if (swipeRefreshLayout == null) {
                return;
            }
            swipeRefreshLayout.removeCallbacks(this);
            if (!mScroller.isFinished()) {
                mScroller.forceFinished(true);
            }
            mLastY = 0;
        }

        void setOnFinishScrollListener(OnFinishScrollListener listener) {
            mListener = listener;
        }

        interface OnFinishScrollListener {
            void onNormalFinish();
        }
    }

    private void destroyInternal() {
        if (mAdapter != null) {
            mAdapter.unregisterObserver();
            mAdapter = null;
        }
    }
}
