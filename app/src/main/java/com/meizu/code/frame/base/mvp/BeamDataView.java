package com.meizu.code.frame.base.mvp;

import com.meizu.code.frame.base.mvp.data.LoadTypeParmas;
import com.meizu.code.frame.base.mvp.interport.IViewShow;

/**
 * BeamDataView: 数据处理基类View
 *
 * Created by maxueming on 17-11-21.
 */

public abstract class BeamDataView<T extends BaseDataPresenter, D> extends BeamView<T> implements IViewShow<D> {

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showContentView() {

    }

    @Override
    public void setData(D data) {

    }

    @Override
    public void setOtherData(Object data, LoadTypeParmas loadTypeParmas) {

    }
}
