/*
 * ************************************************************
 * Class：EmptyBlockItem.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 20:58:07
 * Last modified time：2018-11-15 20:58:07
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.delegate.blockitem;

import com.meizu.code.frame.base.model.delegate.blocklayout.EmptyBlockLayout;

public class EmptyBlockItem extends DelegateBlockItem {
    public EmptyBlockItem(Object data) {
        super(data);
    }

    @Override
    public Class getBlockLayoutClazz() {
        return EmptyBlockLayout.class;
    }
}
