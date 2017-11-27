package com.meizu.code.frame.base.mvp.interport;

import com.meizu.code.frame.base.mvp.data.LoadTypeParmas;

/**
 * View相关接口
 *
 * Created by maxueming on 17-11-21.
 */

public interface IViewShow<D> {

    void showEmptyView();

    void showErrorView();

    void showContentView();

    void setData(D data);

    void setOtherData(Object data, LoadTypeParmas loadTypeParmas);
}
