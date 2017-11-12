package com.meizu.code.frame.common;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池管理
 *
 * Created by mxm on 12/11/17.
 */

public class ThreadPoolServiceHelper {

    private static final int THREADS_COUNT = Math.min(4, Math.max(6, CodeFrameStaticUtils.getNumberOfCPUCores()));
    private static ThreadPoolServiceHelper mInstance;
    private ExecutorService mExecutor;

    public ThreadPoolServiceHelper() {
        mExecutor = Executors.newFixedThreadPool(THREADS_COUNT);
    }

    public static ThreadPoolServiceHelper getInstance() {
        synchronized (ThreadPoolServiceHelper.class) {
            if (mInstance == null) {
                synchronized (ThreadPoolServiceHelper.class) {
                    mInstance = new ThreadPoolServiceHelper();
                }
            }
        }
        return mInstance;
    }

    public void init() {
        addTask(initLogger());
    }

    public void addTask(Runnable runnable) {
        mExecutor.submit(runnable);
    }

    public void addTask(Runnable... runnable) {
        for (int i = 0; i < runnable.length; i++) {
            addTask(runnable[i]);
        }
    }

    private Runnable initLogger() {
        return new Runnable() {
            @Override
            public void run() {
                Logger.addLogAdapter(new AndroidLogAdapter());
                FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                        .showThreadInfo(false)  //是否选择显示线程信息，默认为true
                        .methodCount(0)         //方法数显示多少行，默认2行
                        .methodOffset(5)        //隐藏方法内部调用到偏移量，默认5
                        .tag("CodeFrame")   //自定义TAG全部标签，默认PRETTY_LOGGER
                        .build();
                Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
            }
        };
    }
}
