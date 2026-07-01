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

@WebServlet("/staff/checkin")
public class NhanPhongServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setAttribute("list", datPhongDAO.findByStatus("DA_XAC_NHAN"));
        req.getRequestDispatcher("/views/letan/checkin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!AuthUtil.isStaff(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int id = ParamUtil.getInt(req, "id");
        DatPhong dp = datPhongDAO.findById(id);
        datPhongDAO.updateStatus(id, "DA_NHAN_PHONG");
        if (dp != null) phongDAO.updateStatus(dp.getPhongId(), "DANG_SU_DUNG");
        resp.sendRedirect(req.getContextPath() + "/staff/checkin");
    }
}
