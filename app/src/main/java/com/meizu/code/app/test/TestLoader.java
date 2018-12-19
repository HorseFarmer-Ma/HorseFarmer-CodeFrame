package com.meizu.code.app.test;

import com.meizu.code.app.example.blockitem.CodeFrameBlockItem;
import com.meizu.code.app.example.blockitem.EditTextBlockItem;
import com.meizu.code.app.example.layer.logic.DemoServiceDoHelper;
import com.meizu.code.frame.base.frame.manager.DataBaseManager;
import com.meizu.code.frame.base.frame.mvp.BaseDataLoader;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;
import com.meizu.code.frame.utils.CollectionUtils;
import com.meizu.code.greendao.bean.BookBean;

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
                        DataBaseManager.getInstance().getDaoSession().getBookBeanDao().insert(bookBean);
                        List<DelegateBlockItem> blockItems = new ArrayList<>();
                        blockItems.add(new EditTextBlockItem());
                        if (bookBean != null) {
                            List<BookBean.BooksBean> listData = bookBean.getBooks();
                            for (int i = 0; i < 12; i++) {
                                for (BookBean.BooksBean data : listData) {
                                    int index = listData.indexOf(data);
                                    if (index == 0) {
                                        data.setImage("file:///storage/emulated/0/DCIM/wbmp/87a_4800x3200_5.93M.gif");
                                    } else if (index == 1) {
                                        data.setImage("file:///storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1543066171682.mp4");
                                    } else if (index == 2) {
                                        data.setImage("file:///storage/emulated/0/DCIM/wbmp/27ul0.wbmp");
                                    } else if (index == 3) {
                                        data.setImage("file:///storage/emulated/0/tencent/MicroMsg/WeiXin/wx_camera_1542979266804.jpg");
                                    } else if (index == 4) {
                                        data.setImage("file:///storage/emulated/0/DCIM/Camera/timg.gif");
                                    }

                                    blockItems.add(new CodeFrameBlockItem(data));
                                }
                            }
                        }
//                        List<BookBean> bookBeans = DataBaseManager.getInstance().getDaoSession().getBookBeanDao().loadAll();
                        return blockItems;
                    }
                });
    }

    @Override
    protected Observable<List<DelegateBlockItem>> onRefresh() {
        return DemoServiceDoHelper.getInstance().requestSearchBook("红楼梦")
                .map(new Func1<BookBean, List<DelegateBlockItem>>() {
                    @Override
                    public List<DelegateBlockItem> call(BookBean bookBean) {
                        DataBaseManager.getInstance().getDaoSession().getBookBeanDao().insert(bookBean);
                        List<DelegateBlockItem> blockItems = new ArrayList<>();
                        if (bookBean != null) {
                            List<BookBean.BooksBean> listData = bookBean.getBooks();
                            for (BookBean.BooksBean data : listData) {
                                blockItems.add(new CodeFrameBlockItem(data));
                            }
                        }
                        List<DelegateBlockItem> baseData = getBaseData();
                        if (!CollectionUtils.isEmpty(baseData)) {
                            blockItems.addAll(baseData);
                        }
                        return blockItems;
                    }
                });
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
