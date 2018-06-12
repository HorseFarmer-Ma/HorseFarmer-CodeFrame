package com.meizu.code.frame.base.example.layer.parma;

import com.meizu.code.frame.base.example.bean.BookBean;
import com.meizu.code.frame.base.example.inter.DemoService;
import com.meizu.code.frame.base.frame.retrofit.RetrofitManager;

import java.util.Map;

import rx.Observable;

/**
 * 参数请求服务层
 * <p>
 * Created by mxm on 15:58.
 */
public class DemoServiceHelper {
    private final DemoService mDemoService;

    private static class DemoServiceHelperHolder {
        private static final DemoServiceHelper HOLDER = new DemoServiceHelper();
    }

    public static DemoServiceHelper getInstance() {
        return DemoServiceHelperHolder.HOLDER;
    }

    private DemoServiceHelper() {
        mDemoService = RetrofitManager.getInstance().createService(DemoService.class, DemoService.HOST);
    }

    public Observable<BookBean> requestSearchBook(Map<String, String> maps) {
        return mDemoService.requestSearchBook(maps);
    }
}
