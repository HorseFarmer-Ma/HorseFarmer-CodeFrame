package com.meizu.code.app.test;

import com.meizu.code.frame.base.frame.mvp.BaseDataLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * 测试类
 * <p>
 * Created by maxueming on 17-11-20.
 */

public class TestLoader extends BaseDataLoader<List<String>> {
    @Override
    protected Observable<List<String>> onStart() {
        return Observable.fromCallable(new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> listData = new ArrayList<>();
                listData.add("搞笑");
                listData.add("煞笔");
                listData.add("试验");
                listData.add("恶心");
                return listData;
            }
        });
    }

    @Override
    protected Observable<List<String>> onRefresh() {
        return null;
    }

    @Override
    protected Observable<List<String>> onLoadMore() {
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
