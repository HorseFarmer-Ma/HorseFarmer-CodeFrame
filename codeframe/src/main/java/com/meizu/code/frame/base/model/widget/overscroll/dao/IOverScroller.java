/*
 * ************************************************************
 * Class：IAutoScrollStrategy.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 15:37:08
 * Last modified time：2018-11-08 15:37:08
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.dao;

/**
 * 自动滑动策略接口
 */
public interface IOverScroller {

    void setFriction(float friction);

    boolean isFinished();

    void forceFinished(boolean finished);

    int getCurrX();

    int getCurrY();

    float getCurrVelocity();

    int getStartX();

    int getStartY();

    int getFinalX();

    int getFinalY();

    boolean computeScrollOffset();

    void startScroll(int startX, int startY, int dx, int dy);

    void startScroll(int startX, int startY, int dx, int dy, int duration);

    boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY);

    void fling(int startX, int startY, int velocityX, int velocityY,
               int minX, int maxX, int minY, int maxY);

    void fling(int startX, int startY, int velocityX, int velocityY,
               int minX, int maxX, int minY, int maxY, int overX, int overY);

    void notifyHorizontalEdgeReached(int startX, int finalX, int overX);

    void notifyVerticalEdgeReached(int startY, int finalY, int overY);

    boolean isOverScrolled();

    void abortAnimation();
}
