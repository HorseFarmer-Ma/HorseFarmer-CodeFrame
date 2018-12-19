/*
 * ************************************************************
 * Class：FrescoImageLoadManager.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-26 16:07:21
 * Last modified time：2018-11-26 15:58:18
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.frame.fresco;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.meizu.code.frame.base.frame.common.log.Logger;

public class FrescoImageLoadManager {
    private static final String TAG = "FrescoImageLoadManager";

    public static void bindImageView(CloudDraweeView view, String uri) {
        bindImageView(view, uri, null);
    }

    public static void bindImageView(CloudDraweeView view, String uri, Drawable placeHolder) {
        bindImageView(view, uri, placeHolder, null);
    }

    public static void bindImageView(CloudDraweeView view, String uri, Drawable
            placeHolder, Postprocessor postProcessor) {
        bindImageView(view, uri, placeHolder, postProcessor, true, null, 0, 0);
    }

    public static void bindImageViewNoAutoPlay(CloudDraweeView view, String uri, Drawable
            placeHolder, int width, int height) {
        bindImageView(view, uri, placeHolder, null, false, null, width, height);
    }

    public static void bindImageView(CloudDraweeView view, String uri, Drawable placeHolder,
                                     Postprocessor postProcessor, boolean autoPlay,
                                     CloudDraweeView.OnImageLoadListener imageLoadListener, int width, int height) {
        try {
            if (placeHolder != null) {
                view.setPlaceHolder(placeHolder);
            }
            if (uri == null) {
                uri = "";
            }

            view.setLoadListener(imageLoadListener);

            ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri));
            if (width > 0 && height > 0) {
                builder.setResizeOptions(new ResizeOptions(width, height));
            }
            if (postProcessor != null) {
                builder.setPostprocessor(postProcessor);
            }
            DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(builder.build())
                    .setOldController(view.getController())
                    .setControllerListener(view.getListener())
                    .setAutoPlayAnimations(autoPlay)
                    .build();

            view.setController(draweeController);
        } catch (Exception e) {
            //freso destroyed;
            Logger.logE(TAG, "bindImageView:" + e);
        }
    }
}
