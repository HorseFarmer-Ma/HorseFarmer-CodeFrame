package com.meizu.code.frame.base.mvp;

import com.meizu.code.frame.base.mvp.data.DataHolder;
import com.meizu.code.frame.base.mvp.data.LoadTypeParmas;
import com.meizu.code.frame.base.mvp.interport.IDataLoader;
import com.orhanobut.logger.Logger;

import rx.Observer;

/**
 * BaseDataPresenter： 数据处理基类
 *
 * Created by maxueming on 17-11-21.
 */

public abstract class BaseDataPresenter<M extends BeamDataView, D> extends BasePresenter<M> implements Observer<DataHolder> {

    private static final String TAG = "BaseDataPresenter";
    private IDataLoader<D> mDataLoader;

    @Override
    protected void onResume() {
        super.onResume();
        getLoader().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoader().unRegister();
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
        LoadTypeParmas loadTypeParmas = dataHolder.getLoadTypeParmas();
        switch (loadTypeParmas) {
            case START:
            case REFRESH:
            case LOAD_MORE:
            case UPDATE:
                setData((D)dataHolder.getData(), loadTypeParmas);
                break;
            case OTHER:
                setOtherData(dataHolder.getData(), loadTypeParmas);
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
    protected void setData(D data, LoadTypeParmas loadTypeParmas) {
        Logger.d(TAG, "print result = [" + data.toString() + "]");
        getView().setData(data);
    }

    /**
     * 其他数据加载
     *
     * @param data
     * @param loadTypeParmas
     */
    protected void setOtherData(Object data, LoadTypeParmas loadTypeParmas) {
        Logger.d(TAG, "print result = [" + data.toString() + "]");
        getView().setOtherData(data, loadTypeParmas);
    }
}
