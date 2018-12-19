package com.meizu.code.frame.base.frame.interport;

/**
 * View相关接口
 *
 * Created by maxueming on 17-11-21.
 */

public interface IViewShow<D> {

    void showEmptyView();       // 显示无数据提示界面

    void showErrorView();       // 网络异常或框架请求异常等情况导致的错误需要调用在这里展示界面

    void showContentView();     // 数据回来了，可以开始界面内容
}
