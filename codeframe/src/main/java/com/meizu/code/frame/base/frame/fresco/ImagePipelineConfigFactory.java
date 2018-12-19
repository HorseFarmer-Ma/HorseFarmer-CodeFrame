/*
 * ************************************************************
 * Class：ImagePipelineConfigFactory.java  Module：codeframe  Project：CodeFrame
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
/*
 * This file provided by Facebook is for non-commercial testing and evaluation
 * purposes only.  Facebook reserves all rights not expressly granted.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * FACEBOOK BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import android.annotation.SuppressLint;
import android.content.Context;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.meizu.code.frame.base.frame.retrofit.ServiceClient;

import okhttp3.OkHttpClient;

public class ImagePipelineConfigFactory {
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";

    @SuppressLint("StaticFieldLeak")
    private static volatile ImagePipelineConfig sOkHttpImagePipelineConfig;

    /**
     * Creates config using OkHttp as network backed.
     */
    public static ImagePipelineConfig getOkHttpImagePipelineConfig(Context context) {
        if (sOkHttpImagePipelineConfig == null) {
            synchronized (ImagePipelineConfigFactory.class) {
                if (sOkHttpImagePipelineConfig == null) {
                    OkHttpClient okHttpClient = ServiceClient.getInstance().getClient();
                    ImagePipelineConfig.Builder configBuilder =
                            OkHttpImagePipelineConfigFactory.newBuilder(context, okHttpClient);
                    configureCaches(configBuilder, context);
                    sOkHttpImagePipelineConfig = configBuilder.build();
                }
            }
        }
        return sOkHttpImagePipelineConfig;
    }

    /**
     * Configures disk and memory cache not to exceed common limits
     */
    private static void configureCaches(
            ImagePipelineConfig.Builder configBuilder,
            Context context) {
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
                Integer.MAX_VALUE,                     // Max entries in the cache
                ConfigConstants.MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
                Integer.MAX_VALUE,                     // Max length of eviction queue
                Integer.MAX_VALUE);                    // Max cache entry size
        configBuilder.setCacheKeyFactory(CloudCacheKeyFactory.getInstance())
                .setBitmapMemoryCacheParamsSupplier(
                        new Supplier<MemoryCacheParams>() {
                            public MemoryCacheParams get() {
                                return bitmapCacheParams;
                            }
                        })
                .setMainDiskCacheConfig(
                        DiskCacheConfig.newBuilder(context)
                                .setBaseDirectoryPath(context.getApplicationContext().getExternalCacheDir())
                                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)
                                .setMaxCacheSize(ConfigConstants.MAX_DISK_CACHE_SIZE)
                                .build())
                .setDownsampleEnabled(true);
        if (context instanceof MemoryTrimmableRegistry) {
            MemoryTrimmableRegistry memoryTrimmableRegistry = (MemoryTrimmableRegistry) context;
            configBuilder.setMemoryTrimmableRegistry(memoryTrimmableRegistry);
        }
    }
}
