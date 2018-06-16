package com.meizu.code.frame.base.model.widget.common;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.meizu.code.frame.R;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;
import com.meizu.code.frame.utils.CodeFrameStaticUtils;

/**
 * 加载更多View
 * <p>
 * Author: maxueming 502048
 * Date: 18-6-12
 */
public class LoadMoreView extends View {
    private static final String TAG = "LoadMoreView";

    private static final int DOT_DURATION = 2000;
    private int mDotCount;
    private ObjectAnimator mDotCountAnimator;
    private CharSequence mLoadingText;
    private Paint mTextPaint;

    public LoadMoreView(Context context) {
        this(context, null);
    }

    public LoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTypeface(font);
        mTextPaint.setColor(this.mLoadingTextColor);
        mTextPaint.setTextSize(CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_14dp));
        startDotCountAnimator();
    }

    /**
     * 设置文本
     */
    public void setLoadingText(String str) {
        mLoadingText = str;
    }

    @Keep
    public void setDotCount(@IntRange(from = 0, to = 3) int dotCount) {
        Log.e(TAG, "dotCount = " + dotCount);
        mDotCount = dotCount;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawText(canvas);
        drawDot(canvas);
    }

    /**
     * 画文本
     */
    private void drawText(Canvas canvas) {

    }

    /**
     * 画点
     */
    private void drawDot(Canvas canvas) {

    }

    /**
     * 点动画
     */
    private void startDotCountAnimator() {
        mDotCountAnimator = ObjectAnimator.ofInt(this, "dotCount", 0, 4);
        mDotCountAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mDotCountAnimator.setDuration(DOT_DURATION);
        mDotCountAnimator.setInterpolator(CodeFrameStaticUtils
                .getPathInterpolator(0.33f, 0.00f, 0.67f, 1.00f));
        mDotCountAnimator.start();
    }

    public void stopAnimator() {
        if (mDotCountAnimator != null && mDotCountAnimator.isRunning()) {
            mDotCountAnimator.cancel();
        }
        mDotCountAnimator = null;
    }
}
