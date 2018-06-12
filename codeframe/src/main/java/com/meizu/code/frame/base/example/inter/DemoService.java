package com.meizu.code.frame.base.example.inter;

import com.meizu.code.frame.base.example.bean.BookBean;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * 接口服务
 * <p>
 * Created by mxm on 20:31.
 */
public interface DemoService {
    String HOST = "https://api.douban.com";

    @GET("/v2/book/search")
    Observable<BookBean> requestSearchBook(@QueryMap Map<String, String> map);
}
