package com.meizu.code.app.test;

import com.meizu.code.frame.base.frame.mvp.BaseDataLoader;
import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;
import com.meizu.code.frame.base.model.widget.recyclerview.blockitem.CodeFrameBlockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * 测试类
 * <p>
 * Created by maxueming on 17-11-20.
 */

public class TestLoader extends BaseDataLoader<List<DelegateBlockItem>> {
    @Override
    protected Observable<List<DelegateBlockItem>> onStart() {
        return Observable.fromCallable(new Callable<List<DelegateBlockItem>>() {
            @Override
            public List<DelegateBlockItem> call() throws Exception {
                List<DelegateBlockItem> listData = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    listData.add(new CodeFrameBlockItem("煞笔"));
                    listData.add(new CodeFrameBlockItem("试验"));
                    listData.add(new CodeFrameBlockItem("搞笑"));
                    listData.add(new CodeFrameBlockItem("恶心"));
                }
                return listData;
            }
        });
    }

    @Override
    protected Observable<List<DelegateBlockItem>> onRefresh() {
        return null;
    }

    @Override
    protected Observable<List<DelegateBlockItem>> onLoadMore() {
        return null;
    }

    @Override
    protected Observable onExtraTask(Enum loadTypeParmas) {
        if (loadTypeParmas == TestLoadTypeParmas.LOAD1) {
            return Observable.fromCallable(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "ceshi";
                }
            });
        } else if (loadTypeParmas == TestLoadTypeParmas.LOAD2) {
            return Observable.fromCallable(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return 234;
                }
            });
        } else {
            return null;
        }
    }
}
