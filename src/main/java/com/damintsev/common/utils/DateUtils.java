package com.damintsev.common.utils;

import com.google.gwt.user.datepicker.client.CalendarUtil;

import java.util.Date;

/**
 * Created by ebolgar at 03.04.13 12:34
 * Утилиты для работы с датами
 */
public class DateUtils {
    public static void add(Date date, int days) {
        CalendarUtil.addDaysToDate(date, days);
    }

    public static void addMinutes(Date date, int minutes) {
        date.setTime(date.getTime() + minutes * 60 * 1000);
    }

    public static void setTime(Date date) {
        setTime(date, 0);
    }

    public static void setTime(Date date, int hour) {
        setTime(date, hour, 0);
    }

    public static void setTime(Date date, int hour, int minutes) {
        setTime(date, hour, minutes, 0);
    }

    public static void setTime(Date date, int hour, int minutes, int seconds) {
        date.setHours(hour);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        date.setTime(date.getTime() - date.getTime() % 1000);
    }

    public static int getHour(Date date) {
        return date.getHours();
    }

    public static void setDay(Date date, int day) {
        date.setDate(day);
    }

    public static void nextMonth(Date date) {
        int month = date.getMonth();
        while (date.getMonth() == month) add(date, 1);
    }

    public static Date getStartTimeMonth() {
        Date date = new Date();
        setTime(date);
        setDay(date, 1);
        return date;
    }

    public static Date getNextMonth() {
        Date date = new Date();
        nextMonth(date);
        return date;
    }

    public static boolean between(Date date, Date begin, Date finish) {
        if (begin == null) return (finish == null) || (date.before(finish));
        else if (finish == null) return !date.before(begin);
        else return (!date.before(begin)) && (date.before(finish));
    }
}
