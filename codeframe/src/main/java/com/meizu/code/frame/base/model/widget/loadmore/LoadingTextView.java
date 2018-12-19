package com.meizu.code.frame.base.model.widget.loadmore;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Keep;
import android.text.TextUtils;
import android.util.AttributeSet;
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
public class LoadingTextView extends View {
    private static final String TAG = "LoadingTextView";

    private static final int DOT_COUNT = 3;
    private static final int DOT_DURATION = 1000;
    private static final int DOT_MARGIN = CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_4dp);
    private static final int DOT_RADIUS = CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_1dp);

    private ObjectAnimator mDotCountAnimator;
    private int mDotCount;
    private CharSequence mLoadingText;
    private int mLoadingTextSize;
    private int mLoadingTextColor;
    private Paint mTextPaint;

    public LoadingTextView(Context context) {
        this(context, null);
    }

    public LoadingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingTextView);
        mLoadingText = typedArray.getString(R.styleable.LoadingTextView_loading_text);
        mLoadingTextSize = typedArray.getDimensionPixelSize(R.styleable.LoadingTextView_loading_size,
                CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.common_16sp));
        mLoadingTextColor = typedArray.getColor(R.styleable.LoadingTextView_loading_color,
                CodeFrameStaticResUtils.getColor(R.color.loading_text_color));
        if (TextUtils.isEmpty(mLoadingText)) {
            mLoadingText = CodeFrameStaticResUtils.getString(R.string.load_more);
        }
        typedArray.recycle();
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mLoadingTextColor);
        mTextPaint.setTextSize(mLoadingTextSize);
    }

    /**
     * 设置文本
     */
    public void setLoadingText(CharSequence str) {
        mLoadingText = str;
    }

    @Keep
    private void setDotCount(int dotCount) {
        if (mDotCount != dotCount) {
            mDotCount = dotCount;
            invalidate();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        int width = getWidth();
        int height = getHeight();
        // 画文本,文字的x轴坐标
        float textWidth = mTextPaint.measureText(mLoadingText.toString());
        float x = (width - textWidth) / 2;
        // 文字的y轴baseline坐标
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float yOffset = (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
        float y = height / 2 + yOffset;
        canvas.drawText(mLoadingText.toString(), x, y, mTextPaint);

        // 画点
        final int dotCount = mDotCount;
        if (dotCount > 0 && dotCount <= DOT_COUNT) {
            float dotXOffset = (width + textWidth) / 2;
            for (int i = 1; i <= dotCount; i++) {
                canvas.drawCircle(dotXOffset + i * DOT_MARGIN, y, DOT_RADIUS, mTextPaint);
            }
        }
    }

    /**
     * 点动画
     */
    private void startDotCountAnimator() {
        if (mDotCountAnimator == null) {
            mDotCountAnimator = ObjectAnimator.ofInt(this, "dotCount", 0, DOT_COUNT + 1);
            mDotCountAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mDotCountAnimator.setDuration(DOT_DURATION);
            mDotCountAnimator.setInterpolator(CodeFrameStaticUtils
                    .getPathInterpolator(0.33f, 0.00f, 0.67f, 1.00f));
        }
        if (!mDotCountAnimator.isRunning()) {
            mDotCountAnimator.start();
        }
    }

    /**
     * 停止动画
     */
    public void stopDotCountAnimator() {
        if (mDotCountAnimator != null && mDotCountAnimator.isRunning()) {
            mDotCountAnimator.cancel();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startDotCountAnimator();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopDotCountAnimator();
    }
}
