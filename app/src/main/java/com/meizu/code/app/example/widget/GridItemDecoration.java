/*
 * ************************************************************
 * Class：GridItemDecoration.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-10-19 10:35:34
 * Last modified time：2018-10-19 10:35:34
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.example.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.meizu.code.app.R;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    /*private static final int[] ATTRS = new int[] {
            android.R.attr.listDivider
    };*/

    private Drawable mDivider;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GridItemDecoration(Context context) {
        mDivider = context.getDrawable(R.drawable.grid_divider);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        // 绘制间隔，每一个item，绘制右边和下方间隔样式
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount;
        int orientation;
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
            orientation = ((GridLayoutManager)parent.getLayoutManager()).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager)parent.getLayoutManager()).getSpanCount();
            orientation = ((StaggeredGridLayoutManager)parent.getLayoutManager()).getOrientation();
        } else {
            return;
        }
        boolean isDrawHorizontalDivider = true;
        boolean isDrawVerticalDivider = true;
        int extra = childCount % spanCount;
        extra = extra == 0 ? spanCount : extra;
        for(int i = 0; i < childCount; i++) {
            isDrawVerticalDivider = true;
            isDrawHorizontalDivider = true;
            // 如果是竖直方向，最右边一列不绘制竖直方向的间隔
            if(orientation == LinearLayoutManager.VERTICAL && (i + 1) % spanCount == 0) {
                isDrawVerticalDivider = false;
            }

            // 如果是竖直方向，最后一行不绘制水平方向间隔
            if(orientation == LinearLayoutManager.VERTICAL && i >= childCount - extra) {
                isDrawHorizontalDivider = false;
            }

            // 如果是水平方向，最下面一行不绘制水平方向的间隔
            if(orientation == LinearLayoutManager.HORIZONTAL && (i + 1) % spanCount == 0) {
                isDrawHorizontalDivider = false;
            }

            // 如果是水平方向，最后一列不绘制竖直方向间隔
            if(orientation == LinearLayoutManager.HORIZONTAL && i >= childCount - extra) {
                isDrawVerticalDivider = false;
            }

            if(isDrawHorizontalDivider) {
                drawHorizontalDivider(c, parent, i);
            }

            if(isDrawVerticalDivider) {
                drawVerticalDivider(c, parent, i);
            }
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount;
        int orientation;
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
            orientation = ((GridLayoutManager)parent.getLayoutManager()).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager)parent.getLayoutManager()).getSpanCount();
            orientation = ((StaggeredGridLayoutManager)parent.getLayoutManager()).getOrientation();
        } else {
            return;
        }
        int position = parent.getChildLayoutPosition(view);
        if(orientation == LinearLayoutManager.VERTICAL && (position + 1) % spanCount == 0) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            return;
        }

        if(orientation == LinearLayoutManager.HORIZONTAL && (position + 1) % spanCount == 0) {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
            return;
        }

        outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
    }


    /**
     * 绘制竖直间隔线
     *
     * @param canvas
     * @param parent
     *              父布局，RecyclerView
     * @param position
     *              irem在父布局中所在的位置
     */
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent, int position) {
        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        final int top = child.getTop() - params.topMargin;
        final int bottom = child.getBottom() + params.bottomMargin + mDivider.getIntrinsicHeight();
        final int left = child.getRight() + params.rightMargin;
        final int right = left + mDivider.getIntrinsicWidth();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

    /**
     * 绘制水平间隔线
     *
     * @param canvas
     * @param parent
     *              父布局，RecyclerView
     * @param position
     *              item在父布局中所在的位置
     */
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent, int position) {
        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + mDivider.getIntrinsicHeight();
        final int left = child.getLeft() - params.leftMargin;
        final int right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
        mDivider.setBounds(left, top, right, bottom);
        mDivider.draw(canvas);
    }

}
