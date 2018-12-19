package com.meizu.code.frame.base.model.widget.refresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.meizu.code.frame.R;

/**
 * <p>
 * Author: maxueming 502048
 * Date: 18-8-3
 */

public class SwipeRefreshAdapter implements IAdapter<IRefresh> {

    private Context mContext;
    private IRefresh mIRefresh;
    private View mHeadView;
    private ViewHolder mViewHolder;
    private boolean mIsRefreshing = false;
    private OnRefreshListener mOnRefreshListener;

    public SwipeRefreshAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void registerObserver(IRefresh observer) {
        mIRefresh = observer;
    }

    @Override
    public void unregisterObserver() {
        mIRefresh = null;
    }

    @Override
    public View createHeadView() {
        if (mHeadView == null) {
            mHeadView = LayoutInflater.from(mContext).inflate(R.layout.refresh_head_layout, null);
            mViewHolder = new ViewHolder(mHeadView);
        }
        return mHeadView;
    }

    @Override
    public void reset() {

    }

    @Override
    public void pull() {

    }

    @Override
    public void refreshing() {
        if (!mIsRefreshing) {
            mIsRefreshing = true;
            if (mViewHolder != null) {
                mViewHolder.mRefreshText.setText("正在刷新...");
                if (mOnRefreshListener != null) {
                    mOnRefreshListener.onRefresh();
                }
                mViewHolder.mRefreshText.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mIRefresh != null) {
                            mIRefresh.refreshComplete();
                        }
                    }
                }, 2000);
            }
        }
    }

    @Override
    public void complete() {
        mIsRefreshing = false;
    }

    @Override
    public void onPositionChange(float currentPos, float lastPos, float refreshPos, boolean isTouch, @IRefresh.RefreshState int state) {
        // 开始执行刷新
        if (mViewHolder != null && !mIsRefreshing) {
            if (currentPos >= refreshPos) {
                mViewHolder.mRefreshText.setText("手指释放刷新");
            } else {
                mViewHolder.mRefreshText.setText("下拉刷新");
            }
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefreshListener =  listener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    private static class ViewHolder {
        final TextView mRefreshText;

        ViewHolder(View view) {
            mRefreshText = view.findViewById(R.id.refresh_text);
        }
    }

    public void destroyInternal() {
        unregisterObserver();
        setOnRefreshListener(null);
    }
}
