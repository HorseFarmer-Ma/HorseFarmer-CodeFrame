/*
 * ************************************************************
 * Class：GlideConfigModule.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-27 14:07:57
 * Last modified time：2018-11-27 14:07:57
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.base.frame.glide;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.facebook.common.util.ByteConstants;

@GlideModule
public class GlideConfigModule extends AppGlideModule {

    // 200MB disk cache
    private static final int MAX_DISK_CACHE_SIZE = 200 * ByteConstants.MB;
    // 20% memory default Glide
    private static final float MEMORY_CACHE_MULTIPLE = 1.2F;

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        // cache in internal cache disk
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, MAX_DISK_CACHE_SIZE));

        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context).build();
        int defaultMemoryCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int) (MEMORY_CACHE_MULTIPLE * defaultMemoryCacheSize);
        int customBitmapPoolSize = (int) (MEMORY_CACHE_MULTIPLE * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));
    }

    @Override
    public boolean isManifestParsingEnabled() {
        // disable search module in Manifest
        return false;
    }
}