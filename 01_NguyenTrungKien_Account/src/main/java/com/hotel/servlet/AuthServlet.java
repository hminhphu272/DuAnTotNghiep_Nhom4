package com.hotel.servlet;

import com.hotel.dao.NguoiDungDAO;
import com.hotel.entity.NguoiDung;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {
    private final NguoiDungDAO dao = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/home");
        } else if ("/register".equals(path)) {
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        if ("/register".equals(path)) {
            register(req, resp);
        } else {
            login(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = ParamUtil.getString(req, "email");
        String matKhau = ParamUtil.getString(req, "matKhau");
        NguoiDung user = dao.login(email, matKhau);
        if (user == null) {
            req.setAttribute("message", "Email hoặc mật khẩu không đúng");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String hoTen = ParamUtil.getString(req, "hoTen");
        String email = ParamUtil.getString(req, "email");
        String matKhau = ParamUtil.getString(req, "matKhau");
        String soDienThoai = ParamUtil.getString(req, "soDienThoai");
        if (dao.existsEmail(email)) {
            req.setAttribute("message", "Email đã tồn tại");
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }
        dao.register(hoTen, email, matKhau, soDienThoai);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
