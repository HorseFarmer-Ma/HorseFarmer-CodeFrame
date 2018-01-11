package com.meizu.code.frame.base.mvp.data;

/**
 * 请求错误类型
 * <p>
 * Created by maxueming on 17-11-21.
 */

public enum RequestErrorParmas {

    NO_NET("无网络"), SERVER_ERROR("服务器出错"), OTHER("未知异常");

    private String mErrorType;

    RequestErrorParmas(String errorType) {
        mErrorType = errorType;
    }

    public String getLoadType() {
        return mErrorType;
    }
}
