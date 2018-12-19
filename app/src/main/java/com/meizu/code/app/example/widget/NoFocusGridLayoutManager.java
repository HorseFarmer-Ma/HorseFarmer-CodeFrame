/*
 * ************************************************************
 * Class：NoFocusGridLayoutManager.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-10-23 09:23:09
 * Last modified time：2018-10-23 09:23:08
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.example.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class NoFocusGridLayoutManager extends GridLayoutManager {
    public NoFocusGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NoFocusGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public NoFocusGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public boolean requestChildRectangleOnScreen(RecyclerView parent, View child, Rect rect, boolean immediate, boolean focusedChildVisible) {
        return false;
    }
}
