package com.nhom4.hotel.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static Date sqlDate(LocalDate date) {
        return date == null ? null : Date.valueOf(date);
    }

    public static long nights(LocalDate from, LocalDate to) {
        if (from == null || to == null) return 0;
        return Math.max(1, ChronoUnit.DAYS.between(from, to));
    }
}
