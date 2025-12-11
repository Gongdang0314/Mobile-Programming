package com.example.team_project;

import java.util.Calendar;

public class DateUtils {

    public static void resetTime(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        Calendar c1 = (Calendar) cal1.clone();
        Calendar c2 = (Calendar) cal2.clone();
        resetTime(c1);
        resetTime(c2);

        return c1.getTimeInMillis() == c2.getTimeInMillis();
    }

    public static long getDaysBetween(Calendar startDate, Calendar targetDate) {
        Calendar start = (Calendar) startDate.clone();
        Calendar target = (Calendar) targetDate.clone();

        resetTime(start);
        resetTime(target);

        long diffMillis = target.getTimeInMillis() - start.getTimeInMillis();
        return diffMillis / (24 * 60 * 60 * 1000);
    }
}
