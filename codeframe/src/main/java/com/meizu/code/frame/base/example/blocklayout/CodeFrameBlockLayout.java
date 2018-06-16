package com.meizu.code.frame.base.example.blocklayout;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.model.delegate.DelegateBlockLayout;
import com.meizu.code.frame.base.example.blockitem.CodeFrameBlockItem;

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
    protected void updateView(CodeFrameBlockItem item) {
        if (mViewHolder == null) {
            mViewHolder = new ViewHolder(mView);
        }
        mViewHolder.mTextView.setText(item.getData());
        mViewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mxm", "onClick() called with: v = [" + v + "]");
            }
        });
        mViewHolder.mTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("mxm", "onLongClick() called with: v = [" + v + "]");
                return true;
            }
        });
    }

    private static class ViewHolder {
        final TextView mTextView;
        ViewHolder(View view) {
            mTextView = view.findViewById(R.id.text_test);
        }
    }
}
