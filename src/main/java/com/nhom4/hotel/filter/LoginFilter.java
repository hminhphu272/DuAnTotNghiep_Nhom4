package com.nhom4.hotel.filter;

import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.AuthUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/bookings", "/booking/*", "/admin/*", "/checkout", "/invoices"})
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        String path = req.getServletPath();

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login?returnUrl=" + req.getRequestURI());
            return;
        }

        if (path.startsWith("/admin") && !AuthUtil.isManager(user)) {
            req.getRequestDispatcher("/views/error/403.jsp").forward(req, resp);
            return;
        }

        if (("/checkout".equals(path) || "/invoices".equals(path)) && !AuthUtil.isStaff(user)) {
            req.getRequestDispatcher("/views/error/403.jsp").forward(req, resp);
            return;
        }

        chain.doFilter(request, response);
    }
}
