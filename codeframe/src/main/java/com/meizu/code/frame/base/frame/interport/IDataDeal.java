package com.meizu.code.frame.base.frame.interport;

public interface IDataDeal<D> {

    void setData(D data);                                   // 返回并设置数据显示，针对四种基本类型请求

    void setExtraData(Object data, Enum loadTypeParmas);    // 返回并设置数据显示，针对其他类型请求
}
