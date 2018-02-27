package com.meizu.code.frame.base.model.widget.recyclerview.blocklayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.model.delegate.DelegateBlockLayout;
import com.meizu.code.frame.base.model.widget.recyclerview.blockitem.CodeFrameBlockItem;

/**
 * 布局文件
 * <p>
 * Created by mxm on 24/02/18.
 */

public class CodeFrameBlockLayout extends DelegateBlockLayout<CodeFrameBlockItem> {

    private ViewHolder mViewHolder;

    @Override
    protected View createView(@Nullable ViewGroup rootView, boolean attachToRoot) {
        return inflate(R.layout.code_frame_layout, rootView);
    }

    @Override
    public void updateView(CodeFrameBlockItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(mView);
        }
        mViewHolder.mTextView.setText(item.getText());
    }

    private final class ViewHolder {

        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.text_test);
        }

        final TextView mTextView;
    }
}
