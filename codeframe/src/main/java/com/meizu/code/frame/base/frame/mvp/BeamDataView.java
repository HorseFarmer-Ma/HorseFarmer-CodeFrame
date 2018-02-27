package com.meizu.code.frame.base.frame.mvp;

import com.meizu.code.frame.base.frame.interport.IViewShow;

/**
 * BeamDataView: 数据处理基类View
 * <p>
 * Created by maxueming on 17-11-21.
 */

public abstract class BeamDataView<T extends BaseDataPresenter, D> extends BeamView<T> implements IViewShow<D> {

    @Override
    public abstract void showEmptyView();

    @Override
    public abstract void showErrorView();

    @Override
    public abstract void setData(D data);

    @Override
    public abstract void setExtraData(Object data, Enum loadTypeParmas);
}
