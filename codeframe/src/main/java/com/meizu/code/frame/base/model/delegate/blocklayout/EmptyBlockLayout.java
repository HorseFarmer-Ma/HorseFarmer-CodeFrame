/*
 * ************************************************************
 * Class：EmptyBlockLayout.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:58:34
 * Last modified time：2018-11-15 20:58:34
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

import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;

public class EmptyBlockLayout extends DelegateBlockLayout<DelegateBlockItem> {
    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return new FrameLayout(getContext());
    }

    @Override
    protected void updateView(DelegateBlockItem item) {
    }
}
