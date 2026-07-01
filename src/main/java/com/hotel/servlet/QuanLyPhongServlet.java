package com.hotel.servlet;

import com.hotel.dao.LoaiPhongDAO;
import com.hotel.dao.PhongDAO;
import com.hotel.entity.Phong;
import com.hotel.util.AuthUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/admin/rooms", "/admin/room/create", "/admin/room/edit", "/admin/room/delete"})
public class QuanLyPhongServlet extends HttpServlet {
    private final PhongDAO phongDAO = new PhongDAO();
    private final LoaiPhongDAO loaiPhongDAO = new LoaiPhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.hasRole(req, "QUAN_LY")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        if ("/admin/room/create".equals(path)) {
            form(req, resp, null);
        } else if ("/admin/room/edit".equals(path)) {
            int id = ParamUtil.getInt(req, "id");
            form(req, resp, phongDAO.findById(id));
        } else if ("/admin/room/delete".equals(path)) {
            int id = ParamUtil.getInt(req, "id");
            if (!phongDAO.hasBooking(id)) {
                phongDAO.delete(id);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/rooms");
        } else {
            req.setAttribute("rooms", phongDAO.findAll(req.getParameter("keyword")));
            req.getRequestDispatcher("/views/phong/manager-list.jsp").forward(req, resp);
        }
    }

    private void form(HttpServletRequest req, HttpServletResponse resp, Phong room) throws ServletException, IOException {
        req.setAttribute("room", room);
        req.setAttribute("types", loaiPhongDAO.findAll());
        req.getRequestDispatcher("/views/phong/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!AuthUtil.hasRole(req, "QUAN_LY")) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        String soPhong = ParamUtil.getString(req, "soPhong");
        int loaiPhongId = ParamUtil.getInt(req, "loaiPhongId");
        java.math.BigDecimal giaPhong = ParamUtil.getBigDecimal(req, "giaPhong");
        String trangThai = ParamUtil.getString(req, "trangThai");
        String ghiChu = ParamUtil.getString(req, "ghiChu");
        if ("/admin/room/edit".equals(path)) {
            phongDAO.update(ParamUtil.getInt(req, "id"), soPhong, loaiPhongId, giaPhong, trangThai, ghiChu);
        } else {
            phongDAO.insert(soPhong, loaiPhongId, giaPhong, trangThai, ghiChu);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/rooms");
    }
}
