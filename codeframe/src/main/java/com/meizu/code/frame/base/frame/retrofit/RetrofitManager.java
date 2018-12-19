package com.meizu.code.frame.base.frame.retrofit;

import retrofit2.Retrofit;

/**
 * Retrofit管理类
 * <p>
 * Created by mxm on 20:17.
 */
public class RetrofitManager {
    private static RetrofitManager mInstance;

    private RetrofitManager() {
    }

    private static class RetrofitInstanceHolder {
        private static final RetrofitManager INSTANCE = new RetrofitManager();
    }

    public static RetrofitManager getInstance() {
        return RetrofitInstanceHolder.INSTANCE;
    }

    /**
     * 创建Retrofit请求实体类
     *
     * @param clazz   接口类
     * @param hostUrl 请求的host
     */
    public <T> T createService(Class<T> clazz, String hostUrl) {
        Retrofit retrofit = createRetrofit(hostUrl);
        return retrofit.create(clazz);
    }

    private Retrofit createRetrofit(String baseUrl) {
        return ServiceClient.getInstance().createRetrofit(baseUrl);
    }
}
