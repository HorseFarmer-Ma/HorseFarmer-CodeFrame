/*
 * ************************************************************
 * Class：EditTextBlockItem.java  Module：app  Project：CodeFrame
 *
 * Current modified time：2018-10-18 11:04:38
 * Last modified time：2018-10-18 11:04:38
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.app.example.blockitem;

import com.meizu.code.app.example.blocklayout.EditTextBlockLayout;
import com.meizu.code.frame.base.model.delegate.blockitem.DelegateBlockItem;

public class EditTextBlockItem extends DelegateBlockItem<String> {
    public EditTextBlockItem() {
        this("");
    }

    public EditTextBlockItem(String str) {
        super(str);
    }

    @Override
    public Class getBlockLayoutClazz() {
        return EditTextBlockLayout.class;
    }

    @Override
    public boolean isSpanFull() {
        return true;
    }
}
