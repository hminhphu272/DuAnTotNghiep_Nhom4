package com.hotel.util;

import com.hotel.entity.NguoiDung;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    public static NguoiDung getUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (NguoiDung) session.getAttribute("user");
    }

    public static boolean isLogin(HttpServletRequest request) {
        return getUser(request) != null;
    }

    public static boolean hasRole(HttpServletRequest request, String role) {
        NguoiDung user = getUser(request);
        return user != null && role.equals(user.getVaiTro());
    }

    public static boolean isStaff(HttpServletRequest request) {
        NguoiDung user = getUser(request);
        return user != null && ("LE_TAN".equals(user.getVaiTro()) || "QUAN_LY".equals(user.getVaiTro()));
    }
}
