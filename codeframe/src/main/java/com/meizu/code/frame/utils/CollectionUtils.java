package com.meizu.code.frame.utils;

import java.util.Collection;

/**
 * 数组协助类
 * <p>
 * Created by mxm on 23/02/18.
 */
public class CollectionUtils {
    public static <T1, T2> boolean isEqual(Collection<T1> t1, Collection<T2> t2) {
        return t1 == t2 || !(t1 == null || t2 == null) && t1.size() == t2.size() && t1.equals(t2);
    }
}
