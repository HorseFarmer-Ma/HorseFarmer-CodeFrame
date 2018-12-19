package com.meizu.code.frame.base.model.widget.prompt;

import android.content.Context;
import android.util.AttributeSet;

public class PromptView extends AbsPromptView {
    public PromptView(Context context) {
        super(context);
    }

    public PromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PromptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showEmptyView() {

    }
}
