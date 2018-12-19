package com.meizu.code.frame.base.model.delegate;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.meizu.code.frame.R;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.base.model.delegate.blocklayout.DelegateBlockLayout;
import com.meizu.code.frame.base.model.widget.loadmore.ILoadMore;
import com.meizu.code.frame.base.model.widget.loadmore.LoadMoreState;
import com.meizu.code.frame.utils.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView通用Adapter
 * <p>
 * Created by mxm on 16/02/18.
 */

public class DelegateRecyclerViewAdapter extends RecyclerView.Adapter<DelegateViewHolder> {
    private static final String TAG = "DelegateRecyclerViewAdapter";

    private final WeakReference<Context> mWrContext;
    private final List<DelegateBlockItem> mItems = new ArrayList<>();
    private final List<Class> mLayoutClazzs = new ArrayList<>();
    private static final int LOAD_MORE_ID = Integer.MAX_VALUE;
    private int mLoadMoreViewId = R.layout.load_more_layout;
    private View mLoadMoreView;
    private boolean mEnableLoadMore = true;
    private DelegateBlockLayout.OnElementClickListener mElementClickListener;

    public DelegateRecyclerViewAdapter(Context context) {
        mWrContext = new WeakReference<>(context);
    }

    public void swapData(List<DelegateBlockItem> items) {
        if (CollectionUtils.isEqual(items, mItems)) return;
        mItems.clear();
        if (items != null) {
            mItems.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public DelegateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = mWrContext.get();
        View view;
        DelegateBlockLayout delegateBlockLayout = null;
        if (viewType != LOAD_MORE_ID) {
            delegateBlockLayout = generateBlockLayout(mLayoutClazzs, viewType);
            view = delegateBlockLayout.createView(context, parent);
        } else {
            if (mLoadMoreView == null) {
                mLoadMoreView = LayoutInflater.from(context).inflate(mLoadMoreViewId, parent, false);
                if (mLoadMoreView instanceof ILoadMore) {
                    ((ILoadMore) mLoadMoreView).setEnableLoadMore(mEnableLoadMore);
                }
            }
            view = mLoadMoreView;
        }
        return new DelegateViewHolder<>(view, delegateBlockLayout);
    }

    private DelegateBlockLayout generateBlockLayout(List<Class> layouClazz, int viewType) {
        Class layoutClazz = layouClazz.get(viewType);
        DelegateBlockLayout layout = null;
        try {
            Object object = layoutClazz.newInstance();
            if (object instanceof DelegateBlockLayout) {
                layout = (DelegateBlockLayout) object;
            } else {
                throw new IllegalArgumentException("generateBlockLayout: the layoutClass must extend DelegateBlockLayout!!!");
            }
        } catch (Exception e) {
            Logger.logE(TAG, e.toString());
        }

        if (layout == null) {
            throw new IllegalArgumentException("layout not instanceOf DelegateBlockLayout");
        }

        return layout;
    }

    @Override
    public void onBindViewHolder(DelegateViewHolder holder, int pos) {
        int itemSize = mItems.size();
        if (holder != null && pos < itemSize) {
            holder.updateView(mItems.get(pos), pos);
            holder.setOnElementClickListener(mElementClickListener);
        }

        if (((pos < itemSize && mItems.get(pos).isSpanFull()) || pos == itemSize) && holder != null) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                boolean fullSpan = ((StaggeredGridLayoutManager.LayoutParams) layoutParams).isFullSpan();
                if (!fullSpan) {
                    ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
                }
            }
        }
    }

    @Override
    public void onViewRecycled(DelegateViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder != null) {
            holder.onViewRecycled();
            holder.setOnElementClickListener(null);
        }
    }

    public List<DelegateBlockItem> getItems() {
        return mItems;
    }

    @Override
    public int getItemCount() {
        int size = mItems.size();
        if (size > 0) {
            ++size;
        }
        return size;
    }

    /**
     * 获取实际列表数，不包含加载更多
     */
    public int getRealItemCount() {
        return mItems.size();
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int pos) {
                    int size = mItems.size();
                    if (size > 0 && ((pos < size && mItems.get(pos).isSpanFull()) || pos == size)) {
                        return ((GridLayoutManager) layoutManager).getSpanCount();
                    }
                    return 1;
                }
            });
        }
    }

    public void setElementClickListener(DelegateBlockLayout.OnElementClickListener elementClickListener) {
        mElementClickListener = elementClickListener;
    }

    /**
     * 设置加载更多的视图Id
     */
    public void setLoadMoreViewId(@IdRes int layoutId) {
        mLoadMoreViewId = layoutId;
    }

    /**
     * 设置是否允许加载更多
     */
    public void setEnableLoadMore(boolean isEnable) {
        if (mEnableLoadMore != isEnable) {
            mEnableLoadMore = isEnable;
            if (mLoadMoreView instanceof ILoadMore) {
                ((ILoadMore) mLoadMoreView).setEnableLoadMore(isEnable);
            }
        }
    }

    /**
     * 设置加载更多状态
     */
    public void setLoadMoreState(LoadMoreState state) {
        if (mLoadMoreView instanceof ILoadMore) {
            ((ILoadMore) mLoadMoreView).setLoadMoreState(state);
        }
    }

    /**
     * 触发退出应用时回收视图
     */
    public void destroy() {
        swapData(null);
        setElementClickListener(null);
        mLayoutClazzs.clear();
    }
}
