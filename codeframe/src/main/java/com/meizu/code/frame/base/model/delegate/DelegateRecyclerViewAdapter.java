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
    private final List<Class> mLayoutClazzs = new ArrayList<>();
    private static final int LOAD_MORE_ID = Integer.MAX_VALUE;
    private boolean mHasLoadMore = true;

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
        DelegateBlockLayout delegateBlockLayout = generateBlockLayout(mLayoutClazzs, viewType);
        View view = delegateBlockLayout.createView(mContext, parent, false);
        return new DelegateViewHolder<DelegateBlockLayout>(view, delegateBlockLayout);
    }

    private DelegateBlockLayout generateBlockLayout(List<Class> layouClazz, int viewType) {
        Class layoutClazz = (viewType == Integer.MAX_VALUE ?
                LoadMoreBlockLayout.class : layouClazz.get(viewType));
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
    public void onBindViewHolder(DelegateViewHolder holder, int pos) {
        if (holder != null && pos < mItems.size()) {
            holder.updateView(mItems.get(pos), pos);
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
        int size = mItems.size();
        boolean isNeedLoadMore = (size > 0 && mHasLoadMore);
        return size + (isNeedLoadMore ? 1 : 0);
    }

    /**
     * 获取实际列表数，不包含加载更多
     */
    public int getRealItemCount() {
        return mItems.size();
    }

    public void setLoadMore(boolean hasLoadMore) {
        this.mHasLoadMore = hasLoadMore;
    }

    @Override
    public int getItemViewType(int pos) {
        int size = mItems.size();
        // 加载更多
        if (pos == size) {
            return LOAD_MORE_ID;
        }
        DelegateBlockItem item = mItems.get(pos);
        Class clazz = item.getBlockLayoutClazz();
        if (!mLayoutClazzs.contains(clazz)) {
            mLayoutClazzs.add(clazz);
        }
        return mLayoutClazzs.indexOf(clazz);
    }
}
