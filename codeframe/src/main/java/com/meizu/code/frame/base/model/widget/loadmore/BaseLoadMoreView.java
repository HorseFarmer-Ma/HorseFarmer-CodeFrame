package com.meizu.code.frame.base.model.widget.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.meizu.code.frame.R;

/**
 * 加载更多
 * <p>
 * Created by mxm on 14:36.
 */

public class BaseLoadMoreView extends RelativeLayout implements ILoadMore{

    private boolean mIsEnableLoadMore;
    private LoadMoreState mLoadMoreState;
    private LoadingTextView mLoadingTextView;

    public BaseLoadMoreView(Context context) {
        this(context, null);
    }

    public BaseLoadMoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLoadingTextView = (LoadingTextView) findViewById(R.id.loading_text);
    }

    @Override
    public void setEnableLoadMore(boolean isEnable) {
        mIsEnableLoadMore = isEnable;
        setVisibility(isEnable? VISIBLE : GONE);
    }

    @Override
    public void setLoadMoreState(LoadMoreState state) {
        mLoadMoreState = state;
    }
}
