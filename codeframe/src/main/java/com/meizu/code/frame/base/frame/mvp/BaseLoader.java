package com.meizu.code.frame.base.frame.mvp;

import com.meizu.code.frame.base.frame.data.DataHolder;
import com.meizu.code.frame.base.frame.interport.IDataLoader;
import com.meizu.code.frame.base.frame.manager.RequestSubscriptionManager;
import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.LOAD_MORE;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.REFRESH;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.START;
import static com.meizu.code.frame.base.frame.data.LoadTypeParmas.UPDATE;

/**
 * 基本BaseLoader，数据加载Loader层
 * <p>
 * Created by maxueming on 17-11-9.
 */

public abstract class BaseLoader<D> implements IDataLoader<D> {

    private static final String TAG = "BaseLoader";

    // 行为发射数据
    private SerializedSubject<DataHolder, DataHolder> mPublishSubject = PublishSubject.<DataHolder>create().toSerialized();
    // 储存子请求
    private RequestSubscriptionManager<Enum> mChildTaskSubscriptions = new RequestSubscriptionManager<>();
    // 储存主要请求
    private RequestSubscriptionManager<Observer> mMainTaskSubscription = new RequestSubscriptionManager<>();
    // 储存请求的数据
    private DataHolder<D> mDataHolder = new DataHolder<>();
    // 储存最新数据
    protected D mBaseData;

    @Override
    public void register(Observer<DataHolder> observer) {
        Logger.d(TAG, "register observer");
        if (mMainTaskSubscription.containsKey(observer)) return;
        if (mPublishSubject != null && mMainTaskSubscription != null) {
            synchronized (this) {
                if (mPublishSubject != null && mMainTaskSubscription != null) {
                    Subscription subscription = mPublishSubject
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(observer);
                    mMainTaskSubscription.add(observer, subscription);
                }
            }
        }
    }

    @Override
    public void unRegister(Observer<DataHolder> observer) {
        // 程序处于后台，取消订阅，防止数据回传
        Logger.d(TAG, "unRegister observer");
        if (mMainTaskSubscription != null) {
            synchronized (this) {
                if (mMainTaskSubscription != null) {
                    mMainTaskSubscription.remove(observer);
                }
            }
        }
    }

    /**
     * 真正的销毁资源，跟随Presenter生命周期
     */
    @Override
    public void destroy() {
        // 程序处于后台，取消订阅，防止数据回传
        if (mMainTaskSubscription != null) {
            mMainTaskSubscription.unsubscribe();
        }
        if (mChildTaskSubscriptions != null && !mChildTaskSubscriptions.isUnsubscribed()) {
            mChildTaskSubscriptions.unsubscribe();
        }
        mPublishSubject = null;
        mDataHolder = null;
        mChildTaskSubscriptions = null;
        mMainTaskSubscription = null;
        mBaseData = null;
    }

    public D getBaseData() {
        return mBaseData;
    }

    @Override
    public void doStart() {
        loadData(START);
    }

    @Override
    public void doRefresh() {
        loadData(REFRESH);
    }

    @Override
    public void doLoadMore() {
        loadData(LOAD_MORE);
    }

    @Override
    public void doUpdate() {
        loadData(UPDATE);
    }

    @Override
    public void doExtraTask(Enum loadTypeParmas) {
        loadData(loadTypeParmas);
    }

    private void loadData(final Enum loadTypeParmas) {
        Observable observable = createObservableByLoadType(loadTypeParmas);

        if (observable == null) {
            Logger.e(TAG, "you have not init your loadType of [" + loadTypeParmas.toString() + "] yet");
        }

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
        // 取消上次相同类型的订阅
        mChildTaskSubscriptions.remove(loadTypeParmas);
        // 添加订阅
        mChildTaskSubscriptions.add(loadTypeParmas, subscription);
    }

    private Observable createObservableByLoadType(Enum loadTypeParmas) {
        Observable observable;
        if (loadTypeParmas.equals(START)) {
            observable = onStart();
        } else if (loadTypeParmas.equals(REFRESH)) {
            observable = onRefresh();
        } else if (loadTypeParmas.equals(LOAD_MORE)) {
            observable = onLoadMore();
        } else if (loadTypeParmas.equals(UPDATE)) {
            observable = onUpdate();
        } else {
            observable = onExtraTask(loadTypeParmas);
        }
        return observable;
    }

    protected abstract Observable<D> onStart();

    protected abstract Observable<D> onRefresh();

    protected abstract Observable<D> onLoadMore();

    protected abstract Observable<D> onUpdate();

    protected abstract Observable onExtraTask(Enum loadTypeParmas);

    private Observer<DataHolder> mDataObserver = new Observer<DataHolder>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            Logger.e(TAG, "onError: [run error, reason is = " + e.toString() + "]");
            emitError(e);
        }

        @Override
        public void onNext(DataHolder t) {
            Logger.d(TAG, "onNext: [run finish]" + (t == null ? "result null" : "result valid"));
            emitNext(t);
        }
    };

    /**
     * 处理数据
     * 仅开始加载，下拉刷新，加载更多，更新页面保存数据
     * 额外任务不保存数据，业务自己处理
     *
     * @param holder
     */
    private void emitNext(DataHolder holder) {
        Enum parmas = holder.getLoadTypeParmas();
        if (parmas == START || parmas == LOAD_MORE || parmas == UPDATE || parmas == REFRESH) {
            mDataHolder = (DataHolder<D>) holder;
            mBaseData = mDataHolder.getData();
        }
        mPublishSubject.onNext(holder);
    }

    private void emitError(Throwable throwable) {
    }
}
