package com.meizu.code.frame.base.model.widget.prompt;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 加载框架View
 */
public abstract class AbsPromptView extends RelativeLayout {

    public AbsPromptView(Context context) {
        super(context);
    }

    public AbsPromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsPromptView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 展示无网络/请求异常等框架View
     */
    public abstract void showErrorView();

    /**
     * 展示无数据框架View
     */
    public abstract void showEmptyView();
}
