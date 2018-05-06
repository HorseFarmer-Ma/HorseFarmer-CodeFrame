package com.meizu.code.frame.base.model.delegate;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.meizu.code.frame.R;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;

/**
 * 基本Recyclerview
 * <p>
 * Created by mxm on 14/02/18.
 */
public class DelegateRecyclerView extends RecyclerView implements RecyclerView.OnItemTouchListener {

    private static final String TAG = "DelegateRecyclerView";
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
                new GestureListener());
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
        Logger.d(TAG, "onRequestDisallowInterceptTouchEvent(): disallowIntercept = [" + disallowIntercept + "]");
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

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private View mClickView;

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mClickView != null) {
                RecyclerView.ViewHolder VH = getChildViewHolder(mClickView);
                if (VH != null) {
                    View childView = VH.itemView;
                    boolean isHandled = childView != null && childView.dispatchTouchEvent(e);
                    if (mOnItemClickListener != null && !isHandled) {
                        mOnItemClickListener.onItemClick(childView, VH.getLayoutPosition());
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mClickView != null) {
                RecyclerView.ViewHolder VH = getChildViewHolder(mClickView);
                if (VH != null) {
                    View childView = VH.itemView;
                    boolean isHandled = childView != null && childView.dispatchTouchEvent(e);
                    if (mOnItemLongClickListener != null && !isHandled) {
                        mOnItemLongClickListener.onItemLongClick(childView, VH.getLayoutPosition());
                    }
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            mClickView = findChildViewUnder(e.getX(), e.getY());
            return mClickView != null;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }
    }
}
