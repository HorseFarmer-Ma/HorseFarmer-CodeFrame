package com.meizu.code.frame.base.example.blockitem;

import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;
import com.meizu.code.frame.base.example.blocklayout.CodeFrameBlockLayout;

/**
 * item数据
 * <p>
 * Created by mxm on 24/02/18.
 */
public class CodeFrameBlockItem extends DelegateBlockItem<String> {

    public CodeFrameBlockItem(String data) {
        super(data);
    }

    @Override
    protected Class getBlockLayoutClazz() {
        return CodeFrameBlockLayout.class;
    }
}
