package com.meizu.code.frame.base.model.delegate;

/**
 * 列表Item抽象化
 * <p>
 * Created by mxm on 16/02/18.
 */
public abstract class DelegateBlockItem <LAYOUT extends DelegateBlockLayout>{
    protected abstract Class<LAYOUT> getBlockLayoutClazz();
}
