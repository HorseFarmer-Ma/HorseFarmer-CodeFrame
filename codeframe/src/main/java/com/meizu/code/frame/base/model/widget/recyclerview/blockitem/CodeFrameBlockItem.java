package com.meizu.code.frame.base.model.widget.recyclerview.blockitem;

import com.meizu.code.frame.base.model.delegate.DelegateBlockItem;
import com.meizu.code.frame.base.model.widget.recyclerview.blocklayout.CodeFrameBlockLayout;

/**
 * item数据
 * <p>
 * Created by mxm on 24/02/18.
 */
public class CodeFrameBlockItem extends DelegateBlockItem<CodeFrameBlockLayout> {

    private String mText;

    public CodeFrameBlockItem(String text) {
        mText = text;
    }

    @Override
    protected Class<CodeFrameBlockLayout> getBlockLayoutClazz() {
        return CodeFrameBlockLayout.class;
    }

    public String getText() {
        return mText;
    }
}
