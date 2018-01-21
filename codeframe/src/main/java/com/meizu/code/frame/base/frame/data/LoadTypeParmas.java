package com.meizu.code.frame.base.frame.data;

/**
 * 数据加载类型
 * <p>
 * Created by maxueming on 17-11-20.
 */

public enum LoadTypeParmas {

    START("初始加载"), REFRESH("下拉刷新"), LOAD_MORE("上拉加载"), UPDATE("页面更新");

    private String mLoadType;
    private int id;

    LoadTypeParmas(String loadType) {
        mLoadType = loadType;
    }

    public String getLoadType() {
        return mLoadType;
    }

    public LoadTypeParmas setLoadType(String loadType) {
        mLoadType = loadType;
        return this;
    }
}
