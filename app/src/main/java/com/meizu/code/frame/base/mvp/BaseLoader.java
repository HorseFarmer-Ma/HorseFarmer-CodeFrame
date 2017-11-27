package com.meizu.code.frame.base.mvp;

import com.meizu.code.frame.base.mvp.data.DataHolder;
import com.meizu.code.frame.base.mvp.data.LoadTypeParmas;
import com.meizu.code.frame.base.mvp.interport.IDataLoader;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

/**
 * 基本BaseLoader，数据加载Loader层
 *
 * Created by maxueming on 17-11-9.
 */

public abstract class BaseLoader<D> implements IDataLoader<D> {

    private static final String TAG = "BaseLoader";

    // 行为发射数据
    private BehaviorSubject<DataHolder> mBehaviorSubject = BehaviorSubject.create();
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private Subscription mBehaviorSubscription;
    private DataHolder<D> mDataHolder = new DataHolder<>();
    private D mLastPageData;

    @Override
    public void register(Observer<DataHolder> observer) {
        if (mBehaviorSubject != null && mBehaviorSubscription == null){
            synchronized (this) {
                if (mBehaviorSubject != null && mBehaviorSubscription == null){
                    mBehaviorSubscription = mBehaviorSubject
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                }
            }
        }
    }

    @Override
    public void unRegister() {
        // 程序处于后台，取消订阅，防止数据回传
        if (mBehaviorSubscription != null && !mBehaviorSubscription.isUnsubscribed()) {
            mBehaviorSubscription.unsubscribe();
        }
    }

    /**
     * 真正的销毁资源，跟随Presenter生命周期
     */
    @Override
    public void destroy() {
        // 程序处于后台，取消订阅，防止数据回传
        if (mBehaviorSubscription != null && !mBehaviorSubscription.isUnsubscribed()) {
            mBehaviorSubscription.unsubscribe();
        }
        if (mSubscriptions != null && !mSubscriptions.isUnsubscribed()){
            mSubscriptions.unsubscribe();
        }
        mBehaviorSubject = null;
        mDataHolder = null;
        mSubscriptions = null;
        mBehaviorSubscription = null;
        mLastPageData = null;
    }

    public D getLastPageData() {
        return mLastPageData;
    }

    @Override
    public void doStart() {
        loadData(LoadTypeParmas.START);
    }

    @Override
    public void doRefresh() {
        loadData(LoadTypeParmas.REFRESH);
    }

    @Override
    public void doLoadMore() {
        loadData(LoadTypeParmas.LOAD_MORE);
    }

    @Override
    public void doUpdate() {
        loadData(LoadTypeParmas.UPDATE);
    }

    @Override
    public void doOtherTask(LoadTypeParmas loadTypeParmas) {
        loadData(loadTypeParmas);
    }

    private void loadData(final LoadTypeParmas loadTypeParmas) {
        Observable observable = null;
        switch (loadTypeParmas) {
            case START:
                observable = onStart();
                break;
            case REFRESH:
                observable = onRefresh();
                break;
            case LOAD_MORE:
                observable = onLoadMore();
                break;
            case UPDATE:
                observable = onUpdate();
                break;
            case OTHER:
                observable = onOtherTask(loadTypeParmas);
                break;
        }

        if (observable == null) return;

        Subscription subscription = observable
                .flatMap(new Func1<Object, Observable<DataHolder>>() {
                    @Override
                    public Observable<DataHolder> call(Object o) {
                        DataHolder dataHolder = new DataHolder();
                        dataHolder.setLoadTypeParmas(loadTypeParmas);
                        dataHolder.setData(o);
                        return Observable.just(dataHolder);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(mDataObserver);
        mSubscriptions.add(subscription);
    }

    protected abstract Observable<D> onStart();

    protected abstract Observable<D> onRefresh();

    protected abstract Observable<D> onLoadMore();

    protected abstract Observable<D> onUpdate();

    protected abstract Observable onOtherTask(LoadTypeParmas loadTypeParmas);

    private Observer<DataHolder> mDataObserver = new Observer<DataHolder>() {
        @Override
        public void onCompleted() {
            mBehaviorSubject.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            Logger.e(TAG, "onError: [run error, reason is = " + e.toString() + "]");
            mBehaviorSubject.onError(e);
        }

        @Override
        public void onNext(DataHolder t) {
            Logger.d(TAG, "onNext: [run finish]" + (t == null? "result null" : "result valid"));
            emitNext(t);
        }
    };

    /**
     * 处理数据
     * 仅下拉刷新，加载更多，更新页面保存数据，其他不保存
     *
     * @param holder
     */
    private void emitNext(DataHolder holder) {
        LoadTypeParmas parmas = holder.getLoadTypeParmas();
        if (parmas == LoadTypeParmas.LOAD_MORE || parmas == LoadTypeParmas.UPDATE || parmas == LoadTypeParmas.REFRESH) {
            mDataHolder = holder;
            mLastPageData = mDataHolder.getData();
        }
        mBehaviorSubject.onNext(holder);
    }
}
