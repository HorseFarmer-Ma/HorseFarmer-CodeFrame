package com.meizu.code.app.test;

import com.meizu.code.frame.base.example.bean.BookBean;
import com.meizu.code.frame.base.example.layer.logic.DemoServiceDoHelper;
import com.meizu.code.frame.base.frame.mvp.BaseDataLoader;
import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;
import com.meizu.code.frame.base.example.blockitem.CodeFrameBlockItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.functions.Func1;

/**
 * 测试类
 * <p>
 * Created by maxueming on 17-11-20.
 */

public class TestLoader extends BaseDataLoader<List<DelegateBlockItem>> {
    @Override
    protected Observable<List<DelegateBlockItem>> onStart() {

        return DemoServiceDoHelper.getInstance().requestSearchBook("西游记")
                .map(new Func1<BookBean, List<DelegateBlockItem>>() {
                    @Override
                    public List<DelegateBlockItem> call(BookBean bookBean) {
                        List<DelegateBlockItem> blockItems = new ArrayList<>();
                        if (bookBean != null) {
                            List<BookBean.BooksBean> listData = bookBean.getBooks();
                            for (BookBean.BooksBean data : listData) {
                                blockItems.add(new CodeFrameBlockItem(data.getTitle()));
                            }
                        }
                        return blockItems;
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
