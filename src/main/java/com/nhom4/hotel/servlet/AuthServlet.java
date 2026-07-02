package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.NguoiDungDAO;
import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.AuthUtil;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/register"})
public class AuthServlet extends HttpServlet {
    private final NguoiDungDAO dao = new NguoiDungDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/register".equals(path)) {
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/register".equals(path)) {
            register(req, resp);
        } else {
            login(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = ParamUtil.getString(req, "email");
        String password = ParamUtil.getString(req, "password");
        NguoiDung user = dao.authenticate(email, password);
        if (user == null) {
            req.setAttribute("error", "Email hoặc mật khẩu không đúng.");
            req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
            return;
        }
        req.getSession().setAttribute(AuthUtil.SESSION_USER, user);
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = ParamUtil.getString(req, "email");
        if (dao.findByEmail(email) != null) {
            req.setAttribute("error", "Email đã tồn tại.");
            req.getRequestDispatcher("/views/auth/register.jsp").forward(req, resp);
            return;
        }
        NguoiDung user = new NguoiDung();
        user.setHoTen(ParamUtil.getString(req, "hoTen"));
        user.setEmail(email);
        user.setMatKhau(ParamUtil.getString(req, "password"));
        user.setSoDienThoai(ParamUtil.getString(req, "phone"));
        dao.insertCustomer(user);
        req.setAttribute("success", "Đăng ký thành công. Bạn có thể đăng nhập.");
        req.getRequestDispatcher("/views/auth/login.jsp").forward(req, resp);
    }
}
