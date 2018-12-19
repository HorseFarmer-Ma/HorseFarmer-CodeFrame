/*
 * ************************************************************
 * Class：CloudCacheKeyFactory.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-26 16:07:21
 * Last modified time：2018-11-14 16:21:19
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.frame.fresco;

import android.net.Uri;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.imagepipeline.cache.BitmapMemoryCacheKey;
import com.facebook.imagepipeline.cache.CacheKeyFactory;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.Postprocessor;

public class CloudCacheKeyFactory implements CacheKeyFactory {
    private static volatile CloudCacheKeyFactory sInstance;

    private CloudCacheKeyFactory() {
    }

    public static CloudCacheKeyFactory getInstance() {
        if (sInstance == null) {
            synchronized (CloudCacheKeyFactory.class) {
                if (sInstance == null) {
                    sInstance = new CloudCacheKeyFactory();
                }
            }
        }
        return sInstance;
    }

    public Uri getCacheKeySourceUri(Uri sourceUri) {
        return sourceUri;
    }

    @Override
    public CacheKey getBitmapCacheKey(ImageRequest request, Object callerContext) {
        Postprocessor postprocessor = request.getPostprocessor();
        CacheKey postprocessorCacheKey = null;
        String postprocessorName = null;
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        }
        return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(request.getSourceUri()).toString(),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                postprocessorCacheKey,
                postprocessorName,
                callerContext);
    }

    @Override
    public CacheKey getPostprocessedBitmapCacheKey(ImageRequest request, Object callerContext) {
        Postprocessor postprocessor = request.getPostprocessor();
        CacheKey postprocessorCacheKey = null;
        String postprocessorName = null;
        if (postprocessor != null) {
            postprocessorCacheKey = postprocessor.getPostprocessorCacheKey();
            postprocessorName = postprocessor.getClass().getName();
        }
        return new BitmapMemoryCacheKey(this.getCacheKeySourceUri(request.getSourceUri()).toString(),
                request.getResizeOptions(),
                request.getRotationOptions(),
                request.getImageDecodeOptions(),
                postprocessorCacheKey,
                postprocessorName,
                callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(ImageRequest request, Object callerContext) {
        return getEncodedCacheKey(request, request.getSourceUri(), callerContext);
    }

    @Override
    public CacheKey getEncodedCacheKey(ImageRequest request, Uri sourceUri, Object callerContext) {
        return new SimpleCacheKey(getCacheKeySourceUri(sourceUri).toString());
    }
}
