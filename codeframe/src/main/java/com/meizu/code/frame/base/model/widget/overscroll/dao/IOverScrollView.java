/*
 * ************************************************************
 * Class：IOverScrollView.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 10:20:39
 * Last modified time：2018-11-08 09:58:10
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.dao;

import android.content.Context;
import android.view.View;

public interface IOverScrollView {

    Context getContext();

    /**
     * 获取目标操作View
     */
    View getTargetView();

    /**
     * Check if child view can be scrolled vertically in a certain direction.
     *
     * @param direction Negative to check scrolling up, positive to check scrolling down.
     * @return true if this view can be scrolled in the specified direction, false otherwise.
     */
    boolean canChildScrollY(int direction);

    int getScrollY();

    void overYFling(int vy, int minY, int maxY);

    int getMaxOverFlingHeight();
}
