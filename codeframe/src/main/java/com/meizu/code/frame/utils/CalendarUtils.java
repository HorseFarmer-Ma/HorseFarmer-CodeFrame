/*
 * ************************************************************
 * Class：CalendarUtils.java  Module：codeframe  Project：CodeFrame
 *
 * Current modified time：2018-11-15 10:30:36
 * Last modified time：2018-11-15 10:30:36
 *
 * Author：maxueming
 *
 * Copyright (c) 2017 - 2018
 * ***********************************************************
 */

package com.meizu.code.frame.utils;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A Tool to get Calendar message
 * <p>
 * Author: maxueming
 */
public class CalendarUtils {
    private static final ThreadLocal<Calendar> CALENDAR;    // Avoid thread sync problem
    private static long mUpdateCurrentDateTime;
    private static DateBean sCurrentDayBean;

    static {
        CALENDAR = new ThreadLocal<Calendar>() {
            @Override
            protected Calendar initialValue() {
                return Calendar.getInstance();
            }
        };
    }

    /**
     * Get today DateBean
     */
    public static DateBean getTodayBean() {
        long zeroTime = TimeUtils.getZeroTime();
        if (zeroTime - mUpdateCurrentDateTime > 0 || zeroTime - mUpdateCurrentDateTime < -TimeUtils.MILLISECOND_OF_A_DAY || sCurrentDayBean == null) {
            mUpdateCurrentDateTime = System.currentTimeMillis();
            sCurrentDayBean = getDateBeanByDate(new Date());
        }
        return sCurrentDayBean;
    }

    /**
     * get {@link DateBean} by date
     */
    public static DateBean getDateBeanByDate(Date date) {
        Calendar calendar = CALENDAR.get();
        calendar.setTime(date);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int currentWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int diff = currentWeek - currentDay % 7;
        int index = diff > 0 ? currentDay + diff : currentDay + diff + 6;
        return new DateBean(index, currentDay, currentMonth, currentYear, currentWeek);
    }

    /**
     * Get Calendar detail
     */
    public static List<DateBean> getCurMonthDayList(DateBean dateBean) {
        Calendar calendar = CALENDAR.get();
        calendar.set(dateBean.getYear(), dateBean.getMonth() - 1, dateBean.getDay() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);        // Move to first day
        int sizeOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);
        int firstIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        List<DateBean> dateBeanList = new ArrayList<>(sizeOfMonth);
        for (int i = 0; i < sizeOfMonth; i++) {
            int index = firstIndex + i;
            int currentWeek = (firstIndex + i) % 7;
            dateBeanList.add(new DateBean(index, i + 1, currentMonth, currentYear, currentWeek));
        }
        return dateBeanList;
    }

    /**
     * Determine whether it's the same day
     * 0：yes， -1：before， 1：after
     */
    public static int isToday(@NonNull DateBean dateBean) {
        Calendar calendar = CALENDAR.get();
        calendar.set(dateBean.getYear(), dateBean.getMonth(), dateBean.getDay());
        Date compareDate = calendar.getTime();
        return compareDate.compareTo(new Date());
    }

    /**
     * Date Message
     */
    public static class DateBean {
        private int mIndex;     // range from 0
        private int mDay;       // range form 1
        private int mMonth;     // range from 1
        private int mYear;
        private int mWeek;      // range form 0

        public DateBean() {
        }

        public DateBean(int index, @IntRange(from = 1, to = 31) int day, @IntRange(from = 1, to = 12) int month, int year, @IntRange(from = 1, to = 7) int week) {
            mIndex = index;
            mDay = day;
            mMonth = month;
            mYear = year;
            mWeek = week;
        }

        public int getIndex() {
            return mIndex;
        }

        public void setIndex(int index) {
            mIndex = index;
        }

        public int getDay() {
            return mDay;
        }

        public void setDay(@IntRange(from = 1, to = 31) int day) {
            mDay = day;
        }

        public int getMonth() {
            return mMonth;
        }

        public void setMonth(@IntRange(from = 1, to = 12) int month) {
            mMonth = month;
        }

        public int getYear() {
            return mYear;
        }

        public void setYear(int year) {
            mYear = year;
        }

        public int getWeek() {
            return mWeek;
        }

        /**
         * @param week 0：express sunday
         */
        public void setWeek(@IntRange(from = 0, to = 6) int week) {
            mWeek = week;
        }

        public DateBean copyOf(DateBean bean) {
            mIndex = bean.getIndex();
            mDay = bean.getDay();
            mMonth = bean.getMonth();
            mYear = bean.getYear();
            mWeek = bean.getWeek();
            return this;
        }
    }
}
