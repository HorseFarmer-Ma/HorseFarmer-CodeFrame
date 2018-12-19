/*
 * ************************************************************
 * Class：TimeInfoUtil.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 14:44:46
 * Last modified time：2018-11-15 14:44:45
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Time deal method collection
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {
    /**
     * 一分钟中的毫秒数
     */
    public static final long MILLISECOND_OF_A_MINUTE = 60 * 1000;
    /**
     * 一天中的毫秒数
     */
    public static final long MILLISECOND_OF_A_DAY = 24 * 60 * 60 * 1000L;
    /**
     * 一小时的毫秒数
     */
    public static final long MILLISECOND_OF_A_HOUR = 60 * MILLISECOND_OF_A_MINUTE;
    /**
     * 一星期中的毫秒数
     */
    public static final long MILLISECOND_OF_A_WEEK = 7 * MILLISECOND_OF_A_DAY;
    /**
     * 一小时的秒数
     */
    public static final int SECOND_OF_A_HOUR = 60 * 60;

    public static final String TIME_PATTERN_01 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_PATTERN_02 = "yyyyMMddHH";
    public static final String TIME_PATTERN_03 = "yyyyMMdd";

    private static final ThreadLocal<SimpleDateFormat> TIME_FORMAT;
    private static final ThreadLocal<SimpleDateFormat> HOUR_FORMAT;
    private static final ThreadLocal<SimpleDateFormat> DAY_FORMAT;

    static {
        TIME_FORMAT = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(TIME_PATTERN_01);
            }
        };
        HOUR_FORMAT = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(TIME_PATTERN_02);
            }
        };
        DAY_FORMAT = new ThreadLocal<SimpleDateFormat>(){
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat(TIME_PATTERN_03);
            }
        };
    }

    /**
     * Get the time with the format : YYYY-MM-DD HH:MM:SS
     *
     * @return String
     */
    public static String getFormatTime() {
        return TIME_FORMAT.get().format(new Date());
    }

    /**
     * Get the time with the format : YYYY-MM-DD HH:MM:SS
     *
     * @return String
     */
    public static String getFormatTime(long timeMills) {
        return TIME_FORMAT.get().format(new Date());
    }

    /**
     * s
     * Get the daytime with the format : YYYYMMDDHH
     *
     * @return String
     */

    public static String getFormatHour() {
        return HOUR_FORMAT.get().format(new Date());
    }

    /**
     * Get the daytime with the format : YYYYMMDDHH
     *
     * @return String
     */
    public static String getFormatHour(long timeMills) {
        return HOUR_FORMAT.get().format(new Date());
    }

    /**
     * Get the date with the format : YYYYMMDD
     *
     * @return String
     */
    public static String getFormatDate() {
        return DAY_FORMAT.get().format(new Date());
    }

    /**
     * Get the date with the format : YYYYMMDD
     *
     * @return String
     */
    public static String getFormatDate(long timeMills) {
        return DAY_FORMAT.get().format(new Date(timeMills));
    }

    /**
     * Get the system time(Millisecond)
     *
     * @return long
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    /**
     * zero time
     *
     * @return long
     */
    public static long getZeroTime() {
        return System.currentTimeMillis() / MILLISECOND_OF_A_DAY * MILLISECOND_OF_A_DAY - TimeZone.getDefault().getRawOffset();
    }

    /**
     * compare inputdate with today, accurate to day
     *
     * @return int
     */
    public static int compareWithToday(Date inputDate) {
        Date inputDateDay = new Date(inputDate.getTime() / MILLISECOND_OF_A_DAY * MILLISECOND_OF_A_DAY);
        Date currentDateDay = new Date(getCurrentTime() / MILLISECOND_OF_A_DAY * MILLISECOND_OF_A_DAY);
        return inputDateDay.compareTo(currentDateDay);
    }
}
