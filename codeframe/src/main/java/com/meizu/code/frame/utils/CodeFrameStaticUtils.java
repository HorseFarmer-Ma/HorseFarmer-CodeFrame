package com.meizu.code.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;

import java.io.File;
import java.io.FileFilter;

/**
 * 公共方法
 * <p>
 * Created by mxm on 12/11/17.
 */

public class CodeFrameStaticUtils {
    public static int getNumberOfCPUCores() {
        int cores;
        try {
            cores = new File("/sys/devices/system/cpu/").listFiles(CPU_FILTER).length;
        } catch (Exception e) {
            cores = 4;
        }
        return cores;
    }

    private static final FileFilter CPU_FILTER = new FileFilter() {
        @Override
        public boolean accept(File pathname) {
            String path = pathname.getName();
            //regex is slow, so checking char by char.
            if (path.startsWith("cpu")) {
                for (int i = 3; i < path.length(); i++) {
                    if (path.charAt(i) < '0' || path.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    };

    /**
     * 从View中提取Activity
     */
    public static Activity getActivityByView(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
            if (context instanceof Activity) return (Activity) context;
        }
        return null;
    }
}
