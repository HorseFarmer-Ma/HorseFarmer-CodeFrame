package com.meizu.code.frame.base.mvp.interport;

/**
 * View相关接口
 *
 * Created by maxueming on 17-11-21.
 */

public interface IViewShow<D> {

    void showEmptyView();

    void showErrorView();

    void setData(D data);

    void setExtraData(Object data, Enum loadTypeParmas);
}
