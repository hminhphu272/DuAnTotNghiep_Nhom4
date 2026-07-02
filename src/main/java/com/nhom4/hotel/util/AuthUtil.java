package com.nhom4.hotel.util;

import com.nhom4.hotel.entity.NguoiDung;
import jakarta.servlet.http.HttpSession;

public class AuthUtil {
    public static final String SESSION_USER = "authUser";

    public static NguoiDung getUser(HttpSession session) {
        if (session == null) return null;
        Object value = session.getAttribute(SESSION_USER);
        return value instanceof NguoiDung ? (NguoiDung) value : null;
    }

    public static boolean isLogged(HttpSession session) {
        return getUser(session) != null;
    }

    public static boolean isManager(NguoiDung user) {
        return user != null && "Quản lý".equals(user.getVaiTro());
    }

    public static boolean isStaff(NguoiDung user) {
        return user != null && ("Lễ tân".equals(user.getVaiTro()) || "Quản lý".equals(user.getVaiTro()));
    }
}
