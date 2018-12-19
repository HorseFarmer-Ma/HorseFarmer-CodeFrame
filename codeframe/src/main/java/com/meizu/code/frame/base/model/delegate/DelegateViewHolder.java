package com.meizu.code.frame.base.model.delegate;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;

/**
 * 基本ViewHolder，用于更新列表子项
 * <p>
 * Created by mxm on 16/02/18.
 */
public class DelegateViewHolder<L extends DelegateBlockLayout> extends RecyclerView.ViewHolder {

    private L mBlockLayout;

    DelegateViewHolder(View itemView, L layout) {
        super(itemView);
        mBlockLayout = layout;
    }

    <T extends DelegateBlockItem> void updateView(T item, int position) {
        if (mBlockLayout != null) {
            mBlockLayout.updateView(item, position);
        }
    }

    void setOnElementClickListener(DelegateBlockLayout.OnElementClickListener onElementClickListener){
        if (mBlockLayout != null) {
            mBlockLayout.setOnElementClickListener(onElementClickListener);
        }
    }

    void onViewRecycled() {
        if (mBlockLayout != null) {
            mBlockLayout.onViewRecycled();
        }
    }
}
