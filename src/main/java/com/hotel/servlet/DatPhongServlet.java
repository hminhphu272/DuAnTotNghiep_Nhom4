package com.hotel.servlet;

import com.hotel.dao.DatPhongDAO;
import com.hotel.dao.PhongDAO;
import com.hotel.entity.NguoiDung;
import com.hotel.util.AuthUtil;
import com.hotel.util.DateUtil;
import com.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet({"/booking/create", "/booking/my", "/booking/cancel"})
public class DatPhongServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NguoiDung user = AuthUtil.getUser(req);
        if (user == null || !"KHACH_HANG".equals(user.getVaiTro())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String path = req.getServletPath();
        if ("/booking/create".equals(path)) {
            int phongId = ParamUtil.getInt(req, "phongId");
            req.setAttribute("room", phongDAO.findById(phongId));
            req.getRequestDispatcher("/views/datphong/form.jsp").forward(req, resp);
        } else if ("/booking/cancel".equals(path)) {
            datPhongDAO.updateStatus(ParamUtil.getInt(req, "id"), "DA_HUY");
            resp.sendRedirect(req.getContextPath() + "/booking/my");
        } else {
            req.setAttribute("list", datPhongDAO.findByUser(user.getId()));
            req.getRequestDispatcher("/views/datphong/my-bookings.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        NguoiDung user = AuthUtil.getUser(req);
        if (user == null || !"KHACH_HANG".equals(user.getVaiTro())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        int phongId = ParamUtil.getInt(req, "phongId");
        Date ngayNhan = DateUtil.toDate(ParamUtil.getString(req, "ngayNhan"));
        Date ngayTra = DateUtil.toDate(ParamUtil.getString(req, "ngayTra"));
        if (!datPhongDAO.isRoomAvailable(phongId, ngayNhan, ngayTra)) {
            req.setAttribute("message", "Phòng đã được đặt trong khoảng thời gian này");
            req.setAttribute("room", phongDAO.findById(phongId));
            req.getRequestDispatcher("/views/datphong/form.jsp").forward(req, resp);
            return;
        }
        datPhongDAO.insert(user.getId(), phongId, ngayNhan, ngayTra);
        resp.sendRedirect(req.getContextPath() + "/booking/my");
    }
}
