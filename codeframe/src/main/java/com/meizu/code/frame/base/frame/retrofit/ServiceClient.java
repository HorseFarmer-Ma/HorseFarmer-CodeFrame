/*
 * Reader me
 * 1）应用拦截器: addInterceptor
 * 不需要担心中间过程的响应,如重定向和重试.
 * 总是只调用一次,即使HTTP响应是从缓存中获取.
 * 观察应用程序的初衷. 不关心OkHttp注入的头信息如: If-None-Match.
 * 允许短路而不调用 Chain.proceed(),即中止调用.
 * 允许重试,使 Chain.proceed()调用多次.
 *
 * 2）网络拦截器: addNetworkInterceptor
 * 能够操作中间过程的响应,如重定向和重试.
 * 当网络短路而返回缓存响应时不被调用.
 * 只观察在网络上传输的数据.
 * 携带请求来访问连接.
 */
package com.meizu.code.frame.base.frame.retrofit;

import com.meizu.code.frame.base.frame.retrofit.adapter.FastJsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Retrofit请求框架
 * <p>
 * Created by mxm on 18:06.
 */

public class ServiceClient {
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 15;
    private static final int READ_TIMEOUT = 15;
    private OkHttpClient mOkHttpClient;

    private static class ServiceClientHolder{
        private static final ServiceClient INSTANCE = new ServiceClient();
    }

    public static ServiceClient getInstance() {
        return ServiceClientHolder.INSTANCE;
    }

    public Retrofit createRetrofit(String url) {
        return new Retrofit.Builder()
                        .baseUrl(url)
                        .client(getClient())
                        .addConverterFactory(FastJsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
    }

    public OkHttpClient getClient() {
        if (mOkHttpClient == null) {
            synchronized (ServiceClient.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    private static class CommonInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return null;
        }
    }

}
