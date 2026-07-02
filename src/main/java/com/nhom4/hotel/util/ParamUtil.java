package com.nhom4.hotel.util;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ParamUtil {
    public static String getString(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return value == null ? "" : value.trim();
    }

    public static Integer getInt(HttpServletRequest req, String name) {
        try {
            String value = getString(req, name);
            return value.isEmpty() ? null : Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static BigDecimal getDecimal(HttpServletRequest req, String name) {
        try {
            String value = getString(req, name);
            return value.isEmpty() ? BigDecimal.ZERO : new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    public static LocalDate getDate(HttpServletRequest req, String name) {
        try {
            String value = getString(req, name);
            return value.isEmpty() ? null : LocalDate.parse(value);
        } catch (Exception e) {
            return null;
        }
    }
}
