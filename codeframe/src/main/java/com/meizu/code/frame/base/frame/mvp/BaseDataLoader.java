package com.meizu.code.frame.base.frame.mvp;

import rx.Observable;

/**
 * Created by maxueming on 17-12-20.
 */

public class BaseDataLoader<D> extends BaseLoader<D>{
    @Override
    protected Observable<D> onStart() {
        return null;
    }

    @Override
    protected Observable<D> onRefresh() {
        return null;
    }

    @Override
    protected Observable<D> onLoadMore() {
        return null;
    }

    @Override
    protected Observable<D> onUpdate() {
        return Observable.just(getBaseData());
    }

    @Override
    protected Observable onExtraTask(Enum loadTypeParmas) {
        return null;
    }
}
