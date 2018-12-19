package com.meizu.code.frame.utils;

import com.meizu.code.frame.BuildConfig;
import com.meizu.code.frame.base.frame.common.log.Logger;
import com.meizu.code.frame.base.frame.fresco.FrescoManager;
import com.meizu.code.frame.base.frame.manager.DataBaseManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理
 * <p>
 * Created by mxm on 12/11/17.
 */

public class ThreadPoolServiceHelper {

    private static final String TAG = "CodeFrame";
    private static final int THREADS_COUNT = Math.min(2, Math.max(6, CodeFrameStaticUtils.getNumberOfCPUCores()));
    private ExecutorService mExecutor;

    private ThreadPoolServiceHelper() {
        Logger.logD(TAG, "Threads count : " + THREADS_COUNT);
        mExecutor = Executors.newFixedThreadPool(THREADS_COUNT);
    }

    private static class ThreadPoolServiceHolder {
        private static final ThreadPoolServiceHelper HOLDER = new ThreadPoolServiceHelper();
    }

    public static ThreadPoolServiceHelper getInstance() {
        return ThreadPoolServiceHolder.HOLDER;
    }

    public void init() {
        addTask(
                initCommon(),
                initFresco(),
                initDataBase()
        );
    }

    private void addTask(Runnable runnable) {
        mExecutor.submit(runnable);
    }

    public void addTask(List<Runnable> tasks) {
        if (!CollectionUtils.isEmpty(tasks)) {
            for (Runnable task : tasks) {
                addTask(task);
            }
        }
    }

    public void addTask(Runnable... tasks) {
        if (tasks != null && tasks.length != 0) {
            for (Runnable task : tasks) {
                addTask(task);
            }
        }
    }

    private Runnable initCommon() {
        return new Runnable() {
            @Override
            public void run() {
                Logger.setLogEnable(BuildConfig.DEBUG);
            }
        };
    }

    private Runnable initFresco() {
        return new Runnable() {
            @Override
            public void run() {
                FrescoManager.getInstance().init(CodeFrameUtils.getInstance().getGlobalContext());
            }
        };
    }

    private Runnable initDataBase() {
        return new Runnable() {
            @Override
            public void run() {
                DataBaseManager.getInstance().setUpDataBase();
            }
        };
    }
}
