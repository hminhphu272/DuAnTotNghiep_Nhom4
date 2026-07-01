package com.hotel.servlet;

import com.hotel.dao.DatPhongDAO;
import com.hotel.dao.PhongDAO;
import com.hotel.entity.DatPhong;
import com.hotel.util.AuthUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/staff/bookings", "/staff/booking/confirm", "/staff/booking/cancel"})
public class QuanLyDatPhongServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        if ("/staff/booking/confirm".equals(path)) {
            int id = ParamUtil.getInt(req, "id");
            DatPhong dp = datPhongDAO.findById(id);
            datPhongDAO.updateStatus(id, "DA_XAC_NHAN");
            if (dp != null) phongDAO.updateStatus(dp.getPhongId(), "DA_DAT");
            resp.sendRedirect(req.getContextPath() + "/staff/bookings");
        } else if ("/staff/booking/cancel".equals(path)) {
            int id = ParamUtil.getInt(req, "id");
            DatPhong dp = datPhongDAO.findById(id);
            datPhongDAO.updateStatus(id, "DA_HUY");
            if (dp != null) phongDAO.updateStatus(dp.getPhongId(), "TRONG");
            resp.sendRedirect(req.getContextPath() + "/staff/bookings");
        } else {
            req.setAttribute("list", datPhongDAO.findAll());
            req.getRequestDispatcher("/views/datphong/manager-list.jsp").forward(req, resp);
        }
    }
}
