package com.hotel.servlet;

import com.hotel.dao.LoaiPhongDAO;
import com.hotel.util.AuthUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/loai-phong")
public class LoaiPhongServlet extends HttpServlet {
    private final LoaiPhongDAO dao = new LoaiPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.hasRole(req, "QUAN_LY")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("list", dao.findAll());
        req.getRequestDispatcher("/views/phong/loaiphong.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!AuthUtil.hasRole(req, "QUAN_LY")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        dao.insert(
            ParamUtil.getString(req, "tenLoai"),
            ParamUtil.getString(req, "moTa"),
            ParamUtil.getInt(req, "sucChua"),
            ParamUtil.getBigDecimal(req, "giaCoBan")
        );
        resp.sendRedirect(req.getContextPath() + "/admin/loai-phong");
    }
}
