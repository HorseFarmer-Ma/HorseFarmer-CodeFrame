/*
 * ************************************************************
 * Class：ActivityManager.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-16 19:30:29
 * Last modified time：2018-11-16 19:30:29
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Activity manager
 */
public class ActivityManager {
    private final LinkedList<Activity> mActivityList = new LinkedList<>();

    private ActivityManager() {
    }

    private static class Holder {
        private static final ActivityManager INSTANCE = new ActivityManager();
    }

    public static ActivityManager getInstance() {
        return Holder.INSTANCE;
    }

    public boolean addActivity(Activity activity) {
        return mActivityList.add(activity);
    }

    public boolean removeActivity(Activity activity) {
        return mActivityList.remove(activity);
    }

    public int getActivityCount() {
        return mActivityList.size();
    }
}
