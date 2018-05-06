package com.meizu.code.frame.base.model.delegate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.utils.CollectionUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView通用Adapter
 * <p>
 * Created by mxm on 16/02/18.
 */

public class DelegateRecyclerViewAdapter extends RecyclerView.Adapter<DelegateViewHolder> {

    private Context mContext;
    private final List<DelegateBlockItem> mItems = new ArrayList<>();
    private final List<Class<DelegateBlockLayout>> mLayoutClazzs = new ArrayList<>();

    public DelegateRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void swapData(List<DelegateBlockItem> items) {
        if (CollectionUtils.isEqual(items, mItems)) return;
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public DelegateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DelegateBlockLayout delegateBlockLayout = generateBlockLayout(mItems, viewType);
        View view = delegateBlockLayout.createView(mContext, parent, false);
        return new DelegateViewHolder<DelegateBlockLayout>(view, delegateBlockLayout);
    }

    private DelegateBlockLayout generateBlockLayout(List<DelegateBlockItem> items, int position) {
        Class layoutClazz = items.get(position).getBlockLayoutClazz();
        DelegateBlockLayout layout = null;
        try {
            Object object = layoutClazz.newInstance();
            if (object instanceof DelegateBlockLayout) {
                layout = (DelegateBlockLayout) object;
            }
        } catch (Exception e) {
            Logger.e(e.toString());
        }

        if (layout == null) {
            throw new IllegalArgumentException("layout not instanceOf DelegateBlockLayout");
        }

        return layout;
    }

    @Override
    public void onBindViewHolder(DelegateViewHolder holder, int position) {
        if (holder != null) {
            holder.updateView(mItems.get(position), position);
        }
    }

    @Override
    public void onViewRecycled(DelegateViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder != null) {
            holder.getBlockLayout().onViewRecycled();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        DelegateBlockItem item = mItems.get(position);
        Class clazz = item.getBlockLayoutClazz();
        if (!mLayoutClazzs.contains(clazz)) {
            mLayoutClazzs.add(clazz);
        }
        return mLayoutClazzs.indexOf(clazz);
    }
}
