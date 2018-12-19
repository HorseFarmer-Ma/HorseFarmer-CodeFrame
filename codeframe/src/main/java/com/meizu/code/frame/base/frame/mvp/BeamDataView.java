package com.meizu.code.frame.base.frame.mvp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.meizu.code.frame.base.frame.interport.IDataDeal;
import com.meizu.code.frame.base.frame.interport.IViewShow;
import com.meizu.code.frame.base.model.widget.prompt.AbsPromptView;
import com.meizu.code.frame.base.model.widget.prompt.PromptView;

/**
 * BeamDataView: 数据处理基类View
 * <p>
 * Created by maxueming on 17-11-21.
 */

public abstract class BeamDataView<T extends BaseDataPresenter, D> extends BeamView<T> implements IViewShow<D>, IDataDeal<D> {
    protected AbsPromptView mPromptView;

    @Override
    protected View onCreateView(ViewGroup parent, LayoutInflater inflater) {
        return new FrameLayout(getActivity());
    }

    @Override
    protected void onActivityCreate() {
        super.onActivityCreate();
        showPromptView();
    }

    @Override
    public void showEmptyView() {
        showPromptView();
        mPromptView.showEmptyView();
    }

    @Override
    public void showErrorView() {
        showPromptView();
        mPromptView.showErrorView();
    }

    @Override
    public void showContentView() {
        hidePromptView();
    }

    @Override
    public abstract void setData(D data);

    @Override
    public abstract void setExtraData(Object data, Enum loadTypeParmas);

    protected AbsPromptView createPromptView() {
        return new PromptView(getActivity());
    }

    /**
     * 隐藏加载框架
     */
    private void hidePromptView() {
        if (mPromptView != null) {
            ViewParent parent = mPromptView.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mPromptView);
            }
        }
    }

    /**
     * 显示加载框架
     */
    private void showPromptView() {
        if (mPromptView == null) {
            mPromptView = createPromptView();
        }
        if (mPromptView != null) {
            ViewParent parent = mPromptView.getParent();
            if (parent == null) {
                View rootView = getRootView();
                if (rootView instanceof ViewGroup) {
                    ((ViewGroup) rootView).addView(mPromptView,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
        }
    }
}
