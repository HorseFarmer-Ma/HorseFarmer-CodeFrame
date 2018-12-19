/*
 * ************************************************************
 * Class：FrescoManager.java  Module：codeframe  Project：CodeFrame
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

import android.content.Context;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.meizu.code.frame.utils.CodeFrameUtils;

public class FrescoManager {
    private static volatile FrescoManager mInstance;

    private FrescoManager() {
        init(CodeFrameUtils.getInstance().getGlobalContext());
    }

    public static FrescoManager getInstance() {
        if (mInstance == null) {
            synchronized (FrescoManager.class) {
                if (mInstance == null) {
                    mInstance = new FrescoManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * init Fresco
     * net request use: okhttp2
     */
    public void init(Context context) {
        ImagePipelineConfig config = ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(context);
        Fresco.initialize(context, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
    }
}
