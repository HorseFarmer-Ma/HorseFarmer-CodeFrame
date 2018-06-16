package com.meizu.code.frame.base.example.layer.logic;

import com.meizu.code.frame.base.example.bean.BookBean;
import com.meizu.code.frame.base.example.layer.parma.DemoServiceHelper;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * 逻辑请求服务层
 * <p>
 * Created by mxm on 16:11.
 */

public class DemoServiceDoHelper {
    private DemoServiceHelper mDemoServiceHelper;

    private static class DemoServiceDoHelperHolder {
        private static final DemoServiceDoHelper HOLDER = new DemoServiceDoHelper();
    }

    public static DemoServiceDoHelper getInstance() {
        return DemoServiceDoHelperHolder.HOLDER;
    }

    private DemoServiceDoHelper() {
        mDemoServiceHelper = DemoServiceHelper.getInstance();
    }

    /**
     * 查询图书
     * @param bookName 书名
     */
    public Observable<BookBean> requestSearchBook(String bookName) {
        Map<String, String> maps = new HashMap<>();
        maps.put("q", bookName);
        return mDemoServiceHelper.requestSearchBook(maps);
    }
}
