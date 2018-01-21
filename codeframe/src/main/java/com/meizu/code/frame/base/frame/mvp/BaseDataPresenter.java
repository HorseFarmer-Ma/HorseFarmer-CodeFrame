package com.meizu.code.frame.base.frame.mvp;

import com.meizu.code.frame.base.frame.data.DataHolder;
import com.meizu.code.frame.base.frame.interport.IDataLoader;
import com.meizu.code.frame.base.frame.interport.IViewShow;
import com.orhanobut.logger.Logger;

import rx.Observer;

import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.LOAD_MORE;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.REFRESH;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.START;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.UPDATE;

/**
 * BaseDataPresenter： 数据处理基类
 * <p>
 * Created by maxueming on 17-11-21.
 */

public abstract class BaseDataPresenter<V extends IViewShow<D>, D> extends BasePresenter<V> implements Observer<DataHolder> {

    private static final String TAG = "BaseDataPresenter";
    private IDataLoader<D> mDataLoader;
    private boolean mIsFirstLoad = true;

    @Override
    protected void onResume() {
        super.onResume();
        getLoader().register(this);
        if (mIsFirstLoad) {
            getLoader().doStart();
            mIsFirstLoad = false;
        } else if (isRequestUpdate()) {
            getLoader().doUpdate();
        }
    }

    /**
     * 是否需要更新界面，可重写，默认需要
     */
    protected boolean isRequestUpdate() {
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoader().unRegister(this);
    }

    @Override
    protected void onDestroy() {
        mDataLoader.destroy();
        mDataLoader = null;
        super.onDestroy();
    }

    @Override
    public void onCompleted() {
        Logger.d(TAG, "onCompleted: Load data completed");
    }

    @Override
    public void onError(Throwable e) {
        Logger.d(TAG, "onError: Load data error = [" + e.toString() + "]");
    }

    @Override
    public void onNext(DataHolder dataHolder) {
        Logger.d(TAG, "onNext: Load data finished");
        if (dataHolder == null) return;
        Enum loadTypeParmas = dataHolder.getLoadTypeParmas();
        if (loadTypeParmas == START || loadTypeParmas == LOAD_MORE || loadTypeParmas == UPDATE || loadTypeParmas == REFRESH) {
            setData(((DataHolder<D>)dataHolder).getData());
        } else {
            setExtraData(dataHolder.getData(), loadTypeParmas);
        }
    }

    protected IDataLoader<D> getLoader() {
        if (mDataLoader == null) {
            mDataLoader = createLoader();
            if (mDataLoader == null) {
                throw new IllegalArgumentException("getLoader() fail: please Override the method[createLoader] in " + this.getClass().getSimpleName());
            }
        }
        return mDataLoader;
    }

    protected abstract IDataLoader<D> createLoader();

    /**
     * 刷新，加载更多，更新页面数据加载
     *
     * @param data
     */
    protected void setData(D data) {
        Logger.d(TAG, "print result = [" + data.toString() + "]");
        getView().setData(data);
    }

    /**
     * 其他数据加载
     *
     * @param data
     * @param loadTypeParmas
     */
    protected void setExtraData(Object data, Enum loadTypeParmas) {
        Logger.d(TAG, "print result = [" + data.toString() + "]");
        getView().setExtraData(data, loadTypeParmas);
    }
}
