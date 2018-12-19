/*
 * ************************************************************
 * Class：AnimationUtils.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-22 21:21:34
 * Last modified time：2018-11-22 21:21:33
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

/**
 * Animation helper
 */
public class AnimationUtils {
    /**
     * 界面渐渐消失动画
     */
    @SuppressLint("ClickableViewAccessibility")
    public static void startViewGradientAnimation(View rootView, long duration) {
        if (!(rootView instanceof ViewGroup)) return;
        final Bitmap bitmap = CodeFrameStaticUiUtils.loadBitmapFromView(rootView);
        final ImageView gradientLayout = new ImageView(rootView.getContext());
        gradientLayout.setImageBitmap(bitmap);
        gradientLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        gradientLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        ((ViewGroup) rootView).addView(gradientLayout);
        gradientLayout.animate()
                .alpha(0.f)
                .setDuration(duration)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ViewParent parent = gradientLayout.getParent();
                        if (parent instanceof ViewGroup) {
                            ((ViewGroup) parent).removeView(gradientLayout);
                        }
                        if (bitmap != null) {
                            bitmap.recycle();
                        }
                    }
                }).start();
    }
}
