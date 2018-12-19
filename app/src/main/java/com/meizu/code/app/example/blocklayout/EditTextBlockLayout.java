/*
 * ************************************************************
 * Class：EditTextBlockLayout.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-10-18 11:05:28
 * Last modified time：2018-10-18 11:05:28
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.example.blocklayout;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.meizu.code.app.R;
import com.meizu.code.app.example.blockitem.EditTextBlockItem;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;

import java.lang.ref.WeakReference;

public class EditTextBlockLayout extends DelegateBlockLayout<EditTextBlockItem> {

    private static final String TAG = "EditTextBlockLayout";
    private ViewHolder mViewHolder;
    private OnTextWatcher mOnTextWatcher;

    @Override
    protected View createView(@Nullable ViewGroup rootView) {
        return inflate(R.layout.edit_text_layout, rootView);
    }

    private static class OnTextWatcher implements TextWatcher {
        private final WeakReference<EditTextBlockLayout> mWr;

        public OnTextWatcher(EditTextBlockLayout layout) {
            mWr = new WeakReference<>(layout);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditTextBlockLayout blockLayout = mWr.get();
            if (blockLayout == null) return;
            EditTextBlockItem item = blockLayout.getItem();
            if (item != null) {
                item.setData(String.valueOf(s));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            Logger.logD(TAG, "输入的数量 = " + String.valueOf(s.length()));
        }
    }

    @Override
    public void onViewRecycled() {
        super.onViewRecycled();
        if (mViewHolder != null) {
            mViewHolder.mEditText.removeTextChangedListener(mOnTextWatcher);
        }
    }

    @Override
    protected void updateView(EditTextBlockItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(getView());
        }
        if (mOnTextWatcher == null) {
            mOnTextWatcher = new OnTextWatcher(this);
        }

        mViewHolder.mEditText.addTextChangedListener(mOnTextWatcher);
    }

    public static class ViewHolder {
        private final EditText mEditText;

        public ViewHolder(View view) {
            mEditText = view.findViewById(R.id.edit_text);
        }
    }
}
