/*
 * ************************************************************
 * Class：ConfigConstants.java  Module：codeframe  Project：CodeFrame
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

import com.facebook.common.util.ByteConstants;

public class ConfigConstants {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();

    public static final int MAX_DISK_CACHE_SIZE = 200 * ByteConstants.MB;
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 10;
}
