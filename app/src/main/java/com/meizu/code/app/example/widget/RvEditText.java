/*
 * ************************************************************
 * Class：RvEditText.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-10-18 11:34:06
 * Last modified time：2018-10-18 11:34:05
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */
package com.meizu.code.app.example.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;

public class RvEditText extends AppCompatEditText {
    private static final String TAG = "RvEditText";

    public RvEditText(Context context) {
        super(context);
    }

    public RvEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RvEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ViewParent parent = getParent();
        if (parent != null) {
            int action = event.getAction();
            boolean isCanScroll = canScrollVertically();
            parent.requestDisallowInterceptTouchEvent(isCanScroll
                            || action == MotionEvent.ACTION_CANCEL
                            || action == MotionEvent.ACTION_UP);
        }
        return super.onTouchEvent(event);
    }

    private boolean canScrollVertically() {
        return canScrollVertically(-1) || canScrollVertically(1);
    }
}
