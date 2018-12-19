package com.meizu.code.frame.base.model.widget.loadmore;

public enum LoadMoreState {

    LOADING("正在加载"), LOAD_ERROR("加载失败，稍后再试"), NO_MORE("没有更多内容");

    private String mState;

    LoadMoreState(String state) {
        mState = state;
    }

    public String getState() {
        return mState;
    }
}