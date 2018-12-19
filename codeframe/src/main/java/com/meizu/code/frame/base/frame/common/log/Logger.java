package com.meizu.code.frame.base.frame.common.log;

/**
 * 打印Log
 * <p>
 * Author: maxueming 502048
 * Date: 18-8-1
 */

public class Logger {

    private static volatile Printer sPrinter;

    private Logger() {
        //no instance
    }

    private static Printer getPrinter() {
        if (sPrinter == null) {
            synchronized (Logger.class) {
                if (sPrinter == null) {
                    sPrinter = new LoggerPrinter();
                }
            }
        }
        return sPrinter;
    }

    /**
     * Given tag will be used as tag only once for this method call regardless of the tag that's been
     * set during initialization. After this invocation, the general tag that's been set will
     * be used for the subsequent log calls
     */
    public static Printer t(String tag) {
        return getPrinter().t(tag);
    }

    /**
     * General log function that accepts all configurations as parameter
     */
    public static void logD(String tag, String message, Object... args) {
        t(tag).d(message, args);
    }

    public static void logV(String tag, String message, Object... args) {
        t(tag).v(message, args);
    }

    public static void logI(String tag, String message, Object... args) {
        t(tag).i(message, args);
    }

    public static void logW(String tag, String message, Object... args) {
        t(tag).w(message, args);
    }

    public static void logE(String tag, String message, Object... args) {
        t(tag).e(message, args);
    }

    public static void logF(String tag, String message, Object... args) {
        t(tag).wtf(message, args);
    }

    public static void log(int priority, String tag, String message, Throwable throwable) {
        getPrinter().log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        getPrinter().d(message, args);
    }

    public static void d(Object object) {
        getPrinter().d(object);
    }

    public static void e(String message, Object... args) {
        getPrinter().e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        getPrinter().e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        getPrinter().i(message, args);
    }

    public static void v(String message, Object... args) {
        getPrinter().v(message, args);
    }

    public static void w(String message, Object... args) {
        getPrinter().w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(String message, Object... args) {
        getPrinter().wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(String json) {
        getPrinter().json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(String xml) {
        getPrinter().xml(xml);
    }

    /**
     * Add logger formatStrategy
     */
    public static void addFormatStrategy(FormatStrategy formatStrategy) {
        getPrinter().addFormatStrategy(formatStrategy);
    }

    public static void setLogEnable(boolean isLogEnable) {
        getPrinter().setLogEnable(isLogEnable);
    }

    public static boolean isLogEnable() {
        return getPrinter().isLogEnable();
    }
}
