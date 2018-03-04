package com.meizu.code.frame.base.model.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.meizu.code.frame.R;

import java.lang.ref.WeakReference;

/**
 * 基本Recyclerview
 * <p>
 * Created by mxm on 14/02/18.
 */

public class DelegateRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetectorCompat;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private Drawable mSelector;

    public DelegateRecyclerView(Context context) {
        super(context);
        initView();
    }

    public DelegateRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DelegateRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
        if (mSelector == null) {
            useDefaultSelector();
        }
    }

    private void useDefaultSelector() {
        setSelector(getResources().getDrawable(R.drawable.mz_recyclerview_selector));
    }

    private void initView() {
        addOnItemTouchListener(this);
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(),
                new GestureListener(this));
    }

    public void setSelector(Drawable selector) {
        mSelector = selector;
    }

    public void cleanSelector() {
        mSelector = null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    /**
     * 列表点击事件
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 列表长按事件
     *
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private WeakReference<RecyclerView> mWr;
        private View mClickView;

        public GestureListener(RecyclerView recyclerView) {
            mWr = new WeakReference<RecyclerView>(recyclerView);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mWr == null) return false;
            if (mClickView != null) {
                RecyclerView.ViewHolder VH = mWr.get().getChildViewHolder(mClickView);
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(VH.itemView, VH.getLayoutPosition());
                }
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mWr == null) return;
            if (mClickView != null) {
                RecyclerView.ViewHolder VH = mWr.get().getChildViewHolder(mClickView);
                if (mOnItemLongClickListener != null) {
                    mOnItemLongClickListener.onItemLongClick(VH.itemView, VH.getLayoutPosition());
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mClickView = mWr.get().findChildViewUnder(e.getX(), e.getY());
            return mClickView != null;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            if ()
        }
    }
}
