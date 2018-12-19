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
public class DelegateRecyclerView extends RecyclerView {

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

    private void initView() {
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(),
                new GestureListener(this));
        useDefaultSelector();
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    private void useDefaultSelector() {
        setSelector(getResources().getDrawable(R.drawable.mz_recyclerview_selector));
    }

    public void setSelector(Drawable selector) {
        mSelector = selector;
    }

    public void cleanSelector() {
        mSelector = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return mGestureDetectorCompat.onTouchEvent(e) || super.onTouchEvent(e);
    }

    /**
     * 列表点击事件
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 列表长按事件
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

    private static class GestureListener extends GestureDetector.SimpleOnGestureListener {
        private final WeakReference<DelegateRecyclerView> mWr;
        private View mClickView;

        public GestureListener(DelegateRecyclerView recyclerView) {
            mWr = new WeakReference<DelegateRecyclerView>(recyclerView);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            DelegateRecyclerView recyclerView = mWr.get();
            if (mClickView != null && recyclerView != null) {
                RecyclerView.ViewHolder VH = recyclerView.getChildViewHolder(mClickView);
                OnItemClickListener onItemClickListener = recyclerView.getOnItemClickListener();
                if (VH != null && onItemClickListener != null) {
                    onItemClickListener.onItemClick(VH.itemView, VH.getLayoutPosition());
                    return true;
                }
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            DelegateRecyclerView recyclerView = mWr.get();
            if (mClickView != null && recyclerView != null) {
                RecyclerView.ViewHolder VH = recyclerView.getChildViewHolder(mClickView);
                OnItemLongClickListener onItemLongClickListener = recyclerView.getOnItemLongClickListener();
                if (VH != null && onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(VH.itemView, VH.getLayoutPosition());
                }
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            DelegateRecyclerView recyclerView = mWr.get();
            if (recyclerView != null) {
                mClickView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            }
            return mClickView != null;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }
    }
}
