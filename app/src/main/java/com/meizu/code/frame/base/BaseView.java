package com.meizu.code.frame.base;

import android.view.View;

/**
 * Created by maxueming on 17-11-9.
 */

public abstract class BaseView<T extends BasePresenter> implements ILifeCycle{

    public abstract View onCreateView ();
}
