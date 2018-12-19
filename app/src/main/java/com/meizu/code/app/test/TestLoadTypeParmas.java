package com.meizu.code.app.test;

/**
 * 加载类型
 * <p>
 * Created by maxueming on 17-12-20.
 */

public enum TestLoadTypeParmas {
    LOAD1("加载1",String.class), LOAD2("加载2",Integer.class);

    private String mLoadType;
    private Class clazz;
    private int id;

    TestLoadTypeParmas(String loadType,Class clazz) {
        mLoadType = loadType;
        this.clazz=clazz;
    }

    public String getLoadType() {
        return mLoadType;
    }

    public TestLoadTypeParmas setLoadType(String loadType) {
        mLoadType = loadType;
        return this;
    }
}
