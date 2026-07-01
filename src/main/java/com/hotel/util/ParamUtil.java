package com.hotel.util;

import jakarta.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class ParamUtil {
    public static int getInt(HttpServletRequest request, String name) {
        return Integer.parseInt(request.getParameter(name));
    }

    public static BigDecimal getBigDecimal(HttpServletRequest request, String name) {
        return new BigDecimal(request.getParameter(name));
    }

    public static String getString(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value == null ? "" : value.trim();
    }
}
