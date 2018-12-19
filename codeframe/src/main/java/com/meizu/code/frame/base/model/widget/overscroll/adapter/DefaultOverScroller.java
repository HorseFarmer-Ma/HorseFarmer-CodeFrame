/*
 * ************************************************************
 * Class：DefaultOverScrollAdapter.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-08 15:51:19
 * Last modified time：2018-11-08 15:51:19
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.model.widget.overscroll.adapter;

import android.content.Context;
import android.view.animation.Interpolator;

import com.meizu.code.frame.base.model.widget.overscroll.dao.IOverScroller;

public class DefaultOverScroller implements IOverScroller {
    private final OverScroller mOverScroller;

    public DefaultOverScroller(Context context) {
        this(context, null);
    }

    public DefaultOverScroller(Context context, Interpolator interpolator) {
        mOverScroller = new OverScroller(context, interpolator);
    }

    @Override
    public void setFriction(float friction) {
        mOverScroller.setFriction(friction);
    }

    @Override
    public boolean isFinished() {
        return mOverScroller.isFinished();
    }

    @Override
    public void forceFinished(boolean finished) {
        mOverScroller.forceFinished(finished);
    }

    @Override
    public int getCurrX() {
        return mOverScroller.getCurrX();
    }

    @Override
    public int getCurrY() {
        return mOverScroller.getCurrY();
    }

    @Override
    public float getCurrVelocity() {
        return mOverScroller.getCurrVelocity();
    }

    @Override
    public int getStartX() {
        return mOverScroller.getStartX();
    }

    @Override
    public int getStartY() {
        return mOverScroller.getStartY();
    }

    @Override
    public int getFinalX() {
        return mOverScroller.getFinalX();
    }

    @Override
    public int getFinalY() {
        return mOverScroller.getFinalY();
    }

    @Override
    public boolean computeScrollOffset() {
        return mOverScroller.computeScrollOffset();
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        mOverScroller.startScroll(startX, startY, dx, dy);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        mOverScroller.startScroll(startX, startY, dx, dy, duration);
    }

    @Override
    public boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY) {
        return mOverScroller.springBack(startX, startY, minX, maxX, minY, maxY);
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY) {
        mOverScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY);
    }

    @Override
    public void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY) {
        mOverScroller.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY, overX, overY);
    }

    @Override
    public void notifyHorizontalEdgeReached(int startX, int finalX, int overX) {
        mOverScroller.notifyHorizontalEdgeReached(startX, finalX, overX);
    }

    @Override
    public void notifyVerticalEdgeReached(int startY, int finalY, int overY) {
        mOverScroller.notifyVerticalEdgeReached(startY, finalY, overY);
    }

    @Override
    public boolean isOverScrolled() {
        return mOverScroller.isOverScrolled();
    }

    @Override
    public void abortAnimation() {
        mOverScroller.abortAnimation();
    }
}
