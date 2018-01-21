package com.meizu.code.frame.base.frame.interport;

import com.meizu.code.frame.base.frame.data.DataHolder;

import rx.Observer;

/**
 * 数据加载接口
 *
 * Created by maxueming on 17-11-21.
 */
public interface IDataLoader<D> {

    // 注册Loader
    void register(Observer<DataHolder> observer);

    // 取消注册
    void unRegister(Observer<DataHolder> observer);

    // 销毁Loader
    void destroy();

    // 初始化加载
    void doStart();

    // 下拉刷新
    void doRefresh();

    // 加载更多
    void doLoadMore();

    // 更新页面
    void doUpdate();

    // 执行其他任务
    void doExtraTask(Enum loadTypeParmas);

    // 获取最新数据
    D getBaseData();
}
