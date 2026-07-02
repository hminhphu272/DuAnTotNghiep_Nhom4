package com.nhom4.hotel.servlet;

import com.nhom4.hotel.dao.DatPhongDAO;
import com.nhom4.hotel.dao.PhongDAO;
import com.nhom4.hotel.entity.DatPhong;
import com.nhom4.hotel.entity.NguoiDung;
import com.nhom4.hotel.util.AuthUtil;
import com.nhom4.hotel.util.DateUtil;
import com.nhom4.hotel.util.ParamUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/booking/create", "/bookings", "/booking/status"})
public class BookingServlet extends HttpServlet {
    private final DatPhongDAO datPhongDAO = new DatPhongDAO();
    private final PhongDAO phongDAO = new PhongDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        if ("/booking/create".equals(path)) {
            Integer roomId = ParamUtil.getInt(req, "roomId");
            req.setAttribute("room", roomId == null ? null : phongDAO.findById(roomId));
            req.getRequestDispatcher("/views/bookings/form.jsp").forward(req, resp);
            return;
        }
        if (AuthUtil.isStaff(user)) {
            req.setAttribute("bookings", datPhongDAO.findAll(ParamUtil.getString(req, "keyword"), ParamUtil.getString(req, "status")));
        } else {
            req.setAttribute("bookings", datPhongDAO.findByCustomer(user.getId()));
        }
        req.getRequestDispatcher("/views/bookings/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/booking/status".equals(path)) {
            updateStatus(req, resp);
            return;
        }
        createBooking(req, resp);
    }

    private void createBooking(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        Integer roomId = ParamUtil.getInt(req, "roomId");
        LocalDate ngayNhan = ParamUtil.getDate(req, "ngayNhan");
        LocalDate ngayTra = ParamUtil.getDate(req, "ngayTra");
        if (roomId == null || ngayNhan == null || ngayTra == null || !ngayTra.isAfter(ngayNhan)) {
            req.setAttribute("error", "Ngày nhận và ngày trả phòng không hợp lệ.");
            req.setAttribute("room", roomId == null ? null : phongDAO.findById(roomId));
            req.getRequestDispatcher("/views/bookings/form.jsp").forward(req, resp);
            return;
        }
        if (datPhongDAO.hasOverlap(roomId, ngayNhan, ngayTra)) {
            req.setAttribute("error", "Phòng đã được đặt trong thời gian này.");
            req.setAttribute("room", phongDAO.findById(roomId));
            req.getRequestDispatcher("/views/bookings/form.jsp").forward(req, resp);
            return;
        }
        DatPhong booking = new DatPhong();
        booking.setKhachHangId(user.getId());
        booking.setPhongId(roomId);
        booking.setNgayNhan(DateUtil.sqlDate(ngayNhan));
        booking.setNgayTra(DateUtil.sqlDate(ngayTra));
        datPhongDAO.insert(booking);
        phongDAO.updateStatus(roomId, "Đã đặt");
        resp.sendRedirect(req.getContextPath() + "/bookings");
    }

    private void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        NguoiDung user = AuthUtil.getUser(req.getSession(false));
        Integer id = ParamUtil.getInt(req, "id");
        String action = ParamUtil.getString(req, "action");
        DatPhong booking = id == null ? null : datPhongDAO.findById(id);
        if (booking == null) {
            resp.sendRedirect(req.getContextPath() + "/bookings");
            return;
        }
        if (!AuthUtil.isStaff(user) && booking.getKhachHangId() != user.getId()) {
            req.getRequestDispatcher("/views/error/403.jsp").forward(req, resp);
            return;
        }
        if ("confirm".equals(action) && AuthUtil.isStaff(user)) {
            datPhongDAO.updateStatus(id, "Đã xác nhận");
        } else if ("checkin".equals(action) && AuthUtil.isStaff(user)) {
            datPhongDAO.updateStatus(id, "Đã nhận phòng");
            phongDAO.updateStatus(booking.getPhongId(), "Đang sử dụng");
        } else if ("cancel".equals(action)) {
            datPhongDAO.updateStatus(id, "Đã hủy");
            phongDAO.updateStatus(booking.getPhongId(), "Trống");
        }
        resp.sendRedirect(req.getContextPath() + "/bookings");
    }
}
