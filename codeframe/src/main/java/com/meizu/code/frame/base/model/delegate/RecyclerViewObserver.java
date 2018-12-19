package com.meizu.code.frame.base.model.delegate;

import android.support.v7.widget.RecyclerView;

import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.frame.interport.IViewShow;
import com.meizu.code.frame.base.model.delegate.view.BaseRecyclerViewV;

import java.lang.ref.WeakReference;

/**
 * Adapter数据加载监听器
 * <p>
 * Created by mxm on 15:30.
 */

public class RecyclerViewObserver extends RecyclerView.AdapterDataObserver {

    private static final String TAG = "RecyclerViewObserver";
    private final WeakReference<IViewShow> mWr;

    public RecyclerViewObserver(IViewShow view) {
        mWr =  new WeakReference<>(view) ;
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        Logger.logD(TAG, "onItemRangeChanged(): positionStart = [" + positionStart + "], itemCount = [" + itemCount + "]");
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
        super.onItemRangeChanged(positionStart, itemCount, payload);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
    }

    @Override
    public void onChanged() {
        super.onChanged();
        IViewShow iViewShow = mWr.get();
        if (iViewShow == null) return;
        boolean isListDataEmpty = iViewShow instanceof IRvObserver && ((IRvObserver) iViewShow).checkIfListEmpty();
        if (isListDataEmpty) {
            iViewShow.showContentView();
        } else {

        }
    }

    public interface IRvObserver {
        boolean checkIfListEmpty();
    }
}
