package com.meizu.code.frame.base.model.delegate;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 基本ViewHolder，用于更新列表子项
 * <p>
 * Created by mxm on 16/02/18.
 */
public class DelegateViewHolder<L extends DelegateBlockLayout> extends RecyclerView.ViewHolder {

    private L mBlockLayout;

    public DelegateViewHolder(View itemView, @NonNull L layout) {
        super(itemView);
        mBlockLayout = layout;
    }

    public <ITEM extends DelegateBlockItem> void updateView(ITEM item, int position) {
        mBlockLayout.updateView(item, position);
    }

    public L getBlockLayout() {
        return mBlockLayout;
    }
}
