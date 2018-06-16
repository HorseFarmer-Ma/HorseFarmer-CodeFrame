package com.meizu.code.frame.base.model.delegate;

import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.model.widget.common.LoadMoreView;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;

/**
 * 加载更多
 * <p>
 * Created by mxm on 10:33.
 */

public class LoadMoreBlockLayout extends DelegateBlockLayout{
    @Override
    protected View createView(@Nullable ViewGroup rootView, boolean attachToRoot) {
        LoadMoreView view = new LoadMoreView(getContext());
        view.setTextSize(CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.load_more_text_size));
        view.setGravity(Gravity.CENTER);
        view.setLoadingText(CodeFrameStaticResUtils.getString(R.string.load_more));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.load_more_height));
        view.setLayoutParams(params);
        return view;
    }

    @Override
    protected void updateView(DelegateBlockItem item) {

    }
}
