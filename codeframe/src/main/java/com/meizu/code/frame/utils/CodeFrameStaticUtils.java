package com.meizu.code.frame.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Interpolator;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

/**
 * 公共方法
 * <p>
 * Created by mxm on 12/11/17.
 */

public class CodeFrameStaticUtils {
    private CodeFrameStaticUtils() {
    }

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
    public static Activity scanActivityFromView(View view) {
        Context context = view.getContext();
        return scanActivityFromContext(context);
    }

    public static Activity scanActivityFromContext(Context context) {
        if (context instanceof Activity) return (Activity) context;
        while (context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
            if (context instanceof Activity) return (Activity) context;
        }
        return null;
    }

    public static boolean checkActivityIsAlive(Activity activity) {
        return activity != null && !activity.isFinishing() && !activity.isDestroyed();
    }

    public static Interpolator getPathInterpolator(float controlX1, float controlY1, float controlX2, float controlY2) {
        return PathInterpolatorCompat.create(controlX1, controlY1, controlX2, controlY2);
    }

    /**
     * 将Object转化为字符串
     *
     * @param object 待转换对象
     * @return 转换值
     */
    public static String toString(Object object) {
        if (object == null) {
            return "null";
        }
        if (!object.getClass().isArray()) {
            return object.toString();
        }
        if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        }
        if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        }
        if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        }
        if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        }
        if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        }
        if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        }
        if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        }
        if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        }
        if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        }
        return "Couldn't find a correct type for the object";
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get Current Process Name
     */
    public static String getCurrentProcessName(Context context) {
        int pid = android.os.Process.myPid();
        String processName = "";
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                if (process.pid == pid) {
                    processName = process.processName;
                    break;
                }
            }
        }
        return processName;
    }
}
