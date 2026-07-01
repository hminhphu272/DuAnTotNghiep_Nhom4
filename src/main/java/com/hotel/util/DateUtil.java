package com.hotel.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static Date toDate(String value) {
        return Date.valueOf(value);
    }

    public static long days(Date start, Date end) {
        LocalDate s = start.toLocalDate();
        LocalDate e = end.toLocalDate();
        long days = ChronoUnit.DAYS.between(s, e);
        return days <= 0 ? 1 : days;
    }
}
