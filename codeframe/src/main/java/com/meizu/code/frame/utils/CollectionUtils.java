package com.meizu.code.frame.utils;

import android.text.TextUtils;

import java.util.ArrayList;

/**
 * 数组协助类
 * <p>
 * Created by mxm on 23/02/18.
 */
public class CollectionUtils {
    public static <T1, T2> boolean isEqual(T1 t1, T2 t2) {
        return !(t1 == null || t2 == null) && (t1 == t2);
    }
}
