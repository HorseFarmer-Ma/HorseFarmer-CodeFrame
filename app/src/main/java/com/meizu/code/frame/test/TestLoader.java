package com.meizu.code.frame.test;

import com.meizu.code.frame.base.mvp.BaseLoader;
import com.meizu.code.frame.base.mvp.data.CommonLoadParmas;
import com.meizu.code.frame.base.mvp.data.LoadTypeParmas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * 测试类
 *
 * Created by maxueming on 17-11-20.
 */

public class TestLoader extends BaseLoader<List<String>> {
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
    protected Observable<List<String>> onUpdate() {
        return null;
    }

    @Override
    protected Observable onOtherTask(LoadTypeParmas loadTypeParmas) {
        if (loadTypeParmas.getLoadType().equals(CommonLoadParmas.TEST_LOAD_PARMAS1.getLoadType())) {
            return Observable.fromCallable(new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> listData = new ArrayList<>();
                    listData.add("搞笑");
                    listData.add("煞笔");
                    return listData;
                }
            });
        } else if (loadTypeParmas.getLoadType().equals(CommonLoadParmas.TEST_LOAD_PARMAS2.getLoadType())) {
            return Observable.fromCallable(new Callable<List<String>>() {
                @Override
                public List<String> call() throws Exception {
                    List<String> listData = new ArrayList<>();
                    listData.add("试验");
                    listData.add("恶心");
                    return listData;
                }
            });
        } else {
            return null;
        }
    }
}
