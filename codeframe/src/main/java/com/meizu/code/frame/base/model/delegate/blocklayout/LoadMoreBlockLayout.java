/*
 * ************************************************************
 * Class：LoadMoreBlockLayout.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:56:52
 * Last modified time：2018-09-04 11:49:39
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.delegate.blocklayout;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.widget.loadmore.LoadingTextView;
import com.meizu.code.frame.utils.CodeFrameStaticResUtils;

/**
 * 加载更多
 * <p>
 * Created by mxm on 10:33.
 */

public class LoadMoreBlockLayout extends DelegateBlockLayout {
    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        LoadingTextView view = new LoadingTextView(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                CodeFrameStaticResUtils.getDimensionPixelOffset(R.dimen.load_more_height));
        view.setLayoutParams(params);
        return view;
    }

    @Override
    protected void updateView(DelegateBlockItem item) {

    }
}
